package server.commands;

import java.time.LocalDateTime;

import data.Address;
import data.Coordinates;
import data.Organization;
import data.OrganizationType;
import exceptions.CollectionIsEmptyException;
import exceptions.OrganizationNotFoundException;
import exceptions.WrongAmountOfElementsException;
import interaction.OrganizationPack;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update <ID> ", "","update the value of the collection element whose id is equal to the given one");
        this.collectionManager = collectionManager;
    }


    /**
     * The command updates organization with the given ID 
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.* @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument == null)
                throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0)
                throw new CollectionIsEmptyException();

            long id = Long.parseLong(stringArgument);
            Organization oldOrganization = collectionManager.getById(id);
            if (oldOrganization == null)
                throw new OrganizationNotFoundException();

            OrganizationPack organizationPack = (OrganizationPack) objectArgument;

            String name = organizationPack.getName() == null ?
                    oldOrganization.getName() : organizationPack.getName();
            Coordinates coordinates = organizationPack.getCoordinates() == null ?
                    oldOrganization.getCoordinates()
                    : organizationPack.getCoordinates();
            LocalDateTime creationDate = oldOrganization.getCreationDate();
            long annualTurnover = organizationPack.getAnnualTurnover() == null ?
                    oldOrganization.getAnnualTurnover()
                    : organizationPack.getAnnualTurnover();
            OrganizationType type = organizationPack.getType() == null ?
                    oldOrganization.getOrganizationType()
                    : organizationPack.getOrganizationType();
            Address address = organizationPack.getOfficialAddress() == null ?
                    oldOrganization.getOfficialAddress()
                    : organizationPack.getOfficialAddress();

            collectionManager.removeFromCollection(oldOrganization);
            collectionManager.addToCollection(new Organization(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    annualTurnover,
                    type,
                    address));
            collectionManager.sortById();
            ResponseOutputer.appendln("Organization successfully changed!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendError("Collection is empty!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appendError("ID must be represented by a number!");
        } catch (OrganizationNotFoundException exception) {
            ResponseOutputer.appendError("There is no organization with this ID in the collection!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appendError("The object passed by the client is invalid!!");
        }
        return false;
    }
}
