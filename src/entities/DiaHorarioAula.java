package entities;

public class DiaHorarioAula {
	DiaHorario diaHorario;
	Aula aula;
	
	public DiaHorarioAula(DiaHorario horario, Aula aula) {
		super();
		this.diaHorario = horario;
		this.aula = aula;
	}

	public DiaHorario getDiaHorario() {
		return diaHorario;
	}

	public void setDiaHorario(DiaHorario diaHorario) {
		this.diaHorario = diaHorario;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}
	
	

	@Override
	public String toString() {
		return this.diaHorario.toString() + ";" + aula.getNumero();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aula == null) ? 0 : aula.hashCode());
		result = prime * result
				+ ((diaHorario == null) ? 0 : diaHorario.hashCode());
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
		DiaHorarioAula other = (DiaHorarioAula) obj;
		if (aula == null) {
			if (other.aula != null)
				return false;
		} else if (!aula.equals(other.aula))
			return false;
		if (diaHorario == null) {
			if (other.diaHorario != null)
				return false;
		} else if (!diaHorario.equals(other.diaHorario))
			return false;
		return true;
	}

}
