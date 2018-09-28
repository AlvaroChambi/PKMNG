package es.developer.achambi.coreframework.threading;

public class Response<T> {
    private T data;

    public Response( T data ) {
        this.data = data;
    }

    public T getData( ) {
        return data;
    }
}
