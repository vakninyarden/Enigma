package enigma.machine.component.setting;

import enigma.machine.component.reflector.Reflector;
import enigma.machine.component.rotor.Rotor;

import java.util.List;

public class SettingImpl implements Setting {
    private Reflector reflector;
    private List<RotorPosition> activeRotors;

    @Override
    public Reflector getReflector() {
        return  reflector;
    }

    @Override
    public List<RotorPosition> getActiveRotors() {
        return activeRotors;
    }
}
