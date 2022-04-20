package server.utility;

import interaction.Request;
import interaction.Response;
import interaction.ResponseCode;

/**
 * Handles requests.
 */
public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
    public Response handle(Request request) {
        commandManager.addToHistory(request.getCommandName());
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private ResponseCode executeCommand(String command, String commandStringArgument,
                                        Object commandObjectArgument) {
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "info":
                if (!commandManager.info(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.update(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "save":
                if (!commandManager.save(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add_if_min":
                if (!commandManager.addIfMin(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_lower":
                if (!commandManager.removeLower(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "history":
                if (!commandManager.history(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "average_of_annual_turnover":
                if (!commandManager.averageOfAnnualTurnover(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "count_greater_than_official_address":
                if (!commandManager.countGreaterThanOfficialAddress(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "filter_greater_than_type":
                if (!commandManager.filterGreaterThanType(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "server_exit":
                if (!commandManager.serverExit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                return ResponseCode.SERVER_EXIT;
            default:
                ResponseOutputer.appendln("Command " + command + " not found. Type 'help' for help.");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}
