package es.developer.achambi.pkmng.core.exception;

public class IllegalIDException extends  IllegalArgumentException {
    public IllegalIDException( int id ) {
        super( "bad id: "+ id
                + " parameter should be higher than 1" );
    }
}