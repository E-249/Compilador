package planning;

import java.util.function.BiConsumer;

public class Op {
	
	private Arg izquierda;
	private BiConsumer<Arg, Arg> operacion;
	private Arg derecha;
	
	public Op(Arg izquierda, Arg derecha) {
		this.izquierda = izquierda;
		this.derecha = derecha;
	}
	
	public void operar() {
		operacion.accept(izquierda, derecha);
	}

}
