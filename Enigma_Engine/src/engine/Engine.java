package engine;

import dto.DtoMachineSpecification;

import java.util.List;

public interface Engine {
    void loadXml(String path);
    DtoMachineSpecification showMachineDetails();
    String processMessage(String message);
    public void codeManual(String line, String initialRotorsPositions, int reflectorId);
    void codeAuto();
    void resetCode();
    void statistics();
}
