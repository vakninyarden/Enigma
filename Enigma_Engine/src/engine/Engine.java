package engine;

import dto.DtoMachineSpecification;

public interface Engine {
    void loadXml(String path);
    DtoMachineSpecification showMachineDetails();
    String processMessage(String message);
    void codeManual();
    void codeAuto();
    void statistics();
}
