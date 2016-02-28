package Cliente;

import java.util.ArrayList;

import Servidor.Buffer;

public class Cliente extends Thread {

	public Buffer buffer;
	public int numeroConsultas;
	private boolean fallo;

	public Cliente(Buffer buffer, int numeroConsultas) {

		this.numeroConsultas = numeroConsultas;
		this.buffer = buffer;
		this.fallo = false;
	}

	public void run() {
		while (numeroConsultas > 0) {

			consultar();
		}
		System.out.println(fallo);
		buffer.clienteTermino();
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

}
