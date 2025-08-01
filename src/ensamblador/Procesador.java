package ensamblador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ensamblador.Transcriptor.Comparacion;
import ensamblador.Transcriptor.Operacion;
import ensamblador.Transcriptor.Registro;

public class Procesador {
	
	private static final int REGISTRO_CNT = Registro.values().length;
	private static final int OPERACION_CNT = Registro.values().length;
	private static final int COMPARACION_CNT = Comparacion.values().length + 3; // 3: GE, LE, NE
	
	int PC;
	int cmp;
	ArrayList<Runnable> instr;
	HashMap<String, Integer> regs;
	int[] stack;

	public Procesador(int stackSize) {
		stack = new int[stackSize];
		instr = new ArrayList<>();
		instr.add(null);
		
		regs = new HashMap<>(REGISTRO_CNT);
		operaciones = new HashMap<>(OPERACION_CNT);
		comparaciones = new HashMap<>(COMPARACION_CNT);
		
		regs.put("A", 0);
		regs.put("E", 0);
		regs.put("O", 0);
		regs.put("I", 0);
		regs.put("U", 0);
		regs.put("S", 0);
		regs.put("B", 0);
		regs.put("P", 0);
		regs.put("R", 0);
		
		operaciones.put(":", (_,	right) -> right);
		operaciones.put("+", (left, right) -> left + right);
		operaciones.put("-", (left, right) -> left - right);
		operaciones.put("*", (left, right) -> left * right);
		operaciones.put("/", (left, right) -> left / right);
		operaciones.put("?", (left, right) -> { cmp = left - right; return left; });
		
		comparaciones.put(">",  target -> cmp >  0 ? target - 1 : PC);
		comparaciones.put("<",  target -> cmp <  0 ? target - 1 : PC);
		comparaciones.put("=",  target -> cmp == 0 ? target - 1 : PC);
		comparaciones.put("!",  target -> target - 1);
		comparaciones.put("=>", target -> cmp >= 0 ? target - 1 : PC);
		comparaciones.put("<=", target -> cmp <= 0 ? target - 1 : PC);
		comparaciones.put("<>", target -> cmp != 0 ? target - 1 : PC);
		comparaciones.put(">=", comparaciones.get("=>"));
		comparaciones.put("=<", comparaciones.get("<="));
		comparaciones.put("><", comparaciones.get("<>"));
	}
	
	public Procesador(String lineas, int stackSize) {
		this(stackSize);
		for (String linea : lineas.split("\n"))
			add(linea);
	}

	public void addInstr(Runnable instruction) {
		instr.add(instruction);
	}
	
	public void addInstr(int left, BiConsumer<Integer, Integer> instruction, int right) {
		instr.add(() -> instruction.accept(left, right));
	}
	
	public void addInstr(Consumer<Integer> instruction, int target) {
		instr.add(() -> instruction.accept(target));
	}

	public void run() {
		for (PC = 1; PC < instr.size() && PC > 0; PC++)
			instr.get(PC).run();
	}
	public void runPrintEnd() {
		for (PC = 1; PC < instr.size() && PC > 0; PC++)
			instr.get(PC).run();
		System.out.println(this);
	}
	public void runPrint() {
		for (PC = 1; PC < instr.size() && PC > 0; PC++) {
			String cnt = "[" + PC + "] ";
			instr.get(PC).run();
			System.out.println(cnt + regs+", cmp="+cmp+", "+Arrays.toString(stack));
		}
	}
	private HashMap<String, BiFunction<Integer, Integer, Integer>> operaciones;
	private HashMap<String, Function<Integer, Integer>> comparaciones;
	
