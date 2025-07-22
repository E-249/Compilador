package ensamblador;

import java.util.ArrayList;
import java.util.List;

import ensamblador.Transcriptor.Modificador;
import ensamblador.Transcriptor.Operacion;
import ensamblador.Transcriptor.Registro;

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
	
	// Llamar a normalizar previamente
	public void interpretar(String linea) {
		if (linea.length() < 3) return;
		char[] simbolos = linea.toCharArray();
		int indice = 0;
		
		Modificador modificadorIzq;
		Registro registroIzq;
		Operacion operacion;
		Modificador modificadorDer;
		Registro registroDer;
		int valorDer;
		
		modificadorIzq = Transcriptor.modificadores.get(simbolos[indice]);
		if (modificadorIzq != null) indice++;
		
	}

	public static void main(String[] args) {
		Lector lector = new Lector();
		Automata automata = new Automata();
		System.out.println(automata.normalizar(lector.leerTexto("S + 1\n#S : A")));
	}
	
}
