package pkgservidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.Security;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase servidor con seguridad
 *
 */
public class Coordinador {

	private static ServerSocket ss;	
	private static final String MAESTRO = "MAESTRO: ";
	static java.security.cert.X509Certificate certSer; /* acceso default */
	static KeyPair keyPairServidor; /* acceso default */
	static int MAX=2;
	private int perdidos;
	private int tiempoTotal;
	private int tiempoPromedio;
	private int cantTiempos;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		Coordinador coor = new Coordinador();
		
		System.out.println(MAESTRO + "Establezca puerto de conexion:");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		int ip = Integer.parseInt(br.readLine());
		System.out.println(MAESTRO + "Empezando servidor maestro en puerto " + ip);
		// Adiciona la libreria como un proveedor de seguridad.
		// Necesario para crear llaves.
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());		
		
		int idThread = 0;
		// Crea el socket que escucha en el puerto seleccionado.
		ss = new ServerSocket(ip);
		System.out.println(MAESTRO + "Socket creado.");
		
		keyPairServidor = S.grsa();
		certSer = S.gc(keyPairServidor);
		
		ExecutorService fixedPool = Executors.newFixedThreadPool(MAX);
		while (true) {
			try { 
				// Crea un delegado por cliente. Atiende por conexion.
				System.out.println("Esperando");
				Socket sc = ss.accept();
				System.out.println(MAESTRO + "Cliente " + idThread + " aceptado.");
				Delegado d = new Delegado(sc,idThread, coor);
				idThread++;
				fixedPool.submit(d);
			} catch (IOException e) {
				System.out.println(MAESTRO + "Error creando el socket cliente.");
				e.printStackTrace();
				coor.aumentarPerdidos();
				
			}
		}
		
	}
	
	private void aumentarPerdidos() {
		perdidos++;
		System.out.println("La cantidad de objetos perdidos son: "+ perdidos);
	}
	
	public void recalcularTiempo(long tiempo)
	{
		tiempoTotal+=tiempo;
		cantTiempos++;
		tiempoPromedio=tiempoTotal/cantTiempos;
		System.out.println("El tiempo promedio es: " + tiempoPromedio);
	}

	public Coordinador()
	{
		perdidos = 0;
		tiempoPromedio = 0;
	}
	
	public int getTiempoPromedio()
	{
		return tiempoPromedio;
	}
	
	public int getPerdidos()
	{
		return perdidos;
	}
}
