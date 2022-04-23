package server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.commands.*;
import server.utility.CollectionFileManager;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.RequestHandler;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

/**
 * Main server class. Creates all server instances.
 *
 * @author Nguyen Toan.
 */
public class App {
    public static final int PORT = 4999;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final String ENV_VARIABLE = "TOAN";
//    public static Logger logger = LogManager.getLogger("ServerLogger");
    public static final Logger logger = LoggerFactory.getLogger("ServerLogger");
    public static void main(String[] args) {
        CollectionFileManager collectionFileManager = new CollectionFileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExitCommand(collectionManager),
                new ExecuteScriptCommand(),
                new AddIfMinCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new HistoryCommand(),
                new AverageOfAnnualTurnoverCommand(collectionManager),
                new CountGreaterThanOffAddressCommand(collectionManager),
                new FilterGreaterThanTypeCommand(collectionManager)
        );

        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.start();
    }
}
