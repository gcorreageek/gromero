package com.ransa.portal.model;

import java.io.Serializable;

import com.ransa.portal.util.Util;

public class RecursoUsuario implements Serializable{
	
	private static final long serialVersionUID = -1671242465860552919L;
	
	private int idTipoRecurso;
	private String idRecurso;
	private int favorito;
	
	public RecursoUsuario() { }
		
	public int getIdTipoRecurso() {
		return idTipoRecurso;
	}
	
	public void setIdTipoRecurso(int idTipoRecurso) {
		this.idTipoRecurso = idTipoRecurso;
	}
	
	public String getIdRecurso() {
		return idRecurso;
	}
	
	public void setIdRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
	}
	
	public int getFavorito() {
		return favorito;
	}

	public void setFavorito(int favorito) {
		this.favorito = favorito;
	}

	@Override
	public String toString() {		
		return (idTipoRecurso + Util.SEPARADOR + idRecurso);
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
