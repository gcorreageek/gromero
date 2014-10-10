package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.Collection;

import javax.faces.model.SelectItem;

import com.ransa.portal.model.RolUsuario;

public class FilaAplicacionUsuarioBean {
	
	private String nombresGrupos;
	private String idAplicacion;
	private String nombreNegocio;
	private String nombreAplicacion;
	private RolUsuario  rolUsuario;
	private Collection<SelectItem> rolesAplicacion;
	
	public String getNombresGrupos() {
		return nombresGrupos;
	}
	
	public void setNombresGrupos(String nombresGrupos) {
		this.nombresGrupos = nombresGrupos;
	}
	
	public String getIdAplicacion() {
		return idAplicacion;
	}
	
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	
	public String getNombreNegocio() {
		return nombreNegocio;
	}
	
	public void setNombreNegocio(String nombreNegocio) {
		this.nombreNegocio = nombreNegocio;
	}
	
	public String getNombreAplicacion() {
		return nombreAplicacion;
	}
	
	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}
	
	public RolUsuario getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(RolUsuario rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	public Collection<SelectItem> getRolesAplicacion() {
		return rolesAplicacion;
	}
	
	public void setRolesAplicacion(Collection<SelectItem> rolesAplicacion) {
		this.rolesAplicacion = rolesAplicacion;
	}

}
