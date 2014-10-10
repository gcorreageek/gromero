package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

/**
 * Bean que encapsula un negocio.
 * @author Michael Chihuantito
 *
 */
public class Negocio extends MetaData {
	
	private static final long serialVersionUID = 3580482468687998590L;

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

	@Override
	public String toString() {
		return (getId() + Util.SEPARADOR + getNombre());		
	}
	
	@Override
	public boolean equals(Object object) {
		String tempValue = this.toString();
		if (tempValue.equals(object.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
}
