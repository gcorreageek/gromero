package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class Empresa implements Serializable{
 
	private static final long serialVersionUID = 1935658714194431795L;

	private Integer idEmpresa;
	private String empresa;
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	
	
	
}
