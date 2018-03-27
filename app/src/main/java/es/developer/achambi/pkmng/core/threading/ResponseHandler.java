package es.developer.achambi.pkmng.core.threading;

public abstract class ResponseHandler<T>{
    private ResponseHandler<T> responseHandler;
    public void onSuccess( Response<T> data ) {
        responseHandler.onSuccess( data );
    }
    public void onError( Error error ) {
        responseHandler.onError( error );
    }

    public ResponseHandler(){
        responseHandler = new NullResponseHandler();
    }

    public ResponseHandler( ResponseHandler<T> responseHandler ) {
        this.responseHandler = responseHandler;
    }

    private class NullResponseHandler extends ResponseHandler<T> {
        @Override
        public void onSuccess(Response data) {
        }

        @Override
        public void onError(Error error) {
        }
    }
}
