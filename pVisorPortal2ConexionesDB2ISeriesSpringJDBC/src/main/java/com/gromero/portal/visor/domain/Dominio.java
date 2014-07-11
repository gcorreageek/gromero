package com.gromero.portal.visor.domain;

import java.io.Serializable;

public class Dominio implements Serializable{
 
	private static final long serialVersionUID = -4511354687325551683L;
	public Integer getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(Integer idDominio) {
		this.idDominio = idDominio;
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
	public String getObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}
	public Integer getIdDominioSub() {
		return idDominioSub;
	}
	public void setIdDominioSub(Integer idDominioSub) {
		this.idDominioSub = idDominioSub;
	}
	public Integer getIdDominioHijo() {
		return idDominioHijo;
	}
	public void setIdDominioHijo(Integer idDominioHijo) {
		this.idDominioHijo = idDominioHijo;
	}
	private Integer idDominio;
	private String campo;
	private String valor;
	private String obligatorio;
	private String habilitado;
	private Integer idDominioSub;
	private Integer idDominioHijo;
	
	
	
	
}
