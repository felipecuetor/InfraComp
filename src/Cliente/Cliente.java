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

import Principal.Main;
import Servidor.Buffer;

public class Cliente extends Thread {
	
	// -----------------------------------------------------------------
    // ATRIBUTOS
    // -----------------------------------------------------------------

	/**
	 * El buffer que usa para comunicarse
	 */
	public Buffer buffer;
	
	/**
	 * El numero de consultas que tiene
	 */
	public int numeroConsultas;
	
	/**
	 * Induca si fallo o no la comunicacion
	 */
	private boolean fallo;
	
	/**
	 * Indica si el cliente esta ctivo
	 */
	public boolean estaActivo;
	
	// -----------------------------------------------------------------
    // CONSTRUCTOR
    // -----------------------------------------------------------------

	public Cliente(Buffer buffer, int numeroConsultas) {

		this.numeroConsultas = numeroConsultas;
		this.buffer = buffer;
		this.fallo = false;
	}
	
	// -----------------------------------------------------------------
    // METODOS
    // -----------------------------------------------------------------

	public void run() {
		while (numeroConsultas > 0 && estaActivo) {

			consultar();
		}
		System.out.println(fallo);
		buffer.clienteTermino();
		Main.clientesTerminan();
	}

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
	
	public void activar()
	{
		this.estaActivo = true;
	}

}
