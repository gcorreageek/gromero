package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

public class TipoPortal extends MetaData {
	
	private static final long serialVersionUID = -8596329748958485950L;

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
