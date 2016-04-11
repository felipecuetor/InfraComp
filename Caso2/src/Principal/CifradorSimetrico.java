package Principal;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CifradorSimetrico {

	private SecretKey desKey;
	private final static String ALGORITMO = "AES";
	private final static String PADDING = "AES/ECB/PKCS5Padding";
	
	public CifradorSimetrico(SecretKey deskey)
	{
		this.desKey=deskey;
	}

	public byte[] cifrar(String men) {
		
		byte[] cipheredText;
		try {
			KeyGenerator keygen = KeyGenerator.getInstance(ALGORITMO);
			desKey = keygen.generateKey();
			Cipher cipher = Cipher.getInstance(PADDING);
			String pwd = men;
			byte[] clearText = pwd.getBytes();
			String s1 = new String(clearText);
			System.out.println("	clave original: " + s1);
			cipher.init(Cipher.ENCRYPT_MODE, desKey);

			long startTime = System.nanoTime();
			cipheredText = cipher.doFinal(clearText);

			long endTime = System.nanoTime();
			String s2 = new String(cipheredText);
			System.out.println("	clave cifrada: " + s2);
			System.out.println("	Tiempo: " + (endTime - startTime));

			return cipheredText;
		} catch (Exception e) {
			System.out.println("	Excepcion: " + e.getMessage());
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
