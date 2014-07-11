package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class EmpresaArea implements Serializable {
 
	private static final long serialVersionUID = -7284836004741246235L;
	
	private Integer idEmpresaArea;
	private Integer idEmpresa;
	private Integer idArea;
	
	private Empresa empresa;
	private Area area;
	
	public Integer getIdEmpresaArea() {
		return idEmpresaArea;
	}
	public void setIdEmpresaArea(Integer idEmpresaArea) {
		this.idEmpresaArea = idEmpresaArea;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Integer getIdArea() {
		return idArea;
	}
	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	
	
	
	

}
