package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

/**
 * Clase que representa al grupo del portal.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class Grupo extends MetaData {
	
	private static final long serialVersionUID = 5927425207947102524L;
	
	private String indPortal;

	public String getId() {
		return getValue();
	}

	public void setId(String id) {
		setValue(id);
	}

	public String getDescripcion() {
		return getLabel();
	}

	public void setDescripcion(String descripcion) {
		setLabel(descripcion);
	}
	
	@Override
	public String toString() {
		return (getId() + Util.SEPARADOR + getDescripcion());
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

	public String getIndPortal() {
		return indPortal;
	}

	public void setIndPortal(String indPortal) {
		this.indPortal = indPortal;
	}

}