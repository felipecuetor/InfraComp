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

public class Mensaje {

	// -----------------------------------------------------------------
    // ATRIBUTOS
    // -----------------------------------------------------------------
	
	/**
	 * Mensaje de consulta
	 */
	private int consulta;
	
	/**
	 * Mensaje de respuesta
	 */
	private int respuesta;
	
	// -----------------------------------------------------------------
    // CONSTRUCTOR
    // -----------------------------------------------------------------

	/**
     * Construye un nuevo mensaje vacio. 
     */
	public Mensaje(int consulta) 
	{
		this.consulta = consulta;
		this.respuesta = respuesta;
	}
	
	// -----------------------------------------------------------------
    // 
    // -----------------------------------------------------------------

	/**
     * Retorna la consulta al mensaje
     */
	public int getConsulta() {
		return consulta;
	}

	/**
     * Asigna la consulta al mensaje
     * Post:La consulta fue asignada al mensaje
     * @param int la consulta que se va a realizar
     */
	public synchronized void setConsulta(int consulta) {
		this.consulta = consulta;
	}

	/**
     * Retorna la respuesta al mensaje
     */
	public int getRespuesta() {
		return respuesta;
	}

	/**
     * Asigna la respuesta al mensaje
     * Post: La respuesta fue asignada al mensaje
     * @param int la respuesta que se va a realizar
     */
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
