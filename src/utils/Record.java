package utils;

import java.io.Serializable;

public class Record implements Serializable{


	protected String NombreUsuario;
	protected int puntuacion;
	
	
	
	public Record() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public Record(String nombreUsuario, int puntuacion) {
		super();
		NombreUsuario = nombreUsuario;
		this.puntuacion = puntuacion;
	}

	public String getNombreUsuario() {
		return NombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		NombreUsuario = nombreUsuario;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	
	public String toString() {
		return "Record [NombreUsuario=" + NombreUsuario + ", puntuacion="
				+ puntuacion + "]";
	}
	
	

	
	
	
	

}
