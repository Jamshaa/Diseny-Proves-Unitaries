package data;

public class ProductIDException extends Exception {

    public ProductIDException(String message) {
        super(message);
    }

    public ProductIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
