package enigma.machine.component.rotor;

public interface RotorDefinition {
    int getRotorId();
    int getNotchIndex();
    // int rightToLeftMapping(int indexInRotor);
   // int leftToRightMapping(int indexInRotor);
   int mapping(int indexInRotor, Direction direction);
    boolean atNotch();
    void step();
    void reset();

}