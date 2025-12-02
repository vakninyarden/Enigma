package dto;

public class DtoMachineSpecification {
    int numOfRotors;
    int numOfReflectors;
    int numOfMessages;
    DtoCurrentCode currentCode;
    DtoOriginalCode originalCode;

    public DtoMachineSpecification(int numOfRotors, int numOfReflectors, int numOfMessages,
                                   DtoCurrentCode currentCode, DtoOriginalCode originalCode) {
        this.numOfRotors = numOfRotors;
        this.numOfReflectors = numOfReflectors;
        this.numOfMessages = numOfMessages;
        this.currentCode = currentCode;
        this.originalCode = originalCode;
    }

    public int getNumOfRotors() {
        return numOfRotors;
    }
    public int getNumOfReflectors() {
        return numOfReflectors;
    }
    public int getNumOfMessages() {
        return numOfMessages;
    }
    public DtoCurrentCode getCurrentCode() {
        return currentCode;
    }
    public DtoOriginalCode getOriginalCode() {
        return originalCode;
    }

}
