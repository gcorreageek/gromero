package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

/**
 * Clase que representa la opcion del menu de la aplicacion.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class OpcionAplicacion extends MetaData {
	
	private static final long serialVersionUID = 3238528278264788475L;
	
	private String descripcion;
	private String url;
	private String jerarquia;
	private String orden;
	private String permiso;
	private Estado estado;	
	private String idPadre;
	private String uniqueNamePortal;
	
	public OpcionAplicacion() { }

	public OpcionAplicacion(String descripcion, String id, String jerarquia,
			String nombre, String orden, String url, String uniqueNamePortal) {
		this.descripcion = descripcion;
		setValue(id);
		this.setJerarquia(jerarquia);
		setLabel(nombre);
		this.setOrden(orden);
		this.url = url;
		this.uniqueNamePortal = uniqueNamePortal;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Estado getEstado() {
		if(this.estado==null){
			this.estado = new Estado(); 
		}
		return estado;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setJerarquia(String jerarquia) {
		this.jerarquia = jerarquia;
	}

	public String getJerarquia() {
		return jerarquia;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getOrden() {
		return orden;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public String getUniqueNamePortal() {
		return uniqueNamePortal;
	}

	public void setUniqueNamePortal(String uniqueNamePortal) {
		this.uniqueNamePortal = uniqueNamePortal;
	}
	
}
