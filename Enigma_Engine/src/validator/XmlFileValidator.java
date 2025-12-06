package validator;

import bte.component.jaxb.*;
import exception.fileexceoption.*;

import java.io.File;
import java.util.*;

public class XmlFileValidator implements FileValidator {

    // 1. קובץ קיים
    public boolean isFileExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileDoesNotExistException(filePath);
        }
        return true;
    }

    // 2. קובץ XML
    public boolean validateIsXmlFile(String filePath) {
        if (!filePath.toLowerCase().endsWith(".xml")) {
            throw new NotXmlFileException(filePath);
        }
        return true;

    }

    // 3. גודל ABC זוגי
    @Override
    public boolean isAbcSizeEven(String abc) {
        if (abc.length() % 2 != 0) {
            throw new AbcSizeNotEvenException();
        }
        return true;
    }

    // 4. לפחות 3 רוטורים
    @Override
    public boolean hasAtLeastThreeRotors(List<BTERotor> rotors) {
        if (rotors.size() < 3) {
            throw new NotEnoughRotorsException(rotors.size());
        }
        return true;
    }

    // 5. IDs רציפים וייחודיים
    @Override
    public boolean hasValidRotorIds(List<BTERotor> rotors) {

        if (rotors == null || rotors.isEmpty()) {
            throw new InvalidRotorIdsException();
        }

        Set<Integer> ids = new HashSet<>();
        int maxId = 0;

        for (BTERotor rotor : rotors) {
            int id = rotor.getId();

            if (id <= 0) {
                throw new InvalidRotorIdsException();
            }

            if (!ids.add(id)) {
                throw new InvalidRotorIdsException();
            }

            maxId = Math.max(maxId, id);
        }

        if (maxId != ids.size()) {
            throw new InvalidRotorIdsException();
        }

        return true;
    }

    // 6. אין מיפויים כפולים ברוטור
    @Override
    public boolean hasNoDuplicateMappingsInRotor(List<BTERotor> rotors) {

        for (BTERotor rotor : rotors) {

            Set<Character> leftInputs = new HashSet<>();
            Set<Character> rightInputs = new HashSet<>();

            for (BTEPositioning p : rotor.getBTEPositioning()) {
                String left = p.getLeft();
                for (int i = 0; i < left.length(); i++) {
                    if (!leftInputs.add(left.charAt(i))) {
                        throw new DuplicateRotorMappingException(rotor.getId());
                    }
                }

                String right = p.getRight();
                for (int i = 0; i < right.length(); i++) {
                    if (!rightInputs.add(right.charAt(i))) {
                        throw new DuplicateRotorMappingException(rotor.getId());
                    }
                }
            }
        }

        return true;
    }

    // 7. זיז הפסיעה בטווח
    @Override
    public boolean isNotchPositionInRange(List<BTERotor> rotors, String abc) {

        for (BTERotor rotor : rotors) {
            int notchPosition = rotor.getNotch();
            if (notchPosition < 0 || notchPosition >= abc.length()) {
                throw new NotchOutOfRangeException(rotor.getId());
            }
        }

        return true;
    }

    // 8. מזהי רפלקטורים תקינים (I..V)
    @Override
    public boolean hasValidReflectorIds(List<BTEReflector> reflectors) {

        if (reflectors == null || reflectors.isEmpty()) {
            throw new InvalidReflectorIdException("NULL/EMPTY");
        }

        Set<Integer> ids = new HashSet<>();
        int maxId = 0;

        for (BTEReflector reflector : reflectors) {
            int id = romanToInt(reflector.getId());

            if (id <= 0) {
                throw new InvalidReflectorIdException(reflector.getId());
            }

            if (!ids.add(id)) {
                throw new InvalidReflectorIdException(reflector.getId());
            }

            maxId = Math.max(maxId, id);
        }

        if (maxId != ids.size()) {
            throw new InvalidReflectorIdException("Non-sequential IDs");
        }

        return true;
    }

    // 9. אין מיפוי של אות לעצמה ברפלקטור
    @Override
    public boolean hasNoSelfMappingInReflector(List<BTEReflector> reflectors) {

        for (BTEReflector refl : reflectors) {
            for (BTEReflect mapping : refl.getBTEReflect()) {
                if (mapping.getInput() == mapping.getOutput()) {
                    throw new ReflectorSelfMappingException(
                            String.valueOf(mapping.getInput()),
                            refl.getId()
                    );
                }
            }
        }

        return true;
    }

    // כלי עזר
    public int romanToInt(String roman) {
        if (roman == null) {
            return -1;
        }

        switch (roman) {
            case "I":   return 1;
            case "II":  return 2;
            case "III": return 3;
            case "IV":  return 4;
            case "V":   return 5;
            default:    return -1;
        }
    }

    public void ValidateAll( BTEEnigma bteEnigma) {
        isAbcSizeEven(bteEnigma.getABC());
        hasAtLeastThreeRotors(bteEnigma.getBTERotors().getBTERotor());
        hasValidRotorIds(bteEnigma.getBTERotors().getBTERotor());
        hasNoDuplicateMappingsInRotor(bteEnigma.getBTERotors().getBTERotor());
        isNotchPositionInRange(bteEnigma.getBTERotors().getBTERotor(), bteEnigma.getABC());
        hasValidReflectorIds(bteEnigma.getBTEReflectors().getBTEReflector());
        hasNoSelfMappingInReflector(bteEnigma.getBTEReflectors().getBTEReflector());
    }
    public void ValidateFilePath(String filePath) {
        validateIsXmlFile(filePath);
        isFileExists(filePath);
    }
}
