package funciones;

// VALOR: POS COLUMNA AL IMPORTAR (CAMPOS QUE NO ESTAN EN ARCHIVO A IMPORTAR, SIGUE LA NUMERACIÓN)
// PROBLEMA!! SI CAMBIA UNA COLUMNA DE POSICION/NUMERACION, HAY QUE CAMBIAR TODAS LAS QUE SIGUEN!!! (ESTO ES SOLO PARA LA IMPORTACION)
// **HM : Creo que el nro puede estar duplicado si el campo NO se importa
// NOMBRE: NOMBRE A MOSTRAR PARA ESE CAMPO AL EXPORTAR

// Esto enum se usa en importacion (se usa el orden) y en exportacion (se usa la descripcion)

public enum Cursos_CamposExcel {
	COMISION(1, "COMISION"), MATERIA(2, "MATERIA"), DIA(4, "DIA"), HORAINICIO(5, "HORA DE INICIO"), HORAFIN(6, "HORA DE FIN"),
	DOCENTES(7, "DOCENTE"), AULA(8, "AULA");

	private final int value;
	private final String name;

	Cursos_CamposExcel(int value, String nombreAMostrar) {
		this.value = value;
		this.name = nombreAMostrar;
	}

	public int pos() {
		return this.value;
	}

	public String toString() {
		return this.name;
	}
}
