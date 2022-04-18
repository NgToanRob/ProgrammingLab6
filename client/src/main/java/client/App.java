package client;

import client.utility.UserHandler;
import exceptions.NotInDeclaredLimitsException;
import exceptions.WrongAmountOfElementsException;
import utility.Outputer;

import java.util.Scanner;

public class App {
        private static final int RECONNECTION_TIMEOUT = 5 * 1000;
        private static final int MAX_RECONNECTION_ATTEMPTS = 5;

        // private static String host;
        // private static int port;
        private static String host;
        private static int port ;



    public static boolean initializeConnectionAddress(String[] hostAndPortArgs) {
        try {
            if (hostAndPortArgs.length != 2) throw new WrongAmountOfElementsException();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);
            if (port < 0) throw new NotInDeclaredLimitsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            String jarName = new java.io.File(App.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            Outputer.println("Using: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
            Outputer.printError("The port must be represented by a number!");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printError("The port cannot be negative!");
        }
        return false;
    }

    public static void main(String[] args) {
        String _host = "localhost";
        String _port = "4999";
        String[] hostAndPortArgs = new String[]{_host, _port};
        if (!initializeConnectionAddress(hostAndPortArgs)) return;

        Scanner userScanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler(userScanner);
        Client client = new Client(
                host,
                port,
                RECONNECTION_TIMEOUT,
                MAX_RECONNECTION_ATTEMPTS,
                userHandler
        );
        client.run();
        userScanner.close();
    }
}
