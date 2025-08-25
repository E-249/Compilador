package planning;

import java.util.HashMap;

import procesador.elementos.Valor;

// Factory
public class Regs {
	private HashMap<String, Reg> registros;
	private Pila pila;
	
	public Regs(Pila pila) { this.pila = pila; }
	
	public void newReg(String nombre) {
		registros.put(nombre, new Reg());
	}
	
	public void setReg(String nombre, Valor<Integer> valor) {
		registros.get(nombre).copyValor(valor);
	}
	
	// directos
	public Valor<Integer> getDir(String nombre) {
		return registros.get(nombre).valor;
	}
	
	// indirectos
	public Valor<Integer> getIndir(String nombre) {
		return pila.get(registros.get(nombre).valor);
	}
}
