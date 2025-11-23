package enigma.machine.api;

public interface ReflectorDefinition {
    int getReflectorId();
    int reflect(int inputIndex);
}
