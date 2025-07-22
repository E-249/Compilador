package ensamblador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Lector {
	
	public List<String> leerArchivo(String ruta) {

		try (BufferedReader lector = new BufferedReader(
				new FileReader(getClass().getResource("/" + ruta).getFile()))) {

			LinkedList<String> lineas = new LinkedList<>();
			String linea;
			
			while ((linea = lector.readLine()) != null)
				lineas.add(linea);
			
			return lineas;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> leerTexto(String texto) {

		LinkedList<String> lineas = new LinkedList<>();
		
		for (String linea : texto.split("\n"))
			lineas.add(linea);
		
		return lineas;

	}

}
