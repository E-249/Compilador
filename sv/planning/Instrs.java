package planning;

import java.util.List;

import procesador.elementos.Valor;

public class Instrs {
	private Regs registros;
	private Ops operaciones;
	private Conds condicionales;
	
	private List<Instr> instrucciones;
	
	public Instrs(Regs registros, Ops operaciones, Conds condicionales) {
		this.registros = registros;
		this.operaciones = operaciones;
		this.condicionales = condicionales;
	}
	
	private Runnable newOp(String izquierda, String operacion, String derecha) {
		return () -> operaciones.operar(registros.getDir(izquierda), operacion, registros.getDir(derecha));
	}
	
	public void add(String izquierda, String operacion, String derecha) {
		instrucciones.add(new Instr(getLinea(), newOp(izquierda, operacion, derecha)));
	}
	
	public void add(Arg izquierda, Op operacion, Arg derecha) {
		instrucciones.add(new Instr(getLinea(), () ->  operacion.operar(izquierda, derecha)));
	}
	
	public void add(Cond condicional, Arg derecha) {
		instrucciones.add(new Instr(getLinea(), () ->  condicional.comparar(derecha)));
	}
	
	private int getLinea() { return instrucciones.size() + 1; }
	
	//runInstruccion
	public void run(Valor<Integer> indice) {
		instrucciones.get(indice.num).run();
	}
}
