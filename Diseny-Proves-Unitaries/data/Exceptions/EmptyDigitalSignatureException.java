package data.Exceptions;

public class EmptyDigitalSignatureException extends RuntimeException {
    public EmptyDigitalSignatureException(String message) {
        super(message);
    }
}
