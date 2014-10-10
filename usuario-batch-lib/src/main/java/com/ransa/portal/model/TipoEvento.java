package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

/**
 * Clase que representa el tipo de evento que van hacer registrados.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class TipoEvento extends MetaData {
	
	private static final long serialVersionUID = -8414494099971408294L;
	
	private int id;
	private String descripcion;

	public TipoEvento() {
	}

	public TipoEvento(String descripcion, int id) {
		setDescripcion(descripcion);
		setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		setValue(String.valueOf(id));
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		setLabel(descripcion);
	}

}
