package com.ransa.portal.portlet.administracion.usuario.util;

import com.ransa.portal.model.util.MetaData;

public class LookAndFeelBean extends MetaData {

	public LookAndFeelBean() {
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
