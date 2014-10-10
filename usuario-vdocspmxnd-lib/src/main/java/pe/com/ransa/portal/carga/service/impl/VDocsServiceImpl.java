package pe.com.ransa.portal.carga.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.ransa.portal.carga.dao.VDocsDao;
import pe.com.ransa.portal.carga.dto.EmpresaAreaUsuario;
import pe.com.ransa.portal.carga.dto.EmpresaUsuario;
import pe.com.ransa.portal.carga.dto.TipoDocEmpresaAreaUsuario;
import pe.com.ransa.portal.carga.service.VDocsService;
@Service
public class VDocsServiceImpl implements VDocsService {
	private static Logger logger = Logger.getLogger(ClienteServiceImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();

	@Autowired
	private VDocsDao vDocsDao;
	
	public EmpresaUsuario registraEmpresaUsuario(EmpresaUsuario empresaUsuario) {
		try {
			empresaUsuario = vDocsDao.ingresaEmpresaUsuario(empresaUsuario);
		} catch (Exception e) { 
			logger.error("[registraEmpresaUsuario]",e);
		}
		return empresaUsuario;
	}

	public EmpresaAreaUsuario registraEmpresaAreaUsuario( EmpresaAreaUsuario empresaAreaUsuario) {
		try {
			empresaAreaUsuario = vDocsDao.ingresaEmpresaAreaUsuario(empresaAreaUsuario);
		} catch (Exception e) {
			logger.error("[registraEmpresaAreaUsuario]",e);
		}
		return empresaAreaUsuario;
	}

	public TipoDocEmpresaAreaUsuario registraTipoDocEmpresaAreaUsuario( TipoDocEmpresaAreaUsuario tipoDocEmpresaAreaUsuario) {
		try {
			tipoDocEmpresaAreaUsuario = vDocsDao.ingresaTipoDocEmpresaAreaUsuario(tipoDocEmpresaAreaUsuario);
		} catch (Exception e) {
			logger.error("[registraTipoDocEmpresaAreaUsuario]",e);
		}
		return tipoDocEmpresaAreaUsuario;
	}

}
