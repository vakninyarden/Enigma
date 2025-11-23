package enigma.machine.api;

public interface EnigmaMachineDefinition {
    void resetMachine();
    char encryptLetter(char message);


}
