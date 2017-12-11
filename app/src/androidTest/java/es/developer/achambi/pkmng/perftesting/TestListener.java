/*
 * Copyright 2015, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.developer.achambi.pkmng.perftesting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import es.developer.achambi.pkmng.perftesting.common.PerfTestingUtils;

/**
 * Initiate pre-test and post-test procedures; including cleaning and moving files from the
 * internal app data directory to the external one.  Also perform writing to test-start and test-end
 * files which are used to indicate whether there was a fatal test exception
 * (e.g. OutOfMemoryException).
 */
public class TestListener extends RunListener {
    private static final String LOG_TAG = "TestListener";
    public static final String TEST_DATA_SUBDIR_NAME = "testdata";

    @Override
    public void testRunStarted(Description description) throws Exception {
        // Cleanup data from past test runs.
        deleteExistingTestFilesInAppData();
        deleteExistingTestFilesInExternalData();

        super.testRunStarted(description);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);

        try {
            Log.w(LOG_TAG, "Test run finished");
            File testRunFinishedFile = PerfTestingUtils.getTestRunFile("testRunComplete.log");
            testRunFinishedFile.createNewFile();

            copyTestFilesToExternalData();

            Log.w(LOG_TAG, "Done copying files to external data directory");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Issue taking all log files after test run", e);
        }
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        super.testFailure(failure);
        logTestFailure(failure);
    }

    /**
     * Move files from the app's internal file location to a location that can be read on retail
     * devices with simple ADB pull commands.
     */
    private void copyTestFilesToExternalData() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        Context appUnderTestContext = PerfTestingUtils.getAppContext();
        File externalAppStorageDir = appUnderTestContext.getExternalFilesDir(null);
        File externalTestFilesStorageDir = new File(externalAppStorageDir, TEST_DATA_SUBDIR_NAME);
        if (!externalTestFilesStorageDir.exists()) {
            if (!externalTestFilesStorageDir.mkdirs()) {
                throw new RuntimeException("Not able to create exportable directory for test data");
            }
        }

        String srcAbsolutePath = PerfTestingUtils.getTestRunDir().getAbsolutePath();
        String destAbsolutePath = externalTestFilesStorageDir.getAbsolutePath();

        Log.w(LOG_TAG, "Moving test data from " + srcAbsolutePath + " to " + destAbsolutePath);

        processBuilder.command("cp", "-r", srcAbsolutePath, destAbsolutePath);
        processBuilder.redirectErrorStream();
        Process process = processBuilder.start();
        process.waitFor();
        if (process.exitValue() != 0) {
            StringBuilder errOutput = new StringBuilder();
            char[] charBuffer = new char[1024];
            int readSize;
            InputStream errorStream = null;
            Reader reader = null;
            try {
                errorStream = process.getInputStream();
                reader = new InputStreamReader(errorStream);
                while ((readSize = reader.read()) > 0) {
                    errOutput.append(charBuffer, 0, readSize);
                }
            } finally {
                if (errorStream != null) try { errorStream.close(); } catch (Exception ignored) {}
                if (reader != null) try { reader.close(); } catch (Exception ignored) {}
            }
            String errorString = errOutput.toString();
            Log.e(LOG_TAG, errorString);
            throw new IOException("Not able to move test data to external storage directory:"
                    + " src=" + srcAbsolutePath + ", dest=" + destAbsolutePath + ", out=" +
                    errorString);
        }
    }

    private void deleteExistingTestFilesInExternalData() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        Context appUnderTestContext = PerfTestingUtils.getAppContext();
        File externalAppStorageDir = appUnderTestContext.getExternalFilesDir(null);
        File externalTestFilesStorageDir = new File(externalAppStorageDir, TEST_DATA_SUBDIR_NAME);
        String destAbsolutePath = externalTestFilesStorageDir.getAbsolutePath();

        processBuilder.command("rm", "-r", destAbsolutePath);
        processBuilder.redirectErrorStream();
        Process process = processBuilder.start();
        process.waitFor();
    }

    private void deleteExistingTestFilesInAppData() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();

        File externalTestFilesStorageDir = getAppDataLogDir();
        String destAbsolutePath = externalTestFilesStorageDir.getAbsolutePath();

        processBuilder.command("rm", "-r", destAbsolutePath);
        processBuilder.redirectErrorStream();
        Process process = processBuilder.start();
        process.waitFor();
    }

    private void logTestFailure(Failure failure) throws IOException, InterruptedException {
        File failureLogFile = PerfTestingUtils.getTestFile(
                failure.getDescription().getClassName(),
                failure.getDescription().getMethodName(), "test.failure.log");

        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        String eol = System.getProperty("line.separator");
        try {
            fileWriter = new FileWriter(failureLogFile);
            printWriter = new PrintWriter(fileWriter);
            printWriter.append(failure.getTestHeader());
            printWriter.append(eol);
            failure.getException().printStackTrace(printWriter);
            printWriter.append(eol);
        } finally {
            if (printWriter != null) { try { printWriter.close(); } catch (Exception ignored) { } }
            if (fileWriter != null) { try { fileWriter.close(); } catch (Exception ignored) { } }
        }
    }

    @NonNull
    private File getAppDataLogDir() {
        Context appUnderTestContext = PerfTestingUtils.getAppContext();
        File externalAppStorageDir = appUnderTestContext.getFilesDir();
        File externalTestFilesStorageDir = new File(externalAppStorageDir, TEST_DATA_SUBDIR_NAME);
        if (!externalTestFilesStorageDir.exists()) {
            externalTestFilesStorageDir.mkdirs();
        }
        return externalTestFilesStorageDir;
    }
}
