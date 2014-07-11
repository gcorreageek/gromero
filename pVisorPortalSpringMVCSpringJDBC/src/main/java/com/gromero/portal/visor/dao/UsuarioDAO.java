package com.gromero.portal.visor.dao;

import java.util.List;

import com.gromero.portal.visor.domain.Usuario;

public interface UsuarioDAO {

	Usuario insertarOrActualizar(Usuario a);
	List<Usuario> buscarXid(Usuario a);
	List<Usuario> buscarXuserName(Usuario a);
	
}
