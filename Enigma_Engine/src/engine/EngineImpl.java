package engine;
import dto.*;
import bte.component.jaxb.BTEEnigma;
import validator.XmlFileValidator;
import enigma.machine.component.machine.EnigmaMachine;


public class EngineImpl implements Engine {
    private EnigmaMachine machine;
    private LoadManager loadManager;
    private StatisticsManager statisticsManager;
    private Repository repository;
    private static int messageCount = 0;


    @Override
    public void loadXml(String path) {
        XmlFileValidator validator = new XmlFileValidator();
        validator.ValidateFilePath(path);
        loadManager = new LoadManager();
        BTEEnigma bteMachine = loadManager.loadXmlToObject(path);
        validator.ValidateAll(bteMachine);
        repository = new Repository(bteMachine.getABC());
        repository.loadToRepository(bteMachine);
        repository.printRepositoryContents();
        messageCount = 0;
    }


    @Override
    public DtoMachineSpecification showMachineDetails() {
        DtoCurrentCode dtoCurrentCode = new DtoCurrentCode();
        DtoOriginalCode dtoOriginalCode = new DtoOriginalCode();
        DtoMachineSpecification dtoMachineSpecification = new DtoMachineSpecification(repository.getRotorCount(), repository.getReflectorCount(),messageCount, dtoCurrentCode, dtoOriginalCode);


        return dtoMachineSpecification;
    }

    @Override
    public String processMessage(String message) {
        messageCount++;
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
