package es.developer.achambi.coreframework.threading;

public abstract class ResponseHandler<T>  {
    public abstract void onSuccess( Response<T> response );
    public void onError( Error error ){
    }
}