	private static interface TriConsumer<T, U, R> { void accept(T t, U u, R r); }
	private TriConsumer<String, BiFunction<Integer, Integer, Integer>, String>
			opRegReg = (left, op, right) -> regs.put(left,			op.apply(regs.get(left), 		regs.get(right))),
			opAccReg = (left, op, right) -> stack[regs.get(left)] =	op.apply(regs.get(left), 		regs.get(right)),
			opRegAcc = (left, op, right) -> regs.put(left, 			op.apply(regs.get(left), 		stack[regs.get(right)])),
			opAccAcc = (left, op, right) -> stack[regs.get(left)] =	op.apply(stack[regs.get(left)],	stack[regs.get(right)]),
			opRegVal = (left, op, right) -> regs.put(left,			op.apply(regs.get(left),		Integer.parseInt(right))),
			opAccVal = (left, op, right) -> stack[regs.get(left)] =	op.apply(stack[regs.get(left)],	Integer.parseInt(right));
	
	private BiConsumer<Function<Integer, Integer>, String>
			cmpReg = (cmp, target) -> PC = cmp.apply(regs.get(target)),
			cmpAcc = (cmp, target) -> PC = cmp.apply(stack[regs.get(target)]),
			cmpVal = (cmp, target) -> PC = cmp.apply(Integer.parseInt(target));
	
	private static final Pattern OP_REG_REG = Pattern.compile("^(" +Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Registro.REGEX+")$");
	private static final Pattern OP_ACC_REG = Pattern.compile("^#("+Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Registro.REGEX+")$");
	private static final Pattern OP_REG_ACC = Pattern.compile("^(" +Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*#("+Registro.REGEX+")$");
	private static final Pattern OP_ACC_ACC = Pattern.compile("^#("+Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*#("+Registro.REGEX+")$");
	private static final Pattern OP_REG_VAL = Pattern.compile("^(" +Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Transcriptor.VALOR+")$");
	private static final Pattern OP_ACC_VAL = Pattern.compile("^#("+Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Transcriptor.VALOR+")$");
	
	private static final Pattern CMP_REG = Pattern.compile("^("+Comparacion.REGEX+")[\\s]*(" +Registro.REGEX+")$");
	private static final Pattern CMP_ACC = Pattern.compile("^("+Comparacion.REGEX+")[\\s]*#("+Registro.REGEX+")$");
	private static final Pattern CMP_VAL = Pattern.compile("^("+Comparacion.REGEX+")[\\s]*(" +Transcriptor.VALOR+")$");
	
	public void addOp(TriConsumer<String, BiFunction<Integer, Integer, Integer>, String> operacion, Matcher matcher) {
		addInstr(() -> operacion.accept(matcher.group(1), operaciones.get(matcher.group(2)), matcher.group(3)));
	}
	
	public void addCmp(BiConsumer<Function<Integer, Integer>, String> comparacion, Matcher matcher) {
		addInstr(() -> comparacion.accept(comparaciones.get(matcher.group(1)), matcher.group(2)));
	}
	
	public void add(String linea) {
		Matcher matcher;
			 if ((matcher = OP_REG_REG.matcher(linea)).find()) addOp(opRegReg, matcher);
		else if ((matcher = OP_ACC_REG.matcher(linea)).find()) addOp(opAccReg, matcher);
		else if ((matcher = OP_REG_ACC.matcher(linea)).find()) addOp(opRegAcc, matcher);
		else if ((matcher = OP_ACC_ACC.matcher(linea)).find()) addOp(opAccAcc, matcher);
		else if ((matcher = OP_REG_VAL.matcher(linea)).find()) addOp(opRegVal, matcher);
		else if ((matcher = OP_ACC_VAL.matcher(linea)).find()) addOp(opAccVal, matcher);
		
		else if ((matcher = CMP_REG.matcher(linea)).find())	addCmp(cmpReg, matcher);
		else if ((matcher = CMP_ACC.matcher(linea)).find()) addCmp(cmpAcc, matcher);
		else if ((matcher = CMP_VAL.matcher(linea)).find()) addCmp(cmpVal, matcher);
	}
	
	@Override
	public String toString() {
		return "[" + PC + "] " + regs+", cmp="+cmp+", "+Arrays.toString(stack);
	}

}
