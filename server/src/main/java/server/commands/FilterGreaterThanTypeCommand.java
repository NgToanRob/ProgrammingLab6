package server.commands;

import data.OrganizationType;
import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'filter_by_weapon_type'. Filters the collection by weapon type.
 */
public class FilterGreaterThanTypeCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public FilterGreaterThanTypeCommand(CollectionManager collectionManager) {
        super("filter_greater_than_type  <OrganizationType>",
                "{element}",
                "display elements whose organization type field value is equal to the given one");
        this.collectionManager = collectionManager;
    }

    /**
     * Prints the information about the organizations of the selected type
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
            OrganizationType type = OrganizationType.valueOf(stringArgument.toUpperCase());
            String filteredInfo = collectionManager.organizationTypeFilteredInfo(type);
            if (!filteredInfo.isEmpty()) {
                ResponseOutputer.appendln(filteredInfo);
                return true;
            } else
                ResponseOutputer.appendln("There are no organizations with the selected organization type in the collection!");
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendln("The collection is empty!");
        } catch (IllegalArgumentException exception) {
            ResponseOutputer.appendln("Organizations are not on the list!");
            ResponseOutputer.appendln("List of ranged organizations - " + OrganizationType.nameList());
        }
        return false;
    }
}
