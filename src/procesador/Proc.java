package procesador;

import java.util.ArrayList;
import java.util.List;

public class Proc {
	
	private final Regs registros;
	private final Instrs instrucciones;
	
	private final Valor<Integer> cmp;
	private final Valor<Integer> pc;
	private final List<Runnable> listaInstr;
	
	public Proc(int stackSize) {
		cmp = new Valor<>(0);
		pc = new Valor<>(1);
		listaInstr = new ArrayList<>();
		
		registros = new Regs(stackSize);
		instrucciones = new Instrs(cmp, pc);
	}
	public void reset() { cmp.num = 0; pc.num = 1; }
	
	public static enum TipoArgIzq { REG, ACC }
	public static enum TipoArgDer { REG, ACC, IMM }
	
	public void newOpInstr(String argIzq, TipoArgIzq tipoIzq, String op, String argDer, TipoArgDer tipoDer) {
		Valor<Integer> izq, der;
		
		switch (tipoIzq) {
		case REG -> izq = registros.getRegValor(argIzq);
		case ACC -> izq = registros.getAccValor(argIzq);
		default -> throw new IllegalStateException(); }
		
		switch (tipoDer) {
		case REG -> der = registros.getRegValor(argDer);
		case ACC -> der = registros.getAccValor(argDer);
		case IMM -> der = new Valor<>(Integer.parseInt(argDer));
		default -> throw new IllegalStateException(); }
		
		listaInstr.add(() -> instrucciones.operar(izq, op, der));
	}
	
	public void newCondInstr(String cond, String argDer, TipoArgDer tipoDer) {
		Valor<Integer> der;
		
		switch (tipoDer) {
		case REG -> der = registros.getRegValor(argDer);
		case ACC -> der = registros.getAccValor(argDer);
		case IMM -> der = new Valor<>(Integer.parseInt(argDer));
		default -> throw new IllegalStateException(); }
		
		listaInstr.add(() -> instrucciones.condicional(cond, der));
	}

}
