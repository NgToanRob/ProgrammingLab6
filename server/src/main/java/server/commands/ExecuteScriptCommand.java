package server.commands;

import exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Command 'execute_script'. Executes scripts from a file. Ectually only checks
 * argument and prints messages.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand() {
        super("execute_script <file_name>", "","execute script from specified file");
    }

    /**
     * Execute a script
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty()  || objectArgument != null)
                throw new WrongAmountOfElementsException();
            ResponseOutputer.appendln("Executing a script '" + stringArgument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        }
        return false;
    }
}
