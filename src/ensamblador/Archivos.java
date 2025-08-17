package ensamblador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Archivos {
	private Archivos() {}
	
	public static String leerArchivo(String ruta) throws FileNotFoundException, IOException {

		try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
			StringBuffer str = new StringBuffer();
			String linea;
			
			if ((linea = lector.readLine()) != null)
				str.append(linea);
			
			while ((linea = lector.readLine()) != null)
				str.append('\n').append(linea);
			
			return str.toString();
		}

	}
	
	public static void escribirArchivo(String ruta, String texto) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(ruta));
		writer.write(texto);
		writer.close();
			
	}
	
	public static String ignorarExtension(String ruta) {
		int dot = ruta.lastIndexOf('.');
		return ruta.substring(0, dot);
	}

}
