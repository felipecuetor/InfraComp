/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 *
 * Proyecto Caso1
 * Infraestructura Computacional
 * Autor: Felipe Cueto  - Marzo 1, 2016
 * Autor: Paula Ramirez - Marzo 1, 2016
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package Cliente;

public class Mensaje {
	
	// -----------------------------------------------------------------
    // ATRIBUTOS
    // -----------------------------------------------------------------

	private int consulta;

	private int respuesta;
	
	// -----------------------------------------------------------------
    // CONSTRUCTOR
    // -----------------------------------------------------------------

	public Mensaje(int consulta) {
		this.consulta = consulta;
		this.respuesta = 0;
	}
	
	// -----------------------------------------------------------------
    // METODOS
    // -----------------------------------------------------------------

	public int getConsulta() {
		return consulta;
	}

	public synchronized void setConsulta(int consulta) {
		this.consulta = consulta;
	}

	public int getRespuesta() {
		return respuesta;
	}

	public synchronized void setRespuesta(int respuesta) {
		this.respuesta = respuesta;
	}

	public synchronized void eperar() {
		
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void notificar()
	{
		notify();
	}

}
