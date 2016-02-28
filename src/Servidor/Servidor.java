package Servidor;

import Cliente.Mensaje;

public class Servidor extends Thread{
	public Buffer buffer;

	public boolean continuar;

	public Servidor(Buffer buffer) {
		this.buffer = buffer;
		continuar = true;
	}

	public void run() {
		while (buffer.getFin() != true) {
			
			procesar();
		}
	}

	public void procesar() {
		
		Mensaje mensaje = buffer.atender();

		if ()
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
