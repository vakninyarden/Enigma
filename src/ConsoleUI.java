package console;


import dto.DtoMachineSpecification;
import engine.Engine;
import exception.FileValidationException;

import java.util.Scanner;

public class ConsoleUI {
    private final Engine engine;
    private final Scanner scanner = new Scanner(System.in);


    public ConsoleUI(Engine engine) {
        this.engine = engine;
    }

    void showMenu() {
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleLoadMachineFromXml();
                    break;
                case 2:
                    handleShowCurrentMachineStatus();
                    break;
                case 3:
                    handleManualCodeSelection();
                    break;
                case 4:
                    handleAutomaticCodeSetup();
                    break;
                case 5:
                    handleResetToOriginalCode();
                    break;
                case 6:
                    handleProcessInput();
                    break;
                case 7:
                    handleShowMachineHistory();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting, goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

    }

    private void printMainMenu() {
        System.out.println("===== Enigma Menu =====");
        System.out.println("1. Load machine from XML");
        System.out.println("2. Get current machine status");
        System.out.println("3. Manual code selection");
        System.out.println("4. Automatic code setup");
        System.out.println("5. Reset to original code");
        System.out.println("6. Process input");
        System.out.println("7. Get machine history");
        System.out.println("8. Exit");
        System.out.print("Choose option (1-8): ");
    }

    private void handleLoadMachineFromXml  () {
         String path = readNonEmptyLine("Please enter the XML file full path:");
         try{
             engine.loadXml(path);
                System.out.println("Machine loaded successfully from: " + path);
         }
            catch (FileValidationException e){
                System.out.println("Error loading machine: " + e.getMessage());
         }
           catch (Exception e){
                System.out.println("An unexpected error occurred: " + e.getMessage());
           }
    }

    private void handleShowCurrentMachineStatus() {
        // כאן תקראי ל-engine ו"תדפיסי" את הסטטוס
        DtoMachineSpecification spec = engine.showMachineDetails();
        System.out.println(spec.getNumOfRotors());
        System.out.println(spec.getNumOfReflectors());
        System.out.println(spec.getNumOfMessages());
        System.out.println(spec.getOriginalCode());
        System.out.println(spec.getCurrentCode());
    }

    private void handleManualCodeSelection() {
        // כאן תשאלי את המשתמש על רוטורים/מיקומים/פלגבורד ותעבירי ל-engine
    }

    private void handleAutomaticCodeSetup() {
        // כאן תקראי ל-engine שיבחר קוד אוטומטי ותציגי אותו
    }

    private void handleResetToOriginalCode() {
        // קריאה ל-engine לאיפוס קוד
    }

    private void handleProcessInput() {
        // קריאת טקסט מהמשתמש, קריאה ל-engine להצפנה/פענוח, הדפסת תוצאה
        String input = readNonEmptyLine("Please enter the text to process:");
        input = scanner.nextLine().toUpperCase();

    }

    private void handleShowMachineHistory() {
        // קריאה ל-engine להיסטוריה והצגה
    }

    private  String readNonEmptyLine(String msg)
    {
        while(true){

            if (scanner.hasNextLine()) {  // Clear any leftover newline characters
                String leftover = scanner.nextLine();
                if (!leftover.isEmpty()) {
                    return leftover.trim();
                }
            }

            System.out.println(msg);
            String line = scanner.nextLine().trim();
            if(!line.isEmpty()){
                return line;
            }
            System.out.println("Please enter a non-empty line");
            System.out.println(msg);

        }
    }
}
