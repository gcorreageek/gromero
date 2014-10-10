package com.ransa.portal.service.factory;

import com.ransa.portal.service.AplicacionService;
import com.ransa.portal.service.ColumnaService;
import com.ransa.portal.service.GestionUsuarioGrupoPUMAService;
import com.ransa.portal.service.GestionUsuarioService;
import com.ransa.portal.service.SeguridadService;
import com.ransa.portal.service.TipoRecursoService;
import com.ransa.portal.service.impl.AplicacionServiceImpl;
import com.ransa.portal.service.impl.ColumnaServiceImpl;
import com.ransa.portal.service.impl.GestionUsuarioGrupoPUMAServiceImpl;
import com.ransa.portal.service.impl.GestionUsuarioServiceImpl;
import com.ransa.portal.service.impl.SeguridadServiceImpl;
import com.ransa.portal.service.impl.TipoRecursoServiceImpl;

public class ServiceFactory {

	// Singleton's private instance
	private static ServiceFactory instance = new ServiceFactory();

	private ServiceFactory() {
	}

	static public ServiceFactory getInstance() {
		return instance;
	}

	public GestionUsuarioGrupoPUMAService getGestionUsuarioGrupoPUMAService() {
		return new GestionUsuarioGrupoPUMAServiceImpl();
	}

	public GestionUsuarioService getGestionUsuarioService() {
		return new GestionUsuarioServiceImpl();
	}
	
	public ColumnaService getColumnaService(){
		return new ColumnaServiceImpl();
	}
	
	/**
	 * Entrega Servicio para la extracción de reportes
	 * 
	 * @return Objeto {@link ReporteService}
	 */
//	public ReporteService getReporteService() {
//		return new ReporteServiceImpl();
//	}
	
	public AplicacionService getAplicacionService() {
		return new AplicacionServiceImpl();
	}

	public TipoRecursoService getTipoRecursoService() {
		return new TipoRecursoServiceImpl();
	}
	
	public SeguridadService getSeguridadService() {
		return new SeguridadServiceImpl();
	}
	
}
