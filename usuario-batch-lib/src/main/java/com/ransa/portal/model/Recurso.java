package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

/**
 * Clase que representa al recurso disponible segun tipo.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class Recurso extends MetaData {
	 
	private static final long serialVersionUID = 9202695134277620192L;
	
	private int idTipoRecurso;
	
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
	
	public int getIdTipoRecurso() {
		return idTipoRecurso;
	}

	public void setIdTipoRecurso(int idTipoRecurso) {
		this.idTipoRecurso = idTipoRecurso;
	}

	@Override
	public String toString() {		
		return (idTipoRecurso + Util.SEPARADOR + getId());
	}
	
}
