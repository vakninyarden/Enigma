package enigma.machine.component.rotor;

public interface Rotor {
    int getRotorId();
    // int getNotchIndex();
    int mapping(int indexInRotor, Direction direction);
    boolean atNotch();
    void step();
    void reset();

}