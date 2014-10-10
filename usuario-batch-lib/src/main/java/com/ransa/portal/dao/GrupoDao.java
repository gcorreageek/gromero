package com.ransa.portal.dao;

import java.util.Collection;

import com.ransa.portal.model.Grupo;

public interface GrupoDao {

	public String getIdGrupo(String descripcion, String indPortal);

	public void addGrupo(String descripcion, String indPortal);

	public void delGrupo(Grupo grupo);

	public void updateGrupo(Grupo grupo);

	public Collection<Grupo> getGrupos();

	public String getGruposPorAplicacion(int idAplicacion, String indPortal,
			String idsGrupos);
	
	public Collection<Grupo> getGruposPorNegocio(String indPortal,
			String idsGrupos);
	
	public Collection<Grupo> getGruposPorUsuario(String idUsuario);
	
}