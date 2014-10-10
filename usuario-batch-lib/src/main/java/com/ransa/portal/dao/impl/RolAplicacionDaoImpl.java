package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.ransa.portal.dao.RolAplicacionDao;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.RolAplicacion;

public class RolAplicacionDaoImpl extends BaseDao implements RolAplicacionDao {

	public Collection<RolAplicacion> getRolesAplicacion(Integer idAplicacion) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();			
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_ROLES_X_APLICACION(?)");
			cstm.setInt(1, idAplicacion);
			rs = cstm.executeQuery();
			Collection<RolAplicacion> rolesAplicacion = new ArrayList<RolAplicacion>();
			while (rs.next()) {
				RolAplicacion rolAplicacion = new RolAplicacion();
				rolAplicacion.setId(rs.getString(1));
				rolAplicacion.setNombre(rs.getString(2));
				rolAplicacion.setIdAplicacion("" + idAplicacion);
				rolesAplicacion.add(rolAplicacion);
			}
			return rolesAplicacion;
		} catch (SQLException e) {
			throw new EjecucionException("Error el ejecutar el procedure GET_ROLES_X_APLICACION", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
}
