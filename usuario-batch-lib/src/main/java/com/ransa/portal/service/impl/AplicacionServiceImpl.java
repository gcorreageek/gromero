package com.ransa.portal.service.impl;

import java.util.Collection;
import java.util.List;

import com.ransa.portal.dao.AplicacionDao;
import com.ransa.portal.dao.GrupoDao;
import com.ransa.portal.dao.UtilDao;
import com.ransa.portal.dao.factory.DaoFactory;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.Aplicacion;
import com.ransa.portal.model.Estado;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Negocio;
import com.ransa.portal.model.OpcionAplicacion;
import com.ransa.portal.model.RolAplicacion;
import com.ransa.portal.service.AplicacionService;
import com.ransa.portal.util.Util;

public class AplicacionServiceImpl implements AplicacionService{

	private UtilDao utilDao = null;
	private AplicacionDao aplicacionDao = null;
	private GrupoDao grupoDao = null;
	
	public AplicacionServiceImpl(){
//		utilDao = DaoFactory.getInstance().getUtilDao();
//		aplicacionDao = DaoFactory.getInstance().getAplicacionDao();
		grupoDao = DaoFactory.getInstance().getGrupoDao();
	}
	
	public Collection<Estado> getEstadoAplicacion() {
		return utilDao.getEstadoAplicacion();
	}

	public Collection<Estado> getEstadoOpcionAplicacion() {
		return utilDao.getEstadoOpcionAplicacion();
	}

	public Collection<Estado> getEstadoRolAplicacion() {
		return utilDao.getEstadoRolAplicacion();
	}

	public Collection<Estado> getEstadoTipoRecurso() {
		return utilDao.getEstadoTipoRecurso();
	}

	public Collection<Negocio> getNegocioAplicacion() {
		return utilDao.getNegocioAplicacion();
	}
	 
	/**
	 * Retorna los grupos pertenecientes (y no) de una aplicacion
	 * 
	 * @param idAplicacion
	 * @param inPortal
	 *            Si es intranet o extranet
	 * @return Una lista de Grupos
	 */
//	public List<FilaGrupoBean> getGruposPortalAplicacion(int idAplicacion, String inPortal){		
//		return aplicacionDao.getGruposPortalAplicacion(idAplicacion, inPortal);
//	}
	
	public List<Grupo> getGruposAplicacion(int idAplicacion){
		return aplicacionDao.getGruposAplicacion(idAplicacion);
	}
	/**
	 * @return Retorna true si ocurrio un error interno.
	 */
//	public boolean updateGrupoAplicacion(int idAplicacion, List<FilaGrupoBean> grupos, String usuModif){
//		boolean isError = false;
//		FilaGrupoBean grupo = null;
//		
//		for(int i=0; i < grupos.size(); i++){
//			grupo = grupos.get(i);
//			if(grupo.isSelected()){
//				isError = aplicacionDao.setAplicacionGrupo(idAplicacion, grupo, usuModif);
//			}else{
//				isError = aplicacionDao.delAplicacionGrupos(idAplicacion, Util.parseInt(grupo.getId()), usuModif);
//			}
//		}
//		return isError;
//	}
	
//	public List<FilaAplicacionBean> buscarAplicacion(String descripcion, String estado){
//		List<Aplicacion> listAplicacion = aplicacionDao.buscarAplicacion(null, descripcion, null, estado);
//		List<FilaAplicacionBean> listFilAplicacion = new ArrayList<FilaAplicacionBean>();
//		FilaAplicacionBean filaAplicacion = null;
//		try {
//			for(int i=0; i < listAplicacion.size(); i++){
//				filaAplicacion = new FilaAplicacionBean(listAplicacion.get(i));			
//				listFilAplicacion.add(filaAplicacion);
//			}
//		} catch (Exception e) {
//			throw new EjecucionException("Error al convertir el bean Aplicacion a FilaAplicacion", e);
//		}	
//		return listFilAplicacion;
//	}
	
	public boolean delAplicacion(int idAplicacion, String usuModif){
		return aplicacionDao.delAplicacion(idAplicacion, usuModif);
	}
	
//	public FilaAplicacionBean getAplicacion(int idAplicacion){
//		Aplicacion aplicacion = aplicacionDao.getAplicacion(idAplicacion, UtilApp.NULL_STR, UtilApp.NULL_STR);
//		FilaAplicacionBean filaAplicacion = new FilaAplicacionBean(aplicacion);
//
//		return filaAplicacion;
//	}
	
//	public Collection<OpcionAplicacion> getOpcionesAplicacion(int idAplicacion){		
//		return aplicacionDao.getOpcionesAplicacion(UtilApp.NULL_INT, idAplicacion);
//	}
	
