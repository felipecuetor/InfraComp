package Servidor;

import Cliente.Mensaje;

public class Servidor extends Thread {
	public Buffer buffer;

	public boolean continuar;

	public Servidor(Buffer buffer) {
		this.buffer = buffer;
		continuar = true;
	}

	public void run() {
		while (continuar) {
			procesar();
		}
		System.out.println("Servidor termino");
	}

	public void procesar() {

		Mensaje mensaje = buffer.atender();

		while (mensaje == null && continuar) {
			if (buffer.getFin())
				continuar = false;

			mensaje = buffer.atender();
			yield();
		}  
		if (continuar) {
			int consulta = mensaje.getConsulta();
			mensaje.setRespuesta(consulta++);
			mensaje.notificar();
		}
	}

}
