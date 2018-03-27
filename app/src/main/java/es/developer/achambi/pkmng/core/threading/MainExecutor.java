package es.developer.achambi.pkmng.core.threading;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainExecutor extends ThreadPoolExecutor {
    private static final Handler MAIN_HANDLER = new Handler( Looper.getMainLooper() );
    private static MainExecutor instance;

    public MainExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                        TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void executeRequest(final Request request, final ResponseHandler responseHandler ) {
        execute(new Runnable() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void run() {
                try {
                    postSuccessOnUI( request.perform(), responseHandler );
                } catch (Exception e) {
                    postErrorOnUI( (Error) e, responseHandler );
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
            instance = new MainExecutor( 4, 4, 5000, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(10) );
        }
        return instance;
    }
}
