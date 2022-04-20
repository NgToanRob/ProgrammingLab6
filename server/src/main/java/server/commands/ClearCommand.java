package server.commands;

import exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear","" ,"clear the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Clear the collection
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
            collectionManager.clearCollection();
            ResponseOutputer.appendln("The collection is empty!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        }
        return false;
    }
}
