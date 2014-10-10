package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

public class Pais extends MetaData {
	
	private static final long serialVersionUID = 5596377602762601418L;

	public Pais() {
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