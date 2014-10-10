package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

public class TipoUsuario extends MetaData {
	
	private static final long serialVersionUID = 2717126671129796260L;

	public TipoUsuario() { }
	
	public TipoUsuario(String nombreTipoUsuario) {
		setNombre(nombreTipoUsuario);
	}
	
	public String getId() {
		return getValue();
	}
	
	public void setId(String id) {
		setValue(id);
	}
	
	public String getNombre() {
		return getLabel();
	}
	
	public void setNombre(String nombre) {
		setLabel(nombre);
	}
	
}