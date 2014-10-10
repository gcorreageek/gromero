package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

/**
 * Clase que representa el tipo de uso de la opciones de aplicación
 * 
 * @author César Bardález Vela
 */
public class TipoUso extends MetaData {
	
	private static final long serialVersionUID = -1473686572035350123L;
	
	private int id;
	private String descripcion;

	public TipoUso() {
	}

	public TipoUso(String descripcion, int id) {
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
