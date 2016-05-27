package team17.sheet10b;

import java.io.IOException;
import java.net.Socket;
import java.security.Key;

public class RSAClient {
	private Key privateKeyClient;

	private Key publicKeyServer;

	public RSAClient(String privKeyFileName, String pubKeyFilename) {
		privateKeyClient = RSAMain.readPrivateKeyFromFile(privKeyFileName);
		publicKeyServer = RSAMain.readPublicKeyFromFile(pubKeyFilename);
	}

	public void start() {

		String host = "127.0.0.1";
		int port = RSAServer.SERVICE_PORT;

		try {
			Socket socket = new Socket(host, port);
			EncryptedSocketConnectionRSA connection = new EncryptedSocketConnectionRSA(socket, publicKeyServer,
					privateKeyClient);
			connection.showEncryptedMessages(false);
			EncryptedMessengerRSA messenger = new EncryptedMessengerRSA(connection);

			messenger.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RSAClient client = new RSAClient("privateClient.key", "publicServer.key");
		client.start();
	}
}
