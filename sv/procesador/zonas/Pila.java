package procesador.zonas;

import java.util.ArrayList;

import procesador.elementos.Valor;

public class Pila<N extends Number> {
	
	private ArrayList<Valor<N>> pila;
	
	public Pila(int stackSize, Valor<N> nulo) {
		pila = new ArrayList<>(stackSize);
		for (int i = 0; i < stackSize; i++)
			pila.add(new Valor<>(nulo));
	}
	
	public Valor<N> get(Valor<Integer> indice) {
		return pila.get(indice.num.intValue());
	}
	
}
