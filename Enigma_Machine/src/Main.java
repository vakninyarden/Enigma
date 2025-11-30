import enigma.machine.component.keyboard.KeyBoard;
import enigma.machine.component.keyboard.KeyBoardImpl;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.reflector.ReflectorImpl;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.rotor.RotorImpl;
import enigma.machine.component.setting.Setting;
import enigma.machine.component.setting.SettingImpl;
import enigma.machine.component.setting.Setting.RotorPosition;
import enigma.machine.component.machine.EnigmaMachine;
import enigma.machine.component.machine.EnigmaMachineImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. הגדרת האלפבית
        String alphabet = "ABCDEF";
        KeyBoard keyboard = new KeyBoardImpl(alphabet);

        // 2. יצירת רוטורים (דוגמה פשוטה – זהות עם נוטש במיקום 0)
        List<Character> rightMapping1 = new ArrayList<>();
        List<Character> leftMapping1  = new ArrayList<>();
        rightMapping1.add('A');
        rightMapping1.add('B');
        rightMapping1.add('C');
        rightMapping1.add('D');
        rightMapping1.add('E');
        rightMapping1.add('F');
        leftMapping1.add('F');
        leftMapping1.add('E');
        leftMapping1.add('D');
        leftMapping1.add('C');
        leftMapping1.add('B');
        leftMapping1.add('A');

        List<Character> rightMapping2 = new ArrayList<>();
        List<Character> leftMapping2  = new ArrayList<>();

        rightMapping2.add('A');
        rightMapping2.add('B');
        rightMapping2.add('C');
        rightMapping2.add('D');
        rightMapping2.add('E');
        rightMapping2.add('F');
        leftMapping2.add('A');
        leftMapping2.add('F');
        leftMapping2.add('B');
        leftMapping2.add('E');
        leftMapping2.add('C');
        leftMapping2.add('D');

        List<Character> rightMapping3 = new ArrayList<>();
        List<Character> leftMapping3 = new ArrayList<>();

        rightMapping3.add('A');
        rightMapping3.add('B');
        rightMapping3.add('C');
        rightMapping3.add('D');
        rightMapping3.add('E');
        rightMapping3.add('F');
        leftMapping3.add('E');
        leftMapping3.add('B');
        leftMapping3.add('D');
        leftMapping3.add('F');
        leftMapping3.add('C');
        leftMapping3.add('A');

        // given notch -1
        Rotor rotor1 = new RotorImpl(1, 3, 2, rightMapping1, leftMapping1);
        Rotor rotor2 = new RotorImpl(2, 0, 2, rightMapping3, leftMapping3);
        Rotor rotor3 = new RotorImpl(3, 2, 2, rightMapping2, leftMapping2);

        List<RotorPosition> rotors = new ArrayList<>();
        rotors.add(new RotorPosition(rotor1, 4));
        rotors.add(new RotorPosition(rotor2, 1));
         rotors.add(new RotorPosition(rotor3, 5));
        // 3. יצירת רפלקטור (מיפוי סימטרי פשוט לדוגמה)
        Map<Integer,Integer> refMap = new HashMap<>();
        int size = alphabet.length();
        refMap.put(6, 3);
        refMap.put(3,6);// A <-> D
        refMap.put(1, 4); // B <-> E
        refMap.put(4, 1);
        refMap.put(2, 5); // C <-> F
        refMap.put(5, 2);
        Reflector reflector = new ReflectorImpl(1, refMap);

        // 4. יצירת Setting
        Setting setting = new SettingImpl(reflector, rotors);

        // 5. יצירת המכונה
        EnigmaMachine machine = new EnigmaMachineImpl(keyboard, setting);

        // 6. טקסט לבדיקה
        String plaintext = "AAAEEEBBBDDDCCCFFF";
        StringBuilder cipher = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            cipher.append(machine.processLatter(ch));
        }

        // 7. בדיקת דה-קריפט: מאפסים למצב התחלתי ומריצים שוב
        machine.resetMachine();
        StringBuilder decrypted = new StringBuilder();
        for (char ch : cipher.toString().toCharArray()) {
            decrypted.append(machine.processLatter(ch));
        }

        System.out.println("Plain : " + plaintext);
        System.out.println("Cipher: " + cipher);
        System.out.println("Dec   : " + decrypted);
    }
}
