package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.Collection;

public class FirmasNegocioLoader {

	private Collection<FirmasNegocioBean> firmas;
		
	public void setFirmas(Collection<FirmasNegocioBean> firmas) {
		this.firmas = firmas;
	}

	public Collection<FirmasNegocioBean> createBeanCollection() {		
		return firmas;
	}
	
}
