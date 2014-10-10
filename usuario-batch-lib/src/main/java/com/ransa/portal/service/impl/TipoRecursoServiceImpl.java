package com.ransa.portal.service.impl;

import java.util.Collection;

import com.ransa.portal.dao.TipoRecursoDao;
import com.ransa.portal.dao.UtilDao;
import com.ransa.portal.model.Estado;
import com.ransa.portal.model.TipoRecurso;
import com.ransa.portal.service.TipoRecursoService;

public class TipoRecursoServiceImpl implements TipoRecursoService{

	UtilDao utilDao = null;
	private TipoRecursoDao daoTipoRecurso = null;
	
	public TipoRecursoServiceImpl(){
//		utilDao = DaoFactory.getInstance().getUtilDao();
//		daoTipoRecurso = DaoFactory.getInstance().getTipoRecursoDao();
	}
	
	public Collection<Estado> getEstadoTipoRecurso() {
		return utilDao.getEstadoTipoRecurso();
	}
	
//	public List<FilaTipoRecursoBean> buscarTipoRecurso(String nombre, String idEstado){
//		List<TipoRecurso> listTipoRecurso = daoTipoRecurso.buscarTipoRecurso(-1, nombre, idEstado);
//		List<FilaTipoRecursoBean> listFilaTipoRecurso = new ArrayList<FilaTipoRecursoBean>(); 
//		
//		FilaTipoRecursoBean filaTipoRecurso = null;
//		
//		try {
//			for(int i=0; i<listTipoRecurso.size(); i++){
//				filaTipoRecurso = new FilaTipoRecursoBean(listTipoRecurso.get(i));			
//				listFilaTipoRecurso.add(filaTipoRecurso);
//			}
//		} catch (Exception e) {
//			throw new EjecucionException("Error al convertir el bean TipoRecurso a FilaTipoRecurso", e);
//		}
//		
//		return listFilaTipoRecurso;
//	}
	
	public boolean delTiporecurso(int idTipoRecurso, String usuModif){
		return daoTipoRecurso.delTiporecurso(idTipoRecurso, usuModif);
	}
	
	public boolean updateTipoRecurso(TipoRecurso tipoRecurso, String usuModificacion){
		return daoTipoRecurso.updateTipoRecurso(tipoRecurso, usuModificacion);
	}
	
	public int setTipoRecurso(TipoRecurso tipoRecurso, String usuCreacion){
		return daoTipoRecurso.setTipoRecurso(tipoRecurso, usuCreacion);
	}
	
	public TipoRecurso getTipoRecurso(int idTipoRecurso){
		return daoTipoRecurso.getTipoRecurso(idTipoRecurso);
	}
}
