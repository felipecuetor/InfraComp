package Cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import Principal.CifradorAsimetrico;
import Principal.CifradorSimetrico;
import Principal.Transformacion;

public class Cliente {

	private PrintWriter out;
	private BufferedReader in;

	private OutputStream outByte;
	private InputStream inByte;

	public Socket socket;
	public CifradorSimetrico cSimetrico;
	public CifradorAsimetrico cAsimetrico;

	private PublicKey publicKey;
	private PrivateKey privateKey;

	/**
	 * Main encargado de inicializar valores y de manejar la comunicacion con el
	 * servidor
	 * 
	 * @param socketCliente
	 *            el socket de comunicacion con el servidor
	 */
	public Cliente(Socket socketCliente) {
		this.socket = socketCliente;

		PrivateKeyFactory factory = new PrivateKeyFactory();

		try {
			generarConexion();

			inicializacion();

			intercambioAlgoritmos();

			intercambioCertificados();

			intercambioLlaveSimetrica();

			envioPosicion();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Genera una entrada y una salida de String Genera una entrada y una salida
	 * de bytes
	 */
	public void generarConexion() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			outByte = socket.getOutputStream();
			inByte = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicializa comunicacion con el servidor y verifica su respuesta
	 * 
	 * @throws Exception
	 *             lanza excepcion cuando la respuesta es incorrecta
	 */
	private void inicializacion() throws Exception {
		out.println("HOLA");

		try {
			String entrada = in.readLine();
			System.out.println(entrada);

			if (!entrada.equals("INICIO"))
				throw new Exception("Respuesta incorrecta");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se encarga de enviar los algoritmos de comunicacion
	 * 
	 * @throws Exception
	 *             lanza excepcion cuando se retorna un mensaje de error
	 */
	private void intercambioAlgoritmos() throws Exception {
		out.print("ALGORITMOS");
		out.print(":");
		out.print("DES");
		out.print(":");
		out.print("RSA");
		out.print(":");
		out.println("HMACMD5");

		String entrada = "ERROR";
		try {
			entrada = in.readLine();
			System.out.println(entrada);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] sep = entrada.split(":");
		if (sep[1].equals("ERROR"))
			throw new Exception("ERROR intercambio de algoritmos");

	}

	/**
	 * Genera el certificado de el cliente
	 * 
	 * @return el certificado del cliente
	 */
	@SuppressWarnings("deprecation")
	private X509Certificate certificado() {

		Date startDate = Calendar.getInstance().getTime();
		int year = startDate.getYear();
		startDate.setYear(year + 5);
		Date expiryDate = startDate;
		BigInteger serialNumber = new BigInteger(256, new Random());

		KeyPairGenerator gen;
		KeyPair keyPair = null;
		try {
			gen = KeyPairGenerator.getInstance("RSA");
			gen.initialize(1024, new SecureRandom());
			keyPair = (gen.generateKeyPair());

			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
		X500Principal subjectName = new X500Principal("CN=Test V3 Certificate");

		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(subjectName);// caCert.getSubjectX500Principal());
		certGen.setNotBefore(startDate);
		certGen.setNotAfter(expiryDate);
		certGen.setSubjectDN(subjectName);
		certGen.setPublicKey(keyPair.getPublic());
		certGen.setSignatureAlgorithm("SHA224withRSA");

		try {
			return certGen.generate(keyPair.getPrivate());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Se encarga de enviar el certificado del cliente y de recibir el
	 * certificado del servidor
	 */
	private void intercambioCertificados() {
		out.println("CERCLNT:");

		try {
			X509Certificate cert = certificado();
			byte[] mybyte = cert.getEncoded();
			outByte.write(mybyte);
			outByte.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String entrada;
		try {

			entrada = in.readLine();
			System.out.println(entrada);

			byte[] certificado = new byte[100];
			inByte.read(certificado);
			System.out.println(certificado);

			PKCS10CertificationRequest kpGen = new PKCS10CertificationRequest(certificado);
			System.out.println(kpGen);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void intercambioLlaveSimetrica() {

	}

	private void envioPosicion() {

	}

}
