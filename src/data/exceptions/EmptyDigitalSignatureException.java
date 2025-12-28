package data.exceptions;

public class EmptyDigitalSignatureException extends RuntimeException {
    public EmptyDigitalSignatureException(String message) {
        super(message);
    }
}
