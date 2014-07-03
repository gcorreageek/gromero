package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class Metadata implements Serializable{
 
	private static final long serialVersionUID = -6118199490559966517L;

	private Integer idMetadata;
	private Integer idDocumento;
	private Integer idEstructura;
	private String valor;
	
	public Integer getIdMetadata() {
		return idMetadata;
	}
	public void setIdMetadata(Integer idMetadata) {
		this.idMetadata = idMetadata;
	}
	public Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Integer getIdEstructura() {
		return idEstructura;
	}
	public void setIdEstructura(Integer idEstructura) {
		this.idEstructura = idEstructura;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	
	
	
}
