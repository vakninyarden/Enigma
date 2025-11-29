package enigma.machine.component.keyboard;

public class KeyBoardImpl implements KeyBoard {
    private String alphabet;

    public KeyBoardImpl(String alphabet) {
        this.alphabet = alphabet;
    }
    @Override
        public int processCharacter(char c) {
        return alphabet.indexOf(c);
    }

    @Override
    public char lightALamp(int input) {
        return alphabet.charAt(input);
    }

    @Override
    public  int getAlphabetSize() {
        return alphabet.length();   }
}
