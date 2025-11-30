package engine;

import enigma.machine.component.machine.EnigmaMachine;
import enigma.machine.component.rotor.Rotor;

public class EngineImpl implements Engine {
    private EnigmaMachine machine;
    private LoadManager loadManager;
    private StatisticsManager statisticsManager;
    private Repository repository;


    @Override
    public void loadXml(String path) {

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
