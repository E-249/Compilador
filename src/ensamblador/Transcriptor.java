package ensamblador;

import java.util.HashMap;

public class Transcriptor {
	
	public static enum Comentario {
		DEF('#'),
		WARN('!'),
		INFO('?');
		private final char simbolo;
		private Comentario(char simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<Character, Comentario> comentarios) {
			for (Comentario comentario : Comentario.values()) comentarios.put(comentario.simbolo, comentario); }
	}

	public static enum Modificador {
		ACC('#'),
		OPEN_DEF('['),
		CLOSE_DEF(']'),
		IT('^'),
		HERE('@');
		private final char simbolo;
		private Modificador(char simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<Character, Modificador> modificadores) {
			for (Modificador modificador : Modificador.values()) modificadores.put(modificador.simbolo, modificador); }
	}
	
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
		ASG(':'),
		ADD('+'),
		SUB('-'),
		MUL('*'),
		DIV('/'),
		CMP('?');
		private final char simbolo;
		private Operacion(char simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<Character, Operacion> operaciones) {
			for (Operacion operacion : Operacion.values()) operaciones.put(operacion.simbolo, operacion); }
	}
	public static enum Comparacion {
		GT('>'),
		LT('<'),
		EQ('='),
		AL('.');
		private final char simbolo;
		private Comparacion(char simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<Character, Comparacion> comparaciones) {
			for (Comparacion comparacion : Comparacion.values()) comparaciones.put(comparacion.simbolo, comparacion); }
	}

	public final static HashMap<Character, Comentario> comentarios = new HashMap<>();
	public final static HashMap<Character, Modificador> modificadores = new HashMap<>();
	public final static HashMap<Character, Registro> registros = new HashMap<>();
	public final static HashMap<Character, Operacion> operaciones = new HashMap<>();
	public final static HashMap<Character, Comparacion> comparaciones = new HashMap<>();
	static {
		Comentario.init(comentarios);
		Modificador.init(modificadores);
		Registro.init(registros);
		Operacion.init(operaciones);
		Comparacion.init(comparaciones);
	}	

	public static void main(String[] args) {
		System.out.println(comentarios);
		System.out.println(modificadores);
		System.out.println(registros);
		System.out.println(operaciones);
		System.out.println(comparaciones);
	}

}
