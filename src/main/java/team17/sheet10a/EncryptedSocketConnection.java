package team17.sheet10a;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EncryptedSocketConnection {

	private boolean showEncryptedMessages;
	private VigenereCipher cipher;
	private Socket socket;
	private OutputStream out;
	private InputStream in;

	public EncryptedSocketConnection(Socket connection, String key) throws IOException {

		showEncryptedMessages = false;
		cipher = new VigenereCipher(key);

		socket = connection;
		out = socket.getOutputStream();
		in = socket.getInputStream();
	}

	public void send(String plain) throws IOException {

		byte[] encrypted = cipher.encrypt(plain);

		if (showEncryptedMessages) {
			printMessage(encrypted, "Sending");
		}

		out.write(encrypted);
		out.flush();
	}

	public String receive() throws IOException {

		byte[] buff = new byte[1024];
		int bytesRead = in.read(buff, 0, buff.length);

		if (bytesRead <= 0)
			return null;

		byte[] encrypted = new byte[bytesRead];
		System.arraycopy(buff, 0, encrypted, 0, encrypted.length);

		if (showEncryptedMessages) {
			printMessage(encrypted, "Received");
		}

		return cipher.decrypt(encrypted);
	}

	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}

	public void showEncryptedMessages(boolean value) {
		showEncryptedMessages = value;
	}

	private void printMessage(byte[] message, String action) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < message.length; i++) {
			if (i < 0)
				sb.append(" ");
			sb.append(String.format("[%d]", message[i]));
		}

		System.out.printf("%s: (%s)\n%s\n", action, new String(message), sb.toString());
	}
}
