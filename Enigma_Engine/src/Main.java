import engine.Engine;
import engine.EngineImpl;
import engine.LoadManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
LoadManager loadManager = new LoadManager();
        Engine engine = new EngineImpl(loadManager);
        engine.loadXml("C:\\Yarden\\Computer_Science\\JAVA\\Enigma\\Enigma_Engine\\src\\recources\\ex1-error-3.xml");
   }
}