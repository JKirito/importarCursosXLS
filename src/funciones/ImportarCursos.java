package funciones;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;

import persistencia.Conexion;
import entities.Aula;
import entities.Curso;
import entities.DiaHorario;
import entities.DiaHorarioAula;
import entities.Materia;
import entities.Profesor;

public class ImportarCursos {
	
	private Map<String, Curso> cursos = new HashMap<String, Curso>();
	
	// Conjuntos a los que hay que dar de alta si no existen. Todo esto está incluido ademas en "this.cursos", pero con repetidos ahi
	// Acá se supone que no hay repetidos, asi que son menos consultas a la base.
	private Set<Aula> aulas = new HashSet<Aula>();
	private Set<Materia> materias = new HashSet<Materia>();
	private Set<Profesor> docentes = new HashSet<Profesor>();
	private Set<DiaHorario> diaHorario = new HashSet<DiaHorario>();
	private Set<DiaHorarioAula> diaHorarioAula = new HashSet<DiaHorarioAula>();

	private String semestre;
	private int año;
	
	private static final String PHP_ALTA_AULA = "altaAula.php";
	private static final String PHP_ALTA_MATERIA = "altaMateria.php";
	private static final String PHP_ALTA_DOCENTE = "altaDocente.php";
	private static final String PHP_ALTA_DIAHORARIO = "altaDiaHorario.php";
	private static final String PHP_ALTA_CURSO = "altaCurso.php";
	private static final String PHP_ALTA_CURSO_DOCENTE = "altaCurso_Docente.php";
	private static final String PHP_ALTA_CURSO_HORARIO_AULA = "altaCurso_Horario_Aula.php";
	
	// UNIQUE
	// alter table cursos add constraint uc_cur unique(comision, id_materias, semestre, año);

	public ImportarCursos(String semestre, int año) {
		this.semestre = semestre;
		this.año = año;
	}

