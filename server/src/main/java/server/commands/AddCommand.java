package server.commands;

import java.time.LocalDateTime;

import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import interaction.OrganizationPack;
import server.utility.CollectionManager;
import data.Organization;
import server.utility.ResponseOutputer;

/**
 * Adds a new organization to the collection
 */
public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "{element}", "add a new element to the collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Add an organization to the collection
     * 
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.
     * @return statement of the command.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null)
                throw new WrongAmountOfElementsException();

            OrganizationPack organizationPack = (OrganizationPack) objectArgument;

            collectionManager.addToCollection(new Organization(
                    collectionManager.generateNextId(),
                    organizationPack.getName(),
                    organizationPack.getCoordinates(),
                    LocalDateTime.now(),
                    organizationPack.getAnnualTurnover(),
                    organizationPack.getOrganizationType(),
                    organizationPack.getOfficialAddress()));
            ResponseOutputer.appendln("Organization added successfully!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is invalid!");
        }
        return false;
    }
}
