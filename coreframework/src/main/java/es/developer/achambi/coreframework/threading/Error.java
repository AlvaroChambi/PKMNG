package es.developer.achambi.coreframework.threading;

public class Error extends Exception {
    private String message;

    public Error( Exception exception ) {
        this.message = exception.getMessage();
    }

    public Error( String message ) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
