package Servidor;

public class Servidor {
	
	public boolean activo;
	public int numero;
	public Buffer buffer;
	public boolean[] termino;
	

	public Servidor(int i, Buffer buffer, boolean[] done)
	{
		this.numero = i;
		this.buffer =  buffer;
		this.termino = done;
	}

	public void start() {
		
	}

	public boolean estaActivo()
	{
		return activo;
	}

}
