package team17.sheet10c;

import java.io.IOException;
import java.net.Socket;
import java.security.Key;

import team17.sheet10b.EncryptedSocketConnectionRSA;
import team17.sheet10b.RSAMain;

public class Client {
	private Key privateKeyClient;

	private Key publicKeyServer;

	private String secretKey;

	public Client(String privKeyFileName, String pubKeyFilename) {
		privateKeyClient = RSAMain.readPrivateKeyFromFile(privKeyFileName);
		publicKeyServer = RSAMain.readPublicKeyFromFile(pubKeyFilename);
	}

	public void startRSA() {

		String host = "127.0.0.1";
		int port = Server.SERVICE_PORT;

		try {
			Socket socket = new Socket(host, port);
			EncryptedSocketConnectionRSA connection = new EncryptedSocketConnectionRSA(socket, publicKeyServer,
					privateKeyClient);
			connection.showEncryptedMessages(false);
			secretKey = connection.receive();
			System.out.println(secretKey);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client("privateClient.key", "publicServer.key");
		client.startRSA();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
