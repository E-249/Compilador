package ensamblador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class PreProcesador {
	
	private String ignorarComentarios(String lineas) {
		Matcher matcher = Transcriptor.COMENTARIO.matcher(lineas);
		return matcher.replaceAll("");
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
	
	private LinkedList<String> normalizar(String lineas) {
		Matcher matcher = Transcriptor.INSTRUCCION_LABEL.matcher(lineas);
		LinkedList<String> instrucciones = new LinkedList<>();
		
		while (matcher.find())
			instrucciones.add(matcher.group());
		return instrucciones;
	}
	
	private LinkedList<String> guardarLabels(LinkedList<String> instrucciones, HashMap<String, String> expansiones) {
		LinkedList<String> nuevas = new LinkedList<>();
		Iterator<String> it = instrucciones.iterator();
		
		String str;
		Matcher matcher;
		
		while (it.hasNext()) {
			str = it.next();
			
			matcher = Transcriptor.LABEL.matcher(str);
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
	
	public String reemplazar(String lineas) {
		HashMap<String, String> expansiones = new HashMap<>();
		LinkedList<String> instrucciones;

		lineas = ignorarComentarios(lineas);
		lineas = guardarExpansiones(lineas, expansiones);
		lineas = reemplazarExpansiones(lineas, expansiones); // Colocar en adelante
		
		instrucciones = normalizar(lineas);
		//instrucciones = guardarLabels(instrucciones, expansiones);
		//lineas = reemplazarAqui(instrucciones);
		return lineas;
	}
	
	public static void main(String[] args) {
		reemplazarTest();
	}
	
	private static void reemplazarTest() {
		PreProcesador p = new PreProcesador();
		String lineas =
				"""
				?~ Expansiones
				push [
				    S + 1
				    #S : ^
				]
				
				pop [
				    ^ : #S
				    S - 1
				]
				
				begin [
				    S + 1
				    #S : B
				    B : S
				]
				
				end [
				    B : #S
				    S - 1
				]
				
				var [
				    P : B
				    P + ^
				]
				
				jump [
				    S + 1
				    #S : R
				    R : @
				    R + 3
				    ! ^
				    R : #S
				    S - 1
				]
				
				?~ Tabla ID
				Numero [0]
				PX [1]
				PY [2]
				POSICION [2]
				POSICION_X [1]
				POSICION_Y [2]
				POSICION_Z [3]
				ENTIDAD [4]
				ENTIDAD_POSICION [0]
				!~ POSICION_X [1]
				!~ POSICION_Y [2]
				!~ POSICION_Z [2]
				
				?~ Code
				
				?~ Entidad Mover [Posicion [10, 20, 30]]
				I : S !~
				push 10
				push 20
				push 30
				jump EntidadMover_Posicion_
				
				?~ This Posicion [That Posicion]
				@EntidadMover_Posicion_
				@PosicionSuma_Posicion_
				
				var POSICION
				P + POSICION_Z
				pop #P
				
				var POSICION
				P + POSICION_Y
				pop #P
				
				var POSICION
				P + POSICION_X
				pop #P
				
				! R
	            """;
		System.out.println("{Lineas}\n" + p.reemplazar(lineas));
	}
	
//	private static void ignorarComentariosTest() {
//		PreProcesador p = new PreProcesador();
//		String lineas =
//				"""
//				?~ aaaa
//				aaa !~ si
//				b~as
//				sadas~b
//				""";
//		System.out.println("{Lineas}\n" + p.ignorarComentarios(lineas));
//	}
//	
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
