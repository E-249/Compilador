package ensamblador;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ensamblador.Transcriptor.Operacion;
import ensamblador.Transcriptor.Registro;
import ensamblador.Transcriptor.Salto;

public class Procesador {
	
	private static final int REGISTRO_CNT = Registro.values().length;
	private static final int OPERACION_CNT = Operacion.values().length;
	private static final int COMPARACION_CNT = Salto.values().length + 3*2; // (3: GE, LE, NE)*2
	
	int PC;
	int cmp;
	ArrayList<Runnable> instr;
	HashMap<String, Integer> regs;
	int[] stack;
	
	private Scanner scanner;

	public Procesador(int stackSize) {
		stack = new int[stackSize];
		instr = new ArrayList<>();
		instr.add(null);
		
		regs = new HashMap<>(REGISTRO_CNT);
		operaciones = new HashMap<>(OPERACION_CNT);
		saltos = new HashMap<>(COMPARACION_CNT);
		
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
		operaciones.put("\\", (left, right) -> left % right);
		operaciones.put("?", (left, right) -> { cmp = left - right; return left; });
		operaciones.put("&", (left, right) -> left & right);
		operaciones.put("|", (left, right) -> left | right);
		operaciones.put("!", (left, right) -> left ^ right);
		operaciones.put("<", (left, right) -> left << right);
		operaciones.put(">", (left, right) -> left >> right);
		
		saltos.put(">",  target -> cmp >  0 ? target - 1 : PC);
		saltos.put("<",  target -> cmp <  0 ? target - 1 : PC);
		saltos.put("=",  target -> cmp == 0 ? target - 1 : PC);
		saltos.put("!",  target -> target - 1);
		saltos.put("=>", target -> cmp >= 0 ? target - 1 : PC);
		saltos.put("<=", target -> cmp <= 0 ? target - 1 : PC);
		saltos.put("<>", target -> cmp != 0 ? target - 1 : PC);
		saltos.put(">=", saltos.get("=>"));
		saltos.put("=<", saltos.get("<="));
		saltos.put("><", saltos.get("<>"));
		
		instruccionesExternas();
	}
	
	public Procesador(String lineas, int stackSize) {
		this(stackSize);
		for (String linea : lineas.split("\n"))
			add(linea);
	}

	public void run() {
		for (PC = 1; PC < instr.size() && PC > 0; PC++)
			instr.get(PC).run();
	}
	public void runPrintEnd() {
		for (PC = 1; PC < instr.size() && PC > 0; PC++)
			instr.get(PC).run();
		System.out.print(this);
	}
	public void runPrint() {
		for (PC = 1; PC < instr.size() && PC > 0; PC++) {
			String cnt = "[" + PC + "]\t";
			instr.get(PC).run();
			System.out.println(cnt + regs+", cmp="+cmp+", color=h"+Integer.toHexString(color).toUpperCase()+", "+Arrays.toString(stack));
		}
	}
	private int getStack(String reg) { return stack[regs.get(reg) - 1]; }
	private void setStack(String reg, int value) { stack[regs.get(reg) - 1] = value; }
	
	private HashMap<String, BiFunction<Integer, Integer, Integer>> operaciones;
	private HashMap<String, Function<Integer, Integer>> saltos;
	
	private static interface TriConsumer<T, U, R> { void accept(T t, U u, R r); }
	private TriConsumer<String, BiFunction<Integer, Integer, Integer>, String>
			opRegReg = (left, op, right) -> regs.put(left, op.apply(regs.get(left), regs.get(right))),
			opAccReg = (left, op, right) -> setStack(left, op.apply(getStack(left), regs.get(right))),
			opRegAcc = (left, op, right) -> regs.put(left, op.apply(regs.get(left), getStack(right))),
			opAccAcc = (left, op, right) -> setStack(left, op.apply(getStack(left), getStack(right))),
			opRegVal = (left, op, right) -> regs.put(left, op.apply(regs.get(left), Integer.parseInt(right))),
			opAccVal = (left, op, right) -> setStack(left, op.apply(getStack(left), Integer.parseInt(right)));
	
	private BiConsumer<Function<Integer, Integer>, String>
			cmpReg = (cmp, target) -> PC = cmp.apply(regs.get(target)),
			cmpAcc = (cmp, target) -> PC = cmp.apply(getStack(target)),
			cmpVal = (cmp, target) -> PC = cmp.apply(Integer.parseInt(target));
	
	private static final Pattern OP_REG_REG = Pattern.compile("^(" +Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Registro.REGEX+")$");
	private static final Pattern OP_ACC_REG = Pattern.compile("^#("+Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Registro.REGEX+")$");
	private static final Pattern OP_REG_ACC = Pattern.compile("^(" +Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*#("+Registro.REGEX+")$");
	private static final Pattern OP_ACC_ACC = Pattern.compile("^#("+Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*#("+Registro.REGEX+")$");
	private static final Pattern OP_REG_VAL = Pattern.compile("^(" +Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Transcriptor.VALOR+")$");
	private static final Pattern OP_ACC_VAL = Pattern.compile("^#("+Registro.REGEX+")[\\s]*("+Operacion.REGEX+")[\\s]*(" +Transcriptor.VALOR+")$");
	
	private static final Pattern CMP_REG = Pattern.compile("^("+Salto.REGEX+")[\\s]*(" +Registro.REGEX+")$");
	private static final Pattern CMP_ACC = Pattern.compile("^("+Salto.REGEX+")[\\s]*#("+Registro.REGEX+")$");
	private static final Pattern CMP_VAL = Pattern.compile("^("+Salto.REGEX+")[\\s]*(" +Transcriptor.VALOR+")$");

	public void addOp(TriConsumer<String, BiFunction<Integer, Integer, Integer>, String> operacion, Matcher matcher) {
		instr.add(() -> operacion.accept(matcher.group(1).toUpperCase(), operaciones.get(matcher.group(2)), matcher.group(3).toUpperCase()));
	}
	
	public void addCmp(BiConsumer<Function<Integer, Integer>, String> comparacion, Matcher matcher) {
		instr.add(() -> comparacion.accept(saltos.get(matcher.group(1)), matcher.group(2).toUpperCase()));
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
		return regs+", "+Arrays.toString(stack);
	}
	
	private void instruccionesExternas() {
		scanner = new Scanner(System.in);
		
		operaciones.put("$say$", (left, right) -> { System.out.print((char) (int) right); return left; });
		operaciones.put("$listen$", (_, _) -> scanner.nextInt());
		
		operaciones.put("$open$", (left, right)	->	{ createWindow(left, right);	return left; });
		operaciones.put("$close$", (left, _) ->		{ closeWindow();				return left; });
		operaciones.put("$color$", (left, right) ->	{ changeColor(right);			return left; });
		operaciones.put("$draw$", (left, right) ->	{ drawPixel(left, right);		return left; });
	}
	
	private JFrame window = null;
	private JPanel panel;
	private BufferedImage imagen;
	private int color;
	
	private void createWindow(int width, int height) {
		window = new JFrame();
		imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		color = 0xFFFFFF;
		panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				g.drawImage(imagen, 0, 0, null);
			}
		};
		
		panel.setPreferredSize(new Dimension(width, height));
		window.setContentPane(panel);
		window.pack();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		
		window.setVisible(true);
	}
	
	private void closeWindow() {
		if (window != null)
			window.dispose();
	}
	
	private void changeColor(int color) {
		this.color = color;
	}
	
	private void drawPixel(int x, int y) {
		imagen.setRGB(x, y, color);
		window.repaint();
	}
	
}
