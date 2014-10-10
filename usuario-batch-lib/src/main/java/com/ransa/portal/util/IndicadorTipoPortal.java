package com.ransa.portal.util;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.apache.log4j.Logger;

import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.exception.UsuarioException;
import com.ransa.portal.portletservice.SharedSlotPortletService;
import com.ransa.portal.portletservice.util.VariablesPortalRANSA;
import com.ransa.portal.service.locator.PortletServiceLocator;

public class IndicadorTipoPortal {
	
	private static Logger logger = Logger.getLogger(IndicadorTipoPortal.class);	
	private static IndicadorTipoPortal instance = new IndicadorTipoPortal();
	private SharedSlotPortletService sharedSlotPortletService;
	
	private boolean isIntranet;
	
	public IndicadorTipoPortal() {
		logger.info("constructor");
		try {			
			sharedSlotPortletService = PortletServiceLocator.getInstance().getSharedSlotPortletServiceHome();
			logger.info("sharedSlotPortletService obtenido correctamente");
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_22, e);
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
		isIntranet = true;
	}
		
	public static IndicadorTipoPortal getInstance() {
		return instance;
	}
		
	private void setVariables(PortletRequest portletRequest, HashMap<String, Object> variables) {
		logger.info("setVariables");
		logger.info("estableciendo variables en SharedSlot");
		logger.debug("numVariables=" + variables.size());
		if (logger.isDebugEnabled() && variables.size() > 0) {				
			for(Entry<String, Object> entry : variables.entrySet()) {					
				logger.debug(entry.getKey() + "=" + entry.getValue());
			}
		}
		portletRequest.getPortletSession().setAttribute(SharedSlotPortletService.SHARED_SLOT_RESOURCE, variables, PortletSession.APPLICATION_SCOPE);
		logger.info("variables establecidas en PortletSession correctamente");	
	}
	
	public boolean isLogin() {
		logger.info("getIsLogin");
		boolean bandera = false;
		try {
			Object tempIsLogin = JSFUtil.getParametroSesion(Util.IS_LOGIN, null);
			Boolean isLogin = (Boolean) tempIsLogin;
			bandera = isLogin.booleanValue();
		} catch (Exception e) {
			JSFUtil.setParametroSesion(Util.IS_LOGIN, new Boolean(false), null);
			bandera = false;
		}
		logger.debug("isLogin=" + bandera);
		return bandera;
	}
	
	@SuppressWarnings("unchecked")
	public VariablesPortalRANSA getVariablesPortalRANSA(PortletRequest portletRequest) {
		logger.info("getVariablesPortalRANSA");		
//		if (isLogin()) {
//			return sharedSlotPortletService.getVariablesPortalRANSA(portletRequest);
//		} else {
			VariablesPortalRANSA variablesPortalRANSA = null;
			logger.info("obteniendo HashMap<String, Object>");
			Object tempVariables = portletRequest.getPortletSession().getAttribute(SharedSlotPortletService.SHARED_SLOT_RESOURCE,
					PortletSession.APPLICATION_SCOPE);
			if (tempVariables != null) {
				HashMap<String, Object> variables = (HashMap<String, Object>) tempVariables;
				variablesPortalRANSA = new VariablesPortalRANSA(variables);
			} else {
				HashMap<String, Object> variables = new HashMap<String, Object>();
				variablesPortalRANSA = new VariablesPortalRANSA(variables);
			}
			return variablesPortalRANSA;
//		}		
	}
	
	public boolean isIntranet(VariablesPortalRANSA variablesPortalRANSA) {
		logger.info("isIntranet");
		logger.info("obteniendo tipoPortal");
		String tipoPortal = variablesPortalRANSA.getTipoPortal();
		logger.debug("tipoPortal=" + tipoPortal);
		if (Util.IND_PORTAL_INTRANET.equals(tipoPortal)) {
			logger.debug("tipoPortal es Intranet");
			isIntranet = true;
		} else if (Util.IND_PORTAL_EXTRANET.equals(tipoPortal)) {
			logger.debug("tipoPortal es Extranet");
			isIntranet = false;
		} else {
			logger.debug("tipoPortal no es Intranet ni Extranet");
			isIntranet = true;
		}	
		logger.debug("isIntranet=" + isIntranet);		
		return isIntranet;
	}
	
	public boolean isIntranet(PortletRequest portletRequest) {
		logger.info("isIntranet");
		return isIntranet(getVariablesPortalRANSA(portletRequest));
	}
	
	public String isIntranetLetra(PortletRequest portletRequest) {
		logger.info("isIntranetLetra");
		return isIntranetLetra(getVariablesPortalRANSA(portletRequest));
	}
	
	public String isIntranetLetra(VariablesPortalRANSA variablesPortalRANSA) {
		logger.info("isIntranetLetra");
		logger.info("obteniendo tipoPortal");
		String tipoPortal = variablesPortalRANSA.getTipoPortal();		
		logger.debug("tipoPortal=" + tipoPortal);		
		return tipoPortal;
	}
			
	public void setIsIntranet(PortletRequest portletRequest, HashMap<String, Object> variables) {
		logger.info("setIsIntranet");
		if (isLogin()) {
			sharedSlotPortletService.setVariables(portletRequest, variables);
		} else {
			setVariables(portletRequest, variables);
		}		
	}
		
	public String getIndPortal(PortletRequest portletRequest) {
		logger.info("getIndPortal");
		isIntranet = isIntranet(portletRequest);
		if (isIntranet) {
			return Util.IND_PORTAL_INTRANET;
		} else {
			return Util.IND_PORTAL_EXTRANET;
		}
	}
	
}