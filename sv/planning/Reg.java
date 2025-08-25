package planning;

import procesador.elementos.Valor;

public class Reg {
	public Valor<Integer> valor;
	
	public Reg() {
		this.valor = new Valor<>(0);
	}
	
	public void copyValor(Valor<Integer> otroValor) { this.valor.num = otroValor.num; }
	
	@Override
	public String toString() { return valor.toString(); }
}
