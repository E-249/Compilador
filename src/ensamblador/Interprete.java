package ensamblador;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Interprete {

	public static final int
			A = 0,
			E = 1,
			O = 2,
			I = 3,
			U = 4,
		
			S = 5,
			B = 6,
			P = 7,
			R = 8;
	public static final int REGISTER_CNT = 9;

	int PC;
	int cmp;
	ArrayList<Runnable> instr;
	int[] regs;
	int[] stack;

	public Interprete(int stackSize) {
		regs = new int[REGISTER_CNT];
		instr = new ArrayList<>();
		stack = new int[stackSize];
	}

	public void addInstr(Runnable instruction) {
		instr.add(instruction);
	}
	
	public void addInstr(BiConsumer<Integer, Integer> instruction, int left, int right) {
		instr.add(() -> instruction.accept(left, right));
	}
	
	public void addInstr(Consumer<Integer> instruction, int target) {
		instr.add(() -> instruction.accept(target));
	}

	public void run() {
		String cnt;
		System.out.println("A E O I U S B P R");
		for (PC = 0; PC < instr.size(); PC++) {
			cnt = "[" + PC + "]";
			instr.get(PC).run();
			for (int i = 0; i < REGISTER_CNT; i++)
				System.out.print(regs[i] + " ");
			System.out.println(cnt);
		}
	}
	// reg op reg
	public void asgRR(int left, int right) { regs[left]  = regs[right]; }
	public void addRR(int left, int right) { regs[left] += regs[right]; }
	public void subRR(int left, int right) { regs[left] -= regs[right]; }
	public void mulRR(int left, int right) { regs[left] *= regs[right]; }
	public void divRR(int left, int right) { regs[left] /= regs[right]; }
	public void cmpRR(int left, int right) { cmp = regs[left] - regs[right]; }
	// acc op reg
	public void asgAR(int left, int right) { stack[regs[left]]  = regs[right]; }
	public void addAR(int left, int right) { stack[regs[left]] += regs[right]; }
	public void subAR(int left, int right) { stack[regs[left]] -= regs[right]; }
	public void mulAR(int left, int right) { stack[regs[left]] *= regs[right]; }
	public void divAR(int left, int right) { stack[regs[left]] /= regs[right]; }
	public void cmpAR(int left, int right) { cmp = stack[regs[left]] - regs[right]; }
	// reg op acc
	public void asgRA(int left, int right) { regs[left]  = stack[regs[right]]; }
	public void addRA(int left, int right) { regs[left] += stack[regs[right]]; }
	public void subRA(int left, int right) { regs[left] -= stack[regs[right]]; }
	public void mulRA(int left, int right) { regs[left] *= stack[regs[right]]; }
	public void divRA(int left, int right) { regs[left] /= stack[regs[right]]; }
	public void cmpRA(int left, int right) { cmp = regs[left] - stack[regs[right]]; }
	// acc op acc
	public void asgPP(int left, int right) { stack[regs[left]]  = stack[regs[right]]; }
	public void addPP(int left, int right) { stack[regs[left]] += stack[regs[right]]; }
	public void subPP(int left, int right) { stack[regs[left]] -= stack[regs[right]]; }
	public void mulPP(int left, int right) { stack[regs[left]] *= stack[regs[right]]; }
	public void divPP(int left, int right) { stack[regs[left]] /= stack[regs[right]]; }
	public void cmpPP(int left, int right) { cmp = stack[regs[left]] - stack[regs[right]]; }
	// reg op val
	public void asgRV(int left, int right) { regs[left]  = right; }
	public void addRV(int left, int right) { regs[left] += right; }
	public void subRV(int left, int right) { regs[left] -= right; }
	public void mulRV(int left, int right) { regs[left] *= right; }
	public void divRV(int left, int right) { regs[left] /= right; }
	public void cmpRV(int left, int right) { cmp = regs[left] - right; }
	// acc op val
	public void asgAV(int left, int right) { stack[regs[left]]  = right; }
	public void addAV(int left, int right) { stack[regs[left]] += right; }
	public void subAV(int left, int right) { stack[regs[left]] -= right; }
	public void mulAV(int left, int right) { stack[regs[left]] *= right; }
	public void divAV(int left, int right) { stack[regs[left]] /= right; }
	public void cmpAV(int left, int right) { cmp = stack[regs[left]] - right; }
	// br reg
	public void balR(int reg) { PC = regs[reg] - 1; }
	public void bgtR(int reg) { if (cmp > 0) PC = regs[reg] - 1; }
	public void bltR(int reg) { if (cmp < 0) PC = regs[reg] - 1; }
	public void beqR(int reg) { if (cmp == 0) PC = regs[reg] - 1; }
	public void bgeR(int reg) { if (cmp >= 0) PC = regs[reg] - 1; }
	public void bleR(int reg) { if (cmp <= 0) PC = regs[reg] - 1; }
	public void bneR(int reg) { if (cmp != 0) PC = regs[reg] - 1; }
	// br acc
	public void balA(int reg) { PC = stack[regs[reg]] - 1; }
	public void bgtA(int reg) { if (cmp > 0) PC = stack[regs[reg]] - 1; }
	public void bltA(int reg) { if (cmp < 0) PC = stack[regs[reg]] - 1; }
	public void beqA(int reg) { if (cmp == 0) PC = stack[regs[reg]] - 1; }
	public void bgeA(int reg) { if (cmp >= 0) PC = stack[regs[reg]] - 1; }
	public void bleA(int reg) { if (cmp <= 0) PC = stack[regs[reg]] - 1; }
	public void bneA(int reg) { if (cmp != 0) PC = stack[regs[reg]] - 1; }
	// br val
	public void balV(int val) { PC = val - 1; }
	public void bgtV(int val) { if (cmp > 0) PC = val - 1; }
	public void bltV(int val) { if (cmp < 0) PC = val - 1; }
	public void beqV(int val) { if (cmp == 0) PC = val - 1; }
	public void bgeV(int val) { if (cmp >= 0) PC = val - 1; }
	public void bleV(int val) { if (cmp <= 0) PC = val - 1; }
	public void bneV(int val) { if (cmp != 0) PC = val - 1; }
	
	public void make() {
		final int DotProduct = 14;
		final int here = 8, loop = 16;
		final int halt = 27;
		
//		final int // VECTOR
//				VECTOR_X = 1,
//				VECTOR_Y = 2,
//				VECTOR_Z = 3;
		
		final int THIS = 0 - 1;
		stack[0] = 1;
		stack[1] = 2;
		stack[2] = 3;
		
		final int THAT = 3 - 1;
		stack[3] = 4;
		stack[4] = 5;
		stack[5] = 6;
		
		regs[S] = 5;

		addInstr(this::asgRR, P, B); //0
		addInstr(this::addRV, P, THIS); //1
		addInstr(this::asgRR, I, P); //2

		addInstr(this::asgRR, P, B); //3
		addInstr(this::addRV, P, THAT); //4

		addInstr(this::asgRR, U, P); //5

		addInstr(this::addRV, S, 1); //6
		addInstr(this::asgAR, S, R); //7

		addInstr(this::asgRV, R, here); //8
		addInstr(this::addRV, R, 3); //9
		addInstr(this::balV, DotProduct); //10
		addInstr(this::asgRA, R, S); //11
		addInstr(this::subRV, S, 1); //12
		addInstr(this::balV, halt); //13

		// DotProduct
		addInstr(this::asgRV, O, 0); //14

		addInstr(this::asgRV, A, 1); //15
		// loop
		addInstr(this::cmpRV, A, 3); //16
		addInstr(this::bgtR, R); //17

		addInstr(this::asgRR, P, I); //18
		addInstr(this::addRR, P, A); //19
		addInstr(this::asgRA, E, P); //20

		addInstr(this::asgRR, P, U); //21
		addInstr(this::addRR, P, A); //22
		addInstr(this::mulRA, E, P); //23

		addInstr(this::addRR, O, E); //24

		addInstr(this::addRV, A, 1); //25
		addInstr(this::balV, loop); //26
	}

	public static void main(String[] args) {
		Interprete interprete = new Interprete(8);
		interprete.make();
		interprete.run();
		System.out.println("Resultado: " + interprete.regs[O]);
	}

}
