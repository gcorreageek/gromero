package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.Collection;

import javax.faces.model.SelectItem;

import com.ransa.portal.model.RecursoUsuario;

public class FilaTipoRecursoUsuarioBean {

	private String nombreTipoRecurso;
	private RecursoUsuario[] recursosDisponiblesSeleccionados;
	private Collection<SelectItem> recursosDisponibles;
	private RecursoUsuario[] recursoAsignadoFavorito;
	private Collection<SelectItem> recursosAsignados;
	private String idTipoRecurso;
	private String nombreRecursoDisponible;	
	
	public String getNombreTipoRecurso() {
		return nombreTipoRecurso;
	}
	
	public void setNombreTipoRecurso(String nombreTipoRecurso) {
		this.nombreTipoRecurso = nombreTipoRecurso;
	}
	
	public Collection<SelectItem> getRecursosDisponibles() {
		return recursosDisponibles;
	}
	
	public void setRecursosDisponibles(Collection<SelectItem> recursosDisponibles) {
		this.recursosDisponibles = recursosDisponibles;
	}
			
	public RecursoUsuario[] getRecursoAsignadoFavorito() {
		return recursoAsignadoFavorito;
	}

	public void setRecursoAsignadoFavorito(
			RecursoUsuario[] recursoAsignadoPredeterminado) {
		this.recursoAsignadoFavorito = recursoAsignadoPredeterminado;
	}

	public Collection<SelectItem> getRecursosAsignados() {
		return recursosAsignados;
	}
	
	public void setRecursosAsignados(Collection<SelectItem> recursosAsignados) {
		this.recursosAsignados = recursosAsignados;
	}

	public String getIdTipoRecurso() {
		return idTipoRecurso;
	}

	public void setIdTipoRecurso(String idTipoRecurso) {
		this.idTipoRecurso = idTipoRecurso;
	}

	public RecursoUsuario[] getRecursosDisponiblesSeleccionados() {
		return recursosDisponiblesSeleccionados;
	}

	public void setRecursosDisponiblesSeleccionados(
			RecursoUsuario[] recursosDisponiblesSeleccionados) {
		this.recursosDisponiblesSeleccionados = recursosDisponiblesSeleccionados;
	}

	public String getNombreRecursoDisponible() {
		return nombreRecursoDisponible;
	}

	public void setNombreRecursoDisponible(String nombreRecursoDisponible) {
		this.nombreRecursoDisponible = nombreRecursoDisponible;
	}

}
