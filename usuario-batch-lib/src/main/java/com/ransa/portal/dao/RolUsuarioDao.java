package com.ransa.portal.dao;

import java.util.Collection;

import com.ransa.portal.model.RolUsuario;

public interface RolUsuarioDao {

	public String getRolUsuario(String idUsuario, Integer idAplicacion);
	
	public Collection<RolUsuario> getRolUsuario(String idUsuario);
	
	public Collection<RolUsuario> getRolUsuarioCargaMasiva(String idUsuario);

}