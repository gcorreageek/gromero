package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.Collection;

import javax.faces.model.SelectItem;

public class FilaTipoRecursoPreferenciaBean {

	private int idTipoRecurso;
	private String nombreTipoRecurso;
	private String recursoFavorito;
	private Collection<SelectItem> recursosAsignados;

	public int getIdTipoRecurso() {
		return idTipoRecurso;
	}

	public void setIdTipoRecurso(int idTipoRecurso) {
		this.idTipoRecurso = idTipoRecurso;
	}

	public String getRecursoFavorito() {
		return recursoFavorito;
	}

	public void setRecursoFavorito(String recursoFavorito) {
		this.recursoFavorito = recursoFavorito;
	}

	public Collection<SelectItem> getRecursosAsignados() {
		return recursosAsignados;
	}

	public void setRecursosAsignados(Collection<SelectItem> recursosAsignados) {
		this.recursosAsignados = recursosAsignados;
	}

	public String getNombreTipoRecurso() {
		return nombreTipoRecurso;
	}

	public void setNombreTipoRecurso(String nombreTipoRecurso) {
		this.nombreTipoRecurso = nombreTipoRecurso;
	}
	
}
