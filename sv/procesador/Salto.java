package procesador;

import procesador.elementos.Saltador;
import procesador.elementos.Valor;

public class Salto<N extends Number> implements Instruccion<N> {

	private Saltador<N> saltador;
	private Valor<N> destino;
	
	public Salto(Valor<N> izquierda, Saltador<N> operador, Valor<N> derecha) {
		this.saltador = operador;
		this.destino = derecha;
	}
	
	@Override
	public void run() {
		saltador.saltar(destino);
	}
	
	@Override
	public String getNombre() { return saltador.getNombre() + destino.toString(); }
}
