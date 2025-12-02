package exception;

public class InvalidRotorIdsException extends FileValidationException {
    public InvalidRotorIdsException() {
        super("Rotor IDs must be unique and sequential starting from 1 without gaps.");
    }
}
