package enigma.machine.api;

public interface AlphabetDefinition {
    int getSize();
    int toIndex(char c);
    char toChar(int index);
}
