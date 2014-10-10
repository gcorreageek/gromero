package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.Collection;

import com.ransa.portal.model.TipoRecurso;

public class TipoRecursoLoader {
	
	private Collection<TipoRecurso> tiposRecursos;
	
	public void setTiposRecursos(Collection<TipoRecurso> tiposRecursos) {
		this.tiposRecursos = tiposRecursos;
	}

	public  Collection<TipoRecurso> createBeanCollection() {		
		return tiposRecursos;
	}
	
}