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

package Cliente;

import java.util.ArrayList;

import Servidor.Buffer;

public class Cliente extends Thread {
	
	// -----------------------------------------------------------------
    // ATRIBUTOS
    // -----------------------------------------------------------------

	/**
	 * El buffer por el cual se va a comunicar
	 */
	public Buffer buffer;
	
	/**
	 * Numero de consultas que va a realizar
	 */
	public int numeroConsultas;
	
	/**
	 * Indica si fallo la comunicacion
	 */
	private boolean fallo;
	
	// -----------------------------------------------------------------
    // CONSTRUCTOR
    // -----------------------------------------------------------------

	/**
     * Construye un nuevo Thread cliente, con un numero de consultas que va a realizar
     * @param buffer el buffer por el cual se va a comunicar.
     * @param numConsultas numero de consultas que va a realizar.
     */
	public Cliente(Buffer buffer, int numeroConsultas) 
	{
		this.numeroConsultas = numeroConsultas;
		this.buffer = buffer;
		this.fallo = false;
	}
	
	// -----------------------------------------------------------------
    // 
    // -----------------------------------------------------------------

	/**
	 * Realiza todas las consultas que debe hacer. 
     */
	public void run() {
		while (numeroConsultas > 0) {

			consultar();
		}
		System.out.println(fallo);
		buffer.clienteTermino();
	}

	/**
     * Realiza una consulta 
     */
	public void consultar() {
		numeroConsultas--;
		Mensaje mensaje = new Mensaje(numeroConsultas);
		
		while (!buffer.recibirMensaje(mensaje))
			yield();
		
		
		mensaje.setConsulta(numeroConsultas);
		
		
		mensaje.eperar();
		

		if (!(mensaje.getConsulta() + 1 == mensaje.getRespuesta()))
			fallo = true;
	}

}
