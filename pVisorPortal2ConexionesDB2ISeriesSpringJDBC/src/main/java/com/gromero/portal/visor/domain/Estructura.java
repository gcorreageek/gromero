package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class Estructura implements Serializable{
 
	private static final long serialVersionUID = 8398233233115328120L;
	
	private Integer idEstructura;
	private Integer idTipoDocumental;
	private String campo;
	private String valor;
	
	public Integer getIdEstructura() {
		return idEstructura;
	}
	public void setIdEstructura(Integer idEstructura) {
		this.idEstructura = idEstructura;
	}
	public Integer getIdTipoDocumental() {
		return idTipoDocumental;
	}
	public void setIdTipoDocumental(Integer idTipoDocumental) {
		this.idTipoDocumental = idTipoDocumental;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	
	
	

}
