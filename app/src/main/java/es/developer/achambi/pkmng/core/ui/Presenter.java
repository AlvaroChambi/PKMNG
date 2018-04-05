package es.developer.achambi.pkmng.core.ui;

import android.os.Bundle;

import es.developer.achambi.pkmng.core.threading.LifecycleResponseHandler;
import es.developer.achambi.pkmng.core.threading.MainExecutor;
import es.developer.achambi.pkmng.core.threading.Request;
import es.developer.achambi.pkmng.core.threading.ResponseHandler;

public abstract class Presenter {
    private static final String DATA_STATE_SAVED_STATE = "DATA_STATE_SAVED_STATE";
    private Screen screen;
    private DataState dataState;

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt( DATA_STATE_SAVED_STATE, dataState.ordinal() );
    }
    public void onRestoreInstanceState(Bundle bundle) {
        dataState = DataState.values()[bundle.getInt(DATA_STATE_SAVED_STATE)];
    }

    public Presenter( Screen screen ) {
        this();
        this.screen = screen;
    }
    public Presenter(){
        dataState = DataState.EMPTY;
    }

    /**
     * Perform the request in the main executor, response handler will be a lifecycle aware one.
     * If the Presenter doesn't have screen defined this method will do nothing
     * @param request Request to be performed
     * @param responseHandler response handler
     */
    public void request( Request request, ResponseHandler responseHandler ) {
        if( screen != null ) {
            MainExecutor.executor().executeRequest( request, new LifecycleResponseHandler<>(
                    screen.screenLifecycle(), responseHandler
            ));
        }
    }

    public DataState getDataState() {
        return dataState;
    }

    public void setDataState(DataState dataState) {
        this.dataState = dataState;
    }
}
