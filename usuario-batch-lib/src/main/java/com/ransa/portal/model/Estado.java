package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

/**
 * Encapsula las propiedades de una estado
 * @author Michael Chihuantito
 *
 */
public class Estado extends MetaData {
	
	private static final long serialVersionUID = -2635910618351243935L;
	
	public void setIdEstado(String idEstado) {
		super.setValue(idEstado);
	}
	public String getIdEstado() {
		String _value = getValue();
		if(_value!=null)
			_value = _value.trim();
		
		return _value;
	}
	public void setDescripcion(String descripcion) {
		super.setLabel(descripcion);
	}
	public String getDescripcion() {		
		return super.getLabel();
	}

}
