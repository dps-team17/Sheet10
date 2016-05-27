package team17.sheet10b;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;

public class RSAServer {
	public static final int SERVICE_PORT = 12346;

	private Key privateKeyServer;
	private Key publicKeyClient;

	public RSAServer(String privKeyFileName, String pubKeyFilename) {
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
				EncryptedMessengerRSA messenger = new EncryptedMessengerRSA(connection);

				messenger.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RSAServer server = new RSAServer("privateServer.key", "publicClient.key");
		server.start();
	}
}
