package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class UsuarioEmpresaArea implements Serializable{
 
	private static final long serialVersionUID = -3211877591390895286L;
	
	private Integer idUsuarioEmpresaArea;
	private Integer idUsuario;
	private Integer idEmpresa;
	private Integer idArea; 
	
	public Integer getIdUsuarioEmpresaArea() {
		return idUsuarioEmpresaArea;
	}
	public void setIdUsuarioEmpresaArea(Integer idUsuarioEmpresaArea) {
		this.idUsuarioEmpresaArea = idUsuarioEmpresaArea;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
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
	
	
	
	
	

}
