package exceptions;

public class InvalidProductBookException extends Exception {
    public InvalidProductBookException(String message) {
        super(message);
    }

    public InvalidProductBookException(String message, Throwable cause) {
        super(message, cause);
    }
}
