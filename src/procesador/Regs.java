package procesador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Regs {
	
	private final Map<String, Valor<Integer>> regs;
	private final List<Valor<Integer>> pila;
	
	public Regs(int stackSize) {
		String[] nombresReg = { "A", "E", "O", "I", "U", "S", "B", "P" };
		regs = new HashMap<String, Valor<Integer>>(nombresReg.length);
		for (String nombreReg : nombresReg) newReg(nombreReg);
		
		pila = new ArrayList<>(stackSize);
		for (int i = 0; i < stackSize; i++) newValorPila();
	}
	
	public void newReg(String nombre) { regs.put(nombre, new Valor<>(0)); }
	public void newValorPila() { pila.add(new Valor<>(0)); }
	
	public Valor<Integer> getRegValor(String nombre) { return regs.get(nombre); }
	public Valor<Integer> getAccValor(String nombre) { return pila.get(regs.get(nombre).num); }

}
