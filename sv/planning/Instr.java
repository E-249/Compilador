package planning;

public class Instr {
	private Runnable accion;
	private int linea;
	
	public Instr(int linea, Runnable accion) {
		this.linea = linea;
		this.accion = accion;
	}
	
	public int getLinea() { return linea; }
	
	public void run() { accion.run(); }
}
