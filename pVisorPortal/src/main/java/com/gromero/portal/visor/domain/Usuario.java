package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class Usuario implements Serializable{
 
	private static final long serialVersionUID = 4863066567472686589L;

	private Integer idUsuario;
	private String nombres;
	private String userName;
	private String userPass; 
	private Integer rolD;
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public Integer getRolD() {
		return rolD;
	}
	public void setRolD(Integer rolD) {
		this.rolD = rolD;
	}

	
	
	
}
