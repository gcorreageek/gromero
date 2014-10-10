package com.ransa.portal.service;

import java.util.Collection;

import com.ransa.portal.model.Estado;
import com.ransa.portal.model.TipoRecurso;

public interface TipoRecursoService {
	
	Collection<Estado> getEstadoTipoRecurso();
	
//	List<FilaTipoRecursoBean> buscarTipoRecurso(String nombre, String idEstado);
	
	boolean delTiporecurso(int idTipoRecurso, String usuModif);
	
	boolean updateTipoRecurso(TipoRecurso tipoRecurso, String usuModificacion);
	
	int setTipoRecurso(TipoRecurso tipoRecurso, String usuCreacion);
	
	TipoRecurso getTipoRecurso(int idTipoRecurso);
}
