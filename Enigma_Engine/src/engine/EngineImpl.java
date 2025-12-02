package engine;

import enigma.machine.component.machine.EnigmaMachine;


public class EngineImpl implements Engine {
    private EnigmaMachine machine;
    private LoadManager loadManager;
    private StatisticsManager statisticsManager;
    private Repository repository;

    public EngineImpl( LoadManager loadManager ){

        this.loadManager = loadManager;

    }

    @Override
    public void loadXml(String path) {
        /*if(!loadManager.isFileExistsAndIsXml(path)){
            System.out.println("File does not exist or is not an XML file.");
            return;
        }*/
        loadManager.loadXmlToObject(path);
    }

    @Override
    public void showMachineDetails() {

    }

    @Override
    public String processMessage(String message) {
        char[] result = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            result[i] = machine.processLatter(ch);
        }
        return new String(result);
    }

    @Override
    public void codeManual() {

    }

    @Override
    public void codeAuto() {

    }

    @Override
    public void statistics() {

    }
}
