/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 *
 * Proyecto Caso1
 * Infraestructura Computacional
 * Autor: Felipe Cueto  - Marzo 1, 2016
 * Autor: Paula Ramirez - Marzo 1, 2016
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package Principal;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import Cliente.Cliente;
import Cliente.Mensaje;
import Servidor.Servidor;




public class Main {
	
	// -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
	
	/**
	 * Registros de las consultas
	 */
	private static ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();

	/**
	 * Total de clientes que han terminado
	 */
	private static int totalClientes;
	

	/**
	 * Ejecuci—n de la aplicaci—n. <br>
	 * Se encarga de leer el archivo de propiedades, iniciar los servidores y los clientes.
	 * Espera a que se terminen las consultas y verifica que las respuestas hayan sido correctas. 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Caso1 iniciado...\n");
		//Tiempo de inicio de ejecucion
		long inicio = System.currentTimeMillis();
		
		//Propiedades de la ejecucion
		Properties prop = new Properties();
		prop.load(new FileInputStream(new File("./data/data.txt")));
		int nServidores = Integer.parseInt(prop.getProperty("num_servidores"));
		int noClientes = Integer.parseInt(prop.getProperty("num_clientes"));
		int consultas = Integer.parseInt(prop.getProperty("consultas_cliente"));
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		ArrayList<Servidor> servidores = new ArrayList<Servidor>();
		//Buffer buffer = new Buffer(Integer.parseInt(prop.getProperty("buffer.capacidad")));
		boolean[] done={false};
		
		//Crea e inicia los servidores
//		for (int i = 0; i < nServidores; i++)
//		{
//			Servidor nuevo = new Servidor(i, buffer,done);
//			servidores.add(nuevo);
//			nuevo.start();
//		}
//		
//		//Crear e iniciar los clientes
//		for(int i=0;i<noClientes;i++)
//		{
//			Cliente nuevo = new Cliente(mensajes, buffer, i);
//			clientes.add(nuevo);
//			nuevo.start();
//		}
//		//Espera a que los clientes terminen ejecucion
//		while(clientes.size()>0)
//			for (int i = 0; i < clientes.size(); i++)
//				if(!clientes.get(i).estaActivo())
//					clientes.remove(i);
//		System.out.println("Todos los clientes han terminado sus consultas.\n");	
//		done[0] = true;
//		
//		//Despertar los threads que estan dormidos en el buffer
//		synchronized (buffer)
//		{
//			buffer.notifyAll();
//		}
		//Darle tiemp a los servidores que terminen
		Thread.sleep(4000);
		//Validar que los servidores hayan terminado su ejecuci—n
		int activos =0;
		for(Servidor serv:  servidores)
			if(serv.estaActivo())
				activos++;
		System.out.println("\nHay "+ activos+" threads activos\n");
		DecimalFormat df = new DecimalFormat("###,###");
		System.out.println("Caso1 terminado. \nEl tiempo de ejecuci—n fue de "+df.format(System.currentTimeMillis()-inicio)+" milisegundos\n\n");
		//Validar los datos procesados en la ejecuci—n
		//validar(noClientes, mensajes);
	}

}
