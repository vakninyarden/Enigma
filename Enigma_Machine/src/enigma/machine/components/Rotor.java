package enigma.machine.components;

import enigma.machine.api.RotorDefinition;

import java.util.ArrayList;


public class Rotor implements RotorDefinition {
private final int id;
private final int notchPosition;
private int currentPosition;
private final int sizeOfAlphabet ;
private final int originalPosition;
private final ArrayList<Character> rightMapping;
private final ArrayList<Character> leftMapping;

    public Rotor(int id, int notchPosition, int currentPosition,
                 ArrayList<Character> rightMapping, ArrayList<Character> leftMapping) {
        this.sizeOfAlphabet = rightMapping.size();
        this.originalPosition = currentPosition;
        this.id = id;
        this.notchPosition = notchPosition;
        this.currentPosition = currentPosition;
        this.rightMapping = rightMapping;
        this.leftMapping = leftMapping;

    }

    @Override
    public int getRotorId() {
        return id;
    }

    @Override
    public int getNotchIndex() {
        return notchPosition;
    }

    @Override
    public int rightToLeftMapping(int inputIndex) {
        int shifted = (inputIndex + currentPosition) % sizeOfAlphabet;
        char outChar = rightMapping.get(shifted);
        int mapped = leftMapping.indexOf(outChar);
        return (mapped - currentPosition + sizeOfAlphabet) % sizeOfAlphabet;
    }

    @Override
    public int leftToRightMapping(int indexInRotor) {
        int shifted = (indexInRotor + currentPosition) % sizeOfAlphabet;
        char outChar = leftMapping.get(shifted);
        int mapped = rightMapping.indexOf(outChar);
        return (mapped - currentPosition + sizeOfAlphabet) % sizeOfAlphabet;
    }

    @Override
    public boolean atNotch() {
        return currentPosition == notchPosition;
    }

    @Override
    public void step() {
        currentPosition = (currentPosition + 1) % sizeOfAlphabet;
    }

    @Override
    public void reset() {
        currentPosition = originalPosition;
    }


}
