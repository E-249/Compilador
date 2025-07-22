package ensamblador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ensamblador.Transcriptor.Modificador;

public class Automata {
	
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
	
	public static enum Estado {
		INICIO,
		ACCESO,
		REGISTRO,
		OPERACION,
		COMPARACION,
		COMENTARIO,
		EXPANSION
	}
	
	
	// Llamar a normalizar previamente
	public void interpretar(String linea) {
		if (linea.length() < 3) return;
		char[] simbolos = linea.toCharArray();
		int indice = 0;
		
		HashMap<String, String> names = new HashMap<>();
		
		
		
		}
		
	}

	public static void main(String[] args) {
		Lector lector = new Lector();
		Automata automata = new Automata();
		System.out.println(automata.normalizar(lector.leerTexto("S + 1\n#S : A")));
	}
	
}
