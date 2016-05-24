package team17.sheet10;

import java.io.IOException;
import java.net.Socket;

public class SuperSecretClient {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Invalid parameter count! Expected Key.");
            System.exit(1);
        }

        String key = args[0];
        String host = "127.0.0.1";
        int port = SuperSecretServer.SERVICE_PORT;

        try {
            Socket socket = new Socket(host, port);
            EncryptedSocketConnection connection = new EncryptedSocketConnection(socket, key);
            connection.showEncryptedMessages(true);
            EncryptedMessenger messenger = new EncryptedMessenger(connection);

            messenger.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
