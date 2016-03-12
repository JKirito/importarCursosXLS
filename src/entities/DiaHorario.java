package entities;


public class DiaHorario {
//	Integer id;
	String dia;
//	double horaInicio;
//	double horaFin;
	String horaInicio;
	String horaFin;
	
	public DiaHorario(String dia, String horaInicio, String horaFin) {
		super();
		this.dia = dia;
//		this.horaInicio = Double.valueOf(horaInicio.replace(":", "."));
//		this.horaFin = Double.valueOf(horaFin.replace(":", "."));
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

//	public double getHoraInicio() {
//		return horaInicio;
//	}
//
//	public void setHoraInicio(double horaInicio) {
//		this.horaInicio = horaInicio;
//	}
//
//	public double getHoraFin() {
//		return horaFin;
//	}
//
//	public void setHoraFin(double horaFin) {
//		this.horaFin = horaFin;
//	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	
	@Override
	public String toString() {
		String horaInicio = this.horaInicio + "";
		horaInicio = horaInicio.replace(".", ":");
		//si no está confirmado el horario...
		if(!horaInicio.contains(":") || !horaFin.contains(":"))
			return this.dia + ";" + horaInicio + ";" + horaFin;
		
		// La hora sean 2 dígitos
		horaInicio = horaInicio.split(":")[0].length() != 2 ? "0" + horaInicio
				: horaInicio;
		// Los minutos sean 2 dígitos
		horaInicio = horaInicio.split(":")[1].length() != 2 ? horaInicio + "0"
				: horaInicio;
		String horaFin = this.horaFin + "";
		horaFin = horaFin.replace(".", ":");
		// La hora sean 2 dígitos
		horaFin = horaFin.split(":")[0].length() != 2 ? "0" + horaFin : horaFin;
		// Los minutos sean 2 dígitos
		horaFin = horaFin.split(":")[1] != null ? (horaFin.split(":")[1]
				.length() != 2 ? horaFin + "0" : horaFin) : horaFin + "00";
		
		return this.dia + ";" + horaInicio + ";" + horaFin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result + ((horaFin == null) ? 0 : horaFin.hashCode());
		result = prime * result + ((horaInicio == null) ? 0 : horaInicio.hashCode());
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
		DiaHorario other = (DiaHorario) obj;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (horaFin == null) {
			if (other.horaFin != null)
				return false;
		} else if (!horaFin.equals(other.horaFin))
			return false;
		if (horaInicio == null) {
			if (other.horaInicio != null)
				return false;
		} else if (!horaInicio.equals(other.horaInicio))
			return false;
		return true;
	}
	
}
