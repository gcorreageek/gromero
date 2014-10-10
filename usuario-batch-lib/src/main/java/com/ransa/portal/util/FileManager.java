package com.ransa.portal.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Esta clase permite la administración de archivos durante la sesión de
 * cliente, se encarga de crear archivos temporales en un determinada ruta
 * dentro del modulo web y además puede borrar dichos archivos cuando se le
 * indique que la sesión ha terminado.
 * 
 * @author César Bardález Vela
 */
public final class FileManager {
	
	private static Logger logger = Logger.getLogger(FileManager.class);
	
	private static final String CONFIG_RESOURCE = "com.ransa.portal.util.FileManager";
	private static File folder;
	private static File folder2;	
	private static String sessionAttributeName;
	
	public static final String TIPO_REPORTE_EVENTO = "REPORTE_EVENTO";
	public static final String TIPO_REPORTE_APLICACIONES = "REPORTE_APLICACIONES";
	public static final String TIPO_REPORTE_USUARIOS = "REPORTE_USUARIOS";
	
	/**
	 * Crea configuración inicial del componente seteando la ruta base del
	 * modulo web.
	 * 
	 * @param sessionAttributeName
	 *            Nombre con el que se guardará los archivos dentro de la sesión
	 *            cliente.
	 * 
	 * @param event
	 *            Evento de iniciación de contexto de aplicación.
	 */
	public static void init(String sessionAttributeName, ServletContextEvent event) {
		logger.trace("init");
		if (FileManager.sessionAttributeName == null) {
			FileManager.sessionAttributeName = sessionAttributeName;
			String basePath = event.getServletContext().getRealPath("/");
			String webPath = ResourceBundle.getBundle(CONFIG_RESOURCE).getString("WEB_PATH");
			folder = new File(basePath, webPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			String webPath2 = ResourceBundle.getBundle(CONFIG_RESOURCE).getString("WEB_PATH2");
			folder2 = new File(basePath, webPath2);
			cleanFolder();
		}
	}

	/**
	 * Se utiliza para la administracion de un archivo nuevo.
	 * 
	 * @param fileType
	 *            Tipo de archivo a crear.
	 * @param sessionScope
	 *            Map de la sesion (provisto por Faces)
	 * @return Nuevo archivo temporal
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static File createFile(String fileType, Object request) throws Exception {
		logger.trace("createFile");
		boolean isPortal = request instanceof PortletRequest;

		/* Configura variables de request, locale y bundle */
		PortletRequest portletRequest = null;
		HttpServletRequest httpServletRequest = null;
		Locale locale = null;
		ResourceBundle resourceBundle = null;
		if (isPortal) {
			portletRequest = (PortletRequest) request;
			locale = portletRequest.getLocale();
			resourceBundle = ResourceBundle.getBundle(CONFIG_RESOURCE, locale);
		} else {
			httpServletRequest = (HttpServletRequest) request;
			locale = httpServletRequest.getLocale();
			resourceBundle = ResourceBundle.getBundle(CONFIG_RESOURCE, locale);
		}

		/* Recupera nombre de archivo y tipo de archivo a crear */
		String prefix = null;
		String suffix = null;
		if (TIPO_REPORTE_EVENTO.equals(fileType)) {
			prefix = resourceBundle.getString("REPORTE_EVENTO");
			suffix = resourceBundle.getString("EXCEL");
		} else if (TIPO_REPORTE_APLICACIONES.equals(fileType)) {
			prefix = resourceBundle.getString("REPORTE_APLICACIONES");
			suffix = resourceBundle.getString("EXCEL");				
		} else if (TIPO_REPORTE_USUARIOS.equals(fileType)) {
			prefix = resourceBundle.getString("REPORTE_USUARIOS");
			suffix = resourceBundle.getString("EXCEL");				
		}else {
			prefix = resourceBundle.getString("");
			suffix = resourceBundle.getString("EXCEL");
		}
		
		/* Crea el archivo */
		File file = File.createTempFile(prefix + "-", suffix, folder);
		file.deleteOnExit();

		/* Crea objeto de sesion y recupera los files */
		PortletSession portletSession = null;
		HttpSession httpSession = null;
		Collection<File> files = null;
		if (isPortal) {
			portletSession = portletRequest.getPortletSession();
			if (logger.isDebugEnabled()) {
				Enumeration enumeration = portletSession.getAttributeNames(PortletSession.APPLICATION_SCOPE);
				while (enumeration.hasMoreElements()) {
					logger.debug(enumeration.nextElement());
				}
			}
			files = (Collection<File>) portletSession.getAttribute(sessionAttributeName, PortletSession.APPLICATION_SCOPE);

		} else {
			httpSession = httpServletRequest.getSession();
			if (logger.isDebugEnabled()) {
				Enumeration enumeration = httpSession.getAttributeNames();
				while (enumeration.hasMoreElements()) {
					logger.debug(enumeration.nextElement());
				}
			}
			files = (Collection<File>) httpSession.getAttribute(sessionAttributeName);
		}

		/* Agrega el file a la lista de sesion y entrega file */
		logger.debug(files.size());
		files.add(file);
		logger.debug(files.size());
		return file;
	}

	/**
	 * Se utiliza para borrar los archivos creados durante la sesion de usuario.
	 * 
	 * @param session
	 *            Sesion de usuario
	 * @see PortalRANSAListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@SuppressWarnings("unchecked")
	public static void sessionDestroyed(HttpSession session) {
		logger.trace("sessionDestroyed");
		if (logger.isDebugEnabled()) {
			Enumeration enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				logger.debug(enumeration.nextElement());
			}
		}

		Collection<File> files = (Collection<File>) session.getAttribute(sessionAttributeName);
		logger.debug(files);
		if (files!=null)
			logger.debug(files.size());
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();
			logger.debug(file.getName());
			boolean deleted = file.delete();
			logger.debug(deleted + file.getName());
		}
		
		Collection<String> reports = (Collection<String>) session.getAttribute(Util.PDFS);
		if (folder2!=null && folder2.length()>0){
			for (File file : folder2.listFiles()) {
				if (reports!=null && reports.size()>0){
					for (String report : reports) {
						if (file.getName().equals(report)) {
							logger.debug("file=" + file.getName());
							boolean deleted = file.delete();
							logger.debug("deleted=" +deleted);
							break;
						}
					}
				}
			}
		}
		logger.trace("fin");
	}

	/**
	 * Se utiliza para limpiar la carpeta de descarga de archivos.
	 * 
	 * @see PortalRANSAListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * @see PortalRANSAListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public static void cleanFolder() {
		logger.trace("cleanFolder");
		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (!".files".equalsIgnoreCase(file.getName())) {
				file.delete();
			}
		}
		File[] files2 = folder2.listFiles();
		for (int i = 0; i < files2.length; i++) {
			File file = files2[i];
			if (file.getName().indexOf(".pdf") != -1) {
				file.delete();
			}
		}
	}

	/**
	 * Devuelve el nombre del atributo configuado para la sesión
	 */
	public static String getSessionAttributeName() {
		return sessionAttributeName;
	}
}