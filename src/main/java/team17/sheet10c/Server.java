package team17.sheet10c;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;

import team17.sheet10b.EncryptedSocketConnectionRSA;
import team17.sheet10b.RSAMain;

public class Server {
	public static final int SERVICE_PORT = 12346;

	private Key privateKeyServer;
	private Key publicKeyClient;

	String secretKey;

	public Server(String privKeyFileName, String pubKeyFilename, String secretKey) {
		privateKeyServer = RSAMain.readPrivateKeyFromFile(privKeyFileName);
		publicKeyClient = RSAMain.readPublicKeyFromFile(pubKeyFilename);
	}

	public void start() {

		Socket clientSocket;

		try (ServerSocket serverSocket = new ServerSocket(SERVICE_PORT)) {

			System.out.println("Server started. Listening on port " + SERVICE_PORT);

			while ((clientSocket = serverSocket.accept()) != null) {

				EncryptedSocketConnectionRSA connection = new EncryptedSocketConnectionRSA(clientSocket,
						publicKeyClient, privateKeyServer);
				connection.showEncryptedMessages(false);
				connection.send(secretKey);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid parameter count! Expected Key.");
			System.exit(1);
		}

		String secretKey = args[0];
		Server server = new Server("privateServer.key", "publicClient.key", secretKey);
		server.start();
	}

}
