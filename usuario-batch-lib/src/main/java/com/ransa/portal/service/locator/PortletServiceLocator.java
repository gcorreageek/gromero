package com.ransa.portal.service.locator;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ibm.portal.portlet.service.PortletService;
import com.ibm.portal.portlet.service.PortletServiceHome;
import com.ibm.portal.portlet.service.PortletServiceUnavailableException;
import com.ibm.portal.portlet.service.login.LoginHome;

import com.ibm.portal.um.portletservice.PumaHome;

import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.portletservice.SharedSlotPortletService;

public class PortletServiceLocator {
	
	private static Logger logger = Logger.getLogger(PortletServiceLocator.class);
	
	private static final String JNDI_PUMA_HOME = "portletservice/com.ibm.portal.um.portletservice.PumaHome";
	private static final String JNDI_LOGIN_HOME = "portletservice/com.ibm.portal.portlet.service.login.LoginHome";
	private static final String JNDI_SHARED_SLOT_PORTLET_SERVICE_HOME = "portletservice/com.ransa.portal.portletservice.SharedSlotPortletService";
	
	// Singleton's private instance 
	private static PortletServiceLocator instance = new PortletServiceLocator();
	
	private PumaHome pumaHome;
	private LoginHome loginHome;
	private SharedSlotPortletService sharedSlotPortletService;
	
	private PortletServiceLocator() { }
	
	public static PortletServiceLocator getInstance() { 
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getFromJNDI(String objectName, Class<T> clazz) {
		logger.debug("getFromJNDI");
		logger.debug("objectName=" + objectName);
		T object = null;
		try {
			Context contexto = new InitialContext();
			object = (T)contexto.lookup(objectName);			
		} catch (NamingException e) {
			throw new EjecucionException("Error al obtener object con jndi=" + objectName, e);
		}
		return object;
	}
	
	public PumaHome getPumaHome() {
		if (pumaHome == null) {
			PortletServiceHome portletServiceHome = getFromJNDI(JNDI_PUMA_HOME, PortletServiceHome.class);
			try {
				pumaHome = (PumaHome)portletServiceHome.getPortletService(PortletService.class);
			} catch (PortletServiceUnavailableException e) {
				throw new EjecucionException("Error al hacer el cast a PumaHome", e);
			} catch (EjecucionException e) { throw e; }
		}		
		return pumaHome;
	}

	public LoginHome getLoginHome() {
		if (loginHome == null) {
			PortletServiceHome portletServiceHome = getFromJNDI(JNDI_LOGIN_HOME, PortletServiceHome.class);
			try {
				loginHome = (LoginHome)portletServiceHome.getPortletService(PortletService.class);
			} catch (PortletServiceUnavailableException e) {
				throw new EjecucionException("Error al hacer el cast a LoginHome", e);
			} catch (EjecucionException e) { throw e; }
		}		
		return loginHome;
	}
	
	public SharedSlotPortletService getSharedSlotPortletServiceHome() {
		if (sharedSlotPortletService == null) {
			PortletServiceHome portletServiceHome = getFromJNDI(JNDI_SHARED_SLOT_PORTLET_SERVICE_HOME, PortletServiceHome.class);
			try {
				sharedSlotPortletService = (SharedSlotPortletService)portletServiceHome.getPortletService(SharedSlotPortletService.class);
			} catch (PortletServiceUnavailableException e) {
				throw new EjecucionException("Error al hacer el cast a SharedSlotPortletService", e);
			} catch (EjecucionException e) { throw e; }
		}		
		return sharedSlotPortletService;
	}
		
	public Session getMailSession() {
		Session session = getFromJNDI("mail/PortalRANSA", Session.class);		
	    return session; 
    }
	
}