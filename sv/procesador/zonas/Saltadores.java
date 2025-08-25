package procesador.zonas;

import java.util.ArrayList;

import procesador.Procesador;
import procesador.elementos.Saltador;
import procesador.elementos.Valor;

public class Saltadores {

	private Procesador procesador;

	public static class Integer extends Saltadores {
		private ArrayList<Saltador<java.lang.Integer>> saltos;
		
		private void saltarIf(boolean b, Valor<java.lang.Integer> dest) { if (b) super.procesador.pc.num = dest.num; }
		
		public Integer() {
			final Valor<java.lang.Integer> cmp = super.procesador.cmp;
			
			saltos.add(new Saltador<>("!",  dest -> saltarIf(true,         dest)));
			saltos.add(new Saltador<>("<",  dest -> saltarIf(cmp.num <  0, dest)));
			saltos.add(new Saltador<>(">",  dest -> saltarIf(cmp.num >  0, dest)));
			saltos.add(new Saltador<>("=",  dest -> saltarIf(cmp.num == 0, dest)));
			
			saltos.add(new Saltador<>("<=", dest -> saltarIf(cmp.num <= 0, dest)));
			saltos.add(new Saltador<>("=>", dest -> saltarIf(cmp.num >= 0, dest)));
			saltos.add(new Saltador<>("<>", dest -> saltarIf(cmp.num != 0, dest)));

			saltos.add(new Saltador<>("=<", dest -> saltarIf(cmp.num <= 0, dest)));
			saltos.add(new Saltador<>(">=", dest -> saltarIf(cmp.num >= 0, dest)));
			saltos.add(new Saltador<>("><", dest -> saltarIf(cmp.num != 0, dest)));
		}
	}
	
	public static class Byte extends Saltadores {
		private ArrayList<Saltador<java.lang.Byte>> saltos;
		
		private void saltarIf(boolean b, Valor<java.lang.Byte> pc) { if (b) super.procesador.pc.num += pc.num; }
		
		public Byte() {
			final Valor<java.lang.Integer> cmp = super.procesador.cmp;
			
			saltos.add(new Saltador<>("!",  dest -> saltarIf(true,         dest)));
			saltos.add(new Saltador<>("<",  dest -> saltarIf(cmp.num <  0, dest)));
			saltos.add(new Saltador<>(">",  dest -> saltarIf(cmp.num >  0, dest)));
			saltos.add(new Saltador<>("=",  dest -> saltarIf(cmp.num == 0, dest)));
			
			saltos.add(new Saltador<>("<=", dest -> saltarIf(cmp.num <= 0, dest)));
			saltos.add(new Saltador<>("=>", dest -> saltarIf(cmp.num >= 0, dest)));
			saltos.add(new Saltador<>("<>", dest -> saltarIf(cmp.num != 0, dest)));

			saltos.add(new Saltador<>("=<", dest -> saltarIf(cmp.num <= 0, dest)));
			saltos.add(new Saltador<>(">=", dest -> saltarIf(cmp.num >= 0, dest)));
			saltos.add(new Saltador<>("><", dest -> saltarIf(cmp.num != 0, dest)));
		}
	}
	
}
