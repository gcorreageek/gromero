package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class TipoDocumental implements Serializable{
 
	private static final long serialVersionUID = 9040252885866058785L;

	private Integer idTipoDocumental;
	private Integer idArea;
	private String tipo;
	
	public Integer getIdTipoDocumental() {
		return idTipoDocumental;
	}
	public void setIdTipoDocumental(Integer idTipoDocumental) {
		this.idTipoDocumental = idTipoDocumental;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
	
	
}
