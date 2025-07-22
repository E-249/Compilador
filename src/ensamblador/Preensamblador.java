package ensamblador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Preensamblador {
	
	private static final String SPACE = "\\s";
	public List<String> normalizar(List<String> lineas) {
		ArrayList<String> lineasReemplazadas = new ArrayList<>(lineas.size());
		for (String linea : lineas) {
			String lineaReemplazada = linea.replaceAll(SPACE, "");
			if (!lineaReemplazada.isEmpty())
				lineasReemplazadas.add(lineaReemplazada);
		}
		return lineasReemplazadas;
	}
	
	private void guardarExpansion(HashMap<String, String> expansiones) {
		
	}
	
	private String reemplazarHere(String linea, int indice) {
		return linea.replaceAll(Transcriptor.Modificador.HERE.simbolo + "", String.valueOf(indice));
	}
	
	
	private String reemplazarExpansion(String linea, HashMap<String, String> expansiones) {
		return null;
	}
	
	private String reemplazarComentario(String linea) {
		return null;
	}
	
	public List<String> reemplazar(List<String> lineas) {
		ArrayList<String> lineasReemplazadas = new ArrayList<>(lineas.size());
		
		HashMap<String, String> expansiones = new HashMap<>();
		
		for (int indice = 1; indice <= lineas.size(); indice++) {
			String linea = lineas.get(indice);
			
			//linea.matches()
			
			linea = reemplazarHere(linea, indice);
			
			
		}
		return null;
	}
	
	

}
