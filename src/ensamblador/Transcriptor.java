package ensamblador;

import java.util.HashMap;

// Clase para lexico
public class Transcriptor {
	
	public static final String
			VALOR = "[+-]?[0-9]+",
			NOMBRE = "[a-zA-Z_]{2,}",
			CARACTER = "'(.)'";

	public static enum Comentario {
		DEF("~"),
		WARN("!"),
		INFO("?");
		private final String simbolo;
		private Comentario(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Comentario> comentarios) {
			for (Comentario comentario : Comentario.values()) comentarios.put(comentario.simbolo, comentario); }
		@Override public String toString() { return simbolo; }
		public static final String MULTI_LINE = "(?<![" + WARN + INFO + "])" + DEF + "[^" + DEF + "]*" + DEF;
		public static final String ONE_LINE = "[" + INFO + WARN + "]" + DEF + ".*";
		public static final String REGEX = ONE_LINE + "|" + MULTI_LINE;
	}

	public static enum Expansion {
		OPEN("\\["),
		CLOSE("\\]"),
		SUSTITUTO("\\^");
		private final String simbolo;
		private Expansion(String simbolo) { this.simbolo = simbolo; }
		@Override public String toString() { return simbolo; }
		public static final String REGEX = OPEN + "([^" + CLOSE + "]*)" + CLOSE;
	}

	public static enum Modificador {
		ACC("#"),
		AQUI("@"),
		EXT("\\$"),
		NEXT(",?\n?");
		private final String simbolo;
		private Modificador(String simbolo) { this.simbolo = simbolo; }
		@Override public String toString() { return simbolo; }
		public static final String NO_AQUI = "(?<!["+AQUI+"a-zA-Z_])";
		public static final String EXT_FUNC = EXT + NOMBRE + EXT;
	}

	public static enum Registro {
		A("A"),
		E("E"),
		O("O"),
		I("I"),
		U("U"),

		S("S"),
		B("B"),
		P("P"),
		R("R");
		private final String simbolo;
		private Registro(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Registro> registros) {
			for (Registro registro : Registro.values()) registros.put(registro.simbolo, registro); }
		@Override public String toString() { return simbolo; }
		public static final String MAYUS =""+ A + E + O + I + U + S + B + P + R;
		public static final String MINUS = MAYUS.toLowerCase();
		public static final String REGEX = "[" + MAYUS + MINUS + "]";
	}

	public static enum Operacion {
		ASG("\\:"),
		ADD("\\+"),
		SUB("\\-"),
		MUL("\\*"),
		DIV("\\/"),
		CMP("\\?"),
		
		AND("\\&"),
		OR("\\|"),
		XOR("\\%");
		private final String simbolo;
		private Operacion(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Operacion> operaciones) {
			for (Operacion operacion : Operacion.values()) operaciones.put(operacion.simbolo, operacion); }
		@Override public String toString() { return simbolo; }
		public static final String REGEX = "[" + ASG + ADD + SUB + MUL + DIV + CMP + AND + OR + XOR + "]"
										+"|"+Modificador.EXT_FUNC;
	}
	
	public static enum Salto {
		GT(">"),
		LT("<"),
		EQ("="),
		AL("!");
		public final String simbolo;
		private Salto(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Salto> saltos) {
			for (Salto salto : Salto.values()) saltos.put(salto.simbolo, salto); }
		@Override public String toString() { return simbolo; }
		public static final String CMP = "[" + GT + LT + EQ + AL + "]";
		public static final String COMB =
				"|" + GT + "[" + LT + EQ + "]" +
				"|" + LT + "[" + EQ + GT + "]" +
				"|" + EQ + "[" + LT + GT + "]";
		public static final String REGEX = CMP + COMB
										+"|"+Modificador.EXT_FUNC;
	}
	
	public final static HashMap<String, Registro> registros = new HashMap<>();
	public final static HashMap<String, Operacion> operaciones = new HashMap<>();
	public final static HashMap<String, Salto> saltos = new HashMap<>();
	static {
		Registro.init(registros);
		Operacion.init(operaciones);
		Salto.init(saltos);
	}
	
}
