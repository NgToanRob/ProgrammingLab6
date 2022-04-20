package server.commands;

import exceptions.WrongAmountOfElementsException;
import server.utility.ResponseOutputer;

/**
 * Command 'history'. It's here just for logical structure.
 */
public class HistoryCommand extends AbstractCommand {

    public HistoryCommand() {
        super("history", "","display history of used commands");
    }

    /**
     * The command shows 9 last commands.
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null)
                throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        }
        return false;
    }
}
