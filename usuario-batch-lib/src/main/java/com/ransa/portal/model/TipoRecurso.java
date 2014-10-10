package com.ransa.portal.model;

import java.util.Date;

import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.util.Util;

/**
 * Clase que representa el tipo de recurso de los usuarios.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class TipoRecurso extends MetaData {	
	
	private static final long serialVersionUID = -4311247095824714737L;
	
	private String descripcion;
	private String procedimiento;
	private String parametro;
	private String parametroCookie;
	private String parametroPost;
	private Estado estado;
	private Recurso recursos[];
	private Date fechaCreacion = null;
	private String creador = null;
	private Date fechaModificacion = null;
	private String modificador = null;
	
	public TipoRecurso() { }

	public TipoRecurso(String descripcion, int id, String nombre,
			String parametro, String procedimiento/*, Recurso[] recursos*/) {
		this.descripcion = descripcion;
		setValue(Util.STRING_VACIO + id);
		setLabel(nombre);
		this.parametro = parametro;
		this.procedimiento = procedimiento;
		//this.recursos = recursos;
	}

	public int getId() {
		return Util.parseInt(getValue());
	}

	public void setId(int id) {
		setValue(Util.STRING_VACIO + id);
	}

	public String getNombre() {
		String nombre = getLabel();
		if(nombre!=null) {
			nombre = nombre.trim();
		}
		return nombre;
	}

	public void setNombre(String nombre) {
		setLabel(nombre);
	}

	public String getDescripcion() {
		if(this.descripcion!=null)
			this.descripcion = this.descripcion.trim();
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getProcedimiento() {
		if(this.procedimiento!=null)
			this.procedimiento = this.procedimiento.trim();
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getParametro() {
		if(this.parametro!=null)
			this.parametro = this.parametro.trim();
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	public Recurso[] getRecursos() {
		return recursos;
	}

	public void setRecursos(Recurso[] recursos) {
		this.recursos = recursos;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Estado getEstado() {
		if(this.estado==null)
			this.estado = new Estado();
		return this.estado;
	}
	
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setCreador(String creador) {
		this.creador = creador;
	}

	public String getCreador() {
		return creador;
	}

	public void setModificador(String modificador) {
		this.modificador = modificador;
	}

	public String getModificador() {
		return modificador;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setParametroCookie(String parametroCookie) {
		this.parametroCookie = parametroCookie;
	}

	public String getParametroCookie() {
		return parametroCookie;
	}

	public void setParametroPost(String parametroPost) {
		this.parametroPost = parametroPost;
	}

	public String getParametroPost() {
		return parametroPost;
	}	

}