	public OpcionAplicacion getOpcionAplicacion(int idOpcion, int idAplicacion){
		return aplicacionDao.getOpcionAplicacion(idOpcion, idAplicacion);
	}
	public boolean delOpcionAplicacion(int idOpcion, int idAplicacion, String usuModif){
		return aplicacionDao.delOpcionAplicacion(idOpcion, idAplicacion, usuModif);
	}
	
	public boolean delRolAplicacion(int idRol, int idAplicacion, String usrModif) {
		return aplicacionDao.delRolAplicacion(idRol, idAplicacion, usrModif);
	}
	
//	public Collection<RolAplicacion> getRolesAplicacion(int idAplicacion) {
//		return aplicacionDao.getRolesAplicacion(UtilApp.NULL_INT, idAplicacion);
//	}
	
//	public Collection<FilaRolAplicacionBean> getDetalleRolesAplicacion(int idAplicacion){
//		return aplicacionDao.getDetalleRolesAplicacion(idAplicacion); 
//	}
	
	public int setAplicacion(Aplicacion aplicacion, String usuCreacion){
		return aplicacionDao.setAplicacion(aplicacion, usuCreacion);
	}
	
	public boolean updateAplicacion(Aplicacion aplicacion , String usuModif){
		return aplicacionDao.updateAplicacion(aplicacion, usuModif);
	}
	
	public int setOpcionAplicacion(OpcionAplicacion opcion, int idAplicacion, String usuCreacion){
		return aplicacionDao.setOpcionAplicacion(opcion, idAplicacion, usuCreacion);
	}
	
	public boolean updateOpcionAplicacion(OpcionAplicacion opcion, int idAplicacion, String usuModif){
		return aplicacionDao.updateOpcionAplicacion(opcion, idAplicacion, usuModif);
	}
	//----->>
//	public boolean updateRolXOpciones(int idRol, int idAplicacion, List<FilaOpcionAplicacionBean> opciones, String usuario){
//		FilaOpcionAplicacionBean opcion = null;
//		boolean isError = false; 
//		
//        for(int i=0; i < opciones.size(); i++){
//        	opcion = opciones.get(i);   
//        	if(opcion.isSelected() && opcion.getPermiso()!=null && !opcion.getPermiso().equals(""))//AGREGAR
//        		isError = this.setRolXOpciones(idRol, idAplicacion, opcion, usuario);
//        	else 
//        	    //ELIMINAR
//        		isError = this.delRolXOpciones(idRol, idAplicacion, Util.parseInt(opcion.getId()), usuario);
//        }
//        return isError;
//	}
	//<<------
//	public boolean setRolXOpciones(int idRol, int idAplicacion, FilaOpcionAplicacionBean opcion, String usuCrea){
//		return aplicacionDao.setRolXOpciones(idRol, idAplicacion, opcion, usuCrea);
//	}
	
	public boolean delRolXOpciones(int idRol, int idAplicacion, int idOpcion, String usuModif){
		return aplicacionDao.delRolXOpciones(idRol, idAplicacion, idOpcion, usuModif);
	} 
	
//	public List<FilaOpcionAplicacionBean> getRolXOpciones( int idRol, int idAplicacion){
//		return aplicacionDao.getRolXOpciones(idRol, idAplicacion);
//	}
	
	public int setRolAplicacion(RolAplicacion rol, int idAplicacion, String usuCreacion){
		return aplicacionDao.setRolAplicacion(rol, idAplicacion, usuCreacion);
	}
	
	public boolean updateRolAplicacion(RolAplicacion rol, int idAplicacion, String usuModif){
		return aplicacionDao.updateRolAplicacion(rol, idAplicacion, usuModif);
	}
	
	public RolAplicacion getRolAplicacion(int idRol, int idAplicacion){
		return aplicacionDao.getRolAplicacion(idRol, idAplicacion);
	}
	/**
	 * @see AplicacionService#delGrupo(Grupo) 
	 */
	public void delGrupo(Grupo grupo) {
		grupoDao.delGrupo(grupo);
    }
	/**
	 * @see AplicacionService#getGrupos() 
	 */
	public Collection<Grupo> getGrupos() {
	    return grupoDao.getGrupos();
    }
	/**
	 * @see AplicacionService#updateGrupo(Grupo)
	 */
	public void updateGrupo(Grupo grupo) {
		grupoDao.updateGrupo(grupo);
    }
	
	public int getIdAplicacionLogin() {
		int idAplicacionLogin = Util.parseInt(utilDao.getParametroConfiguracion(13));
		if (idAplicacionLogin <= 0) {
			throw new EjecucionException("idAplicacionLogin no valido");
		}
		return idAplicacionLogin;
	}

	public Collection<OpcionAplicacion> getOpcionesAplicacion(int idAplicacion) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<RolAplicacion> getRolesAplicacion(int idAplicacion) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
