package com.ransa.portal.dao;

import java.util.Collection;

import com.ransa.portal.model.RecursoUsuario;

public interface RecursoUsuarioDao {

	public String getIdRecursoUsuarioPredeterminado(String idUsuario,
			int idTipoRecurso);
	
	public Collection<RecursoUsuario> getRecursoUsuario(String idUsuario);
	
}