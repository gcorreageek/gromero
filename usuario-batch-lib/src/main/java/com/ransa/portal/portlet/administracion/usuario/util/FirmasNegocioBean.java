package com.ransa.portal.portlet.administracion.usuario.util;

import com.ransa.portal.util.Util;

public class FirmasNegocioBean {
	
	private String negocio1;
	private String negocio2;
	
	public FirmasNegocioBean() {
		negocio1 = Util.STRING_VACIO;
		negocio2 = Util.STRING_VACIO;
	}
	
	public String getNegocio1() {
		return negocio1;
	}
	
	public void setNegocio1(String negocio1) {
		this.negocio1 = negocio1;
	}
	
	public String getNegocio2() {
		return negocio2;
	}
	
	public void setNegocio2(String negocio2) {
		this.negocio2 = negocio2;
	}
	
}