	public void importarCursos(File file) throws Exception {
		if (file == null)
			throw new Exception("File inexistente!!!");

		String fileName = file.getName();
		String extension = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
		boolean is2007plus = extension.equalsIgnoreCase(".XLSX") ? true : false;

		Iterator<Row> rowIterator = ExcelPOI.getFilasDelArchivo(new FileInputStream(file), is2007plus);

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext())
		{
			Row row = rowIterator.next();

			// Le sumo 1; empieza en 0
			Integer filaNro = row.getRowNum() + 1;

			// Salteo el header
			if (filaNro == 1)
			{
				continue;
			}
			
			String keyComision = ExcelPOI.valorCeldaToString(row.getCell(Cursos_CamposExcel.COMISION.pos()));
			
			//HORARIOS_AULA (SIEMPRE SE AGREGA)
			String horaInicio = ExcelPOI.valorCeldaToHora(row.getCell(Cursos_CamposExcel.HORAINICIO.pos()));
			String horaFin = ExcelPOI.valorCeldaToHora(row.getCell(Cursos_CamposExcel.HORAFIN.pos()));
			String dia = ExcelPOI.valorCeldaToString(row.getCell(Cursos_CamposExcel.DIA.pos()));
			String aulaString = ExcelPOI.valorCeldaToString(row.getCell(Cursos_CamposExcel.AULA.pos())).replace("AULA ", "");
			Aula aula = new Aula(aulaString);
			// Agrego aula
			aulas.add(aula);
			DiaHorario diaHorario = new DiaHorario(dia, horaInicio, horaFin);
			// agrego diaHorario
			this.diaHorario.add(diaHorario);
			DiaHorarioAula diaHorarioEnAula = new DiaHorarioAula(diaHorario, aula);
			//agrego diahorarioaula
			this.diaHorarioAula.add(diaHorarioEnAula);
			
			//Si ya agregué un curso con ese código-comision, entonces sólo falta agregar el diaHoraAula diferente
			if(cursos.containsKey(keyComision))
			{
				cursos.get(keyComision).getHorariosAula().add(diaHorarioEnAula);
			}
			else
			{
				// COMISION
				//String comision = keyComision.split(" ")[1];
				
				// MATERIA
				Materia materia = new Materia(row.getCell(Cursos_CamposExcel.MATERIA.pos()).getStringCellValue());
				this.materias.add(materia);
				
				// PROFESORES
				String[] nombresProfesores = new String[0];
				try {
					String tmp = ExcelPOI.valorCeldaToString(row.getCell(Cursos_CamposExcel.DOCENTES.pos()));
					nombresProfesores = tmp != null ? tmp.split("-") : nombresProfesores;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				List<Profesor> profesores = new ArrayList<Profesor>();
				Profesor profesor;
				for(String p : nombresProfesores)
				{
					profesor = new Profesor(p.trim());
					profesores.add(profesor);
					this.docentes.add(profesor);
				}
				
				//HORARIOS_AULA
				List<DiaHorarioAula> horariosAula = new ArrayList<DiaHorarioAula>();
				horariosAula.add(diaHorarioEnAula);
				Curso curso = new Curso(keyComision, horariosAula, profesores, materia, this.semestre, this.año);
				this.cursos.put(keyComision, curso);
				
				//TODO: insertar solo si no existe!!!
				
				
				//insertar un aula si no existe el numero
				/*
				INSERT INTO aulas (numero)
				SELECT * FROM (select '$numero') AS tmp
				WHERE NOT EXISTS (
				    SELECT id FROM aulas WHERE numero = '$numero'
				);
				*/

				/*
				INSERT INTO cursos (comision, id_materias, semestre, año)
		   SELECT * FROM (select '$comision', (select id from materias where alias = '$nombreMateria'), '$semestre', '$anio') AS tmp
		   WHERE NOT EXISTS (
	SELECT id FROM cursos WHERE comision = '$comision' AND id_materias in (select id from materias where alias = '$nombreMateria') AND semestre = '$semestre' AND año = '$anio') limit 1;
				 */
			}

			
			
			//Insertar curso en base (si es que no existe) y setear id curso
			
			//Insertar id curso, ids de horarios y ids aula en cursos_horarios_aula
			//insert into cursos_horarios_aulas values(2,4, (select id from aulas where numero='1000'));
			
			//Insertar id curso y ids de profesores en cursos_profesores
		}
		
		// ALTAS!!!
		
		// Alta Aulas
		//altaAulas(this.aulas);
		
		//Alta Horarios
		//altaDiasHorarios(this.diaHorario);
		
		//Alta Materias
		//altaMaterias(this.materias);
		
		//Alta Docentes
		//altaDocentes(this.docentes);
		
		//Alta cursos
		//altaCursos(new HashSet<Curso>(this.cursos.values()));
		
		//Alta cursos_docentes
		//altaCursos_Docentes(new HashSet<Curso>(this.cursos.values()));
		
		//Alta cursos_horarios_aulas
		altaCurso_Horario_Aula(new HashSet<Curso>(this.cursos.values()));
	}
	
	private void altaAulas(Set<Aula> aulas)
	{
		StringBuilder data = new StringBuilder();
		final String SEPARADOR = ",";
		System.out.println("TOTAL AULAS: " + aulas.size());
		for(Aula aula : aulas)
		{
			data.append(aula.getNumero()+SEPARADOR);
		}
		Map<String, String> datos = new HashMap<String, String>();
		datos.put("aula", data.toString());
		Conexion.enviarPost(datos, PHP_ALTA_AULA);
	}
	
	private void altaMaterias(Set<Materia> materias)
	{
		StringBuilder data = new StringBuilder();
		final String SEPARADOR = ";";
		System.out.println("TOTAL MATERIAS: " + materias.size());
		for (Materia materia : materias)
		{
			data.append(materia.getNombre()+SEPARADOR);
		}
		Map<String, String> datos = new HashMap<String, String>();
		datos.put("nombre", data.toString());
		Conexion.enviarPost(datos, PHP_ALTA_MATERIA);
	}
	
