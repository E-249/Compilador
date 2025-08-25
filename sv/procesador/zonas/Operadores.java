package procesador.zonas;

import java.util.ArrayList;

import procesador.Procesador;
import procesador.elementos.Operador;

public class Operadores {
	
	private Procesador procesador;

	public static class Integer extends Operadores {
		private ArrayList<Operador<java.lang.Integer>> operaciones;
		
		public Integer() {
			operaciones.add(new Operador<>(":", (izq, der) -> izq.num =  der.num));
			operaciones.add(new Operador<>("+", (izq, der) -> izq.num += der.num));
			operaciones.add(new Operador<>("-", (izq, der) -> izq.num -= der.num));
			operaciones.add(new Operador<>("*", (izq, der) -> izq.num *= der.num));
			operaciones.add(new Operador<>("/", (izq, der) -> izq.num /= der.num));
			operaciones.add(new Operador<>("\\",(izq, der) -> izq.num %= der.num));
			
			operaciones.add(new Operador<>("?", (izq, der) -> super.procesador.cmp.num = izq.num - der.num));
			
			operaciones.add(new Operador<>("|", (izq, der) -> izq.num |= der.num));
			operaciones.add(new Operador<>("&", (izq, der) -> izq.num &= der.num));
			operaciones.add(new Operador<>("!", (izq, der) -> izq.num ^= der.num));
			operaciones.add(new Operador<>("<", (izq, der) -> izq.num <<=der.num));
			operaciones.add(new Operador<>(">", (izq, der) -> izq.num >>=der.num));
		}
	}
	
	public static class Byte extends Operadores {
		private ArrayList<Operador<java.lang.Byte>> operaciones;
		
		public Byte() {
			operaciones.add(new Operador<>(":", (izq, der) -> izq.num =  der.num));
			operaciones.add(new Operador<>("+", (izq, der) -> izq.num = (byte) (izq.num + der.num)));
			operaciones.add(new Operador<>("-", (izq, der) -> izq.num = (byte) (izq.num - der.num)));
			operaciones.add(new Operador<>("*", (izq, der) -> izq.num = (byte) (izq.num * der.num)));
			operaciones.add(new Operador<>("/", (izq, der) -> izq.num = (byte) (izq.num / der.num)));
			operaciones.add(new Operador<>("\\",(izq, der) -> izq.num = (byte) (izq.num % der.num)));
			
			operaciones.add(new Operador<>("?", (izq, der) -> super.procesador.cmp.num = izq.num - der.num));
			
			operaciones.add(new Operador<>("|", (izq, der) -> izq.num = (byte) (izq.num | der.num)));
			operaciones.add(new Operador<>("&", (izq, der) -> izq.num = (byte) (izq.num & der.num)));
			operaciones.add(new Operador<>("!", (izq, der) -> izq.num = (byte) (izq.num ^ der.num)));
			operaciones.add(new Operador<>("<", (izq, der) -> izq.num = (byte) (izq.num <<der.num)));
			operaciones.add(new Operador<>(">", (izq, der) -> izq.num = (byte) (izq.num >>der.num)));
		}
	}
	
	public static class Float extends Operadores {
		private ArrayList<Operador<java.lang.Float>> operaciones;
		
		public Float() {
			operaciones.add(new Operador<>(":", (izq, der) -> izq.num =  der.num));
			operaciones.add(new Operador<>("+", (izq, der) -> izq.num += der.num));
			operaciones.add(new Operador<>("-", (izq, der) -> izq.num -= der.num));
			operaciones.add(new Operador<>("*", (izq, der) -> izq.num *= der.num));
			operaciones.add(new Operador<>("/", (izq, der) -> izq.num /= der.num));
			operaciones.add(new Operador<>("\\",(izq, der) -> izq.num %= der.num));
		}
	}
	
}
