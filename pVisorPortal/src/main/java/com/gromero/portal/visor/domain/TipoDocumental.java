package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class TipoDocumental implements Serializable{
 
	private static final long serialVersionUID = 9040252885866058785L;

	private Integer idTipoDocumental;
	private Integer idEmpresaArea;
	private String tipo;
	
	private EmpresaArea empresaArea;

	public Integer getIdTipoDocumental() {
		return idTipoDocumental;
	}

	public void setIdTipoDocumental(Integer idTipoDocumental) {
		this.idTipoDocumental = idTipoDocumental;
	}

	public Integer getIdEmpresaArea() {
		return idEmpresaArea;
	}

	public void setIdEmpresaArea(Integer idEmpresaArea) {
		this.idEmpresaArea = idEmpresaArea;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public EmpresaArea getEmpresaArea() {
		return empresaArea;
	}

	public void setEmpresaArea(EmpresaArea empresaArea) {
		this.empresaArea = empresaArea;
	}
	
	
	
	
 
	
	
	
}
