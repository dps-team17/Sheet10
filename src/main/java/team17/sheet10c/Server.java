package team17.sheet10c;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;

import team17.sheet10a.EncryptedMessenger;
import team17.sheet10a.EncryptedSocketConnection;
import team17.sheet10b.EncryptedSocketConnectionRSA;
import team17.sheet10b.RSAMain;

public class Server {
	public static final int SERVICE_PORT_RSA = 55555;
	public static final int SERVICE_PORT_SECRET = 55556;

	private Key privateKeyServer;
	private Key publicKeyClient;

	private String secretKey;

	public Server(String privKeyFileName, String pubKeyFilename) {
		privateKeyServer = RSAMain.readPrivateKeyFromFile(privKeyFileName);
		publicKeyClient = RSAMain.readPublicKeyFromFile(pubKeyFilename);
	}

	public void start() {

		Socket clientSocket;

		try (ServerSocket serverSocket = new ServerSocket(SERVICE_PORT_RSA)) {

			System.out.println("Server started. Listening on port " + SERVICE_PORT_RSA);

			clientSocket = serverSocket.accept();

			EncryptedSocketConnectionRSA connection = new EncryptedSocketConnectionRSA(clientSocket, publicKeyClient,
					privateKeyServer);
			connection.showEncryptedMessages(false);
			secretKey = connection.receive();
			connection.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startSecretConnection() {
		Socket clientSocket;

		try (ServerSocket serverSocket = new ServerSocket(SERVICE_PORT_SECRET)) {

			System.out.println("Server started. Listening on port " + SERVICE_PORT_SECRET);

			while ((clientSocket = serverSocket.accept()) != null) {

				EncryptedSocketConnection connection = new EncryptedSocketConnection(clientSocket, secretKey);
				connection.showEncryptedMessages(false);
				EncryptedMessenger messenger = new EncryptedMessenger(connection);

				messenger.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server("privateServer.key", "publicClient.key");
		System.out.println("Get secret key!");
		server.start();
		System.out.println("Secret connection start:");
		server.startSecretConnection();
	}

}
