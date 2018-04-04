package es.developer.achambi.pkmng.core.threading;

public abstract class ResponseHandlerDecorator<T> extends ResponseHandler<T> {
    private ResponseHandler<T> responseHandler;

    public ResponseHandlerDecorator( ResponseHandler<T> responseHandler ) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void onSuccess(Response<T> response) {
        responseHandler.onSuccess( response );
    }

    @Override
    public void onError(Error error) {
        responseHandler.onError( error );
    }
}
