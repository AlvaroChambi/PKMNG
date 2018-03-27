package es.developer.achambi.pkmng.core.threading;

public class Response<T> {
    private T data;

    public Response( T data ) {
        this.data = data;
    }

    public T getData( ) {
        return data;
    }
}
