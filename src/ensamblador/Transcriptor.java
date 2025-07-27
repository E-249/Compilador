package ensamblador;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transcriptor {

	private static final String
			VALOR = "[0-9]+",
			NOMBRE = "[a-zA-Z]{2,}",
			
			INSTR = Operacion.REGEX + "|" + Comparacion.REGEX;
	
	
	public static final Pattern
			LLAMADA = Pattern.compile(Expansion.IN, Pattern.MULTILINE),
			EXPANSION = Pattern.compile(Expansion.OUT, Pattern.MULTILINE),
	
			OPERACION = Pattern.compile(Operacion.REGEX, Pattern.MULTILINE),
			COMPARACION = Pattern.compile(Comparacion.REGEX, Pattern.MULTILINE),
			
			INSTRUCCION = Pattern.compile(INSTR, Pattern.MULTILINE);

	public static enum Comentario {
		DEF("~"),
		WARN("!"),
		INFO("?");
		public final String simbolo;
		private Comentario(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Comentario> comentarios) {
			for (Comentario comentario : Comentario.values()) comentarios.put(comentario.simbolo, comentario); }
		@Override public String toString() { return simbolo; }
		public static final String MULTI_LINE = "(?<![" + WARN + INFO + "])" + DEF + "[^" + DEF + "]*" + DEF;
		public static final String ONE_LINE = "[" + INFO + WARN + "]" + DEF + ".*";
	}

	public static enum Expansion {
		OPEN("\\["),
		CLOSE("\\]"),
		SUSTITUTO("\\^");
		public final String simbolo;
		private Expansion(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Expansion> expansiones) {
			for (Expansion expansion : Expansion.values()) expansiones.put(expansion.simbolo, expansion); }
		@Override public String toString() { return simbolo; }
		public static final String IN = "(" + NOMBRE + ") ([^\\r\\n]*)";
		public static final String OUT = "(" + NOMBRE + ") *" + OPEN + "([^" + CLOSE + "]*)" + CLOSE;
	}

	public static enum Modificador {
		ACC("#"),
		AQUI("@");
		public final String simbolo;
		private Modificador(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Modificador> modificadores) {
			for (Modificador modificador : Modificador.values()) modificadores.put(modificador.simbolo, modificador); }
		@Override public String toString() { return simbolo; }
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
		public final String simbolo;
		private Registro(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Registro> registros) {
			for (Registro registro : Registro.values()) registros.put(registro.simbolo, registro); }
		@Override public String toString() { return simbolo; }
		public static final String REG = "[" + A + E + O + I + U + S + B + P + R + "]";
		public static final String REG_ACC = Modificador.ACC + "?" + REG;
		public static final String REG_ACC_VAL = REG_ACC + "|" + VALOR + "|" + Modificador.AQUI;
	}

	public static enum Operacion {
		ASG("\\:"),
		ADD("\\+"),
		SUB("\\-"),
		MUL("\\*"),
		DIV("\\/"),
		CMP("\\?");
		public final String simbolo;
		private Operacion(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Operacion> operaciones) {
			for (Operacion operacion : Operacion.values()) operaciones.put(operacion.simbolo, operacion); }
		@Override public String toString() { return simbolo; }
		public static final String OP = "[" + ASG + ADD + SUB + MUL + DIV + CMP + "]";
		public static final String REGEX = "(" + Registro.REG_ACC + ")[\\s]*(" + OP + ")[\\s]*(" + Registro.REG_ACC_VAL + ")";
	}
	
	public static enum Comparacion {
		GT(">"),
		LT("<"),
		EQ("="),
		AL("!");
		public final String simbolo;
		private Comparacion(String simbolo) { this.simbolo = simbolo; }
		public static void init(HashMap<String, Comparacion> comparaciones) {
			for (Comparacion comparacion : Comparacion.values()) comparaciones.put(comparacion.simbolo, comparacion); }
		@Override public String toString() { return simbolo; }
		public static final String CMP = "[" + GT + LT + EQ + AL + "]";
		public static final String REGEX = CMP + "|>[=<]|<[=>]|=[<>]";
	}

	public final static HashMap<String, Modificador> modificadores = new HashMap<>();
	public final static HashMap<String, Registro> registros = new HashMap<>();
	public final static HashMap<String, Operacion> operaciones = new HashMap<>();
	public final static HashMap<String, Comparacion> comparaciones = new HashMap<>();
	public final static HashMap<String, Expansion> expansiones = new HashMap<>();
	public final static HashMap<String, Comentario> comentarios = new HashMap<>();
	static {
		Comentario.init(comentarios);
		Modificador.init(modificadores);
		Expansion.init(expansiones);
		Registro.init(registros);
		Operacion.init(operaciones);
		Comparacion.init(comparaciones);
	}

	public static void regex(String regex, String string, String subst) {
		System.out.println(regex);
		System.out.println();
		System.out.println(string);
		System.out.println();
		System.out.println(subst);
		System.out.println();

		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(string);
		while (matcher.find()) {
			matcher.group(1);
		}
			for (int i = 1; i <= matcher.groupCount(); i++)
				System.out.println("Grupo " + i + ": " + matcher.group(i));
	}

	public static void main(String[] args) {
		System.out.println(Comentario.MULTI_LINE);
	}

}
