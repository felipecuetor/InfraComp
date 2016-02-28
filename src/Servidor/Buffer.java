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

package Servidor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

import Cliente.Mensaje;

public class Buffer {
	
	// -----------------------------------------------------------------
    // ATRIBUTOS
    // -----------------------------------------------------------------
	private LinkedList<Mensaje> porProcesar;
	
	private int capacidad;
	
	private int contador;
	
	private int clientes;
	
	private boolean fin;
	
	// -----------------------------------------------------------------
    // CONSTRUCTOR
    // -----------------------------------------------------------------
	
	/**
	 * Crea un nuevo buffer a partir de una capacidad maxima y una cantidad especifica de clientes
	 * @param capacidad
	 */
	public Buffer(int capacidad, int clientes) {
		porProcesar= new LinkedList();
		this.capacidad=capacidad;
		this.clientes = clientes;
		this.fin=false;
	}
	
	// -----------------------------------------------------------------
    // METODOS
    // -----------------------------------------------------------------
	
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
		notify();
		return true;
	}
	
	/**
	 * metodo por donde atienden los servidores a los mensajes de los clientes
	 * @return retorna el mensaje por procesar
	 */
	public synchronized Mensaje atender()
	{
		
		if(contador == 0)
		{
			if(clientes == 0){
				System.out.println("procesando");
				return null;
			}
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Mensaje mensaje = porProcesar.poll();
		contador--;
		return mensaje;
		
	}
	
	public void clienteTermino()
	{
		
		clientes--;
		
		if(clientes == 0)
		{
			fin = true;
			notifyAll();
		}
	}

	public boolean getFin() {
		return fin;
	}
}
