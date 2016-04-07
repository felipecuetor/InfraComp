package Cliente;

public class Mensaje {

	private int consulta;

	private int respuesta;

	/**
	 * Crea un nuevo mensaje
	 * @param consulta el valor a consultar
	 */
	public Mensaje(int consulta) {
		this.consulta = consulta;
		this.respuesta = respuesta;
	}

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

	/**
	 * Duerme el thread que lo active
	 */
	public synchronized void eperar() {
		
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Notifica el thread que esta en este mensaje
	 */
	public synchronized void notificar()
	{
		notify();
	}

}
