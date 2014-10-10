package com.ransa.portal.domain;

import java.io.Serializable;

public class Columna implements Serializable {

	private static final long serialVersionUID = -7354344344041615752L;

	private int id;
	private int idAplicacion;
	private int codPantalla;
	private String vista;
	private int codigoColumna;
	private String nombreColumna;
	private String valor;
	private String flag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public int getCodPantalla() {
		return codPantalla;
	}

	public void setCodPantalla(int codPantalla) {
		this.codPantalla = codPantalla;
	}

	public String getVista() {
		return vista;
	}

	public void setVista(String vista) {
		this.vista = vista;
	}

	public int getCodigoColumna() {
		return codigoColumna;
	}

	public void setCodigoColumna(int codigoColumna) {
		this.codigoColumna = codigoColumna;
	}

	public String getNombreColumna() {
		return nombreColumna;
	}

	public void setNombreColumna(String nombreColumna) {
		this.nombreColumna = nombreColumna;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
