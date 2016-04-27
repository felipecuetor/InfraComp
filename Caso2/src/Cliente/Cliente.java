package Cliente;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import Principal.Cifrado;
import Principal.CifradorAsimetrico;
import Principal.CifradorSimetrico;
import Principal.Hash;
import Principal.Transformacion;
import uniandes.gload.core.Task;

public class Cliente {

	private final static String PADDING = "/ECB/PKCS5Padding";

	private PrintWriter out;
	private BufferedReader in;
	private OutputStream outByte;
	private InputStream inByte;

	public Socket socket;

	private PublicKey publicKey;
	private PrivateKey privateKey;

	private PublicKey publicKeySer;

	private CifradorAsimetrico asim;
	private CifradorSimetrico sim;

	private Key llaveSim;

	/**
	 * Main encargado de inicializar valores y de manejar la comunicacion con el
	 * servidor
	 * 
	 * @param socketCliente
	 *            el socket de comunicacion con el servidor
	 */
	public Cliente(Socket socketCliente) 
	{
		this.socket = socketCliente;

		PrivateKeyFactory factory = new PrivateKeyFactory();

		try {
			generarConexion();

			inicializacion();

			intercambioAlgoritmos();

			System.out.println("certificados:----------------------------");
			intercambioCertificados();

			System.out.println("llaves:----------------------------");
			intercambioLlaveSimetrica();

			System.out.println("posicion:----------------------------");
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

			asim = new CifradorAsimetrico(keyPair);
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

			byte[] certificado = new byte[1024];
			inByte.read(certificado);
			System.out.println(certificado);

			X509Certificate certSer = (X509Certificate) CertificateFactory.getInstance("X.509")
					.generateCertificate(new ByteArrayInputStream(certificado));
			publicKeySer = certSer.getPublicKey();
			certSer.verify(publicKeySer);
			System.out.println(certSer.toString());

			out.println("ESTADO:OK");

		} catch (Exception e) {
			e.printStackTrace();
			out.println("ESTADO:ERROR");
		}
	}

	private void intercambioLlaveSimetrica() throws Exception {

		try {
			String entrada = in.readLine();
			System.out.println(entrada);

			String[] div = entrada.split(":");

			String des = asim.descifrar(new Transformacion().destransformar(div[1]));

			SecretKey deskey = new SecretKeySpec(des.getBytes(), "DES");

			llaveSim = deskey;

			sim = new CifradorSimetrico(deskey);

			byte[] cif = asim.cifrarPublic(des, publicKeySer);

			out.println("DATA:" + new Transformacion().transformar(cif));

			String resp = in.readLine();
			System.out.println(resp);

			String[] split = resp.split(":");
			if (split[1].equals("ERROR"))
				throw new Exception("No se sincronizaron las llaves");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void envioPosicion() {
		String resp;
		try {
			String posicion = "4124.2028,210.4418";
			// Cifrar los datos
			byte[] cipher = Cifrado.cifrar(posicion.getBytes(), llaveSim, "DES" + PADDING);

			System.out.println(encapsular(cipher));

			// Enviar datos cifrados
			out.println("ACT1:" + encapsular(cipher));

			// Calcular el Hash
			// Calcular el MAC
			byte[] mac = Cifrado.calcularMAC(posicion.getBytes(), llaveSim, "HMACSHA1");

			// Enviar MAC
			out.println(
					"ACT2:" + new Transformacion().transformar(Cifrado.cifrar(mac, privateKey, "RSA")));

			String linea = in.readLine();
			System.out.println(linea);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String encapsular(byte[] data) {
		String rta = "";
		for (byte b : data)
			rta += String.format("%2s", Integer.toHexString((char) b & 0xFF)).replace(' ', '0');
		return rta;
	}

}
