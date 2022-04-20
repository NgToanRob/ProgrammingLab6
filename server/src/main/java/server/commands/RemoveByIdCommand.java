package server.commands;

import exceptions.CollectionIsEmptyException;
import exceptions.OrganizationNotFoundException;
import exceptions.WrongAmountOfElementsException;
import data.Organization;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;


/**
 * Command 'remove_by_id'. Removes the element by its ID.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id <ID>", "","remove item from collection by ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Remove an organization from the collection
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null)
                throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0)
                throw new CollectionIsEmptyException();
            long id = Long.parseLong(stringArgument);
            Organization organizationToRemove = collectionManager.getById(id);
            if (organizationToRemove == null)
                throw new OrganizationNotFoundException();
            collectionManager.removeFromCollection(organizationToRemove);
            ResponseOutputer.appendln("Organization successfully removed!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendError("The collection is empty!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appendError("ID must be represented by a number!");
        } catch (OrganizationNotFoundException exception) {
            ResponseOutputer.appendError("There is no organization with this ID in the collection!");
        }
        return false;
    }
}
