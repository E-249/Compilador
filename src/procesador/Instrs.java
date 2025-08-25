package procesador;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Instrs {
	
	private final Map<String, BiConsumer<Valor<Integer>, Valor<Integer>>> operaciones;
	private final Map<String, Consumer<Valor<Integer>>> condicionales;
	
	private final Valor<Integer> cmp;
	private final Valor<Integer> pc;
	
	public Instrs(Valor<Integer> cmp, Valor<Integer> pc) {
		this.cmp = cmp;
		this.pc = pc;
		
		operaciones = new HashMap<>(16);
		addOps();
		
		condicionales = new HashMap<>(16);
		addConds();
	}
	
	public void addOps() {
		operaciones.put(":", (izq, der) -> izq.num =  der.num);
		operaciones.put("+", (izq, der) -> izq.num += der.num);
		operaciones.put("-", (izq, der) -> izq.num -= der.num);
		operaciones.put("*", (izq, der) -> izq.num *= der.num);
		operaciones.put("/", (izq, der) -> izq.num /= der.num);
		operaciones.put("\\",(izq, der) -> izq.num %= der.num);
		
		operaciones.put("?", (izq, der) -> cmp.num = izq.num - der.num);
		
		operaciones.put("|", (izq, der) -> izq.num |= der.num);
		operaciones.put("&", (izq, der) -> izq.num &= der.num);
		operaciones.put("!", (izq, der) -> izq.num ^= der.num);
		operaciones.put("<", (izq, der) -> izq.num <<=der.num);
		operaciones.put(">", (izq, der) -> izq.num >>=der.num);
	}
	
	private void saltarIf(boolean cnd, Valor<Integer> dest) { if (cnd) pc.num = dest.num; }
	public void addConds() {
		condicionales.put("!",  dest -> saltarIf(true,         dest));
		condicionales.put("<",  dest -> saltarIf(cmp.num <  0, dest));
		condicionales.put(">",  dest -> saltarIf(cmp.num >  0, dest));
		condicionales.put("=",  dest -> saltarIf(cmp.num == 0, dest));
		
		condicionales.put("<=", dest -> saltarIf(cmp.num <= 0, dest));
		condicionales.put("=>", dest -> saltarIf(cmp.num >= 0, dest));
		condicionales.put("<>", dest -> saltarIf(cmp.num != 0, dest));

		condicionales.put("=<", dest -> saltarIf(cmp.num <= 0, dest));
		condicionales.put(">=", dest -> saltarIf(cmp.num >= 0, dest));
		condicionales.put("><", dest -> saltarIf(cmp.num != 0, dest));
	}
	
	public void newOp(String nombre, BiConsumer<Valor<Integer>, Valor<Integer>> op) { operaciones.put(nombre, op); }
	public void newCond(String nombre, Consumer<Valor<Integer>> cond) { condicionales.put(nombre, cond); }
	
	public BiConsumer<Valor<Integer>, Valor<Integer>> getOpAccion(String nombre) { return operaciones.get(nombre); }
	public Consumer<Valor<Integer>> getCondAccion(String nombre) { return condicionales.get(nombre); }
	
	public void operar(Valor<Integer> izq, String nombreOp, Valor<Integer> der) { operaciones.get(nombreOp).accept(izq, der); }
	public void condicional(String nombreCond, Valor<Integer> der) { condicionales.get(nombreCond).accept(der); }
	
}
