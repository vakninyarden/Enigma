package exception;

public class NotEnoughRotorsException extends FileValidationException {
    public NotEnoughRotorsException(int actualCount) {
        super("At least 3 rotors are required. Provided: " + actualCount);
    }
}
