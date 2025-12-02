package engine.exception;

public class NotchOutOfRangeException extends FileValidationException {
    public NotchOutOfRangeException(int rotorId) {
        super("Notch position for rotor " + rotorId + " is out of valid range (must match rotor size).");
    }
}

