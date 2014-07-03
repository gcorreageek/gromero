package com.gromero.portal.visor.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gromero.portal.visor.dao.UsuarioDAO;
import com.gromero.portal.visor.domain.Usuario;
@Service
public class UsuarioServiceImpl implements UsuarioService{
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	UsuarioDAO usuarioDAO;
	
	
	@Override
	public Usuario insertar(Usuario a) { 
		a.setIdUsuario(null);
		return usuarioDAO.insertarOrActualizar(a);
	}

	@Override
	public Usuario actualizar(Usuario a) { 
		return usuarioDAO.insertarOrActualizar(a);
	}

	@Override
	public List<Usuario> listar() { 
		Usuario a = new Usuario();
		a.setUserName("");
		return usuarioDAO.buscarXuserName(a);
	}

	@Override
	public List<Usuario> buscarXidUsuario(Usuario a) {
		return usuarioDAO.buscarXid(a);
	}

	@Override
	public List<Usuario> buscarXuserName(Usuario a) {
		return usuarioDAO.buscarXuserName(a);
	}

}
