package team17.sheet10b;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptedSocketConnectionRSA {

	private boolean showEncryptedMessages;
	private Cipher enc;
	private Cipher dec;
	private Socket socket;
	private OutputStream out;
	private InputStream in;

	public EncryptedSocketConnectionRSA(Socket connection, Key pubKey, Key privKey) throws IOException {

		showEncryptedMessages = false;
		try {
			enc = Cipher.getInstance("RSA");
			dec = Cipher.getInstance("RSA");
			enc.init(Cipher.ENCRYPT_MODE, pubKey);
			dec.init(Cipher.DECRYPT_MODE, privKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		socket = connection;
		out = socket.getOutputStream();
		in = socket.getInputStream();
	}

	public void send(String plain) throws IOException {

		byte[] encrypted = encrypt(plain);

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

		return decrypt(encrypted);
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

	private byte[] encrypt(String message) {
		try {
			return enc.doFinal(message.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String decrypt(byte[] message) {
		try {
			return new String(dec.doFinal(message));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
