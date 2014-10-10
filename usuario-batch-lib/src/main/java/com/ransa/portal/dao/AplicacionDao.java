package com.ransa.portal.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;

import com.ransa.portal.model.Aplicacion;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.OpcionAplicacion;
import com.ransa.portal.model.RolAplicacion;
//import com.ransa.portal.portlet.administracion.aplicacion.util.FilaGrupoBean;
//import com.ransa.portal.portlet.administracion.aplicacion.util.FilaOpcionAplicacionBean;
//import com.ransa.portal.portlet.administracion.aplicacion.util.FilaRolAplicacionBean;

public interface AplicacionDao {

	public RolAplicacion getRolAplicacion(int idRol, int idAplicacion);

//	public Collection<FilaRolAplicacionBean> getDetalleRolesAplicacion(
//			int idAplicacion);

	/**
	 * Elimina los roles de uan aplicacion
	 * @param idRol
	 * @param idAplicacion
	 */

	public Collection<RolAplicacion> getRolesAplicacion(int idRol,
			int idAplicacion);

	public boolean delRolAplicacion(int idRol, int idAplicacion, String usrModif);

	/**
	 * Elimina un opcion de una aplicacion
	 * @param idAplicacion
	 * @param idOpcion
	 * @throws SQLException 
	 * @throws NamingException 
	 */
	public boolean delOpcionAplicacion(int idOpcion, int idAplicacion,
			String usuModif);

	public OpcionAplicacion getOpcionAplicacion(int idOpcion, int idAplicacion);

	/**
	 * Recupera la opciones por idAplicacion y idOpcion
	 * @param idAplicacion 
	 * @param idOpcion 
	 * @return Collection  -> ArrayList
	 * @throws SQLException 
	 * @throws SQLException 
	 * @throws NamingException 
	 */
	public Collection<OpcionAplicacion> getOpcionesAplicacion(Integer idOpcion,
			Integer idAplicacion);

	/**
	 * Inserta un nuevo rol de aplicacion
	 * @param rol
	 * @param idAplicacion
	 * @param usuCreacion
	 * @return PK del nuevo rol creado
	 */
	public int setRolAplicacion(RolAplicacion rol, int idAplicacion,
			String usuCreacion);

	/**
	 * Actualiza un rol de aplicacion
	 * @param rol
	 * @param idAplicacion
	 * @param usuModif Usuario que modifica el rol
	 * @return true si hubo error
	 */
	public boolean updateRolAplicacion(RolAplicacion rol, int idAplicacion,
			String usuModif);

	/**
	 * Inserta una nueva OPCION DE APLICACION
	 * @param opcion Bean OpcionAplicacion
	 * @param idAplicacion id de Aplicacion a la que esta relacionada
	 * @param usuCreacion Usuario que crea la nueva opcion
	 * @return Id de la nueva OPCION DE APLICACION
	 * @see OpcionAplicacion
	 */
	public int setOpcionAplicacion(OpcionAplicacion opcion, int idAplicacion,
			String usuCreacion);

	/**
	 * Actualiza una opcion 
	 * @param opcion
	 * @param idAplicacion
	 * @param usuModif Usuario de modificacion
	 * @return true si hubo error
	 */
	public boolean updateOpcionAplicacion(OpcionAplicacion opcion,
			int idAplicacion, String usuModif);

	/**
	 * Elimina una aplicacion
	 * @param idAplicacion
	 * @return true si ocurrio un error
	 */
	public boolean delAplicacion(int idAplicacion, String usuModif);

	/**
	 * Busca aplicaciones
	 * @param idAplicacion
	 * @param nombre
	 * @param descripcion
	 * @param estado
	 * @return retorna una coleccion de objectos Aplicacion
	 */
	public List<Aplicacion> buscarAplicacion(Integer idAplicacion,
			String nombre, String descripcion, String estado);

	/**
	 * Busca un aplicacion por idAplicacion, nombre, descripcion
	 * @param idAplicacion
	 * @param nombre
	 * @param descripcion
	 * @throws SQLException 
	 * @throws NamingException 
	 */
	public Aplicacion getAplicacion(int idAplicacion, String nombre,
			String descripcion);

	/** 
	 * @param idRol
	 * @param idAplicacion
	 * @return Retorna una coleccion de Opciones segun IDROL IDAPLICACION
	 */
//	public List<FilaOpcionAplicacionBean> getRolXOpciones(int idRol,
//			int idAplicacion);

	/**
	 * Inserta opciones de un rol
	 * @param idRol
	 * @param idAplicacion
	 * @param usuCrea
	 * @param opciones Lista de OpcionesAplicacion
	 * @return true si hubo error
	 */
//	public boolean setRolXOpciones(int idRol, int idAplicacion,
//			FilaOpcionAplicacionBean opcion, String usuCrea);

	/**
	 * Elimina una opcion de un rol. 
	 * @param idRol
	 * @param idAplicacion
	 * @param idOpcion Si idOpcion se especifica -1, se eliminan todos las opciones pertenecientes al ROL
	 * @return true si hubo error
	 */
	public boolean delRolXOpciones(int idRol, int idAplicacion, int idOpcion,
			String usuModif);

	/**
	 * Actualiza una aplicacion
	 * 
	 * @param aplicacion
	 *            Bean Aplicacion
	 * @param usuModif
	 *            Usuario de modificacion
	 * @return true si hubo error
	 */
	public boolean updateAplicacion(Aplicacion aplicacion, String usuModif);

	/**
	 * Inserta una nueva aplicacion. Los errores se manejan por excepciones
	 * 
	 * @param aplicacion
	 * @return Retorna el pk de la nueva aplicacion o -1 si hubi error
	 * @throws SQLException 
	 * @throws NamingException 
	 */
	public int setAplicacion(Aplicacion aplicacion, String usuCreacion);

	public List<Grupo> getGruposAplicacion(int idAplicacion);

	/**
	 * Retorna los grupos del portal pertenecientes (y no) a una aplicacion 
	 * 
	 * @param idAplicacion
	 * @param inPortal
	 *            Si es intranet o extranet
	 * @return Una Coleccion de FilaGrupo
	 */
//	public List<FilaGrupoBean> getGruposPortalAplicacion(int idAplicacion,
//			String inPortal);

	/**
	 * Elimina los grupos de una aplicacion
	 * 
	 * @param idAplicacion
	 * @param idGrupo
	 *            Si se pasa -1 invalida este campo, eliminando la aplicacion de
	 *            todos los grupos a que pertenece.
	 * @return true si ocurrio un error
	 */
	public boolean delAplicacionGrupos(int idAplicacion, int idGrupo,
			String usuModif);

	/**
	 * 
	 * @param idAplicacion
	 * @param grupo
	 * @return true si ocurrio un error.
	 */
	public boolean setAplicacionGrupo(int idAplicacion, Grupo grupo,
			String usuario);

	public Collection<Aplicacion> getAplicacionesPorGrupos(String idsGrupos);

}