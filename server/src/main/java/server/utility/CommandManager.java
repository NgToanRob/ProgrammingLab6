package server.utility;

import exceptions.HistoryIsEmptyException;
import server.commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 9;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private List<Command> commands = new ArrayList<>();
    private Command helpCommand;
    private Command infoCommand;
    private Command showCommand;
    private Command addCommand;
    private Command updateCommand;
    private Command removeByIdCommand;
    private Command clearCommand;
    private Command saveCommand;
    private Command exitCommand;
    private Command executeScriptCommand;
    private Command addIfMinCommand;
    private Command removeGreaterCommand;
    private Command historyCommand;
    private Command averageOfAnnualTurnoverCommand;
    private Command countGreaterThanOfficialAddressCommand;
    private Command filterGreaterThanTypeCommand;

    public CommandManager(Command helpCommand,
                          Command infoCommand,
                          Command showCommand,
                          Command addCommand,
                          Command updateCommand,
                          Command removeByIdCommand,
                          Command clearCommand,
                          Command saveCommand,
                          Command exitCommand,
                          Command executeScriptCommand,
                          Command addIfMinCommand,
                          Command removeGreaterCommand,
                          Command historyCommand,
                          Command averageOfAnnualTurnoverCommand,
                          Command countGreaterThanOfficialAddressCommand,
                          Command filterGreaterThanTypeCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.removeGreaterCommand = removeGreaterCommand;
        this.historyCommand = historyCommand;
        this.averageOfAnnualTurnoverCommand = averageOfAnnualTurnoverCommand;
        this.countGreaterThanOfficialAddressCommand = countGreaterThanOfficialAddressCommand;
        this.filterGreaterThanTypeCommand = filterGreaterThanTypeCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(addIfMinCommand);
        commands.add(removeGreaterCommand);
        commands.add(historyCommand);
        commands.add(averageOfAnnualTurnoverCommand);
        commands.add(countGreaterThanOfficialAddressCommand);
        commands.add(filterGreaterThanTypeCommand);
    }




    /**
     * @return List of manager's commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Adds command to command history.
     *
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) {

        for (Command command : commands) {
            if (command.getName().equals(commandToStore)) {
                System.arraycopy(commandHistory, 0, commandHistory, 1, COMMAND_HISTORY_SIZE - 1);
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints info about the all commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean help(String stringArgument, Object objectArgument) {
        if (helpCommand.execute(stringArgument, objectArgument)) {
            for (Command command : commands) {
                ResponseOutputer.appendTable(command.getName() + " " + command.getUsage(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean info(String stringArgument, Object objectArgument) {
        return infoCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean show(String stringArgument, Object objectArgument) {
        return showCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean add(String stringArgument, Object objectArgument) {
        return addCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean update(String stringArgument, Object objectArgument) {
        return updateCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeById(String stringArgument, Object objectArgument) {
        return removeByIdCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument, Object objectArgument) {
        return clearCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean save(String stringArgument, Object objectArgument) {
        return saveCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument, Object objectArgument) {
        return exitCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument) {
        return executeScriptCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean addIfMin(String stringArgument, Object objectArgument) {
        return addIfMinCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeLower(String stringArgument, Object objectArgument) {
        return removeGreaterCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Prints the history of used commands.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean history(String stringArgument, Object objectArgument) {
        if (historyCommand.execute(stringArgument, objectArgument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.appendln("Last used commands:");
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendln(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendln("Not a single team has been used yet!");
            }
        }
        return false;
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean averageOfAnnualTurnover(String stringArgument, Object objectArgument) {
        return averageOfAnnualTurnoverCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean countGreaterThanOfficialAddress(String stringArgument, Object objectArgument) {
        return countGreaterThanOfficialAddressCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean filterGreaterThanType(String stringArgument, Object objectArgument) {
        return filterGreaterThanTypeCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean serverExit(String stringArgument, Object objectArgument) {
        return exitCommand.execute(stringArgument, objectArgument);
    }
}
