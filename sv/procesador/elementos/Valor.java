package procesador.elementos;

public class Valor<N extends Number> {
	public N num;
	
	public Valor(N num) {
		this.num = num;
	}
	
	public Valor(Valor<N> otro) {
		this.num = otro.num;
	}
	
	@Override
	public String toString() { return num.toString(); }
}
