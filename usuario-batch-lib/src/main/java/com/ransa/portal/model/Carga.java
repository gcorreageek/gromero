package com.ransa.portal.model;

import java.util.Date;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

public class Carga extends MetaData {	
	
	private static final long serialVersionUID = 5935297319661422017L;
	
	private int idCarga;
	private Date fechaCreacion;
	private int numeroUsuariosCargadosCorrectamente;
	private int numeroUsuariosCargadosIncorrectamente;
	
	public int getIdCarga() {
		return idCarga;
	}
	
	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
		setValue("" + idCarga);
		setLabel(this.toString());
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public int getNumeroUsuariosCargadosCorrectamente() {
		return numeroUsuariosCargadosCorrectamente;
	}
	
	public void setNumeroUsuariosCargadosCorrectamente(
			int numeroUsuariosCargadosCorrectamente) {
		this.numeroUsuariosCargadosCorrectamente = numeroUsuariosCargadosCorrectamente;
	}
	
	public int getNumeroUsuariosCargadosIncorrectamente() {
		return numeroUsuariosCargadosIncorrectamente;
	}
	
	public void setNumeroUsuariosCargadosIncorrectamente(
			int numeroUsuariosCargadosIncorrectamente) {
		this.numeroUsuariosCargadosIncorrectamente = numeroUsuariosCargadosIncorrectamente;
	}

	@Override
	public String toString() {		
		return getValue() + " - " + Util.formatFecha(fechaCreacion) + " (" + numeroUsuariosCargadosCorrectamente + " correctos, "
			+ numeroUsuariosCargadosIncorrectamente + " incorrectos)";
	}	
	
}