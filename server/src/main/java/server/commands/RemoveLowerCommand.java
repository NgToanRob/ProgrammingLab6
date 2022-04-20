package server.commands;

import java.time.LocalDateTime;

import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import interaction.OrganizationPack;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;
import data.Organization;

/**
 * Command 'remove_lower'. Removes elements greater than user entered.
 */
public class RemoveLowerCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower ", "{element}","remove from the collection all elements greater than the specified");
        this.collectionManager = collectionManager;
    }

    /**
     * Remove the organization with the higher annual turnover from the collection
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.* @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null)
                throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0)
                throw new CollectionIsEmptyException();
            OrganizationPack organizationPack = (OrganizationPack) objectArgument;
            Organization organizationToCompare = new Organization(
                    collectionManager.generateNextId(),
                    organizationPack.getName(),
                    organizationPack.getCoordinates(),
                    LocalDateTime.now(),
                    organizationPack.getAnnualTurnover(),
                    organizationPack.getOrganizationType(),
                    organizationPack.getOfficialAddress());
            collectionManager.removeLower(organizationToCompare);
            ResponseOutputer.appendln("Organizaition successfully removed!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendln("The collection is empty!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is invalid!");
        }

        return false;
    }
}
