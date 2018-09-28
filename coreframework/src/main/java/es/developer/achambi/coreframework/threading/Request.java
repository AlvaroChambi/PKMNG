package es.developer.achambi.coreframework.threading;

public interface Request<T> {
    Response<T> perform() throws Exception;
}
