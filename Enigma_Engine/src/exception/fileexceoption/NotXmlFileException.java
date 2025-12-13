package exception.fileexceoption;

public class NotXmlFileException extends FileValidationException {
    public NotXmlFileException(String filePath) {
        super("The provided file is not an XML file: " + filePath);
    }
}
