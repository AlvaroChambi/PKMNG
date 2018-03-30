package es.developer.achambi.pkmng.core.threading;

public interface Request<T> {
    Response<T> perform() throws Exception;
}
