package client;

import client.utility.UserHandler;
import exceptions.ConnectionErrorException;
import exceptions.NotInDeclaredLimitsException;
import interaction.Request;
import interaction.Response;
import utility.Outputer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Runs the client.
 */
public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processRequestToServer();
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        Outputer.printError("Connection attempts exceeded!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        Outputer.printError("Connection timeout '" + reconnectionTimeout +
                                "' is out of range!");
                        Outputer.println("Reconnection will be made immediately.");
                    } catch (Exception timeoutException) {
                        Outputer.printError("An error occurred while trying to connect!");
                        Outputer.println("Reconnection will be made immediately.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            Outputer.println("The client's work has been completed successfully.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printError("The client cannot be started!");
        } catch (IOException exception) {
            Outputer.printError("An error occurred while trying to terminate the connection to the server!");
        }
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1)
                Outputer.println("Reconnecting to the server...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            Outputer.println("The connection to the server was successfully established.");
            Outputer.println("Waiting for permission to share data...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            Outputer.println("Permission to share data has been received.");
        } catch (IllegalArgumentException exception) {
            Outputer.printError("Server address entered incorrectly!");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            Outputer.printError("An error occurred while connecting to the server!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Server request process.
     */
    private boolean processRequestToServer() {
        Request requestToServer = null; 
        Response serverResponse = null; 
        do {
            try {
                requestToServer = serverResponse != null ?
                        userHandler.handle(serverResponse.getResponseCode()) : userHandler.handle(null);

                if (requestToServer.isEmpty()) continue;

                serverWriter.writeObject(requestToServer);

                serverResponse = (Response) serverReader.readObject();
                Outputer.print(serverResponse.getResponseBody());

            } catch (InvalidClassException | NotSerializableException exception) {
                exception.printStackTrace();
                Outputer.printError("An error occurred while sending data to the server!");
            } catch (ClassNotFoundException exception) {
                Outputer.printError("An error occurred while reading the received data!");
            } catch (IOException exception) {
                Outputer.printError("The connection to the server has been broken!");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    if (requestToServer.getCommandName().equals("exit"))
                        Outputer.println("The command will not be registered to the server.");
                    else Outputer.println("Try to repeat the command later.");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }


}
