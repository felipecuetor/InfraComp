package Servidor;

import java.util.ArrayList;
import java.util.Queue;

import Cliente.Mensaje;

public class Buffer {
	private Queue<Mensaje> porProcesar;
	
	private int capacidad;
	
	private int contador;
	
	public synchronized boolean recibirMensaje(Mensaje mensaje)
	{
		if(contador == capacidad)
			return false;
		porProcesar.add(mensaje);
		contador++;
		notify();
		return true;
	}
	
	public synchronized Mensaje atender()
	{
		if(contador == 0)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return porProcesar.poll();
		
	}

	public Buffer(int parseInt) {
		// TODO Auto-generated constructor stub
	}

}
