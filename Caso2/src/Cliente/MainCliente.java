package Cliente;

import java.io.IOException;
import java.net.Socket;

public class MainCliente {
	public static void main(String[] args) {

		Socket socketCliente = null;
		try {
			socketCliente = new Socket("0", 8090);
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Cliente(socketCliente);
	}
}
