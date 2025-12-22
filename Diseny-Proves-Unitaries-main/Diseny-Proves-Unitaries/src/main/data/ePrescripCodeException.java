package data;
/**
 * Excepción checada específica para ePrescripCode.
 * Se lanza cuando la validación del código de prescripción falla.
 */
public class ePrescripCodeException extends Exception {
    public ePrescripCodeException(String message) {
        super(message);
    }
    public ePrescripCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}