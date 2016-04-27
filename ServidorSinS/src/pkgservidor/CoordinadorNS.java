package pkgservidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CoordinadorNS {
	private static ServerSocket ss;	
	private static final String MAESTRO = "MAESTRO: ";
	
	static int MAX=16;
	private int perdidos;
	private int tiempoTotal;
	private int tiempoPromedio;
	private int cantTiempos;
	private long tiempoTotalActua;
	private int cantTiemposActua;
	private long tiempoPromedioActua;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		CoordinadorNS coor = new CoordinadorNS();
		System.out.println(MAESTRO + "Establezca puerto de conexion:");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		int ip = Integer.parseInt(br.readLine());
		System.out.println(MAESTRO + "Empezando servidor maestro en puerto " + ip);	
		
		int idThread = 0;
		// Crea el socket que escucha en el puerto seleccionado.
		ss = new ServerSocket(ip);
		System.out.println(MAESTRO + "Socket creado.");
		
		ExecutorService fixedPool = Executors.newFixedThreadPool(MAX);
		while (true) {
			try { 
				// Crea un delegado por cliente. Atiende por conexion. 
				Socket sc = ss.accept();
				System.out.println(MAESTRO + "Cliente " + idThread + " aceptado.");
				DelegadoNS d = new DelegadoNS(sc,idThread, coor);
				idThread++;
				fixedPool.submit(d);
			} catch (IOException e) {
				System.out.println(MAESTRO + "Error creando el socket cliente.");
				e.printStackTrace();
			}
		}
		
		
	}
	public void aumentarPerdidos() {
		perdidos++;
		System.out.println("La cantidad de objetos perdidos son: "+ perdidos);
	}
	
	public void recalcularTiempo(long tiempo, long duracionActualizacion)
	{
		tiempoTotal+=tiempo;
		cantTiempos++;
		tiempoPromedio=tiempoTotal/cantTiempos;
		System.out.println("El tiempo promedio de autenticacion es: " + tiempoPromedio);
		
		tiempoTotalActua+=duracionActualizacion;
		cantTiemposActua++;
		tiempoPromedioActua=tiempoTotalActua/cantTiemposActua;
		System.out.println("El tiempo promedio de actualizacion es: " + tiempoPromedioActua);
	}

	public CoordinadorNS()
	{
		perdidos = 0;
		tiempoPromedio = 0;
		tiempoPromedioActua=0;
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
