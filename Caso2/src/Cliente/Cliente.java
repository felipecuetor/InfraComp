package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
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
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

import Principal.CifradorAsimetrico;
import Principal.CifradorSimetrico;

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

	public Cliente(Socket socketCliente) {
		this.socket = socketCliente;

		PrivateKeyFactory factory = new PrivateKeyFactory();

		try {
			generarConexion();

			inicializacion();

			intercambioAlgoritmos();

			intercambioCertificados();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	private void inicializacion() {
		out.println("HOLA");

		try {
			String entrada = in.readLine();
			System.out.println(entrada);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	private X509Certificate certificado() {
		
//		Date startDate = Calendar.getInstance().getTime();
//		int year = startDate.getDate();
//		startDate.setYear(year+5);
//		Date expiryDate = startDate;
//		BigInteger serialNumber = new BigInteger(1024, new Random());
//		PrivateKey caKey = ;
//		X509Certificate nombreExpedidor = new X509Certificate();
//		
//		KeyPair keyPair = (new KeyPairGenerator("RAS")).generateKeyPair();
//		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
//		X500Principal subjectName = new X500Principal("CN=Test V3 Certificate");
//		String signatureAlgorithm = "";
//		
//		
//		certGen.setSerialNumber(serialNumber);
//		certGen.setIssuerDN(nombreExpedidor);//caCert.getSubjectX500Principal());
//		certGen.setNotBefore(startDate);
//		certGen.setNotAfter(expiryDate);
//		certGen.setSubjectDN(subjectName);
//		certGen.setPublicKey(keyPair.getPublic());
//		certGen.setSignatureAlgorithm(signatureAlgorithm);
		
		return null;
	}

	private void intercambioCertificados() {
		out.println("CERTCLNT:");

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
			
			
			byte[] certificado= new byte[100];
			inByte.read(certificado);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
