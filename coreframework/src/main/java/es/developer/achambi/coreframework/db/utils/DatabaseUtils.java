package es.developer.achambi.coreframework.db.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseUtils {
    public static void copyDataBase(Context context, String dbName) throws IOException {

        InputStream myInput = context.getAssets().open(dbName);
        File file = context.getDatabasePath(dbName);
        if (file.exists()) {
            return;
        }
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        OutputStream myOutput = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);

        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
