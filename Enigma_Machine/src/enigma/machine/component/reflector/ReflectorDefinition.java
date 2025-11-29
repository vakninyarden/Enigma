package enigma.machine.component.reflector;

public interface ReflectorDefinition {
    String getReflectorId();
    int reflect(int inputIndex);
}
