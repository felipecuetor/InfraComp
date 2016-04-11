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
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.*;

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

	public Cliente(Socket socketCliente) {
		this.socket = socketCliente;
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

	private java.security.cert.X509Certificate certificado() {
		
//		Calendar cal = Calendar.getInstance();
//		
//		Date startDate = cal.getTime();                // time from which certificate is valid
//		Date expiryDate = cal.getTime();               // time after which certificate is not valid
//		BigInteger serialNumber = null;       // serial number for certificate
//		PrivateKey caKey = null;              // private key of the certifying authority (ca) certificate
//		X509Certificate caCert = null;        // public key certificate of the certifying authority
//		KeyPair keyPair = null;               // public/private key pair that we are creating certificate for
//		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
//		X509Name subjectName = new X509Name("CN=Test V3 Certificate");
//		 
//		certGen.setSerialNumber(serialNumber);
//		certGen.setIssuerDN(null);
//		certGen.setNotBefore(startDate);
//		certGen.setNotAfter(expiryDate);
//		certGen.setSubjectDN(subjectName);
//		certGen.setPublicKey(keyPair.getPublic());
//		certGen.setSignatureAlgorithm(signatureAlgorithm);
//		certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
//		                        new AuthorityKeyIdentifierStructure(caCert));
//		certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
//		                        new SubjectKeyIdentifierStructure(keyPair.getPublic());
//		 
//		java.security.cert.X509Certificate cert = certGen.generate(caKey, "BC");   // note: private key of CA
//		
//		return cert;
		return null;
	}

	private void intercambioCertificados() {
		out.println("CERTCLNT:");

		try {
			java.security.cert.X509Certificate cert = certificado();
			byte[] mybyte = cert.getEncoded();
			outByte.write(mybyte);
			outByte.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
