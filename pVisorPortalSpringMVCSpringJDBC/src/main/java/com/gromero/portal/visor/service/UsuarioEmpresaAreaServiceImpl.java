package com.gromero.portal.visor.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gromero.portal.visor.dao.UsuarioEmpresaAreaDAO;
import com.gromero.portal.visor.domain.UsuarioEmpresaArea;
@Service
public class UsuarioEmpresaAreaServiceImpl implements UsuarioEmpresaAreaService{
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	UsuarioEmpresaAreaDAO usuarioEmpresaAreaDAO;
	
	
//	@Override
//	public UsuarioEmpresaArea insertar(UsuarioEmpresaArea a) { 
//		a.setIdUsuario(null);
//		return usuarioDAO.insertarOrActualizar(a);
//	}
//
//	@Override
//	public UsuarioEmpresaArea actualizar(UsuarioEmpresaArea a) { 
//		return usuarioDAO.insertarOrActualizar(a);
//	}
//
//	@Override
//	public List<UsuarioEmpresaArea> listar() { 
//		UsuarioEmpresaArea a = new UsuarioEmpresaArea();
//		a.setUserName("");
//		return usuarioDAO.buscarXuserName(a);
//	}

	@Override
	public List<UsuarioEmpresaArea> buscarXidUsuario(UsuarioEmpresaArea a) {
		return usuarioEmpresaAreaDAO.buscarXidUsuario(a);
	}

//	@Override
//	public List<UsuarioEmpresaArea> buscarXuserName(UsuarioEmpresaArea a) {
//		return usuarioDAO.buscarXuserName(a);
//	}

}
