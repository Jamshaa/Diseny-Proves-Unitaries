package data.Exceptions;

public class InvalidHealthCardIDFormatException extends RuntimeException {
    public InvalidHealthCardIDFormatException(String message) {
        super(message);
    }
}
