package GLoad;

import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;
import GLoad.ClientServerTask;

public class Generator {

	/**
	 * Carga el servicio del generador(Goad 1.3)
	 */
	private LoadGenerator generador;

	/**
	 * Construye un nuevo generador
	 */
	public Generator() {
		Task tarea = createTask();
		int numTareas = 80;
		int tmpEntreTareas = 100;
		generador = new LoadGenerator("Cliente - Test de carga del servidor", numTareas, tarea, tmpEntreTareas);
		generador.generate();
	}

	/**
	 * Construye una tarea
	 */
	private Task createTask() {
		return new ClientServerTask();
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Generator gen = new Generator();
	}
}
