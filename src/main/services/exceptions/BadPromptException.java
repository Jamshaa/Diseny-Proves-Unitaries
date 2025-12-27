package services.exceptions;

public class BadPromptException extends Exception {
    public BadPromptException(String message) {
        super(message);
    }
}
