package team17.sheet10b;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAMain {
	private static KeyPairGenerator kpgClient;
	private static KeyPair kpClient;
	private static Key publicKeyClient;
	private static Key privateKeyClient;

	private static KeyPairGenerator kpgServer;
	private static KeyPair kpServer;
	private static Key publicKeyServer;
	private static Key privateKeyServer;

	public static void main(String args[]) {
		try {
			kpgClient = KeyPairGenerator.getInstance("RSA");
			kpgClient.initialize(2048);
			kpClient = kpgClient.genKeyPair();
			publicKeyClient = kpClient.getPublic();
			privateKeyClient = kpClient.getPrivate();

			kpgServer = KeyPairGenerator.getInstance("RSA");
			kpgServer.initialize(2048);
			kpServer = kpgServer.genKeyPair();
			publicKeyServer = kpServer.getPublic();
			privateKeyServer = kpServer.getPrivate();

			KeyFactory fact = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec pub = fact.getKeySpec(kpClient.getPublic(), RSAPublicKeySpec.class);
			RSAPrivateKeySpec priv = fact.getKeySpec(kpClient.getPrivate(), RSAPrivateKeySpec.class);

			saveToFile("publicClient.key", pub.getModulus(), pub.getPublicExponent());
			saveToFile("privateClient.key", priv.getModulus(), priv.getPrivateExponent());

			pub = fact.getKeySpec(kpServer.getPublic(), RSAPublicKeySpec.class);
			priv = fact.getKeySpec(kpServer.getPrivate(), RSAPrivateKeySpec.class);

			saveToFile("publicServer.key", pub.getModulus(), pub.getPublicExponent());
			saveToFile("privateServer.key", priv.getModulus(), priv.getPrivateExponent());

			System.out.println(publicKeyClient.toString());
			System.out.println(privateKeyClient);
			System.out.println(publicKeyServer.toString());
			System.out.println(privateKeyServer);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {

		ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			oout.close();
		}
	}

	public static PublicKey readPublicKeyFromFile(String keyFileName) {
		ObjectInputStream oin = null;
		try {
			InputStream in = new FileInputStream(keyFileName);
			oin = new ObjectInputStream(new BufferedInputStream(in));
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey pubKey = fact.generatePublic(keySpec);
			return pubKey;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static PrivateKey readPrivateKeyFromFile(String keyFileName) {
		ObjectInputStream oin = null;
		try {
			InputStream in = new FileInputStream(keyFileName);
			oin = new ObjectInputStream(new BufferedInputStream(in));
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey privKey = fact.generatePrivate(keySpec);
			return privKey;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
