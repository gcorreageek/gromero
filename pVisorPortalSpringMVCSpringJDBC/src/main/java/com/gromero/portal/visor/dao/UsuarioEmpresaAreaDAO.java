package com.gromero.portal.visor.dao;

import java.util.List;

import com.gromero.portal.visor.domain.Usuario;
import com.gromero.portal.visor.domain.UsuarioEmpresaArea;

public interface UsuarioEmpresaAreaDAO {

//	Usuario insertarOrActualizar(Usuario a);
	List<UsuarioEmpresaArea> buscarXidUsuario(UsuarioEmpresaArea a);
//	List<Usuario> buscarXuserName(Usuario a);
	
}
