package app;

import java.util.HashMap;
import java.util.Map;

import persistencia.Conexion;

public class VaciarTablas {
	
	private static final String PHP_vaciarCursos_horarios_aulas_y_cursos_profesores = "vaciarCursos_horarios_aulas_y_cursos_profesores.php";
	private static final String PASS = "simiungsRules";
	private static Map<String, String> datos;
	
	
	public VaciarTablas()
	{
		datos = new HashMap<String, String>();
		datos.put("pass", PASS);
	}

	public void vaciarCursos_horarios_aulas_y_cursos_profesores() {
		Conexion.enviarPost(datos, PHP_vaciarCursos_horarios_aulas_y_cursos_profesores);
	}

}
