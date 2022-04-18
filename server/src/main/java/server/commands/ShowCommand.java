package server.commands;

import exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'show'. Shows information about all elements of the collection.
 */
public class ShowCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "{element}","display all items in the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Prints the collection manager
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.* @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null)
                throw new WrongAmountOfElementsException();
             ResponseOutputer.appendln(collectionManager.showCollection());
            return true;
        } catch (WrongAmountOfElementsException exception) {
             ResponseOutputer.appendln("Using: '" + getName() + "'");
        }
        return false;
    }
}
