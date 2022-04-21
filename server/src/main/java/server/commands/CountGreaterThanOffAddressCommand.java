/**
 * Prints the number of elements whose officialAddress field value is greater than the specified one
 */
package server.commands;

import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import data.Address;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Prints the number of elements whose officialAddress field value is greater
 * than the specified one
 */
public class CountGreaterThanOffAddressCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public CountGreaterThanOffAddressCommand(CollectionManager collectionManager) {
        super("count_greater_than_official_address",
                "{element}",
                "print the number of elements whose officialAddress field value is greater than the specified one");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null)
                throw new WrongAmountOfElementsException();
            Address addressPack = (Address) objectArgument;

            ResponseOutputer.appendln("Number of elements whose officialAddress field value is greater than the given one: " 
            + collectionManager.getCountGreaterThanOffAddress(addressPack));
            return true; // create Address from street and zipcode

        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");

        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendError("The collection is empty!");
        }
        return true;
    }

}
