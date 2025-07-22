package ensamblador;

public class Automata {
	
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
		
		
	}
	
}
