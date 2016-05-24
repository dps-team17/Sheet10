package team17.sheet10;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SuperSecretServer {

    static final int SERVICE_PORT = 12345;

    public static void main(String[] args) {

        String key = args[0];
        Socket clientSocket;

        try (ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) {

            System.out.println("Server started. Listening on port " + SERVICE_PORT);

            while ((clientSocket = serverSocket.accept()) != null) {

                EncryptedSocketConnection connection = new EncryptedSocketConnection(clientSocket, key);
                connection.showEncryptedMessages(true);
                EncryptedMessenger messenger = new EncryptedMessenger(connection);

                messenger.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
