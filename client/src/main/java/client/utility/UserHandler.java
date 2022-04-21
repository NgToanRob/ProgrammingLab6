package client.utility;

import data.*;
import exceptions.CommandUsageException;
import exceptions.IncorrectInputInScriptException;
import exceptions.ScriptRecursionException;
import interaction.OrganizationPack;
import interaction.Request;
import interaction.ResponseCode;
import utility.Outputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Receives user requests.
 */
public class UserHandler {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Receives user input.
     *
     * @param serverResponseCode Last server's response code.
     * @return New request to server.
     */
    public Request handle(ResponseCode serverResponseCode) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        int rewriteAttempts = 0;
        try {
            // A part of the main loop of the handler. It reads the user input or file input and processes it.
            do {
                try {
                    // If the server returned an error or the server exited, then the script execution is aborted.
                    if (fileMode() && (serverResponseCode == ResponseCode.ERROR ||
                            serverResponseCode == ResponseCode.SERVER_EXIT))
                        throw new IncorrectInputInScriptException();

                    // It checks if the script has ended and if so, it returns to the previous script.
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        Outputer.println("Back to the script '" + scriptStack.pop().getName() + "'...");
                    }

                    // Printing the command from the script file.
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            Outputer.print(Outputer.PS1);
                            Outputer.println(userInput);
                        }
                    } else {
                        Outputer.print(Outputer.PS1);
                        userInput = userScanner.nextLine();
                    }

                    // It splits the user input into two parts: the command and the command argument.
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();

                } catch (NoSuchElementException | IllegalStateException exception) {
                    // Catching an exception and printing an error message.
                    Outputer.println("An error occurred while entering the command!");
                    Outputer.printError("An error occurred while entering the command!");

                    // A part of the main loop of the handler. It reads the user input or file input and processes it.
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputer.printError("Number of input attempts exceeded!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());

            // A part of the code that is responsible for the execution of the script.
            try {
                if (fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR))
                    throw new IncorrectInputInScriptException();

                switch (processingCode) {
                    case OBJECT:
                        OrganizationPack organizationAddPack = generateOrganizationAdd();
                        return new Request(userCommand[0], userCommand[1], organizationAddPack);
                    case UPDATE_OBJECT:
                        OrganizationPack organizationUpdatePack = generateOrganizationUpdate();
                        return new Request(userCommand[0], userCommand[1], organizationUpdatePack);
                    case ADDRESS:
                        Address officialAddressPack = generateOfficialAddress();
                        return new Request(userCommand[0], userCommand[1], officialAddressPack);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Outputer.println("Executing a script '" + scriptFile.getName() + "'...");
                }
            } catch (FileNotFoundException exception) {
                Outputer.printError("Script file not found!");
            } catch (ScriptRecursionException exception) {
                Outputer.printError("Scripts cannot be called recursively!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            Outputer.printError("Script execution aborted!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request();
        }
        return new Request(userCommand[0], userCommand[1]);
    }

    
    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                return ProcessingCode.ERROR;
                case "help":
                if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                // case "save":
                //     if (!commandArgument.isEmpty()) throw new CommandUsageException();
                //     break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                    case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                    case "add_if_min":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "history":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "count_greater_than_official_address":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("<official_address>");
                    return ProcessingCode.ADDRESS;
                case "average_of_annual_turnover":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "filter_greater_than_type":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<organization_type>");
                    break;
                case "server_exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                default:
                Outputer.println("Command '" + command + "'does not found. Type 'help' for help.");
                return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputer.println("Using: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }
    
    /**
     * Generates marine to add.
     *
     * @return Marine to add.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */
    private OrganizationPack generateOrganizationAdd() throws IncorrectInputInScriptException {
        Asker asker = new Asker(userScanner);
        if (fileMode()) asker.setFileMode();
        return new OrganizationPack(
            asker.askName(),
            asker.askCoordinates(),
                asker.askAnnualTurnover(),
                asker.askOrganizationType(),
                asker.askOfficialAddress()
        );
    }
    
    /**
     * Generates marine to update.
     *
     * @return Marine to update.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */
    private OrganizationPack generateOrganizationUpdate() throws IncorrectInputInScriptException {
        Asker asker = new Asker(userScanner);
        if (fileMode()) asker.setFileMode();
        String name = asker.askQuestion("Do you want to change the organization's name?") ?
        asker.askName() : null;
        Coordinates coordinates = asker.askQuestion("Do you want to change the organization's coordinates") ?
                asker.askCoordinates() : null;
        long annualTurnover = asker.askQuestion("Do you want to change the organization's annual turnover") ?
        asker.askAnnualTurnover() : -1; // annualTurnover always >= 0, so -1 is a bad value
        OrganizationType type = asker.askQuestion("Do you want to change the organization's type?") ?
                asker.askOrganizationType() : null;
        Address address =  asker.askQuestion("Do you want to change the organization's official address?") ?
                asker.askOfficialAddress() : null;
                return new OrganizationPack(
                name,
                coordinates,
                annualTurnover,
                type,
                address
        );
        // Need to handle filed not allow null value
    }
    private Address generateOfficialAddress() throws IncorrectInputInScriptException {
        Asker asker = new Asker(userScanner);
        if (fileMode()) asker.setFileMode();
        return new Address(asker.askStreet(), asker.askZipCode());
    }
    
    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
