package server.commands;

import java.time.LocalDateTime;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import interaction.OrganizationPack;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;
import data.Organization;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the
 * minimal one.
 */
public class AddIfMinCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min {element}","{element}" ,"update the value of the collection element whose id is equal to the given one");
        this.collectionManager = collectionManager;
    }

    /**
     * Adds an organization to the collection
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
            OrganizationPack organizationPack = (OrganizationPack) objectArgument;
            Organization organizationToAdd = new Organization(
                    collectionManager.generateNextId(),
                    organizationPack.getName(),
                    organizationPack.getCoordinates(),
                    LocalDateTime.now(),
                    organizationPack.getAnnualTurnover(),
                    organizationPack.getOrganizationType(),
                    organizationPack.getOfficialAddress());

            if (collectionManager.collectionSize() == 0
                    || organizationToAdd.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(organizationToAdd);
                ResponseOutputer.appendln("Organization added successfully!");
                return true;
            } else
                ResponseOutputer.appendError(
                        "The value of an Organization is greater than the value of the smallest of the soldiers!");
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is invalid!");
        }
        return false;
    }
}
