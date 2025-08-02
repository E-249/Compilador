package ensamblador;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Launcher {
	
	public static void make(String archive)
			throws FileNotFoundException, IOException {
		
		var a = new Archivos();
		String leer = a.leerArchivo(archive + ".cos");
		
		var pp = new PreProcesador();
		String reemplazado = pp.reemplazar(leer);
		a.escribirArchivo(archive + ".ccos", reemplazado);
	}
	
	public static void makePrint(String archive)
			throws FileNotFoundException, IOException {
		
		var a = new Archivos();
		String leer = a.leerArchivo(archive + ".cos");
		
		var pp = new PreProcesador();
		String reemplazado = pp.reemplazar(leer);
		System.out.println(reemplazado);
	}
	
	public static void run(String archive, int stackSize, PrintOption printOption)
			throws FileNotFoundException, IOException {
		
		var a = new Archivos();
		String leer = a.leerArchivo(archive + ".ccos");
		
		var p = new Procesador(leer, stackSize);
		switch (printOption) {
		
		case NONE -> p.run();
		case END -> p.runPrintEnd();
		case ALWAYS -> p.runPrint();
		
		}
	}
	
	public static void makeRun(String archive, int stackSize, PrintOption printOption)
			throws FileNotFoundException, IOException {
		
		var a = new Archivos();
		String leer = a.leerArchivo(archive + ".cos");
		
		var pp = new PreProcesador();
		String reemplazado = pp.reemplazar(leer);
		
		var p = new Procesador(reemplazado, stackSize);
		switch (printOption) {
		
		case NONE -> p.run();
		case END -> p.runPrintEnd();
		case ALWAYS -> p.runPrint();
		
		}
	}
	
	public static void makeSaveRun(String archive, int stackSize, PrintOption printOption)
			throws FileNotFoundException, IOException {
		
		var a = new Archivos();
		String leer = a.leerArchivo(archive + ".cos");
		
		var pp = new PreProcesador();
		String reemplazado = pp.reemplazar(leer);
		a.escribirArchivo(archive + ".ccos", reemplazado);
		
		var p = new Procesador(reemplazado, stackSize);
		switch (printOption) {
		
		case NONE -> p.run();
		case END -> p.runPrintEnd();
		case ALWAYS -> p.runPrint();
		
		}
	}
	
	private static enum MakeRunOption { MAKE, RUN, MAKE_PRINT, MAKE_RUN, MAKE_CREATE_RUN }
	private static enum PrintOption { NONE, END, ALWAYS }
	
	// ./cos "test/Entity"
	// ./cos "test/Entity" -mr -s 16 -p true
	public static void main(String[] args) {
		final int EXITO = 0;
		final int SIN_RUTA = 1;
		final int STACK_SIN_TAMANNO = 2;
		final int OPCION_DESCONOCIDA = 3;
		final int ARCHIVO_NO_ENCONTRADO = 4;
		final int ERROR_EN_ARCHIVO = 5;
		final int INDICE_PILA_SUPERIOR = 6;
		final int INDICE_PILA_NO_POSITIVO = 7;
		args = new String[1];
		args[0] = "C:\\Users\\User\\Documents\\_\\PROYECTOS\\Java\\Compilador (cos)\\BugFixTester";
		
		if (args.length < 1) {
			System.err.println("No se ha incluido la ruta. Usa el flag \"-h\" para más información."); System.exit(SIN_RUTA); }
		
		if (args[0].equals("-h")) {
			System.out.print("""
					Por defecto: "java -jar cos.jar [archivo] -s 16 -mr -ep
					
					archivo: Nombre del archivo ignorando la extensión
					
					-m (make): ensambla archivo ".cos", creando ".ccos"
					-mp (make print): ensambla archivo ".cos", imprimiendo ".ccos"
					-r (run): corre archivo ".ccos"
					-mr (make run): ensambla y corre archivo ".cos", sin crear ".ccos"
					-mcr (make create run): ensambla y corre archivo ".cos", creando ".ccos"
					
					-s [tamaño] (size): tamaño de la pila. Cada unidad equivale al tamaño de un entero (int)
					
					-np (no print): no imprime datos
					-ep (end print): imprime datos tras terminar la ejecución
					-ap (always print): imprime datos por cada instrucción ejecutada
					""");
			System.exit(EXITO);
		}

		String archive = args[0];
		int stackSize = 16;
		var makeRunOption = MakeRunOption.MAKE_RUN;
		var printOption = PrintOption.END;
		
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-s")) {
				i++;
				if (i == args.length) {
					System.err.println("No hay valores para -s [tamaño]."); System.exit(STACK_SIN_TAMANNO); }
				stackSize = Integer.parseInt(args[i]);
			}
			
			else if (args[i].equals("-np"))
				printOption = PrintOption.NONE;
			else if (args[i].equals("-ep"))
				printOption = PrintOption.END;
			else if (args[i].equals("-ap"))
				printOption = PrintOption.ALWAYS;
			
			else if (args[i].equals("-m"))
				makeRunOption = MakeRunOption.MAKE;
			else if (args[i].equals("-mp"))
				makeRunOption = MakeRunOption.MAKE_PRINT;
			else if (args[i].equals("-r"))
				makeRunOption = MakeRunOption.RUN;
			else if (args[i].equals("-mr"))
				makeRunOption = MakeRunOption.MAKE_RUN;
			else if (args[i].equals("-mcr"))
				makeRunOption = MakeRunOption.MAKE_CREATE_RUN;
			else {
				System.err.println("Opciones desconocidas"); System.exit(OPCION_DESCONOCIDA); }
		}
		
		try {
		
			switch (makeRunOption) {
			
			case MAKE -> make(archive);
			case MAKE_PRINT -> makePrint(archive);
			case RUN -> run(archive, stackSize, printOption);
			case MAKE_RUN -> makeRun(archive, stackSize, printOption);
			case MAKE_CREATE_RUN -> makeSaveRun(archive, stackSize, printOption);
			
			}
		
		} catch (FileNotFoundException e) {
			System.err.println("Archivo no encontrado:\n" + archive); System.exit(ARCHIVO_NO_ENCONTRADO);
		} catch (IOException e) {
			System.err.println("Error al leer o crear el archivo para:\n" + archive); System.exit(ERROR_EN_ARCHIVO);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Indíce superior al rango de la pila."); System.exit(INDICE_PILA_NO_POSITIVO);
		} catch (NegativeArraySizeException e) {
			System.err.println("Indíce no positivo para la pila."); System.exit(INDICE_PILA_SUPERIOR);
		}
	}
	
}
