package pe.com.ransa.portal.carga.service;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.ibm.portal.ObjectID;

public interface PageService
{
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
    ObjectID obtenerIdPagina(PortletRequest request, PortletResponse response);
    
    /**
     * 
     * @param request
     * @param response
     * @param nameSpace
     * @param nombreUnico
     * @return
     */
    String generarUrlPagina(PortletRequest request, PortletResponse response, String nameSpace, String nombreUnico);
    
    /**
     * 
     * @param request
     * @param response
     * @param nameSpace
     * @param currentPage
     * @return
     */
    String generarUrlPagina(PortletRequest request, PortletResponse response, String nameSpace, ObjectID currentPage);
    
    /**
     * 
     * @param request
     * @param response
     * @param currentPage
     * @return
     */
    String generarUrlPagina(PortletRequest request, PortletResponse response, ObjectID currentPage);
}