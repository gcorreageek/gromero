package com.ransa.portal.service;

import java.util.Collection;
import java.util.List;

import com.ransa.portal.model.Aplicacion;
import com.ransa.portal.model.Estado;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Negocio;
import com.ransa.portal.model.OpcionAplicacion;
import com.ransa.portal.model.RolAplicacion;

public interface AplicacionService {
	
	Collection<Estado> getEstadoAplicacion();
	
	Collection<Negocio> getNegocioAplicacion();

	Collection<Estado> getEstadoOpcionAplicacion();
	
	Collection<Estado> getEstadoRolAplicacion();
	
	Collection<Estado> getEstadoTipoRecurso();
	
//	List<FilaAplicacionBean> buscarAplicacion(String descripcion, String estado);
	
	boolean delAplicacion(int idAplicacion, String usuModif);
	
//	FilaAplicacionBean getAplicacion(int idAplicacion);
	
	Collection<OpcionAplicacion> getOpcionesAplicacion(int idAplicacion);
	
	OpcionAplicacion getOpcionAplicacion(int idOpcion, int idAplicacion);
	
//	List<FilaGrupoBean> getGruposPortalAplicacion(int idAplicacion, String inPortal);
	
	List<Grupo> getGruposAplicacion(int idAplicacion);
	
//	boolean updateGrupoAplicacion(int idAplicacion, List<FilaGrupoBean> grupos, String usuModif);
	
	boolean delOpcionAplicacion(int idOpcion, int idAplicacion, String usuModif);
	
	boolean delRolAplicacion(int idRol, int idAplicacion, String usrModif);
	
	Collection<RolAplicacion> getRolesAplicacion(int idAplicacion);
	
//	Collection<FilaRolAplicacionBean> getDetalleRolesAplicacion(int idAplicacion);
	
	int setAplicacion(Aplicacion aplicacion, String usuCreacion);
	
	boolean updateAplicacion(Aplicacion aplicacion , String usuModif);
	
	int setOpcionAplicacion(OpcionAplicacion opcion, int idAplicacion, String usuCreacion);
	
	boolean updateOpcionAplicacion(OpcionAplicacion opcion, int idAplicacion, String usuModif);
	
//	boolean setRolXOpciones(int idRol, int idAplicacion, FilaOpcionAplicacionBean opcion, String usuCrea);
	
	boolean delRolXOpciones(int idRol, int idAplicacion, int idOpcion, String usuModif);
	
//	List<FilaOpcionAplicacionBean> getRolXOpciones( int idRol, int idAplicacion);
	
//	boolean updateRolXOpciones(int idRol, int idAplicacion, List<FilaOpcionAplicacionBean> opciones, String usuCrea);
	
	int setRolAplicacion(RolAplicacion rol, int idAplicacion, String usuCreacion);
	
	boolean updateRolAplicacion(RolAplicacion rol, int idAplicacion, String usuModif);
	
	RolAplicacion getRolAplicacion(int idRol, int idAplicacion);
	
	/**
	 * Elimina un grupo de las tablas del esquema
	 */
	public void delGrupo(Grupo grupo);
	
	/**
	 * Recupera los grupos del esquema 
	 */
	public Collection<Grupo> getGrupos();

	/**
	 * Actualiza la informacion de un grupo
	 */
	public void updateGrupo(Grupo grupo);
	
	public int getIdAplicacionLogin();
	
}
