package engine;

import dto.*;
import bte.component.jaxb.BTEEnigma;
import enigma.machine.component.keyboard.KeyBoard;
import enigma.machine.component.keyboard.KeyBoardImpl;
import enigma.machine.component.machine.EnigmaMachineImpl;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.setting.Setting;
import enigma.machine.component.setting.SettingImpl;
import validator.FileValidator;
import validator.InputValidator;
import validator.XmlFileValidator;
import enigma.machine.component.machine.EnigmaMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class EngineImpl implements Engine {
    private final int NUMBER_OF_ROTORS = 3;

    private EnigmaMachine machine;
    private LoadManager loadManager;
    private StatisticsManager statisticsManager;
    private Repository repository;
    private static int messageCount = 0;
    private DtoMachineSpecification dtoMachineSpecification;

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
        StringBuilder originalSbString = new StringBuilder();
        Setting code = machine.getSetting();

        BuildOrinigalCodeString(code, originalSbString);

        StringBuilder currentSbString= new StringBuilder();

        BuildCurrentCodeString(code, currentSbString);


        dtoMachineSpecification = new DtoMachineSpecification(repository.getRotorCount(),
                                                              repository.getReflectorCount(),
                                                              messageCount,
                originalSbString.toString(),currentSbString.toString());

        return dtoMachineSpecification;
    }

    private void BuildCurrentCodeString(Setting machineOrinialCode, StringBuilder currentSbString) {
        BuildRotorsIdString(machineOrinialCode, currentSbString);

        BuildCurrentCode(currentSbString, machineOrinialCode);
        BuildReflectorIdSring(currentSbString, machineOrinialCode);
    }

    private void BuildCurrentCode(StringBuilder currentSbString, Setting machineOrinialCode) {
        currentSbString.append('<');
        for (int i = 0; i < machineOrinialCode.getActiveRotors().size(); i++) {
            int currentPosition = machineOrinialCode.getActiveRotors().get(i).getRotor().getCurrentPosition();
            currentSbString.append(repository.getRotor(machineOrinialCode.getActiveRotors().get(i).getRotor().getRotorId()).getRightMapping().get(currentPosition));
            currentSbString.append('(');
            int DistanceFromNotch = (machineOrinialCode.getActiveRotors().get(i).getRotor().getNotchIndex() - currentPosition + repository.getAbc().length()) % repository.getAbc().length();
            currentSbString.append(DistanceFromNotch);
            currentSbString.append(')');
            if (i != machineOrinialCode.getActiveRotors().size() - 1) {
                currentSbString.append(',');
            }
        }
        currentSbString.append('>');
    }

    private void BuildOrinigalCodeString(Setting machineOrinialCode, StringBuilder sb) {
        BuildRotorsIdString(machineOrinialCode, sb);
        BuildOrignialCode(machineOrinialCode, sb);
        BuildReflectorIdSring(sb, machineOrinialCode);
    }

    private static void BuildReflectorIdSring(StringBuilder sb, Setting machineOrinialCode) {
        sb.append('<');
        sb.append(machineOrinialCode.getReflector().getReflectorId());
        sb.append('>');
    }

    private void BuildOrignialCode(Setting machineOrinialCode, StringBuilder sb) {
        sb.append('<');
        for (int i = 0; i < machineOrinialCode.getActiveRotors().size(); i++) {
            int orinigalPosition = machineOrinialCode.getActiveRotors().get(i).getRotor().getOriginalPosition();
            sb.append(repository.getRotor(machineOrinialCode.getActiveRotors().get(i).getRotor().getRotorId()).getRightMapping().get(orinigalPosition));
            sb.append('(');
            int DistanceFromNotch = (machineOrinialCode.getActiveRotors().get(i).getRotor().getNotchIndex() - orinigalPosition + repository.getAbc().length()) % repository.getAbc().length();
            sb.append(DistanceFromNotch);
            sb.append(')');
            if (i != machineOrinialCode.getActiveRotors().size() - 1) {
                sb.append(',');
            }
        }
        sb.append('>');

    }

    private static void BuildRotorsIdString(Setting machineOrinialCode, StringBuilder sb) {
        sb.append('<');
        for (int i = 0; i < machineOrinialCode.getActiveRotors().size(); i++) {
            sb.append(machineOrinialCode.getActiveRotors().get(i).getRotor().getRotorId());
            if (i != machineOrinialCode.getActiveRotors().size() - 1) {
                sb.append(',');
            }
        }
        sb.append('>');

    }

    @Override
    public String processMessage(String message) {
        InputValidator.validateMessageInput(message, repository.getAbc());
        messageCount++;
        char[] result = new char[message.length()];
        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            result[i] = machine.processLatter(ch);
        }
        System.out.println(result);
        return new String(result);
    }



    @Override
    public void codeManual(String line, String initialRotorsPositions, int reflectorId) {
        InputValidator.validateRotorIds(line, NUMBER_OF_ROTORS);
        List<Integer> rotorIds = Arrays.stream(line.split(","))
                .map(String::trim)          // להוריד רווחים
                .map(Integer::parseInt)     // להפוך ל־Integer
                .collect(Collectors.toList());
        InputValidator.validateRotorExistence(rotorIds, repository.getRotorCount());
        InputValidator.validateDuplicateRotorIds(rotorIds);
        InputValidator.validatePositionsLength(initialRotorsPositions,rotorIds.size(),repository.getAbc());
        InputValidator.validateReflectorSelecttion(reflectorId);

        String reflectorIdStr = intToRoman(reflectorId);
        setMachineSetting(rotorIds, initialRotorsPositions, reflectorIdStr);

     /*   System.out.println(machine.getSetting().getActiveRotors().size());
        for(int i=0; i< machine.getSetting().getActiveRotors().size(); i++){
            System.out.println(machine.getSetting().getActiveRotors().get(i).getRotor().getRotorId());
        }
        System.out.println(machine.getSetting().getReflector().getReflectorId());
*/
    }

    private void setMachineSetting(List<Integer> rotorIds, String initialRotorsPositions, String reflectorId) {
        List<Setting.RotorPosition> activeRotors = new ArrayList<>();
        int size = rotorIds.size();
        for (int i = size - 1; i >= 0; i--) {
            Rotor rotor = repository.getRotor(rotorIds.get(i));
//            int position = repository.getAbc().indexOf(initialRotorsPositions.charAt(i));
            int position = rotor.getRightMapping().indexOf(initialRotorsPositions.charAt(i));
            Setting.RotorPosition rotorPosition = new Setting.RotorPosition(rotor, position);
            activeRotors.add(rotorPosition);
        }
        Reflector reflector = repository.getReflecton(reflectorId);
        Setting setting = new SettingImpl(reflector, activeRotors);
        KeyBoard keyBoard = new KeyBoardImpl(repository.getAbc());
        machine = new EnigmaMachineImpl(keyBoard, setting);
    }

    @Override
    public void codeAuto() {
        Random rand = new Random();
        String initialRotorsPositions = "";
        int numberOfReflectors = repository.getReflectorCount();
        List<Integer> rotorIds = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ROTORS; i++) {
            int minId = 1;
            int maxId = repository.getRotorCount();
            initialRandomRotorId(rotorIds, rand, maxId, minId);
            initialRotorsPositions = initialRandomRotorPosition(initialRotorsPositions, rand);
        }
        int ReflectorId = rand.nextInt((numberOfReflectors))+1;
        String id = intToRoman(ReflectorId);
        setMachineSetting(rotorIds, initialRotorsPositions, id);
    }

    private String initialRandomRotorPosition(String initialRotorsPositions, Random rand) {
        while (initialRotorsPositions.length() < NUMBER_OF_ROTORS) {
            char randomChar = repository.getAbc().charAt(rand.nextInt(repository.getAbc().length()));
            initialRotorsPositions += randomChar;
        }
        return initialRotorsPositions;
    }

    private void initialRandomRotorId(List<Integer> rotorIds, Random rand, int maxId, int minId) {
        while (rotorIds.size() < NUMBER_OF_ROTORS) {
            int randomId = rand.nextInt((maxId - minId) + 1) + minId;
            if (!rotorIds.contains(randomId)) {
                rotorIds.add(randomId);
            }
        }
    }

    @Override
    public void resetCode() {
        machine.resetMachine();
    }

    @Override
    public void statistics() {

    }


    private String intToRoman(int num) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[num / 1000] +
                hundreds[(num % 1000) / 100] +
                tens[(num % 100) / 10] +
                units[num % 10];
    }

    public static int getMessageCount() {
        return messageCount;
    }
}
