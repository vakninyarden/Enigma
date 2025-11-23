package enigma.machine.api;

public interface ReflectorDefinition {
    String getReflectorId();
    int reflect(int inputIndex);
}
