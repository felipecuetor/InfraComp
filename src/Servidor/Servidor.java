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

package Servidor;

import Cliente.Mensaje;

public class Servidor extends Thread{
	
	// -----------------------------------------------------------------
    // ATRIBUTOS
    // -----------------------------------------------------------------
	/**
	 * El buffer por el cual se va a comunicar
	 */
	public Buffer buffer;

	/**
	 * Indica si debe continuar a procesar un mensaje  
	 */
	public boolean continuar;
	
	// -----------------------------------------------------------------
    // CONSTRUCTOR
    // -----------------------------------------------------------------
	
	/**
     * Construye un nuevo Servidor que va a atender un Buffer.
     * @param iD identificador del servidor
     * @param buffer El buffer de comunicaci—n.
     */
	public Servidor(Buffer buffer) {
		this.buffer = buffer;
		continuar = true;
	}
	
	// -----------------------------------------------------------------
    // METODOS
    // -----------------------------------------------------------------

	public void run() {
		while (buffer.getFin() != true) {
			
			procesar();
		}
	}

	public void procesar() {
		
		Mensaje mensaje = buffer.atender();

		if (continuar == true)
		{
			System.out.println(mensaje);
			continuar = false;
		}
		else {
			
			int consulta = mensaje.getConsulta();
			mensaje.setRespuesta(consulta++);
			mensaje.notificar();
			
		}
	}

}
