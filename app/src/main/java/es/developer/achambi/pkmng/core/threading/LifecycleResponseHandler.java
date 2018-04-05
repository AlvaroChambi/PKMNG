package es.developer.achambi.pkmng.core.threading;

import android.arch.lifecycle.Lifecycle;

public class LifecycleResponseHandler<T> extends ResponseHandlerDecorator<T> {
    private Lifecycle lifecycle;

    public LifecycleResponseHandler( Lifecycle lifecycle,  ResponseHandler responseHandler) {
        super(responseHandler);
        this.lifecycle = lifecycle;
    }

    @Override
    public void onSuccess(Response<T> response) {
        if( lifecycle.getCurrentState().isAtLeast( Lifecycle.State.CREATED ) ) {
            super.onSuccess(response);
        }
    }

    @Override
    public void onError(Error error) {
        if( lifecycle.getCurrentState().isAtLeast( Lifecycle.State.CREATED ) ) {
            super.onError(error);
        }
    }
}
