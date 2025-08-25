package procesador;

public interface Instruccion<N extends Number> extends Nombrable {
	
	public void run();
	
}
