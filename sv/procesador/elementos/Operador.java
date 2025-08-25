package procesador.elementos;

import java.util.function.BiConsumer;

import procesador.Nombrable;

public class Operador<N extends Number> implements Nombrable {
	public String nombre;
	public BiConsumer<Valor<N>, Valor<N>> operacion;
	
	public Operador(String nombre, BiConsumer<Valor<N>, Valor<N>> operacion) {
		this.nombre = nombre;
		this.operacion = operacion;
	}
	
	@Override
	public String toString() { return nombre; }
	
	public void operar(Valor<N> izquierda, Valor<N> derecha) {
		operacion.accept(izquierda, derecha);
	}
	
	@Override
	public String getNombre() { return nombre; }
}
