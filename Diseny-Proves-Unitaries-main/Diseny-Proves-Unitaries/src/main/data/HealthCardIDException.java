package data;
/**
 * Excepción checada específica para HealthCardID.
 * Se lanza cuando la validación del código CIP falla.
 * 
 * Hereda de Exception (checada, debe ser capturada o declarada).
 */
public class HealthCardIDException extends Exception {
    /**
     * Constructor con mensaje de error.
     * 
     * @param message Descripción del error de validación
     */
    public HealthCardIDException(String message) {
        super(message);
    }
    /**
     * Constructor con mensaje y causa raíz.
     * 
     * @param message Descripción del error
     * @param cause Excepción que causó este error
     */
    public HealthCardIDException(String message, Throwable cause) {
        super(message, cause);
    }
}