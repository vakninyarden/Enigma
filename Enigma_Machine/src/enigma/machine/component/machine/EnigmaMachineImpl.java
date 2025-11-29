package enigma.machine.component.machine;

import enigma.machine.component.keyboard.KeyBoard;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Direction;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.setting.Setting;

import java.util.List;


public class EnigmaMachineImpl implements EnigmaMachine {
    private final List<Rotor> activeRotors;
    private final Reflector reflector;
    private final KeyBoard keyboard;
    private Setting setting;
    
    public EnigmaMachineImpl(List<Rotor> activeRotors, Reflector reflector, KeyBoard keyboard) {
        this.keyboard = keyboard;
        this.activeRotors = activeRotors;
        this.reflector = reflector;
    }


    @Override
    public void resetMachine() {
        for (Rotor rotor : activeRotors) {
            rotor.reset();
        }
    }

    @Override
    public void machineSettings(Setting settings) {
        
    }

 //   @Override
/*
    public char processLatter(char latter) {
        int intermediate = keyboard.processCharacter(latter);
        int n = getRotorCount();
        boolean[] willStep = new boolean[n];
        willStep[n - 1] = true;
        int i = n-1;
        while (i>0 && willStep[i]) {
            if (activeRotors.get(i).atNotch()) {
                willStep[i - 1] = true;
            }
            i--;
        }
        for (int j = 0; j < n; j++) {
            if (willStep[j]) {
                activeRotors.get(j).step();
            }
        }
        int signalIndex = alphabet.toIndex(latter);
        for (i = n - 1; i >= 0; i--) {
            signalIndex = activeRotors.get(i).rightToLeftMapping(signalIndex);
        }
        signalIndex = reflector.reflect(signalIndex);
        for (i = 0; i < n; i++) {
            signalIndex = activeRotors.get(i).leftToRightMapping(signalIndex);
        }
        char encryptedChar = alphabet.toChar(signalIndex);
        return encryptedChar;
    }
*/
    @Override
    public char processLatter(char latter) {
        int intermediate = keyboard.processCharacter(latter);
        List<Setting.RotorPosition> rotors = setting.getActiveRotors();

        rotorsStep(rotors);
        intermediate = moveForward(rotors, intermediate);
        intermediate = reflector.reflect(intermediate);
        intermediate = moveBackward(rotors, intermediate);
        return keyboard.lightALamp(intermediate);
    }

    private static int moveBackward(List<Setting.RotorPosition> rotors, int intermediate) {
        for (int i = rotors.size() - 1; i >= 0; i--) {
            intermediate = rotors.get(i).rotor.mapping(intermediate, Direction.BACKWARD);
        }
        return intermediate;
    }

    private static int moveForward(List<Setting.RotorPosition> rotors, int intermediate) {
        for (int i = 0; i < rotors.size(); i++) {
            intermediate = rotors.get(i).rotor.mapping(intermediate, Direction.FORWARD);
        }
        return intermediate;
    }

    private void rotorsStep(List<Setting.RotorPosition> rotors) {
        boolean shouldStepNext = false;
        int rotorIndex = 0;
        do {
            Rotor rotor = rotors.get(rotorIndex).rotor;
            rotor.step();
            shouldStepNext = rotor.atNotch();
            rotorIndex++;
        } while (shouldStepNext && rotorIndex < rotors.size());
    }

    int getRotorCount() {
        return activeRotors.size();
    }
}
