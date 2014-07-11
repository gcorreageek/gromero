package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class Area implements Serializable{
 
	private static final long serialVersionUID = 3684283378414350631L;
	
	private Integer idArea;
	private String area; 
	private Integer tipoD;
	
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	} 
	public Integer getTipoD() {
		return tipoD;
	}
	public void setTipoD(Integer tipoD) {
		this.tipoD = tipoD;
	}
	

}
