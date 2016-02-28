package Cliente;

public class Mensaje {

	private int consulta;

	private int respuesta;

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
