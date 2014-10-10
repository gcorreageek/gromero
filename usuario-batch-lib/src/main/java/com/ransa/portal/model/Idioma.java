package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

public class Idioma extends MetaData {
	
	private static final long serialVersionUID = -9113958788068810106L;

	public Idioma() {
		setLabel(Util.STRING_VACIO);
		setValue(Util.STRING_VACIO);
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