package team17.sheet10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SuperSecretServer {

    public static final int SERVICE_PORT = 12345;
    private static SuperSecretEncryptor encryptor;

    public static void main(String[] args) {

        encryptor = new SuperSecretEncryptor(args[0]);
        Socket clientSocket;

        try (ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) {

            System.out.println("Server started. Listening on port " + SERVICE_PORT);

            while ((clientSocket = serverSocket.accept()) != null) {

                handleRequest(clientSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String request = null;
            String response;

            while ((request = in.readLine()) != null) {

                System.out.println("Received:  " + request);
                System.out.println("Decrypted: " + encryptor.decrypt(request));
            }

            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
