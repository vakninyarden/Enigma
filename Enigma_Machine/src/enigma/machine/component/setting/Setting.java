package enigma.machine.component.setting;

import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;
import java.util.List;


public interface Setting {
    Reflector getReflector();
    List<RotorPosition> getActiveRotors();

    public static class RotorPosition {
      public Rotor rotor;
      public int position;
    }
}
