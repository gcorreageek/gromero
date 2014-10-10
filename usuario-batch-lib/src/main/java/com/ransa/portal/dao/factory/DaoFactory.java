package com.ransa.portal.dao.factory;

import com.ransa.portal.dao.AplicacionDao;
import com.ransa.portal.dao.CargaDao;
import com.ransa.portal.dao.ColumnaDAO;
import com.ransa.portal.dao.GrupoDao;
import com.ransa.portal.dao.RecursoUsuarioDao;
import com.ransa.portal.dao.RolAplicacionDao;
import com.ransa.portal.dao.RolUsuarioDao;
import com.ransa.portal.dao.TipoRecursoDao;
import com.ransa.portal.dao.UsuarioDao;
import com.ransa.portal.dao.UtilDao;
import com.ransa.portal.dao.impl.CargaDaoImpl;
import com.ransa.portal.dao.impl.ColumnaDAODB2;
import com.ransa.portal.dao.impl.GrupoDaoImpl;
import com.ransa.portal.dao.impl.RecursoUsuarioDaoImpl;
import com.ransa.portal.dao.impl.RolAplicacionDaoImpl;
import com.ransa.portal.dao.impl.RolUsuarioDaoImpl;
import com.ransa.portal.dao.impl.UsuarioDaoImpl;
import com.ransa.portal.dao.impl.UtilDaoImpl;

public class DaoFactory {

	// Singleton's private instance
	private static DaoFactory instance = new DaoFactory();
	
	private AplicacionDao aplicacionDao;	
	private GrupoDao grupoDao;
	private RecursoUsuarioDao recursoUsuarioDao;
//	private ReporteDaoImpl reporteDaoImpl;
	private RolAplicacionDao rolAplicacionDao;
	private RolUsuarioDao rolUsuarioDao;
	private TipoRecursoDao tipoRecursoDao;	
	private UsuarioDao usuarioDao;
	private UtilDao utilDao;
	private CargaDao cargaDao;
	private ColumnaDAO columnaDao;
	
	private DaoFactory() {
	}

	// Returns the Service Locator instance
	static public DaoFactory getInstance() {
		return instance;
	}

	public UtilDao getUtilDao() {
		if (utilDao == null) {
			utilDao = new UtilDaoImpl();
		}
		return utilDao; 
	}

	public GrupoDao getGrupoDao() {
		if (grupoDao == null) {
			grupoDao = new GrupoDaoImpl();	
		}
		return grupoDao;
	}

	public UsuarioDao getUsuarioDao() {
		if (usuarioDao == null) {
			usuarioDao = new UsuarioDaoImpl();
		}
		return usuarioDao;
	}

//	public AplicacionDao getAplicacionDao() {
//		if (aplicacionDao == null) {
//			aplicacionDao = new AplicacionDaoImpl();
//		}
//		return aplicacionDao;
//	}

	public RolAplicacionDao getRolAplicacionDao() {
		if (rolAplicacionDao == null) {
			rolAplicacionDao = new RolAplicacionDaoImpl();
		}
		return rolAplicacionDao;
	}

	public RolUsuarioDao getRolUsuarioDao() {
		if (rolUsuarioDao == null) {
			rolUsuarioDao = new RolUsuarioDaoImpl();
		}
		return rolUsuarioDao;
	}

//	public TipoRecursoDao getTipoRecursoDao() {
//		if (tipoRecursoDao == null) {
//			tipoRecursoDao = new TipoRecursoDaoImpl();
//		}
//		return tipoRecursoDao;
//	}

	public RecursoUsuarioDao getRecursoUsuarioDao() {
		if (recursoUsuarioDao == null) {
			recursoUsuarioDao = new RecursoUsuarioDaoImpl();
		}
		return recursoUsuarioDao;
	}

	/**
	 * Entrega objeto DAO para la extracción de reportes
	 * 
	 * @return Objeto {@link ReporteDao}
	 */
//	public ReporteDao getReporteDao() {
//		if (reporteDaoImpl == null) {
//			reporteDaoImpl = new ReporteDaoImpl();
//		}
//		return reporteDaoImpl;
//	}
	
	public CargaDao getCargaDao() {
		if (cargaDao == null) {
			cargaDao = new CargaDaoImpl();
		}
		return cargaDao;
	}

	public ColumnaDAO getColumnaDao() {
		if(columnaDao == null) {
			columnaDao = new ColumnaDAODB2();
		}
		return columnaDao;
	}
	
}
