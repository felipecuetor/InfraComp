package Principal;

import java.io.BufferedReader;
import java.security.MessageDigest;

public class Hash {

	public byte[] getKeyedDigest(byte[] buffer) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(buffer);
			return md5.digest();
		} catch (Exception e) {
			return null;
		}
	}

	public byte[] calcular(String stdIn) {
		try {
			String dato = stdIn;
			byte[] text = dato.getBytes();
			String s1 = new String(text);
			byte[] digest = getKeyedDigest(text);
			String s2 = new String(digest);
			return digest;
		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getMessage());
			return null;
		}
	}

}
