package server.commands;

import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'sum_of_health'. Prints the sum of health of all marines.
 */
public class AverageOfAnnualTurnoverCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public AverageOfAnnualTurnoverCommand(CollectionManager collectionManager) {
        super("average_of_annual_turnover", "{element}" ,"read and execute the script from the specified file");
        this.collectionManager = collectionManager;
    }

    /**
     * Prints the average value of annual turnover for all organizations
     *
     * @param stringArgument The argument is the user input.
     * @param objectArgument The object is the user input.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null)
                throw new WrongAmountOfElementsException();
            double averageOfAnnualTurnover = collectionManager.getAverageOfAnnualTurnover();

            ResponseOutputer.appendln("Average value of annual turnover for all organizations: " + averageOfAnnualTurnover);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appendError("The collection is empty!");
        }
        return false;
    }
}
