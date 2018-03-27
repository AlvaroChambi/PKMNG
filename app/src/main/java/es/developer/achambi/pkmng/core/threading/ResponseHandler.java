package es.developer.achambi.pkmng.core.threading;

public abstract class ResponseHandler<T>{
    public abstract void onSuccess( Response<T> data );
    public void onError( Error error ) {
    }
}
