package GLoad;

import java.io.IOException;
import java.net.Socket;

import Cliente.Cliente;
import Cliente.MainCliente;
import uniandes.gload.core.Task;

public class ClientServerTask extends Task
{
	public void execute()
	{
		Socket socketCliente = null;
		try {
			socketCliente = new Socket("172.24.100.42", 9090);
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Cliente(socketCliente);
	}
	
	public void fail()
	{
		System.out.println(Task.MENSAJE_FAIL);
	}

	@Override
	public void success() 
	{
		System.out.println(Task.OK_MESSAGE);
	}
}
