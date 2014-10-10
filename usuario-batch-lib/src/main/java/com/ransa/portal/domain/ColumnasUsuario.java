package com.ransa.portal.domain;

import java.io.Serializable;

public class ColumnasUsuario implements Serializable {

	private static final long serialVersionUID = 5368900418866392825L;

	private int codColumna;
	private String nomColumna;

	public int getCodColumna() {
		return codColumna;
	}

	public void setCodColumna(int codColumna) {
		this.codColumna = codColumna;
	}

	public String getNomColumna() {
		return nomColumna;
	}

	public void setNomColumna(String nomColumna) {
		this.nomColumna = nomColumna;
	}
}
