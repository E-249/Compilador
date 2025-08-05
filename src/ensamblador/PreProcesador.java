package ensamblador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ensamblador.Transcriptor.*;

public class PreProcesador {
	
	private static final String VALOR			= Transcriptor.VALOR;
	private static final String NOMBRE			= Transcriptor.NOMBRE;
	private static final String CARACTER		= Transcriptor.CARACTER;
	private static final String REGISTRO		= Registro.REGEX;
	private static final String OPERACION		= Operacion.REGEX;
	private static final String COMPARACION		= Salto.REGEX;
	private static final String COMENTARIO		= Comentario.REGEX;
	private static final String EXPANSION		= Expansion.REGEX;
	//////////////////////////////////////////////////////////
	public PreProcesador() {
		
		String acceso =				Modificador.ACC + REGISTRO;
		String otros =				VALOR
									+"|"+ NOMBRE
									+"|"+ Modificador.AQUI;
		String regAcc =				acceso
									+"|"+ REGISTRO;
		String regAccVal =			VALOR
									+"|"+ acceso
									+"|"+ REGISTRO;
		String regAccOtr =			otros
									+"|"+ acceso
									+"|"+ REGISTRO;
		
		String instrOp =			"^("+regAcc+")[ \\t]*("+OPERACION+")[ \\t]*("+regAccVal+")$";
		String instrSalto =			"^("+COMPARACION+")[ \\t]*("+regAccVal+")$";
		String instr =				instrOp
									+"|"+ instrSalto;
		
		String instrOpNom =			"^("+regAcc+")[ \\t]*("+OPERACION+")[ \\t]*("+regAccOtr+")$";
		String instrCmpNom =		"^("+COMPARACION+")[ \\t]*("+regAccOtr+")$";
		String etiq =				"^"+Modificador.AQUI+"("+NOMBRE+")$";
		String instrEtiq =			instrOpNom
									+"|"+ instrCmpNom
									+"|"+ etiq;
		
		String expan =				Modificador.NO_AQUI+"("+NOMBRE+")[ \\t]*"+ EXPANSION;
		String llam =				Modificador.NO_AQUI+"("+NOMBRE+")[ \\t]+("+regAccOtr+")"
									+"|"+ Modificador.NO_AQUI+"("+NOMBRE+")";
		
		comentario				= Pattern.compile(COMENTARIO, Pattern.MULTILINE);
		expansion				= Pattern.compile(expan, Pattern.MULTILINE);
		llamada					= Pattern.compile(llam, Pattern.MULTILINE);
		instruccion				= Pattern.compile(instr, Pattern.MULTILINE);
		instruccionEtiqueta		= Pattern.compile(instrEtiq, Pattern.MULTILINE);
		etiqueta				= Pattern.compile(etiq, Pattern.MULTILINE);
	}
	//////////////////////////////////////////////////////////
	private final Pattern comentario;
	private final Pattern expansion;
	private final Pattern llamada;
	private final Pattern instruccion;
	private final Pattern instruccionEtiqueta;
	private final Pattern etiqueta;
	
	private static final Pattern VACIO			= Pattern.compile("^\\s*\\n", Pattern.MULTILINE);
	private static final Pattern INDENTACION	= Pattern.compile("^[ \\t]+|[ \\t]+$", Pattern.MULTILINE);
	
	private String eliminarIndentacion(String lineas) { return INDENTACION.matcher(lineas).replaceAll(""); }
	private String borrarVacias(String lineas) { return VACIO.matcher(lineas).replaceAll(""); }
	private String borrarFrom(Matcher matcher) { return borrarVacias(matcher.replaceAll("")); }
	private String corregir(String lineas) { return eliminarIndentacion(borrarVacias(lineas)); }
	
	private String ignorarComentarios(String lineas) {
		Matcher matcher = comentario.matcher(lineas);
		return borrarFrom(matcher);
	}
	
