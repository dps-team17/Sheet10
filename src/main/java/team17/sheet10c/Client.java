package team17.sheet10c;

import java.io.IOException;
import java.net.Socket;
import java.security.Key;

import team17.sheet10a.EncryptedMessenger;
import team17.sheet10a.EncryptedSocketConnection;
import team17.sheet10b.EncryptedSocketConnectionRSA;
import team17.sheet10b.RSAMain;

public class Client {
	private Key privateKeyClient;

	private Key publicKeyServer;

	private String secretKey;

	private String host = "127.0.0.1";
	private int portRSA = Server.SERVICE_PORT_RSA;
	private int portSecret = Server.SERVICE_PORT_SECRET;

	public Client(String privKeyFileName, String pubKeyFilename, String secretKey) {
		privateKeyClient = RSAMain.readPrivateKeyFromFile(privKeyFileName);
		publicKeyServer = RSAMain.readPublicKeyFromFile(pubKeyFilename);
		this.secretKey = secretKey;
	}

	public void startRSA() {
		try {
			Socket socket = new Socket(host, portRSA);
			EncryptedSocketConnectionRSA connection = new EncryptedSocketConnectionRSA(socket, publicKeyServer,
					privateKeyClient);
			connection.showEncryptedMessages(false);
			connection.send(secretKey);
			connection.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startSecretConnection() {
		try {
			Socket socket = new Socket(host, portSecret);
			EncryptedSocketConnection connection = new EncryptedSocketConnection(socket, secretKey);
			connection.showEncryptedMessages(false);
			EncryptedMessenger messenger = new EncryptedMessenger(connection);

			messenger.start();

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

		Client client = new Client("privateClient.key", "publicServer.key", secretKey);
		System.out.println("Send secret key!");
		client.startRSA();
		System.out.println("Secret connection start:");
		client.startSecretConnection();
	}

}
