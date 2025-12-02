package exception;

public class ReflectorSelfMappingException extends FileValidationException {
    public ReflectorSelfMappingException(String letter, String reflectorId) {
        super("Reflector '" + reflectorId + "' contains illegal self-mapping: " + letter + " → " + letter);
    }
}
