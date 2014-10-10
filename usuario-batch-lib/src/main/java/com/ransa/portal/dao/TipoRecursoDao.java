package com.ransa.portal.dao;

import java.util.Collection;
import java.util.List;

import com.ransa.portal.model.TipoRecurso;

public interface TipoRecursoDao {

	/**
	 * Actualiza un tipo recurso segun idRecurso
	 * 
	 * @param idTipoRecurso
	 * @param tipoRecurso
	 * @param usuModificacion
	 * @return true si hubo error.
	 */
	public boolean updateTipoRecurso(TipoRecurso tipoRecurso,
			String usuModificacion);

	/**
	 * Agrega un nuevo tipo recurso
	 * 
	 * @param tipoRecurso
	 * @param usuCreacion
	 * @return id del Tipo Recurso creado.
	 */
	public int setTipoRecurso(TipoRecurso tipoRecurso, String usuCreacion);

	public boolean delTiporecurso(int idTipoRecurso, String usuModif);

	/**
	 * Busca en TIPORECURSO por idRecurso y nombre
	 * 
	 * @param idTipoRecurso
	 * @param nombre
	 * @return retorna una coleccion de TipoRecurso
	 */
	public List<TipoRecurso> buscarTipoRecurso(int idTipoRecurso,
			String nombre, String idEstado);

	public TipoRecurso getTipoRecurso(int idTipoRecurso);

	public Collection<TipoRecurso> getTiposRecursos();
	
	public Collection<TipoRecurso> getTiposRecursos(String idAplicaciones);
	
	public Collection<TipoRecurso> getTiposRecursosPorUsuario(String idUsuario);
	
	public boolean updatePreferencias(String idUsuario, String tipos_recurso[], String recursos[]);
	
	public Collection<TipoRecurso> getTiposRecursosPorGrupos(String idsGrupos);
	
	public String[] getIdsTiposRecursosPorAplicacion(int idAplicacion);
	
	public void updateTiposRecursosPorAplicacion(int idAplicacion, String idsTiposRecursos);
	
}