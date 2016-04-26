package Principal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class CifradorAsimetrico {

	private final static String ALGORITMO = "RSA";

	private KeyPair keyPair;

	public CifradorAsimetrico(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public byte[] cifrar(String dat) {
		try {

			KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITMO);
			generator.initialize(1024);
			keyPair = generator.generateKeyPair();
			Cipher cipher = Cipher.getInstance(ALGORITMO);
			String pwd = dat;
			byte[] clearText = pwd.getBytes();
			String s1 = new String(clearText);
			System.out.println("clave original: " + s1);
			cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
			long startTime = System.nanoTime();
			byte[] cipheredText = cipher.doFinal(clearText);
			long endTime = System.nanoTime();
			System.out.println("clave cifrada: " + cipheredText);
			System.out.println("Tiempo asimetrico: " + (endTime - startTime));
			return cipheredText;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Excepcion: " + e.getMessage());
			return null;
		}
	}

	public String descifrar(byte[] cipheredText) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITMO);
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

			byte[] clearText = cipher.doFinal(cipheredText);
			String s3 = new String(clearText);
			return s3;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public byte[] cifrarPublic(String data, PublicKey llave) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITMO);
			generator.initialize(1024);
			Cipher cipher = Cipher.getInstance(ALGORITMO);

			String pwd = data;
			byte[] clearText = pwd.getBytes();
			String s1 = new String(clearText);
			cipher.init(Cipher.ENCRYPT_MODE, llave);
			byte[] cipheredText = cipher.doFinal(clearText);
			return cipheredText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public byte[] cifrarPrivate(String string, PrivateKey llave) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITMO);

			String pwd = string;
			byte[] clearText = pwd.getBytes();
			String s1 = new String(clearText);
			cipher.init(Cipher.ENCRYPT_MODE, llave);
			byte[] cipheredText = cipher.doFinal(clearText);
			return cipheredText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
