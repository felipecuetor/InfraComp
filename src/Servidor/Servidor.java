package Servidor;

import Cliente.Mensaje;

public class Servidor extends Thread {
	public Buffer buffer;

	public boolean continuar;

	/**
	 * Crea un nuevo Servidor
	 * @param buffer es el buffer de los mensajes
	 */
	public Servidor(Buffer buffer) {
		this.buffer = buffer;
		continuar = true;
	}

	/**
	 * Corre el thread y trabaja hasta que haya terminado de ejecutar el programa
	 */
	public void run() {
		while (continuar) {
			procesar();
		}
		System.out.println("Servidor termino");
	}

	/**
	 * Trabaja hasta que haya un mensaje por procesar o hasta que el programa termine de ejecutar
	 * La respuesta dentro del mensaje es la consulta mas 1
	 */
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
			mensaje.setRespuesta(consulta+1);
			mensaje.notificar();
		}
	}

}
