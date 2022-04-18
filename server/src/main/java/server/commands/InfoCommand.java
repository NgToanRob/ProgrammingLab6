package server.commands;

import java.time.LocalDateTime;

import exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'info'. Prints information about the collection.
 */
public class InfoCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "{element}",
                "print information about the collection to standard output (type, initialization date, number of elements, etc.)");
        this.collectionManager = collectionManager;
    }

   /**
    * Prints information about the collection
    *
    * @param stringArgument The argument is the user input.
    * @param objectArgument The object is the user input.* @return Command exit status.
    */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null)
                throw new WrongAmountOfElementsException();
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "no initialization has yet taken place in this session"
                    : lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "in this session has not yet occurred"
                    : lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            ResponseOutputer.appendln("Collection Information:");
            ResponseOutputer.appendln(" Type: " + collectionManager.collectionType());
            ResponseOutputer.appendln(" Number of elements: " + collectionManager.collectionSize());
            ResponseOutputer.appendln(" Date of the last save: " + lastSaveTimeString);
            ResponseOutputer.appendln(" Date of last initialization: " + lastInitTimeString);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        }
        return false;
    }
}
