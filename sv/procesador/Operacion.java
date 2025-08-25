package procesador;

import procesador.elementos.Operador;
import procesador.elementos.Valor;

public class Operacion<N extends Number> implements Instruccion<N> {

	private Valor<N> izquierda;
	private Operador<N> operador;
	private Valor<N> derecha;
	
	public Operacion(Valor<N> izquierda, Operador<N> operador, Valor<N> derecha) {
		this.izquierda = izquierda;
		this.operador = operador;
		this.derecha = derecha;
	}
	
	@Override
	public void run() {
		operador.operar(izquierda, derecha);
	}
	
	@Override
	public String getNombre() {
		return izquierda.toString() + operador.getNombre() + derecha.toString();
	}
	
}
