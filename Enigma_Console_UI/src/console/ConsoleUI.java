package console;


import dto.DtoMachineSpecification;
import engine.Engine;
import exception.fileexceoption.FileValidationException;
import exception.inputexception.InputValidationException;
import exception.inputexception.InvalidEnigmaCharacterException;
import validator.InputValidator;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleUI {
    private final Engine engine;
    private final Scanner scanner = new Scanner(System.in);
    private final int NUMBER_OF_ROTORS = 3;
    private boolean isMachineLoaded = false;
    private boolean isCodeSet = false;

    public ConsoleUI(Engine engine) {
        this.engine = engine;
    }

    void showMenu() {
        boolean exit = false;
        int choice = -1;
        while (!exit) {
            printMainMenu();
            try {
                choice = scanner.nextInt();
                if ((choice > 1 && choice <=7) && !isMachineLoaded) {
                    throw  new InputValidationException("Machine not loaded. Please load a machine from XML first.");
                }
                if ((choice == 2 || choice==5 || choice == 6) && !isCodeSet) {
                    throw new InputValidationException("Code not set. Please set the code first.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid choice, please enter a number between 1-8.");
                continue;
            }
            catch (InputValidationException e) {
                System.out.println(e.getMessage());
                continue;
            }


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
                    handleProcessInput();
                    break;
                case 6:
                    handleResetToOriginalCode();
                    break;
                case 7:
                    handleShowMachineHistory();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting, goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice, please enter a number between 1-8.");
            }
        }

    }

    private void printMainMenu() {
        System.out.println("===== Enigma Menu =====");
        System.out.println("1. Load machine from XML");
        System.out.println("2. Get current machine status");
        System.out.println("3. Manual code selection");
        System.out.println("4. Automatic code setup");
        System.out.println("5. Process input");
        System.out.println("6. Reset to original code");
        System.out.println("7. Get machine history");
        System.out.println("8. Exit");
        System.out.print("Choose option (1-8): ");
    }

    private void handleLoadMachineFromXml() {
        String path = readNonEmptyLine("Please enter the XML file full path:");
        try {
            engine.loadXml(path);
            System.out.println("Machine loaded successfully from: " + path);
            isMachineLoaded = true;
            isCodeSet = false;
        } catch (FileValidationException e) {
            System.out.println("Error loading machine: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleShowCurrentMachineStatus() {
       // checkMachineLoaded();
        // כאן תקראי ל-engine ו"תדפיסי" את הסטטוס
        DtoMachineSpecification spec = engine.showMachineDetails();
        System.out.println("Current machine status: ");
        System.out.println("Amount of rotors: ");
        System.out.println(spec.getNumOfRotors());
        System.out.println("Amount of reflectors: ");
        System.out.println(spec.getNumOfReflectors());
        System.out.println("Number of processed messages: ");
        System.out.println(spec.getNumOfMessages());
        System.out.println("Original Setting Code:");
        System.out.println(spec.getOriginalCode());
        System.out.println("Current Setting Code");
        System.out.println(spec.getCurrentCode());
    }

    private void handleManualCodeSelection() {
        System.out.println("Please enter the ids of the rotor you want to use (3 rotors):");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

//        List<Integer> rotorPositions = Arrays.stream(line.split(","))
//                .map(String::trim)          // להוריד רווחים
//                .map(Integer::parseInt)     // להפוך ל־Integer
//                .collect(Collectors.toList());

        System.out.println("Please enter the initial positions of the rotors (from the ABC):");
        String positionsLine = scanner.nextLine();
        System.out.println("Please enter the ids of the reflector you want to use:");
        System.out.println("1 = I, 2 = II, 3 = III, 4 = IV, 5 = V");
        int reflectorId;
        try {
             reflectorId = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Invalid choice, the reflector id must be a number between 1-5.");
            return;
        }
        try{
            engine.codeManual(line, positionsLine, reflectorId);
        } catch (InputValidationException e) {
            System.out.println("An error occurred while setting the manual code: " + e.getMessage());
            return;
        }
        isCodeSet = true;
    }

    private void handleAutomaticCodeSetup() {

        engine.codeAuto();
        isCodeSet = true;
    }

    private void handleResetToOriginalCode() {
        // קריאה ל-engine לאיפוס קוד
        engine.resetCode();
    }

    private void handleProcessInput() {
        // קריאת טקסט מהמשתמש, קריאה ל-engine להצפנה/פענוח, הדפסת תוצאה
        String input = readNonEmptyLine("Please enter the text to process:");

        input = input.toUpperCase();
        try {
            String output = engine.processMessage(input);
            System.out.println(output);
        } catch (InvalidEnigmaCharacterException e) {
            System.out.println("An error occurred while processing the message: " + e.getMessage());
            return;
        }
    }

    private void handleShowMachineHistory() {
        engine.statistics();
    }

    private String readNonEmptyLine(String msg) {
        while (true) {

            if (scanner.hasNextLine()) {  // Clear any leftover newline characters
                String leftover = scanner.nextLine();
                if (!leftover.isEmpty()) {
                    return leftover.trim();
                }
            }

            System.out.println(msg);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Please enter a non-empty line");
            System.out.println(msg);

        }
    }

    private void checkMachineLoaded() {
        if (!isMachineLoaded) {
            throw new IllegalStateException("Machine not loaded. Please load a machine from XML first.");
        }
    }
}