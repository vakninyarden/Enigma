package enigma.machine.component.machine;

import enigma.machine.component.setting.Setting;

public interface EnigmaMachine {
    void resetMachine();
    void machineSettings(Setting settings);
    char processLatter(char message);


}
