package ensamblador;

import java.util.HashMap;

public class Transcriptor {

	public static enum Registro {
		A('A'),
		E('E'),
		O('O'),
		I('I'),
		U('U'),
		
		S('S'),
		B('B'),
		P('P'),
		R('R');
		private final char simbolo;
		private Registro(char simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<Character, Registro> registros) {
			for (Registro registro : Registro.values()) registros.put(registro.simbolo, registro); }
	}

	public static enum Operacion {
		ASG(':', false),
		ADD('+', false),
		SUB('-', false),
		MUL('*', false),
		DIV('/', false),
		CMP('?', false),
		
		GT('>', true),
		LT('<', true),
		EQ('=', true);
		private final char simbolo;
		public final boolean salto;
		private Operacion(char simbolo, boolean salto) { this.simbolo = simbolo; this.salto = salto; }
		public static void init(HashMap<Character, Operacion> operaciones) {
			for (Operacion operacion : Operacion.values()) operaciones.put(operacion.simbolo, operacion); }
	}

	public static enum Modificador {
		DIR('#'),
		LBL('@');
		private final char simbolo;
		private Modificador(char simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<Character, Modificador> modificadores) {
			for (Modificador modificador : Modificador.values()) modificadores.put(modificador.simbolo, modificador); }
	}

	public final static HashMap<Character, Registro> registros = new HashMap<>();
	public final static HashMap<Character, Operacion> operaciones = new HashMap<>();
	public final static HashMap<Character, Modificador> modificadores = new HashMap<>();
	static {
		Registro.init(registros);
		Operacion.init(operaciones);
		Modificador.init(modificadores);
	}	

	public static void main(String[] args) {
		System.out.println(registros);
		System.out.println(operaciones);
		System.out.println(modificadores);
	}

}
