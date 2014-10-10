package com.ransa.portal.model;

import com.ransa.portal.model.util.MetaData;

/**
 * Clase que representa la aplicacion que los usuarios pueden utilizar.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class Aplicacion extends MetaData {
	
	private static final long serialVersionUID = -2891897778794232742L;
	
	private String descripcion;
	private String url;
	private String ordenVisual; 	
	private Estado estado;	
	private Negocio negocio;
	private String tipo;
	private RolAplicacion[] roles;
	private OpcionAplicacion[] opciones;

	public Aplicacion() { }
	
	public void setId(String id) {
		setValue(id);
	}

	public String getId() {
		return getValue();
	}

	public void setNombre(String nombre) {
		setLabel(nombre);
	}

	public String getNombre() {
		return getLabel();
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	public void setOrdenVisual(String ordenVisual) {
		this.ordenVisual = ordenVisual;
	}

	public String getOrdenVisual() {
		return ordenVisual;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
		
	public Estado getEstado() {
		if(this.estado == null)
			this.estado	= new Estado();
		return estado;
	}

	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}

	public Negocio getNegocio() {
		if(this.negocio == null){
			this.negocio = new Negocio();
		}
		return negocio;
	}

	public RolAplicacion[] getRoles() {
		return roles;
	}

	public void setRoles(RolAplicacion[] roles) {
		this.roles = roles;
	}

	public OpcionAplicacion[] getOpciones() {
		return opciones;
	}

	public void setOpciones(OpcionAplicacion[] opciones) {
		this.opciones = opciones;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
		
}
