package data;
/**
 * Excepción checada específica para ProductID.
 * Se lanza cuando la validación del código UPC falla.
 */
public class ProductIDException extends Exception {
    public ProductIDException(String message) {
        super(message);
    }
    public ProductIDException(String message, Throwable cause) {
        super(message, cause);
    }
}