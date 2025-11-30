package engine.file.validator;
import bte.component.jaxb.BTEReflector;
import bte.component.jaxb.BTERotor;


import java.util.List;

public interface FileValidator {
    // 1. גודל ה-ABC הוא זוגי
    boolean isAbcSizeEven(String abc);

    // 2. מוגדרים לפחות 3 רוטורים
    boolean hasAtLeastThreeRotors(List<BTERotor> rotors);

    // 3. לכל רוטור יש id ייחודי ורציף מ-1 בלי חורים
    boolean hasValidRotorIds(List<BTERotor> rotors);

    // 4. אין מיפויים כפולים בתוך רוטור (אין אות שמופיעה פעמיים ב-right או ב-left)
    boolean hasNoDuplicateMappingsInRotor(List<BTERotor> rotors);

    // 5. זיז הפסיעה (notch) בטווח גודל הרוטור
    boolean isNotchPositionInRange(List<BTERotor> rotors, String abc);

    // 6. לכל רפלקטור יש מספר סידורי רומאי רציף (I..V)
    boolean hasValidReflectorIds(List<BTEReflector> reflectors);

    // 7. אין מיפוי של אות לעצמה בשום רפלקטור
    boolean hasNoSelfMappingInReflector(List<BTEReflector> reflector);
}
