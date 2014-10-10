package pe.com.ransa.portal.carga.service.impl;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.springframework.stereotype.Component;

import pe.com.ransa.portal.carga.service.PageService;

import com.ibm.portal.ObjectID;

@Component
public class PageServiceImpl implements PageService {

	public ObjectID obtenerIdPagina(PortletRequest request,
			PortletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generarUrlPagina(PortletRequest request,
			PortletResponse response, String nameSpace, String nombreUnico) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generarUrlPagina(PortletRequest request,
			PortletResponse response, String nameSpace, ObjectID currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generarUrlPagina(PortletRequest request,
			PortletResponse response, ObjectID currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

}