package procesador.zonas;

import java.util.ArrayList;

import procesador.Instruccion;
import procesador.Procesador;

public class Instrucciones<N extends Number> {

	private ArrayList<Instruccion<N>> instrucciones;
	private Procesador procesador;
	
	public Instrucciones(Procesador procesador) {
		this.procesador = procesador;
	}
	
	public void add(Instruccion<N> instruccion) {
		instrucciones.add(instruccion);
	}
	
	public void recorrer() {
		var pc = procesador.pc;
		for (pc.num = 0; 0 <= pc.num && pc.num < instrucciones.size(); pc.num++)
			instrucciones.get(pc.num + 1).run();
	}
	
}