	private void altaDocentes(Set<Profesor> profesores)
	{
		StringBuilder data = new StringBuilder();
		final String SEPARADOR = ";";
		System.out.println("TOTAL DOCENTES: " + profesores.size());
		for (Profesor profe : profesores)
		{
			data.append(profe.getNombre()+SEPARADOR);
		}
		Map<String, String> datos = new HashMap<String, String>();
		datos.put("nombre", data.toString());
		Conexion.enviarPost(datos, PHP_ALTA_DOCENTE);
	}
	
	private void altaDiasHorarios(Set<DiaHorario> horarios)
	{
		StringBuilder data = new StringBuilder();
		final String SEPARADOR = ",";
		System.out.println("TOTAL DIAHORARIOS: " + horarios.size());
		for (DiaHorario diaHorario : horarios)
		{
			data.append(diaHorario.toString()+SEPARADOR);
		}
		Map<String, String> datos = new HashMap<String, String>();
		datos.put("diaHora", data.toString());
		Conexion.enviarPost(datos, PHP_ALTA_DIAHORARIO);
	}
	
	private void altaCursos(Set<Curso> cursos)
	{
		StringBuilder data = new StringBuilder();
		System.out.println("TOTAL CURSOS: " + cursos.size());
		int i = 0;
		final String SEPARADOR = "//";
		for (Curso curso : cursos)
		{
			i++;
			String com = curso.getComision();
			String nombreMat = curso.getMateria().getNombre();
			String semestre = curso.getSemestre();
			Integer año = curso.getAño();
			data.append(com + ";" + nombreMat + ";" + semestre + ";" + año
					+ SEPARADOR);
		}
		Map<String, String> datos = new HashMap<String, String>();
		datos.put("curso", data.toString());
		Conexion.enviarPost(datos, PHP_ALTA_CURSO);
	}
	
	private void altaCursos_Docentes(Set<Curso> cursos)
	{
		StringBuilder data = new StringBuilder();
		int i = 0;
		final String SEPARADOR = "//";
		for (Curso curso : cursos)
		{
			for(Profesor prof : curso.getProfesores())
			{
			i++;
			String com = curso.getComision();
			String nombreMat = curso.getMateria().getNombre();
			String semestre = curso.getSemestre();
			Integer año = curso.getAño();
			String nombreProf = prof.getNombre();
			data.append(com + ";" + nombreMat + ";" + semestre + ";" + año + ";" + nombreProf
					+ SEPARADOR);
			}
		}
		System.out.println("TOTAL CURSOS_DOCENTES: " + i);
		Map<String, String> datos = new HashMap<String, String>();
		datos.put("curso_docente", data.toString());
		Conexion.enviarPost(datos, PHP_ALTA_CURSO_DOCENTE);
	}
	
	private void altaCurso_Horario_Aula(Set<Curso> cursos)
	{
		StringBuilder data = new StringBuilder();
		int i = 0;
		final String SEPARADOR = "//";
		Set<String> a = new HashSet<String>();
		for (Curso curso : cursos)
		{
			for(DiaHorarioAula HA : curso.getHorariosAula())
			{
			i++;
			String com = curso.getComision();
			String nombreMat = curso.getMateria().getNombre();
			String semestre = curso.getSemestre();
			Integer año = curso.getAño();
			String horarioAula = HA.toString();
			data.append(com + ";" + nombreMat + ";" + semestre + ";" + año + ";" + horarioAula
					+ SEPARADOR);
			if(a.contains(com + ";" + nombreMat + ";" + semestre + ";" + año + ";" + horarioAula
					+ SEPARADOR)){
				System.out.println(com + ";" + nombreMat + ";" + semestre + ";" + año + ";" + horarioAula
					+ SEPARADOR);
			}else{
				a.add(com + ";" + nombreMat + ";" + semestre + ";" + año + ";" + horarioAula
						+ SEPARADOR);				
			}
			}
		}
		System.out.println("TOTAL CURSOS_HORARIOS_AULAS: " + i);
		System.out.println(a.size() == i ? "OK" : a.size() +": s diferente, hay algo mal :(");
		Map<String, String> datos = new HashMap<String, String>();
		datos.put("curso_horario_aula", data.toString());
//		Conexion.enviarPost(datos, PHP_ALTA_CURSO_HORARIO_AULA);
	}

}