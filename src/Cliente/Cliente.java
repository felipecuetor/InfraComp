package Cliente;

import java.util.ArrayList;

import Servidor.Buffer;

public class Cliente {
	
	public boolean activo;
	public ArrayList<Mensaje> mensajes;
	public Buffer buffer;
	public int numero;

	public Cliente(ArrayList<Mensaje> mensajes, Buffer buffer, int i)
	{
		this.mensajes = mensajes;
		this.buffer = buffer;
		this.numero = i;
	}

	public void start() {
		
	}

	public boolean estaActivo() {
		return activo;
	}

}
