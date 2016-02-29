package Cliente;

import java.util.ArrayList;

import Servidor.Buffer;

public class Cliente extends Thread {

	public Buffer buffer;
	public int numeroConsultas;
	private boolean fallo;

	/**
	 * Crea un nuevo Cliente
	 * @param buffer buffer que maneja mensajes
	 * @param numeroConsultas numero de consultas que tiene que hacer este cliente
	 */
	public Cliente(Buffer buffer, int numeroConsultas) {

		this.numeroConsultas = numeroConsultas;
		this.buffer = buffer;
		this.fallo = false;
	}

	/**
	 * Corre el thread de cliente
	 * Itera hasta terminar el programa
	 * Cuando termina el programa avisa si el resultado fue el correcto
	 */
	public void run() {
		while (numeroConsultas > 0) {
			consultar();
		}
		System.out.println(fallo);
		buffer.clienteTermino();
	}

	/**
	 * Genera la consulta con un mensaje
	 * Trabaja hasta que buffer tenga espacio disponible
	 * Se duerme en el mensaje
	 * Es desperatado por el servidor
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
