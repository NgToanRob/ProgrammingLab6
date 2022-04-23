package server;

import exceptions.ClosingSocketException;
import exceptions.ConnectionErrorException;
import exceptions.OpeningServerSocketException;
import interaction.Request;
import interaction.Response;
import interaction.ResponseCode;
import server.utility.RequestHandler;
import utility.Outputer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * The Server class is a class that represents a server.
 */
public class Server {

    private int port;
    private int soTimeout;
    private ServerSocket serverSocket;
    private RequestHandler requestHandler;

    public Server(int port, int soTimeout, RequestHandler requestHandler) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.requestHandler = requestHandler;
    }

    /**
     * Starts server operation.
     */
    public void start(){
        try {
            openServerSocket(); // throws OpeningServerSocketException

            boolean processingStatus = true;
            while (processingStatus) {
                try (Socket clientSocket = connectToClient()) {
                    processingStatus = processClientRequest(clientSocket);

                } catch (ConnectionErrorException | SocketTimeoutException exception) {
                    Outputer.printError("Connection timed out. Reconnect! ");
                    App.logger.error("Connection timed out. Reconnect!");
                    processingStatus = false; // Test logic sau nhe

                } catch (IOException exception) {
                    Outputer.printError("An error occurred while trying to terminate the connection with the client!");
                    App.logger.error("An error occurred while trying to terminate the connection with the client!");
                }
            }
            stop();
        } catch (OpeningServerSocketException exception) {
            Outputer.printError("The server cannot be started!");
            App.logger.error("The server cannot be started!");
        }
    }

    /**
     * Stops server operation.
     */
    public void  stop(){
        try {
            App.logger.info("Shutting down the server...");

            if (serverSocket == null) throw new ClosingSocketException();
            serverSocket.close();
            Outputer.println("Server operation completed successfully.");
            App.logger.info("Server operation completed successfully.");

        } catch (ClosingSocketException exception) {
            Outputer.printError("It is not possible to shut down a server that has not yet been started!");
            App.logger.error("It is not possible to shut down a server that has not yet been started!");

        } catch (IOException exception) {
            Outputer.printError("An error occurred while shutting down the server!");
            App.logger.error("An error occurred while shutting down the server!");
        }
    }

    // Methods are used in start method

    /**
     * This function opens a server socket.
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try {
            App.logger.info("Server start...");
            serverSocket = new ServerSocket(port); // throws IOException
            serverSocket.setSoTimeout(soTimeout); // throws IllegalArgumentException "time out < 0"
            App.logger.info("The server started successfully.");

        } catch (IllegalArgumentException exception) {
            Outputer.printError("Port '" + port + "' is out of range!");
            App.logger.error("Port '" + port + "' is out of range!");
            throw new OpeningServerSocketException();

        } catch (IOException exception) {
            Outputer.printError("An error occurred while trying to use port " + port + "'!");
            App.logger.error("An error occurred while trying to use port " + port + "'!");
            throw new OpeningServerSocketException();
        }
    }


    /**
     * It tries to connect to the client, and if it fails, it throws an exception
     *
     * @return Socket
     */
    private Socket connectToClient() throws SocketTimeoutException, ConnectionErrorException {
        try {
            Outputer.println("Port listening '" + port + "'...");
            App.logger.info("Port listening '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Outputer.println("The connection with the client was successfully established.");
            App.logger.info("The connection with the client was successfully established.");
            return clientSocket;
        } catch (SocketTimeoutException exception) {
            Outputer.printError("Connection timed out!");
            App.logger.warn("Connection timed out!");
            throw new SocketTimeoutException();
        } catch (IOException exception) {
            Outputer.printError("An error occurred while connecting to the client!");
            App.logger.error("An error occurred while connecting to the client!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * It reads a request from the client, processes it, and sends a response to the client
     *
     * @param clientSocket the socket of the client to which the server is connected.
     * @return The method returns a boolean value.
     */
    private boolean processClientRequest(Socket clientSocket){
        Request userRequest = null;
        Response responseToUser = null;

        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject(); // throws ClassNotFoundException
                responseToUser = requestHandler.handle(userRequest);
                App.logger.info("Request '" + userRequest.getCommandName() + "' processed successfully.");
                clientWriter.writeObject(responseToUser); // throws InvalidClassException | NotSerializableException
                clientWriter.flush();

            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;

        } catch (ClassNotFoundException exception) {
            Outputer.printError("An error occurred while reading received data!");
            App.logger.error("An error occurred while reading received data!");
            
        } catch (InvalidClassException | NotSerializableException exception) {
            Outputer.printError("An error occurred while sending data to the client!");
            App.logger.error("An error occurred while sending data to the client!");
            
        } catch (IOException exception) {
            if (userRequest == null) {
                Outputer.printError("Unexpected termination of connection with the client!");
                App.logger.error("Unexpected termination of connection with the client!");

            } else {
                Outputer.println("Client successfully disconnected from server!");
                App.logger.info("Client successfully disconnected from server!");
            }
        }
        return true;
    }

}
