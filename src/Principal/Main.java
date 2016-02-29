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

package Principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import Cliente.Cliente;
import Cliente.Mensaje;
import Servidor.Buffer;
import Servidor.Servidor;

public class Main {
	public static void main(String[] args) throws Exception {		
		File data = new File("./data/data.txt");

		BufferedReader reader = new BufferedReader(new FileReader(data));

		reader.readLine();
		int numeroClientes = Integer.parseInt(reader.readLine());
		reader.readLine();
		int numeroServidores = Integer.parseInt(reader.readLine());
		reader.readLine();
		int numeroConsultas = Integer.parseInt(reader.readLine());
		reader.readLine();
		int capacidadBuffer = Integer.parseInt(reader.readLine());

		Buffer buffer = new Buffer(capacidadBuffer, numeroClientes);

		for (int i = 0; i < numeroServidores; i++) {

			(new Servidor(buffer)).start();
		}

		for (int i = 0; i < numeroClientes; i++) {

			(new Cliente(buffer, numeroConsultas)).start();
		}

	}

}
