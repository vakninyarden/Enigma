package engine;

import bte.component.jaxb.BTEEnigma;
import engine.file.validator.FileValidator;
import engine.file.validator.XmlFileValidator;
import enigma.machine.component.machine.EnigmaMachine;


public class EngineImpl implements Engine {
    private EnigmaMachine machine;
    private LoadManager loadManager;
    private StatisticsManager statisticsManager;
    private Repository repository;


    @Override
    public void loadXml(String path) {
        XmlFileValidator validator = new XmlFileValidator();
        validator.ValidateFilePath(path);

        BTEEnigma bteMachine = loadManager.loadXmlToObject(path);
        validator.ValidateAll(bteMachine);
        repository = new Repository(bteMachine.getABC());
        repository.loadToRepository(bteMachine);
        repository.printRepositoryContents();
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
