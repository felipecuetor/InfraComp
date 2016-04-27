package Cliente;

import java.io.IOException;
import java.net.Socket;

public class MainCliente {
	
	/**
	 * Main encargado de inicializar la conexion con el servidor y de inicializar el cliente.
	 * @param args
	 */
	public static void main(String[] args) {

		Socket socketCliente = null;
		try {
			socketCliente = new Socket("172.24.100.42", 9090);
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Cliente(socketCliente);
	}
}
