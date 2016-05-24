package team17.sheet10;

import team17.sheet10.helpers.KeyIn;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SuperSecretClient {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Invalid parameter count! Expected Key.");
            System.exit(1);
        }

        SuperSecretEncryptor encryptor = new SuperSecretEncryptor(args[0]);
        String message;
        String encrypted;

        try (
                Socket socket = new Socket("127.0.0.1", SuperSecretServer.SERVICE_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {

            while (!(message = KeyIn.inString("Message: ")).equals("exit")) {

                encrypted = encryptor.encrypt(message);
                out.println(encrypted);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
