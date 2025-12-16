package data;

public class DigitalSignatureException extends Exception {

    public DigitalSignatureException(String message) {
        super(message);
    }

    public DigitalSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}
