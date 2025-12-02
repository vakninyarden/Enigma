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

    int getNumOfRotors() {
        return numOfRotors;
    }
    int getNumOfReflectors() {
        return numOfReflectors;
    }
    int getNumOfMessages() {
        return numOfMessages;
    }
    DtoCurrentCode getCurrentCode() {
        return currentCode;
    }
    DtoOriginalCode getOriginalCode() {
        return originalCode;
    }

}
