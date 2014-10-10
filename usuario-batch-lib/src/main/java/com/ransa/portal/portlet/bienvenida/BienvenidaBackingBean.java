package com.ransa.portal.portlet.bienvenida;

import java.io.IOException;

import javax.portlet.*;

import org.apache.log4j.Logger;

import com.ransa.portal.portlet.PageCodeBase;
import com.ransa.portal.util.IndicadorTipoPortal;
import com.ransa.portal.util.JSFUtil;
import com.ransa.portal.util.Util;
import com.ransa.portal.portletservice.util.VariablesPortalRANSA;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlCommandButton;

public class BienvenidaBackingBean extends PageCodeBase {

	private static Logger logger = Logger.getLogger(BienvenidaBackingBean.class);
	private static final String BUNDLE = "com.ransa.portal.portlet.bienvenida.nl.BienvenidaPortletResource";
	private String URLPaginaInicial;
	protected HtmlForm form_Bienvenida;
	protected HtmlCommandButton btnRedireccionar;
	public BienvenidaBackingBean() {
		logger.info("constructor");
		PortletRequest portletRequest = (PortletRequest) getRequest();
		IndicadorTipoPortal indicadorTipoPortal = IndicadorTipoPortal.getInstance();
		URLPaginaInicial = indicadorTipoPortal.getVariablesPortalRANSA(portletRequest).getURLPaginaInicial();
		logger.debug("URLPaginaInicial=" + URLPaginaInicial);		
		if(!indicadorTipoPortal.isLogin()) {
			VariablesPortalRANSA variablesPortalRANSA = indicadorTipoPortal.getVariablesPortalRANSA(portletRequest);
			logger.info("estableciendo " + Util.IS_LOGIN);			
			JSFUtil.setParametroSesion(Util.IS_LOGIN, new Boolean(true), portletRequest);
			indicadorTipoPortal.setIsIntranet(portletRequest, variablesPortalRANSA.getVariables());
		}		
	}
	
	public String getURLPaginaInicial() {
		return URLPaginaInicial;
	}

	public void setURLPaginaInicial(String paginaInicial) {
		URLPaginaInicial = paginaInicial;
	}

	public String getMensajeBienvenida() {
		logger.info("getMensajeBienvenida");
		String mensaje = null;
		PortletRequest portletRequest = (PortletRequest) getRequest();
		if (IndicadorTipoPortal.getInstance().isIntranet(portletRequest)) { // Intranet
			mensaje = JSFUtil.getMensaje("bienvenidaView_msg_bienvenida_intranet", BUNDLE);
		} else {// Extranet
			mensaje = JSFUtil.getMensaje("bienvenidaView_msg_bienvenida_extranet", BUNDLE);
		}
		return mensaje;
	}
	
	public String getIndicadorPortal() {
		logger.info("getIndicadorPortal");
		String indicador = null;
		VariablesPortalRANSA variablesPortalRANSA = IndicadorTipoPortal.getInstance().getVariablesPortalRANSA((PortletRequest) getRequest());
		indicador = variablesPortalRANSA.getTipoPortal();
		if (indicador != null && !indicador.equals(""))
			return indicador;
		else
			return Util.IND_PORTAL_INTRANET;
	}

	public String doRedireccionarAction() {
		logger.info("doRedireccionarAction");
		logger.debug("URLPaginaInicial=" + URLPaginaInicial);
		try {
			logger.info("limpiando campo URLPaginaInicial");
			PortletRequest portletRequest = (PortletRequest) getRequest();
			IndicadorTipoPortal indicadorTipoPortal = IndicadorTipoPortal.getInstance();
			VariablesPortalRANSA variablesPortalRANSA = indicadorTipoPortal.getVariablesPortalRANSA(portletRequest);
			variablesPortalRANSA.setURLPaginaInicial(null);
			indicadorTipoPortal.setIsIntranet(portletRequest, variablesPortalRANSA.getVariables());			
			logger.info("redireccionando");
			JSFUtil.getFacesContext().responseComplete();
			JSFUtil.getExternalContext().redirect(URLPaginaInicial);
		} catch (IOException e) {				
			Util.showStackTrace(e);
			logger.error("no se pudo redireccionar");
		}
		return "";
	}

	protected HtmlForm getForm_Bienvenida() {
		if (form_Bienvenida == null) {
			form_Bienvenida = (HtmlForm) findComponentInRoot("form_Bienvenida");
		}
		return form_Bienvenida;
	}

	protected HtmlCommandButton getBtnRedireccionar() {
		if (btnRedireccionar == null) {
			btnRedireccionar = (HtmlCommandButton) findComponentInRoot("btnRedireccionar");
		}
		return btnRedireccionar;
	}
	
}