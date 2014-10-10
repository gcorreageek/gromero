package com.ransa.portal.model;

import java.io.Serializable;

import com.ransa.portal.util.Util;

/**
 * Clase que representa la relacion entre el usuario y sus roles en las
 * aplicaciones que tiene asignado.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class RolUsuario implements Serializable{
	
	private static final long serialVersionUID = -4792244433696750492L;
	
	private int idRol;
	private String idAplicacion;
	private String nombreRol;
	
	public RolUsuario() { }

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
		
	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	@Override
	public String toString() {
		return (idAplicacion + Util.SEPARADOR + idRol);
	}
	
	@Override
	public boolean equals(Object object) {
		String tempValue = this.toString();
		if (tempValue.equals(object.toString())) {
			return true;
		} else {
			return false;
		}
	}
		
}
