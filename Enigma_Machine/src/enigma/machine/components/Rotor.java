package enigma.machine.components;

import enigma.machine.api.RotorDefinition;

import java.util.Collection;

public class Rotor implements RotorDefinition {
private final int id;
private final int notchPosition;
private final int currentPosition;
private final Collection<Character> rightMapping;
private final Collection<Character> leftMapping;
    @Override
    public int getRotorId() {
        return 0;
    }

    @Override
    public int getNotchIndex() {
        return 0;
    }
}
