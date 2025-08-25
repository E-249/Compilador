package optimizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import planning.Instr;
import procesador.Valor;

public class Opt {
	
	/**
	 * Ejemplo:<p>
	 * Teniendo las siguientes dos instrucciones:
	 * <blockquote><pre>
	 * E : 2
	 * O : E
	 * </pre></blockquote>
	 * Tras sustituir los registros derechos por su valor:
	 * <blockquote><pre>
	 * E : 2
	 * O : 2
	 * </pre></blockquote>
	 * En la segunda instrucción, {@code O : E}, se ha sustituido el registro derecho
	 * {@code E} por su valor {@code 2}.
	 * 
	 * @param instrucciones Instrucciones a sustituir
	 * @return nuevas instrucciones sustituidas
	 */
	public List<Runnable> sustituir(List<Runnable> instrucciones) {
		List<Runnable> nuevasInstrucciones = new ArrayList<>(instrucciones.size());
		
		HashMap<Valor<Integer>, List<Runnable>> registros = new HashMap<>();
		//Registros.init(registros);
		
		for (Runnable instruccion : instrucciones)
			registros.get(instruccion.getIzquierda()).add(instruccion);
		
		// cosas
		
		return nuevasInstrucciones;
	}

}
