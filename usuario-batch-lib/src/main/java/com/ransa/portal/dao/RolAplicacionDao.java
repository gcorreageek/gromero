package com.ransa.portal.dao;

import java.util.Collection;

import com.ransa.portal.model.RolAplicacion;

public interface RolAplicacionDao {

	public Collection<RolAplicacion> getRolesAplicacion(Integer idAplicacion);

}