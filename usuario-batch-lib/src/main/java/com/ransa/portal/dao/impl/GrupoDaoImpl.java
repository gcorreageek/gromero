package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ransa.portal.dao.GrupoDao;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.util.Util;

public class GrupoDaoImpl extends BaseDao implements GrupoDao {
		
	private static Logger logger = Logger.getLogger(GrupoDaoImpl.class);
			
	public String getIdGrupo(String descripcion, String indPortal) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_GRUPO_X_DESCRIPCION(?, ?)");
			cstm.setString(1, descripcion);
			cstm.setString(2, indPortal);
			rs = cstm.executeQuery();
			String idGrupo = Util.STRING_VACIO;
			if (rs.next()) {
				idGrupo = rs.getString(1);
				if (rs.next()) {
					throw new EjecucionException(String.format("Existe mas de un valor retornado por GET_GRUPO_X_DESCRIPCION para el grupo '%s'", descripcion), new Exception());
				}
			}
			return idGrupo;
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure GET_GRUPO_X_DESCRIPCION", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}

	public void addGrupo(String descripcion, String indPortal) {
		Connection conn = null;
		CallableStatement cstm = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".ADD_GRUPO(?, ?)");
			cstm.setString(1, descripcion);
			cstm.setString(2, indPortal);
			cstm.executeUpdate();
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure ADD_GRUPO", e);
		} finally {
			close(conn);
			close(cstm);
		}
	}

	public void delGrupo(Grupo grupo) {
		Connection conn = null;
		CallableStatement cstm = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".DEL_GRUPO(?)");
			cstm.setInt(1, Integer.valueOf(grupo.getId()));

			cstm.executeUpdate();
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure DEL_GRUPO", e);
		} finally {
			close(conn);
			close(cstm);
		}
	}

	public void updateGrupo(Grupo grupo) {
		Connection conn = null;
		CallableStatement cstm = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".UPD_GRUPO(?,?,?)");
			cstm.setInt(1, Integer.valueOf(grupo.getId()));

			if (grupo.getDescripcion() != null) {
				cstm.setString(2, grupo.getDescripcion());
			} else {
				cstm.setNull(2, Types.VARCHAR);
			}

			if (grupo.getIndPortal() != null) {
				cstm.setString(3, grupo.getIndPortal());
			} else {
				cstm.setNull(3, Types.CHAR);
			}
			cstm.executeUpdate();
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure UPD_GRUPO(?,?,?)", e);
		} finally {
			close(conn);
			close(cstm);
		}
	}

	public Collection<Grupo> getGrupos() {
		logger.info("getGrupos");
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		Collection<Grupo> grupos = new ArrayList<Grupo>();
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_GRUPOS()");
			rs = cstm.executeQuery();
			while (rs.next()) {
				Grupo grupo = new Grupo();
				grupo.setId(rs.getString(1));
				grupo.setDescripcion(rs.getString(2));
				grupo.setIndPortal(rs.getString(3));
				grupos.add(grupo);
			}
			return grupos;
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure GET_GRUPOS", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}

	public String getGruposPorAplicacion(int idAplicacion, String indPortal, String idsGrupos) {
		logger.info("getGruposPorAplicacion");
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_GRUPOS_X_APLICACION(?, ?, ?)");
			cstm.setInt(1, idAplicacion);
			cstm.setString(2, indPortal);
			cstm.setString(3, idsGrupos);
			rs = cstm.executeQuery();
			String nombresGrupos = Util.STRING_VACIO;
			if (rs.next()) {
				nombresGrupos = rs.getString(1);
			}
			return nombresGrupos;
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure GET_GRUPOS_X_APLICACION", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
	public Collection<Grupo> getGruposPorNegocio(String indPortal, String idsGrupos) {
		logger.info("getGruposPorNegocio");
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			Collection<Grupo> grupos = new ArrayList<Grupo>();
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_GRUPOS_X_NEGOCIO(?, ?)");			
			cstm.setString(1, indPortal);
			cstm.setString(2, idsGrupos);
			rs = cstm.executeQuery();			
			while (rs.next()) {
				Grupo grupo = new Grupo();
				grupo.setId(rs.getString(1));
				grupo.setDescripcion((rs.getString(2)));
				grupos.add(grupo);
			}
			return grupos;
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure GET_GRUPOS_X_NEGOCIO", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
	public Collection<Grupo> getGruposPorUsuario(String idUsuario) {
		logger.info("getGruposPorUsuario");
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		Collection<Grupo> grupos = new ArrayList<Grupo>();
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_GRUPOUSUARIO(?)");
			cstm.setString(1, idUsuario);
			rs = cstm.executeQuery();
			while (rs.next()) {
				Grupo grupo = new Grupo();
				grupo.setDescripcion(rs.getString("DESCRIPCIONGRUPO"));
				grupos.add(grupo);
			}
			return grupos;
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure GET_GRUPOUSUARIO(?)", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
}
