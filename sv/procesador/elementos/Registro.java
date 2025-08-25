package procesador.elementos;

import procesador.Nombrable;

public class Registro<N extends Number> implements Nombrable {
	public String nombre;
	public Valor<N> valor;
	
	public Registro(String nombre, Valor<N> valor) {
		this.nombre = nombre;
		this.valor = new Valor<>(valor);
	}
	
	public void copyValor(Valor<N> otroValor) { this.valor.num = otroValor.num; }
	
	@Override
	public String toString() { return nombre + ": " + valor; }

	@Override
	public String getNombre() { return nombre; }
}

