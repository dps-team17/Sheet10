package team17.sheet10a;

import java.io.IOException;
import java.util.Scanner;

public class EncryptedMessenger {

	private Thread listener;
	private Thread sender;
	private Scanner scanner;
	private EncryptedSocketConnection connection;

	public EncryptedMessenger(EncryptedSocketConnection connection) {
		this.connection = connection;
		this.scanner = new Scanner(System.in);
		createListener();
	}

	public void start() {

		try {
			listener.start();

			System.out.println("Connection established");

			while (!Thread.interrupted()) {

				sendMessage();
			}

			connection.close();
			listener.join();

			System.out.println("Connection terminated");

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage() throws IOException {

		if (System.in.available() == 0)
			return;

		String message = scanner.nextLine();

		if (message.equals("exit")) {

			sender.interrupt();
			return;
		}

		connection.send(message);
	}

	private void createListener() {

		sender = Thread.currentThread();

		listener = new Thread() {

			public void run() {
				String message;
				boolean listening = true;

				while (listening) {

					try {

						message = connection.receive();

						if (message == null)
							throw new NullPointerException();

						System.out.println("Other: " + message);

					} catch (IOException ignored) {
						listening = false;
					} catch (NullPointerException e) {
						sender.interrupt();
						listening = false;
					}
				}
			}
		};
	}
}
