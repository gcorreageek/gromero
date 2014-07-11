package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class Documento implements Serializable{
 
	private static final long serialVersionUID = 425857682444896992L;

	private Integer idDocumento;
	private Integer idTipoDocumental;
	private String ruta;
	private String nombre;
	
	public Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Integer getIdTipoDocumental() {
		return idTipoDocumental;
	}
	public void setIdTipoDocumental(Integer idTipoDocumental) {
		this.idTipoDocumental = idTipoDocumental;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
	
	
}
