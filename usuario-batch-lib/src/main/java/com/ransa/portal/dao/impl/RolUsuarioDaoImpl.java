package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.ransa.portal.dao.RolUsuarioDao;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.RolUsuario;
import com.ransa.portal.util.Util;

public class RolUsuarioDaoImpl extends BaseDao implements RolUsuarioDao {

	public String getRolUsuario(String idUsuario, Integer idAplicacion) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_ROL_X_USUARIO(?, ?)");
			cstm.setString(1, idUsuario);
			cstm.setInt(2, idAplicacion);
			rs = cstm.executeQuery();
			String idRol = Util.STRING_VACIO;
			if (rs.next()) {
				idRol = rs.getString(1);
			}
			return idRol;
		} catch (SQLException e) {
			throw new EjecucionException("Error el ejecutar el procedure GET_ROL_X_USUARIO", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
	public Collection<RolUsuario> getRolUsuario(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_ROLUSUARIO(?)");
			cstm.setString(1, idUsuario);
			rs = cstm.executeQuery();
			Collection<RolUsuario> rolesUsuario = new ArrayList<RolUsuario>();
			RolUsuario rolUsuario = null;
			while (rs.next()) {
				rolUsuario = new RolUsuario();
				rolUsuario.setIdRol(rs.getInt("IDROL"));
				rolUsuario.setIdAplicacion(rs.getString("IDAPLICACION"));
				rolesUsuario.add(rolUsuario);
			}
			return rolesUsuario;
		} catch (SQLException e) {
			throw new EjecucionException("Error el ejecutar el procedure GET_ROLUSUARIO(?)", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
	public Collection<RolUsuario> getRolUsuarioCargaMasiva(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_ROLUSUARIO_CARGAMASIVA(?)");
			cstm.setString(1, idUsuario);
			rs = cstm.executeQuery();
			Collection<RolUsuario> rolesUsuario = new ArrayList<RolUsuario>();
			RolUsuario rolUsuario = null;
			while (rs.next()) {
				rolUsuario = new RolUsuario();
				rolUsuario.setIdRol(rs.getInt("IDROL"));
				rolUsuario.setIdAplicacion(rs.getString("IDAPLICACION"));
				rolesUsuario.add(rolUsuario);
			}
			return rolesUsuario;
		} catch (SQLException e) {
			throw new EjecucionException("Error el ejecutar el procedure GET_ROLUSUARIO_CARGAMASIVA(?)", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
}
