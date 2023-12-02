package christmas.exception;

public class InputException extends IllegalArgumentException {
    public static final String PREFIX = "[ERROR] ";

    public InputException(String message) {
        super(PREFIX + message);
    }
}
