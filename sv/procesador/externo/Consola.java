package procesador.externo;

import java.util.Scanner;

public class Consola<N extends Number> {
	private Scanner scanner;
	
	public void say(byte caracter) {
		System.out.print((char) caracter);
	}
	
	public int listen() {
		return scanner.nextInt();
	}
}