	private String reemplazarCaracteres(String lineas) {
		Matcher matcher = Pattern.compile(CARACTER, Pattern.DOTALL).matcher(lineas);
		StringBuffer str = new StringBuffer();
		
		while (matcher.find())
			matcher.appendReplacement(str, String.valueOf((int) matcher.group(1).charAt(0)));
		matcher.appendTail(str);
		return str.toString();
	}
	
	private String guardarExpansiones(String lineas, HashMap<String, String> expansiones) {
		Matcher matcher = expansion.matcher(lineas);
		
		while (matcher.find())
			expansiones.put(matcher.group(1), matcher.group(2));
		
		return borrarFrom(matcher);
	}
	
	private String reemplazarSustituto(Matcher matcher, HashMap<String, String> expansiones) {

		String nombre = matcher.group(1);
		if (nombre == null)
			nombre = matcher.group(3);
		
		String lineas = expansiones.get(nombre);
		if (lineas == null)
			return nombre;
		
		String sustituto = matcher.group(2);
		if (sustituto == null)
			return lineas;
		
		if (expansiones.containsKey(sustituto))
			sustituto = expansiones.get(sustituto);
		
		return lineas.replaceAll(Expansion.SUSTITUTO.toString(), sustituto);
	}
	
	private String reemplazarExpansiones(String lineas, HashMap<String, String> expansiones) {
		Matcher matcher = llamada.matcher(lineas);
		StringBuffer str = new StringBuffer();
		
		while (matcher.find())
			matcher.appendReplacement(str, reemplazarSustituto(matcher, expansiones));
		matcher.appendTail(str);
		return str.toString();
	}
	
	private LinkedList<String> normalizar(String lineas) {
		Matcher matcher = instruccionEtiqueta.matcher(lineas);
		LinkedList<String> instrucciones = new LinkedList<>();
		
		while (matcher.find())
			instrucciones.add(matcher.group());
		return instrucciones;
	}
	
	private LinkedList<String> guardarLabels(LinkedList<String> instrucciones, HashMap<String, String> expansiones) {
		expansiones.clear();
		LinkedList<String> nuevas = new LinkedList<>();
		Iterator<String> it = instrucciones.iterator();
		
		String str;
		Matcher matcher;
		
		while (it.hasNext()) {
			str = it.next();
			
			matcher = etiqueta.matcher(str);
			if (matcher.matches())
				expansiones.put(matcher.group(1), String.valueOf(nuevas.size() + 1));
			else
				nuevas.add(str);
		}
		return nuevas;
	}
	
	private String reemplazarAqui(LinkedList<String> instrucciones) {
		StringBuffer str = new StringBuffer();
		Iterator<String> it = instrucciones.iterator();
		
		for (int indice = 0; it.hasNext(); indice++)
			str.append(it.next().replaceAll(Transcriptor.Modificador.AQUI.toString(), String.valueOf(indice + 1)) + (it.hasNext() ? "\n" : ""));
		return str.toString();
	}
	
	private String ignorarResiduos(String lineas) {
		Matcher matcher = instruccion.matcher(lineas);
		StringBuffer str = new StringBuffer();
		
		if (matcher.find())
			str.append(matcher.group());
		while (matcher.find())
			str.append('\n').append(matcher.group());
		
		return str.toString();
	}
	
	public String reemplazar(String lineas) {
		HashMap<String, String> expansiones = new HashMap<>();
		LinkedList<String> instrucciones;

		lineas = eliminarIndentacion(lineas);
		
		lineas = ignorarComentarios(lineas);
		lineas = reemplazarCaracteres(lineas);
		lineas = guardarExpansiones(lineas, expansiones);
		lineas = reemplazarExpansiones(lineas, expansiones);
		lineas = corregir(lineas);
		
		instrucciones = normalizar(lineas);
		instrucciones = guardarLabels(instrucciones, expansiones);
		lineas = reemplazarAqui(instrucciones);
		lineas = reemplazarExpansiones(lineas, expansiones);
		lineas = ignorarResiduos(lineas);
		return lineas;
	}

}
