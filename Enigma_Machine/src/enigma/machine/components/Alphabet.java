package enigma.machine.components;


import enigma.machine.api.AlphabetDefinition;

public class Alphabet implements AlphabetDefinition {
private final int size;
private String alphabet;
    public Alphabet(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int toIndex(char c) {
        return alphabet.indexOf(c);
    }
    public char toChar(int index) {
        return alphabet.charAt(index);
    }
}
