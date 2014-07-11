package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class UsuarioEmpresaArea implements Serializable{
 
	private static final long serialVersionUID = -3211877591390895286L;
	
	private Integer idUsuarioEmpresaArea;
	private Integer idUsuario;
	private Integer idEmpresaArea; 
	
	private Usuario usuario;
	private EmpresaArea empresaArea;
	
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
	public Integer getIdEmpresaArea() {
		return idEmpresaArea;
	}
	public void setIdEmpresaArea(Integer idEmpresaArea) {
		this.idEmpresaArea = idEmpresaArea;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public EmpresaArea getEmpresaArea() {
		return empresaArea;
	}
	public void setEmpresaArea(EmpresaArea empresaArea) {
		this.empresaArea = empresaArea;
	}
	
	
 
	

}
