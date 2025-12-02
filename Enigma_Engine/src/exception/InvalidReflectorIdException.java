package exception;

public class InvalidReflectorIdException extends FileValidationException {
    public InvalidReflectorIdException(String reflectorId) {
        super("Reflector ID '" + reflectorId + "' is invalid. Allowed: I, II, III, IV, V.");
    }
}

