package com.gromero.portal.visor.service;

import java.util.List;

import com.gromero.portal.visor.domain.Usuario;

public interface UsuarioService {

	Usuario insertar(Usuario a);
	Usuario actualizar(Usuario a);
	List<Usuario> listar();
	List<Usuario> buscarXidUsuario(Usuario a);
	List<Usuario> buscarXuserName(Usuario a);
	 
}
