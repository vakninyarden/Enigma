package engine.exception;

public class AbcSizeNotEvenException extends FileValidationException {
    public AbcSizeNotEvenException() {
        super("ABC size must be even.");
    }
}



