package server.commands;

import exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'save'. Saves the collection to a file.
 */
public class SaveCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "","save collection to file");
        this.collectionManager = collectionManager;
    }

    /**
     * Save the collection to the file
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.* @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null)
                throw new WrongAmountOfElementsException();
            collectionManager.saveCollection();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        }
        return false;
    }
}