package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

/**
 * Clase que representa el rol de la aplicacion.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class RolAplicacion extends MetaData {
	
	private static final long serialVersionUID = -4773163488521725868L;
	
	private String descripcion;
	private Estado estado;
	private int opciones[];
	private String idAplicacion;
	
	public RolAplicacion() { }

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int[] getOpciones() {
		return opciones;
	}

	public void setOpciones(int[] opciones) {
		this.opciones = opciones;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Estado getEstado() {
		if(this.estado == null){
			this.estado = new Estado();
		}
		return estado;
	}

	public String getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	@Override
	public String toString() {
		return (idAplicacion + Util.SEPARADOR + getId());
	}
	
}
