package data;
/**
 * Excepción checada específica para DigitalSignature.
 * Se lanza cuando la validación de la firma digital falla.
 */
public class DigitalSignatureException extends Exception {
    public DigitalSignatureException(String message) {
        super(message);
    }
    public DigitalSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}