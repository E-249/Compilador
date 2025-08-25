package planning;

import java.util.ArrayList;

import procesador.elementos.Valor;

public class Pila {

private ArrayList<Valor<Integer>> pila;
	
	public Pila(int stackSize, Valor<Integer> nulo) {
		pila = new ArrayList<>(stackSize);
		for (int i = 0; i < stackSize; i++)
			pila.add(new Valor<>(nulo));
	}
	
	public Valor<Integer> get(Valor<Integer> indice) {
		return pila.get(indice.num.intValue());
	}
	
}
