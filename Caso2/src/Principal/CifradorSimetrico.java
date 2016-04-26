package Principal;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CifradorSimetrico {

	private Key desKey;
	private final static String ALGORITMO = "RC4";
	private final static String PADDING = "RC4";

	public CifradorSimetrico(Key deskey) {
		this.desKey = deskey;
	}

	public byte[] cifrar(String men) {

		byte[] cipheredText;
		try {
			Cipher cipher = Cipher.getInstance(PADDING);
			String pwd = men;
			byte[] clearText = (new Transformacion().destransformar(men));

			cipher.init(Cipher.ENCRYPT_MODE, desKey);

			long startTime = System.nanoTime();
			cipheredText = cipher.doFinal(clearText);

			long endTime = System.nanoTime();

			System.out.println("LLEGO");
			System.out.println(cipheredText);
			return cipheredText;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String descifrar(byte[] cipheredText) {
		try {
			Cipher cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			byte[] clearText = cipher.doFinal(cipheredText);
			String s3 = new String(clearText);
			System.out.println("	clave original: " + s3);

			return s3;
		} catch (Exception e) {
			System.out.println("	Excepcion: " + e.getMessage());
		}
		return null;
	}

}
