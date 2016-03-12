package entities;

import java.util.List;

public class Curso {
	private Integer id;
	private String nombre;
	private String comision;
	private List<DiaHorarioAula> horarioAula;
	private List<Profesor> profesores;
	private Materia materia;
	private String semestre;
	private Integer año;

	public Curso(String comision, List<DiaHorarioAula> horariosAula,
			List<Profesor> profesores, Materia materia, String semestre, Integer año) {
		super();
		this.comision = comision;
		this.horarioAula = horariosAula;
		this.profesores = profesores;
		this.materia = materia;
		this.semestre = semestre;
		this.año = año;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public List<DiaHorarioAula> getHorariosAula() {
		return horarioAula;
	}

	public void setHorariosAula(List<DiaHorarioAula> horario) {
		this.horarioAula = horario;
	}

	public List<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public Integer getAño() {
		return año;
	}

	public void setAño(Integer año) {
		this.año = año;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((año == null) ? 0 : año.hashCode());
		result = prime * result
				+ ((comision == null) ? 0 : comision.hashCode());
		result = prime * result
				+ ((horarioAula == null) ? 0 : horarioAula.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((materia == null) ? 0 : materia.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((profesores == null) ? 0 : profesores.hashCode());
		result = prime * result
				+ ((semestre == null) ? 0 : semestre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (año == null) {
			if (other.año != null)
				return false;
		} else if (!año.equals(other.año))
			return false;
		if (comision == null) {
			if (other.comision != null)
				return false;
		} else if (!comision.equals(other.comision))
			return false;
		if (horarioAula == null) {
			if (other.horarioAula != null)
				return false;
		} else if (!horarioAula.equals(other.horarioAula))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (materia == null) {
			if (other.materia != null)
				return false;
		} else if (!materia.equals(other.materia))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (profesores == null) {
			if (other.profesores != null)
				return false;
		} else if (!profesores.equals(other.profesores))
			return false;
		if (semestre == null) {
			if (other.semestre != null)
				return false;
		} else if (!semestre.equals(other.semestre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", comision="
				+ comision + ", horarioAula=" + horarioAula + ", profesores="
				+ profesores + ", materia=" + materia + ", semestre="
				+ semestre + ", año=" + año + "]";
	}
	
}
