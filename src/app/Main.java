package app;

import java.io.File;

import funciones.ImportarCursos;

public class Main {

	public static void main(String[] args) throws Exception {
		File file = new File("/home/javi/Escritorio/OFERTA-1-2016-ANUAL-2016-v12.xlsx");
		String semestre = "P"; // P = PRIMERO / S = SEGUNDO
		int año = 2016;
		ImportarCursos ic = new ImportarCursos(semestre, año);
		
		// si true, entonces guarda todo en la base. False para validar los datos, no grabo nada.
		boolean persistirAltas = true;

		//Antes de persistir una actualización de oferta académica...
		//vaciarCursos_horarios_aulas_y_cursos_profesores y luego persistir con los nuevos datos
		boolean actualizarOferta = false;
		
		// si vacío las tablas, no persistir las altas de las tablas
		if(actualizarOferta)
		{
			VaciarTablas vt = new VaciarTablas();
			vt.vaciarCursos_horarios_aulas_y_cursos_profesores();
			Thread.sleep(1000);
			System.out.println("empty tables Ok! :)");
		}
		
		ic.importarCursos(file, persistirAltas);
		System.out.println("FIN!!!!");
	}

}
