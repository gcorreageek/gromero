package com.ransa.portal.util;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.apache.log4j.Logger;

import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.util.Displayable;

public class JSFUtil {
	
	private static Logger logger = Logger.getLogger(JSFUtil.class);
	
	/**
	 * Returns an InputStream for a resource at the given path
	 */
	public static InputStream getResourceInputStream(String relPath) {
		return getExternalContext().getResourceAsStream(relPath);
	}
	
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public static ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}
	
	public static Collection<SelectItem> toSelectItems(Collection<? extends Displayable> displayables,
			boolean isValueObject) {
		Collection<SelectItem> selectItems = new ArrayList<SelectItem>();
		if (isValueObject) {
			// Value es un Object
			for (Displayable displayable : displayables) {
				selectItems.add(new SelectItem(displayable, displayable.getLabel()));
			}
		} else {
			// Value es un String
			for (Displayable displayable : displayables) {
				selectItems.add(new SelectItem(displayable.getValue(), displayable.getLabel()));
			}
		}
		return selectItems;
	}
	
	public static Object getParametroSesion(String nombreParametro, PortletRequest portletRequest) {
		logger.debug("getParametroSesion");
		if (portletRequest == null) {
			logger.debug("portletRequest es null");
			portletRequest = (PortletRequest)getExternalContext().getRequest();
		}		
		Object tempParametroSesion = portletRequest.getPortletSession().getAttribute(nombreParametro,
				PortletSession.APPLICATION_SCOPE);
		if (tempParametroSesion != null) {
			logger.debug("parametroSesion no es null");
			return tempParametroSesion;
		} else {
			throw new EjecucionException("parametro " + nombreParametro + " de sesion es null", new Exception());
		}
	}
	
	public static void setParametroSesion(String nombreParametro, Object valorParametro,
			PortletRequest portletRequest) {
		logger.debug("setParametroSesion");
		if (portletRequest == null) {
			logger.debug("portletRequest es null");
			portletRequest = (PortletRequest)getExternalContext().getRequest();
		}
		portletRequest.getPortletSession().setAttribute(nombreParametro, valorParametro, PortletSession.APPLICATION_SCOPE);
	}
			
	public static String getMensaje(String keyMensaje, String PATH_RESOURCE_BUNDLE){
		ResourceBundle bundle = ResourceBundle.getBundle(PATH_RESOURCE_BUNDLE,				
				FacesContext.getCurrentInstance().getViewRoot().getLocale());
		return bundle.getString(keyMensaje);
	}
		
	public static String getMensaje(String keyMensaje, String[] parametros, String PATH_RESOURCE_BUNDLE){		
		String parametricMessage = getMensaje(keyMensaje, PATH_RESOURCE_BUNDLE);
		StringBuffer message = new StringBuffer(parametricMessage);

		for (int i = 0; i < parametros.length; i++) {
			String value = parametros[i];
			String regex = new StringBuffer("{").append(i).append("}").toString();

			int pos = parametricMessage.indexOf(regex);
			while (0 <= pos) {
				message = new StringBuffer(parametricMessage.substring(0, pos));
				message.append(value);
				message.append(parametricMessage.substring(pos + regex.length()));
				parametricMessage = message.toString();
				pos = parametricMessage.indexOf(regex);
			}
		}

		return message.toString();
	}

	/**
	 * Return the managed bean with the given name
	 * 
	 * @param mgdBeanName
	 *            the name of the managed bean to retrieve
	 * @return
	 */
	public static Object getManagedBean(String managedBean) {
		String expression = new StringBuffer("#{").append(managedBean).append("}").toString();
		if ((expression.indexOf("#{") != -1) && (expression.indexOf("#{") < expression.indexOf('}'))) {
			return getFacesContext().getApplication().createValueBinding(expression).getValue(getFacesContext());
		} else {
			return expression;
		}
	}

	/**
	 * Returns a full system path for a file path given relative to the web
	 * project
	 */
	public static File getRealPath(String relPath) {
		String path = relPath;
		try {
			URL url = getExternalContext().getResource(relPath);
			if (url != null) {
				path = url.getPath();
			}
		} catch (MalformedURLException e) {
			Util.showStackTrace(e);
		}
		return new File(path);
	}
	
}