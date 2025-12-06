package validator;

import exception.inputexception.*;

import java.util.List;

public class InputValidator {
    public static void validateMessageInput(String massege, String abc) {
        for (char c : massege.toCharArray()) {
            if (!abc.contains(String.valueOf(c))) {
            throw new InvalidEnigmaCharacterException(c);
            }
        }
    }

    public static void validateDuplicateRotorIds(List<Integer> rotorIds) {
        java.util.Set<Integer> seen = new java.util.HashSet<>();
        for (Integer id : rotorIds) {
            if (id == null) continue;
            if (!seen.add(id)) {
                throw new DuplicateRotorException(id.toString());
            }
        }
    }

    public static void validateReflectorSelecttion(int reflectorId) {
        if (reflectorId <= 0 || reflectorId > 5 ) throw new InvalidReflectorSelectionException(""+ reflectorId);
    }

    public static void validateRotorIds(String input, int maxRotors) {

        if (input == null || input.trim().isEmpty()) {
            throw new TooFewRotorsSelectedException("0");
        }

        // פצל לפי פסיק
        String[] parts = input.split(",");

        // בדוק שכל חלק הוא מספר תקין
        for (String part : parts) {
            String trimmed = part.trim();

            // בדוק שהחלק לא ריק
            if (trimmed.isEmpty()) {
                throw new NonNumericRotorIdException(part);
            }

            // בדוק שהוא מספר
            try {
                Integer.parseInt(trimmed);
            } catch (NumberFormatException e) {
                throw new NonNumericRotorIdException(part);
            }
        }

        if (parts.length != 3) {
            if (parts.length > 3) throw new TooManyRotorsSelectedException("you selected" + parts.length + " rotors");
            else throw new TooFewRotorsSelectedException("you selected" + parts.length + " rotors");
        }

    }

    public static void validateRotorExistence(List<Integer> rotorIds, int sizeOfRotors) {
        for (Integer id : rotorIds) {
            if (id <= 0 || id > sizeOfRotors) {
                throw new RotorNotFoundException(id.toString());
            }
        }
    }

    public static void validatePositionsLength(String positionsInput, int expectedLength, String abc) {
        if (positionsInput.length() != expectedLength) {
            throw new PositionLengthMismatchException("expected length: " + expectedLength + ", but got: " + positionsInput.length());
        }
        for (Character character : positionsInput.toCharArray()) {
            if (!abc.contains(String.valueOf(character))) {
                throw new InvalidEnigmaCharacterException(character);
            }
        }
    }



}
