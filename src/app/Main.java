package app;

import java.io.File;

import funciones.ImportarCursos;

public class Main {

	public static void main(String[] args) throws Exception {
		File file = new File("/home/javi/Escritorio/OFERTA-ACADEMICA-1-2015-CON-AULAS-v4.xlsx");
		String semestre = "P"; // P = PRIMERO / S = SEGUNDO
		int año = 2015;
		ImportarCursos ic = new ImportarCursos(semestre, año);
		ic.importarCursos(file);
		System.out.println("FIN!!!!");
	}

}
