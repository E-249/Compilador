package procesador.zonas;

import java.util.ArrayList;

import procesador.elementos.Registro;
import procesador.elementos.Valor;

public class Registros<N extends Number> {
	private static final String[] nombres = { "A", "E", "O", "I", "U", "S", "B", "P", "R" };

	private ArrayList<Registro<N>> registros;
	
	public Registros(Valor<N> nulo) {
		for (String nombre : nombres)
			registros.add(new Registro<N>(nombre, nulo));
	}
	
}
