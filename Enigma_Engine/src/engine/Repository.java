package engine;

import bte.component.jaxb.*;
import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.reflector.ReflectorImpl;
import enigma.machine.component.rotor.Rotor;
import enigma.machine.component.rotor.RotorImpl;

import java.util.*;

public class Repository {
  Map<Integer, Rotor> rotors;
  Map<String, Reflector> reflectors;
  private final String abc;

  public Repository(String abc) {
        this.abc = abc;
  }
  public void loadToRepository(BTEEnigma bteEnigma) {
      this.rotors = buildRotorsRepository(bteEnigma.getBTERotors(), abc);
      this.reflectors = buildReflectorsRepository(bteEnigma.getBTEReflectors(), abc);
  }

    private Map<Integer, Rotor> buildRotorsRepository(BTERotors bteRotors, String abc) {
        Map<Integer, Rotor> result = new HashMap<>();

        for (BTERotor bteRotor : bteRotors.getBTERotor()) {
            Rotor rotor = buildRotor(bteRotor, abc);
            result.put(rotor.getRotorId(), rotor);
        }

        return result;
    }

    public Rotor buildRotor(BTERotor bteRotor, String abc) {
        int id = bteRotor.getId();              // מ-id של ה-XML
        int notch = bteRotor.getNotch();        // מיקום ה-notch לפי ה-XML

        int size = abc.length();
        List<Character> rightMapping = new ArrayList<>(size);
        List<Character> leftMapping = new ArrayList<>(size);

        for (BTEPositioning pos : bteRotor.getBTEPositioning()) {   // כל זוג left/right ברוטור
            String leftStr  = pos.getLeft();   // String
            String rightStr = pos.getRight();  // String

            char leftChar  = leftStr.charAt(0);
            char rightChar = rightStr.charAt(0);
            rightMapping.add(rightChar);
            leftMapping.add(leftChar);

        }
        return new RotorImpl(id, notch, rightMapping, leftMapping);
    }



    private Map<String, Reflector> buildReflectorsRepository(BTEReflectors bteReflectors, String abc) {
            Map<String, Reflector> result = new HashMap<>();
            for (BTEReflector bteRef : bteReflectors.getBTEReflector()) { // כל <BTE-Reflector> ב-XML
                Reflector reflector = buildReflector(bteRef);              // הפונקציה שבונה ReflectorImpl
                String key = bteRef.getId();                               // "I"/"II"/"III"/...
                result.put(key, reflector);
            }
            return result;

    }


    // בונה ReflectorImpl אחד מתוך BTEReflector
    public Reflector buildReflector(BTEReflector bteReflector) {
        String xmlId = bteReflector.getId();   // "I","II","III",...

        Map<Integer, Integer> mapping = new HashMap<>();

        for (BTEReflect pair : bteReflector.getBTEReflect()) {
            // אם ה-XML 1-based והלוגיקה שלך 0-based – להחסיר 1
            int in  = pair.getInput() ;   // או בלי -1 אם כבר 0-based
            int out = pair.getOutput() ;

            mapping.put(in, out);
            mapping.put(out, in);
        }

        return new ReflectorImpl(xmlId, mapping);
    }
public void printRepositoryContents() {
    System.out.println("Rotors in Repository:");
    for (Map.Entry<Integer, Rotor> entry : rotors.entrySet()) {
        System.out.println("Rotor ID: " + entry.getKey());
        System.out.println("Rotor Right: " + entry.getValue().getRightMapping().toString());
        System.out.println("Rotor Left: " + entry.getValue().getLeftMapping().toString());
    }

    System.out.println("\nReflectors in Repository:");
    for (Map.Entry<String, Reflector> entry : reflectors.entrySet()) {
        System.out.println("Reflector ID: " + entry.getKey());
        System.out.println("Reflector Mapping: " + entry.getValue().getMapping().toString());
    }}

}
