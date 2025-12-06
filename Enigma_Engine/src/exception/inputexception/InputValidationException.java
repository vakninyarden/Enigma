package exception.inputexception;

import exception.fileexceoption.FileValidationException;

public class InputValidationException extends RuntimeException {
    public InputValidationException(String message) {
        super(message);
    }
}
