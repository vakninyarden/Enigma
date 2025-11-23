package enigma.machine.components;
import enigma.machine.api.ReflectorDefinition;
import java.util.Map;

//todo::: check xml implementetion and create map
public class Reflector implements ReflectorDefinition {
   private final int reflectorId;
   private final Map<Integer, Integer> mapping;

    public Reflector(int reflectorId, Map<Integer, Integer> mapping) {
        this.reflectorId = reflectorId;
        this.mapping = mapping;
    }

    @Override
    public String getReflectorId() {
        return toRoman(reflectorId);
    }

    @Override
    public int reflect(int inputIndex) {
        return mapping.get(inputIndex);
    }

    public static String toRoman(int num) {
        switch (num) {
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            case 5: return "V";
            default: throw new IllegalArgumentException("Number must be between 1 and 5");
        }
    }

//    public static String toRoman(int num) {
//        if (num < 1 || num > 3999) {
//            throw new IllegalArgumentException("Number must be between 1 and 3999");
//        }
//
//        // מערכים של ערכים וסימנים מתאימים
//        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
//        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
//
//        StringBuilder result = new StringBuilder();
//
//        for (int i = 0; i < values.length; i++) {
//            while (num >= values[i]) {
//                result.append(symbols[i]);
//                num -= values[i];
//            }
//        }
//
//        return result.toString();
//    }






}
