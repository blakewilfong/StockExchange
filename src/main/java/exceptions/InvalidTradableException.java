package exceptions;

public class InvalidTradableException extends Exception {
    public InvalidTradableException(String message) {
        super(message);
    }

    public InvalidTradableException(String message, Throwable cause) {
        super(message, cause);
    }
}
