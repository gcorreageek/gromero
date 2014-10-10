package com.ransa.portal.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class PortalRANSAListener implements ServletContextListener, HttpSessionListener {
	private static Logger logger = Logger.getLogger(PortalRANSAListener.class);

	public PortalRANSAListener() {
		logger.info("PortalRANSAListener >> Contructor");
	}
	
	/*
	 * (non-Java-doc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(ServletContextEvent
	 * arg0)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		logger.trace("contextInitialized");
		ServletContext application = arg0.getServletContext();

		FileManager.init("SESSION_FILES", arg0);

		/* Set de constantes para la sesion */
		logger.debug("COMBO_DEFAULT_SELECTION=" + Util.COMBO_DEFAULT_SELECTION);
		application.setAttribute("COMBO_DEFAULT_SELECTION", Util.COMBO_DEFAULT_SELECTION);
		logger.debug("COMBO_DEFAULT_SELECTION=" + application.getAttribute("COMBO_DEFAULT_SELECTION"));
		application.setAttribute("STRING_VACIO", Util.STRING_VACIO);
		logger.trace("fin");
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextDestroyed(ServletContextEvent
	 * arg0)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.trace("contextDestroyed");
		//ServletContext application = arg0.getServletContext();
		FileManager.cleanFolder();
		logger.trace("fin");
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(HttpSessionEvent
	 * arg0)
	 */
	@SuppressWarnings("unchecked")
	public void sessionCreated(HttpSessionEvent arg0) {
		logger.trace("sessionCreated");
		HttpSession session = arg0.getSession();

		session.setAttribute(FileManager.getSessionAttributeName(), new ArrayList<File>());
		session.setAttribute("SECRET_KEY", DesEncrypter.createSecretKey());

		if (logger.isDebugEnabled()) {
			Enumeration enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				logger.debug(enumeration.nextElement());
			}
		}		
		logger.trace("fin");
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(HttpSessionEvent
	 * arg0)
	 */
	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent arg0) {
		logger.trace("sessionDestroyed");
		HttpSession session = arg0.getSession();
		
		if (logger.isDebugEnabled()) {
			Enumeration enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				logger.debug(enumeration.nextElement());
			}
		}

		FileManager.sessionDestroyed(session);
		logger.trace("fin");
	}
}