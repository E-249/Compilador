package ensamblador;

import java.util.ArrayList;

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

	public void programa() {
		final int DotProduct = 14;
		final int loop = 16;
		
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

		addInstr(() -> regs[P] = regs[B]); //0
		addInstr(() -> regs[P] += THIS); //1
		addInstr(() -> regs[I] = regs[P]); //2

		addInstr(() -> regs[P] = regs[B]); //3
		addInstr(() -> regs[P] += THAT); //4

		addInstr(() -> regs[U] = regs[P]); //5

		addInstr(() -> regs[S] += 1); //6
		addInstr(() -> stack[regs[S]] = regs[R]); //7

		addInstr(() -> regs[R] = 8); //8
		addInstr(() -> regs[R] += 3); //9
		addInstr(() -> PC = DotProduct - 1); //10
		addInstr(() -> regs[R] = stack[regs[S]]); //11
		addInstr(() -> regs[S] -= 1); //12
		addInstr(() -> PC = 27); //13

		// DotProduct
		addInstr(() -> regs[O] = 0); //14

		addInstr(() -> regs[A] = 1); //15
		// loop
		addInstr(() -> cmp = regs[A] - 3); //16
		addInstr(() -> { if (cmp > 0) PC = regs[R]; }); //17

		addInstr(() -> regs[P] = regs[I]); //18
		addInstr(() -> regs[P] += regs[A]); //19
		addInstr(() -> regs[E] = stack[regs[P]]); //20

		addInstr(() -> regs[P] = regs[U]); //21
		addInstr(() -> regs[P] += regs[A]); //22
		addInstr(() -> regs[E] *= stack[regs[P]]); //23

		addInstr(() -> regs[O] += regs[E]); //24

		addInstr(() -> regs[A] += 1); //25
		addInstr(() -> PC = loop - 1); //26
	}

	public static void main(String[] args) {
		Interprete interprete = new Interprete(1024);
		interprete.programa();
		interprete.run();
		System.out.println("Resultado: " + interprete.regs[O]);
	}

}
