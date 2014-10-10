package com.ransa.portal.portlet.administracion.usuario.util;

public class FilaUsuarioBean {
	
	private String idUsuario;
	private String nombresUsuario;
	private String apellidosUsuario;
	private String correoElectronicoUsuario;
	private String mensajeError;
	
	public FilaUsuarioBean() {
		idUsuario = "";
		nombresUsuario = "";
		apellidosUsuario = "";
		correoElectronicoUsuario = "";		
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombresUsuario() {
		return nombresUsuario;
	}

	public void setNombresUsuario(String nombresUsuario) {
		this.nombresUsuario = nombresUsuario;
	}

	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	public String getCorreoElectronicoUsuario() {
		return correoElectronicoUsuario;
	}

	public void setCorreoElectronicoUsuario(String correoElectronicoUsuario) {
		this.correoElectronicoUsuario = correoElectronicoUsuario;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}	
	
}