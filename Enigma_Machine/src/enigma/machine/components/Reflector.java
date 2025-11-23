package enigma.machine.components;
import enigma.machine.api.ReflectorDefinition;
import java.util.Map;


public class Reflector implements ReflectorDefinition {
   private final int reflectorId;
   private final int sizeOfAlphabet;
   private final Map<Integer, Integer> mapping;

    public Reflector(int reflectorId, Map<Integer, Integer> mapping) {
        this.reflectorId = reflectorId;
        this.mapping = mapping;
        this.sizeOfAlphabet = mapping.size();
    }

    @Override
    public int getReflectorId() {
        return reflectorId;
    }

    @Override
    public int reflect(int inputIndex) {
        return mapping.get(inputIndex);
    }
}
