package es.developer.achambi.pkmng.core.threading;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainExecutor extends ThreadPoolExecutor {
    private static final long KEEP_ALIVE_TIME = 0;
    private static final int THREAD_NUMBERS = 4;
    private static final Handler MAIN_HANDLER = new Handler( Looper.getMainLooper() );
    private static MainExecutor instance;

    private MainExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                        TimeUnit unit, LinkedBlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void executeRequest(final Request request, final ResponseHandler responseHandler ) {
        execute(new Runnable() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void run() {
                try {
                    postSuccessOnUI( request.perform(), responseHandler );
                } catch (Error e) {
                    postErrorOnUI( e, responseHandler );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postErrorOnUI( final Error error,
                                final ResponseHandler responseHandler ) {
        MAIN_HANDLER.post(new Runnable() {
            @Override
            public void run() {
                responseHandler.onError( error );
            }
        });
    }

    private void postSuccessOnUI( final Response response,
                                  final ResponseHandler responseHandler ) {
        MAIN_HANDLER.post(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                responseHandler.onSuccess( response );
            }
        });
    }

    public static MainExecutor executor() {
        if( instance == null ) {
            instance = new MainExecutor( THREAD_NUMBERS, THREAD_NUMBERS,
                    KEEP_ALIVE_TIME,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
        }
        return instance;
    }
}
