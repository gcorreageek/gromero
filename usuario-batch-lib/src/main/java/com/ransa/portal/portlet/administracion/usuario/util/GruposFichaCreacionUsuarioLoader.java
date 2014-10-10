package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.Collection;

import com.ransa.portal.model.Grupo;

public class GruposFichaCreacionUsuarioLoader {	
	
	private Collection<Grupo> grupos;
		
	public void setGrupos(Collection<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	public Collection<Grupo> createBeanCollection() {		
		return grupos;
	}
	
}