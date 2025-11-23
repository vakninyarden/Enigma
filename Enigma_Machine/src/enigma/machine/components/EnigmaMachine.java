package enigma.machine.components;

import enigma.machine.api.EnigmaMachineDefinition;

import java.util.ArrayList;
import java.util.List;

public class EnigmaMachine implements EnigmaMachineDefinition {
    private final List<Rotor> activeRotors;
    private final Reflector reflector;
    private final Alphabet alphabet;

    public EnigmaMachine(List<Rotor> activeRotors, Reflector reflector, Alphabet alphabet) {
        this.activeRotors = activeRotors;
        this.reflector = reflector;
        this.alphabet = alphabet;
    }


    @Override
    public void resetMachine() {
        for (Rotor rotor : activeRotors) {
            rotor.reset();
        }
    }

    @Override
    public char encryptLetter(char message) {
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
        int signalIndex = alphabet.toIndex(message);
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

    int getRotorCount() {
        return activeRotors.size();
    }
}
