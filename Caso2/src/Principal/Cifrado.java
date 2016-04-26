package Principal;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class Cifrado
{
	/**
	 * descifrá la información con la llave usando el algoritmo especificado
	 * @param data arreglo de bytes con la información que va a ser cifrada
	 * @param llave
	 * @param algoritmo si el algoritmo tiene padding tiene que estar especificado:  <b>algoritmo/padding</b>
	 * @return arreglo de bytes con la información cifrada
	 * @throws Exception
	 */
	public static byte[] descifrar(byte [] data, Key llave, String algoritmo) throws Exception
	{ 
		Cipher cipher = Cipher.getInstance(algoritmo); 
		cipher.init(Cipher.DECRYPT_MODE, llave);
		return cipher.doFinal(data); 
	}

	/**
	 * Cifra la información con la llave usando el algoritmo especificado
	 * @param data arreglo de bytes con la información que va a ser cifrada
	 * @param llave
	 * @param algoritmo si el algoritmo tiene padding tiene que estar especificado:  <b>algoritmo/padding</b>
	 * @return arreglo de bytes con la información cifrada
	 * @throws Exception
	 */
	public static byte[] cifrar(byte[] data, Key llave, String algoritmo) throws Exception
	{
		Cipher cipher = Cipher.getInstance(algoritmo);
		cipher.init(Cipher.ENCRYPT_MODE, llave);
		return cipher.doFinal(data);
	}
	
	/**
	 * Cálcula el código MAC de la información usando la llave y el algoritmo dado. 
	 * @param data arreglo de bytes con la información a la cual se le calculara el MAC
	 * @param llave 
	 * @param algoritmo especificación de algoritmo con el cual se calculará el MAC
	 * @return arreglo de bytes con el código MAC
	 * @throws Exception
	 */
	public static byte[] calcularMAC(byte[] data, Key llave, String algoritmo) throws Exception
	{
	    Mac mac = Mac.getInstance(algoritmo);
	    mac.init(llave);
	    return mac.doFinal(data);
	}
	
	/**
	 * Genera una pareja de llaves para cifrado asimétrico usando RSA
	 * @return un KeyPair aleatorio
	 * @throws Exception
	 */
	public static KeyPair crearLlaves() throws Exception
	{
	    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	    keyGen.initialize(1024, new SecureRandom());
	    return keyGen.generateKeyPair();
	}
	
	public static X509Certificate crearCertificado(KeyPair pair) throws Exception
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(System.currentTimeMillis()- 31535000000L); //Desde hoy hace un año
		Date startDate = gc.getTime();                // time from which certificate is valid
		gc.add(GregorianCalendar.YEAR, 4);			  //Valido por 4 años mas	
		Date expiryDate = gc.getTime();               // time after which certificate is not valid
		BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());       // serial number for certificate
		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
		X500Principal              subjectName = new X500Principal("CN=Test V3 Certificate");
		 
		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(new X500Principal("CN=Test Certificate"));
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		certGen.setSubjectDN(subjectName);
		certGen.setPublicKey(pair.getPublic());
		certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
		
		Security.addProvider(new BouncyCastleProvider());

		return  certGen.generate(crearLlaves().getPrivate(), "BC");  
	}
	
	public static Key generarLlave(String algoritmo) throws NoSuchAlgorithmException
	{
		KeyGenerator keygen = KeyGenerator.getInstance(algoritmo);
		return keygen.generateKey();
	}
}
