package procesador;

import procesador.elementos.Valor;
import procesador.zonas.Instrucciones;
import procesador.zonas.Pila;
import procesador.zonas.Registros;

public class Procesador {
	
	private static final Valor<Integer> NULO = new Valor<>(0);
	
	public final Valor<Integer> cmp;
	
	public final Registros<Integer> registros;
	public final Pila<Integer> pila;
	
	public final Valor<Integer> pc;
	public final Instrucciones<Integer> instrucciones;
	
//	private ArrayList<Externo<N>> externos;
	
	/* CONSTRUCTORA */
	
	public Procesador(int stackSize) {
		registros		= new Registros<>(NULO);
		pila			= new Pila<>(stackSize, NULO);
		instrucciones	= new Instrucciones<>(this);
		
		cmp = new Valor<>(NULO);
		pc = new Valor<>(NULO);
	}
	
	public void setup() {}
	
	public void loop() {
		instrucciones.recorrer();
	}
	
	public void end() {}
	
	public void run() {
		setup();
		loop();
		end();
	}
	
}
