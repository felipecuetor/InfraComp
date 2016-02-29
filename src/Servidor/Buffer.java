package Servidor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import Cliente.Mensaje;

public class Buffer {
	private LinkedList<Mensaje> porProcesar;
	
	private int capacidad;
	
	private int contador;
	
	private int clientes;
	
	
	/**
	 * Crea un nuevo buffer a partir de una capacidad maxima y una cantidad especifica de clientes
	 * @param capacidad
	 */
	public Buffer(int capacidad, int clientes) {
		porProcesar= new LinkedList();
		this.capacidad=capacidad;
		this.clientes = clientes;
		this.contador=0;
	}
	
	/**
	 * Recibe un mensaje de un cliente
	 * @param mensaje un mensaje de un cliente
	 * @return return falso si no se logro agregar, y verdadero si si
	 */
	public synchronized boolean recibirMensaje(Mensaje mensaje)
	{
		if(contador == capacidad)
			return false;
		porProcesar.add(mensaje);
		contador++;
		return true;
	}
	
	/**
	 * metodo por donde atienden los servidores a los mensajes de los clientes
	 * @return retorna el mensaje por procesar
	 */
	public synchronized Mensaje atender()
	{
		
		if(contador <= 0)
		{
			return null;
		}
		
		Mensaje mensaje = porProcesar.poll();

		contador--;
		return mensaje;
		
	}
	
	/**
	 * El cliente le avisa al buffer que termino
	 * Se reduce la cantidad de clientes activos
	 */
	public synchronized void clienteTermino()
	{
		clientes--;
		System.out.println("Clientes activos: "+clientes);
		
		if(clientes == 0)
		{
			notifyAll();
		}
	}

	/**
	 * Dice si se terminaron los clientes por atender
	 * @return true si no hay clientes, false si hay
	 */
	public synchronized boolean getFin() {
		return (clientes == 0);

	}
}
