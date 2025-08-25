package procesador.elementos;

import java.util.function.Consumer;

import procesador.Nombrable;

public class Saltador<N extends Number> implements Nombrable {

	public String nombre;
	public Consumer<Valor<N>> salto;
	
	public Saltador(String nombre, Consumer<Valor<N>> salto) {
		this.nombre = nombre;
		this.salto = salto;
	}
	
	@Override
	public String toString() { return nombre; }
	
	public void saltar(Valor<N> pc) {
		salto.accept(pc);
	}

	@Override
	public String getNombre() { return nombre; }
	
}
