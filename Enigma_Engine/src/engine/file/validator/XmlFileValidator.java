package engine.file.validator;

import bte.component.jaxb.*;

import javax.xml.validation.Validator;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.lang.String;

public class XmlFileValidator implements FileValidator {
    @Override
    public boolean isAbcSizeEven(String abc) {
        return abc.length() % 2 == 0;
    }

    @Override
    public boolean hasAtLeastThreeRotors(List<BTERotor> rotors) {
        return rotors.size() >= 3;
    }

    @Override
    public boolean hasValidRotorIds(List<BTERotor> rotors) {
        if (rotors == null || rotors.isEmpty()) {
            return false; // אין רוטורים → לא תקין
        }

        Set<Integer> ids = new HashSet<>();
        int maxId = 0;

        for (BTERotor rotor : rotors) {
            int id = rotor.getId();

            if (id <= 0) {
                return false;
            }

            if (!ids.add(id)) {
                return false;
            }

            if (id > maxId) {
                maxId = id;
            }
        }

        return ids.size() == rotors.size() && maxId == ids.size();
    }

    public boolean hasNoDuplicateMappingsInRotor(List<BTERotor> rotors) {
        for (BTERotor rotor : rotors) {
            Set<Character> leftInputs = new HashSet<>();
            Set<Character> rightInputs = new HashSet<>();
            List<BTEPositioning> posList = rotor.getBTEPositioning();

            for (BTEPositioning p : posList) {
                String left = p.getLeft();
                for (int i = 0; i < left.length(); i++) {
                    if (!leftInputs.add(left.charAt(i))) {
                        return false;
                    }
                }
                String right = p.getRight();
                for (int i = 0; i < right.length(); i++) {
                    if (!rightInputs.add(right.charAt(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isNotchPositionInRange(List<BTERotor> rotors, String abc) {
        for (BTERotor rotor : rotors) {
            int notchPosition = rotor.getNotch();
            if (notchPosition < 0 || notchPosition >= abc.length()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasValidReflectorIds(List<BTEReflector> reflectors) {
        if (reflectors == null || reflectors.isEmpty()) {
            return false;
        }

        Set<Integer> ids = new HashSet<>();
        int maxId = 0;

        for (BTEReflector reflector : reflectors) {
            int id = romanToInt(reflector.getId());

            if (id <= 0) {
                return false;
            }

            if (!ids.add(id)) {
                return false;
            }

            if (id > maxId) {
                maxId = id;
            }
        }

        return ids.size() == reflectors.size() && maxId == ids.size();
    }

    @Override
    public boolean hasNoSelfMappingInReflector(List<BTEReflector> reflector) {
         for (BTEReflector refl : reflector) {
             for (BTEReflect bteReflect : refl.getBTEReflect()) {
                 if (bteReflect.getInput() == bteReflect.getOutput()) {
                     return false;
                 }
             }
         }
         return true;
    }

    public int romanToInt(String roman) {
        if (roman == null) {
            throw new IllegalArgumentException("roman is null");
        }

        switch (roman) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            default:
                return -1;
        }
    }


}
