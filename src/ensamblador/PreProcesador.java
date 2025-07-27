package ensamblador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class PreProcesador {
	
	private LinkedList<String> normalizar(String lineas) {
		Matcher matcher = Transcriptor.INSTRUCCION.matcher(lineas);
		LinkedList<String> instrucciones = new LinkedList<>();
		
		while (matcher.find())
			instrucciones.add(matcher.group());
		return instrucciones;
	}
	
	private String guardarExpansiones(String lineas, HashMap<String, String> expansiones) {
		Matcher matcher = Transcriptor.EXPANSION.matcher(lineas);
		
		while (matcher.find())
			expansiones.put(matcher.group(1), matcher.group(2));
		
		return matcher.replaceAll("");
	}
	
	private String reemplazarSustituto(String lineas, String sustituto) {
		return sustituto != null ?
				lineas.replaceAll(Transcriptor.Expansion.SUSTITUTO.toString(), sustituto)
				: lineas;
	}
	
	private String reemplazarExpansiones(String lineas, HashMap<String, String> expansiones) {
		return Transcriptor.LLAMADA.matcher(lineas)
				.replaceAll(mr -> reemplazarSustituto(expansiones.get(mr.group(1)), mr.group(2)));
	}
	
	private String reemplazarAqui(LinkedList<String> instrucciones) {
		StringBuffer str = new StringBuffer();
		Iterator<String> it = instrucciones.iterator();
		
		for (int indice = 0; it.hasNext(); indice++)
			str.append(it.next().replaceAll(Transcriptor.Modificador.AQUI.toString(), String.valueOf(indice + 1)) + (it.hasNext() ? "\n" : ""));
		return str.toString();
	}
	
	public String reemplazar(String lineas) {
		HashMap<String, String> expansiones = new HashMap<>();

		lineas = guardarExpansiones(lineas, expansiones);
		lineas = reemplazarExpansiones(lineas, expansiones);
		
		LinkedList<String> instrucciones = normalizar(reemplazarExpansiones(lineas, expansiones));
		return reemplazarAqui(instrucciones);
	}
	
	public static void main(String[] args) {
		reemplazarTest();
	}
	
	private static void reemplazarTest() {
		PreProcesador p = new PreProcesador();
		String lineas =
				"""
				push [
				    S + 1
				    #S : ^
				]
				
				pop [
				    ^ : #S
				    S - 1
				]
				
				begin [
				    push B
				    B : S
				]
				
				end [
				    pop B
				]
				
				var [
				    P : B
				    P + ^
				]
				
				jump [
				    push R
				    R : @
				    R + 3
				    ^
				    pop R
				]
				var 2
				P + 3
				pop #P
				
				var 2
				P + 2
				pop #P
				
				var 2
				P + 1
				pop #P
				
				! R
	            """;
		System.out.println("{Lineas}\n" + p.reemplazar(lineas));
	}
	
//	private static void reemplazarExpansionTest() {
//		PreProcesador p = new PreProcesador();
//		String lineas = """
//				push A
//				pop B
//				""";
//		HashMap<String, String> expansiones = new HashMap<>();
//		expansiones.put("push", "hola");
//		expansiones.put("pop", "adios");
//		System.out.println(p.reemplazarExpansiones(lineas, expansiones));
//	}
//	
//	private static void guardarExpansionTest() {
//		PreProcesador p = new PreProcesador();
//		String lineas = """
//				push [A + S]
//				pop [B]
//				""";
//		HashMap<String, String> expansiones = new HashMap<>();
//		p.guardarExpansiones(lineas, expansiones);
//		System.out.println(expansiones);
//	}
//	
//	private static void reemplazarAquiTest() {
//		PreProcesador p = new PreProcesador();
//		String lineas = """
//				push [
//					A
//				]
//				S + A
//				
//				S : @
//				S + B
//				
//				S + @
//				S + 3
//				S + E
//				S + U
//				#S : @
//				""";
//		System.out.println(p.reemplazarAqui(lineas));
//	}

}
