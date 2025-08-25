package planning;

import java.util.HashMap;
import java.util.Map;

public class Ops {
	private Map<String, Op> operaciones;
	
	public Ops() { this.operaciones = new HashMap<>(); }
	
	// newOperacion
	public void newOp(String nombre, Op operacion) {
		operaciones.put(nombre, operacion);
	}
	
	public void operar(Arg izquierda, String nombre, Arg derecha) {
		operaciones.get(nombre).operar(izquierda, derecha);
	}
	
}
