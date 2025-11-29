package enigma.machine.component.rotor;

import java.util.ArrayList;
import java.util.List;


public class Rotor implements RotorDefinition {
private final int id;
private final int notchPosition;
private int currentPosition;
private final int sizeOfAlphabet;
private final int originalPosition;
private final List<Character> rightMapping;
private final List<Character> leftMapping;

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

  /*  @Override
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
    }*/

    @Override
    public int mapping(int indexInRotor, Direction direction) {
        int shifted = (indexInRotor + currentPosition) % sizeOfAlphabet;
        char outChar;
        int mapped;
        if (direction == Direction.FORWARD) {
            outChar = rightMapping.get(shifted);
            mapped = leftMapping.indexOf(outChar);
        }
        else {
            outChar = leftMapping.get(shifted);
            mapped = rightMapping.indexOf(outChar);
        }
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
