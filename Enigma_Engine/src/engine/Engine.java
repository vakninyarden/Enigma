package engine;

public interface Engine {
    void loadXml(String path);
    void showMachineDetails();
    String processMessage(String message);
    void codeManual();
    void codeAuto();
    void statistics();
}
