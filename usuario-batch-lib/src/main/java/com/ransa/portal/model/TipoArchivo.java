package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

public class TipoArchivo extends MetaData {
	
	private static final long serialVersionUID = 801837890734638195L;

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
