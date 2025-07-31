package ensamblador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Archivos {
	
	public String leerArchivo(String ruta) {

		try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {

			StringBuffer str = new StringBuffer();
			String linea;
			
			if ((linea = lector.readLine()) != null)
				str.append(linea);
			
			while ((linea = lector.readLine()) != null)
				str.append('\n').append(linea);
			
			return str.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void escribirArchivo(String ruta, String texto) {
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(ruta));
			writer.write(texto);
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void test(String archivo) {
		var a = new Archivos();
		var p = new PreProcesador();
		String leer, reemplazar;
		
		leer = a.leerArchivo(archivo + ".cos");
		reemplazar = p.reemplazar(leer);
		a.escribirArchivo(archivo + ".ccos", reemplazar);
	}
	
	public static void main(String[] args) {
		test("test/Entity");
	}

}
