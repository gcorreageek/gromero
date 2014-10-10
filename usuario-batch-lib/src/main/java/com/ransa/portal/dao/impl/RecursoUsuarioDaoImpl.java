package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.ransa.portal.dao.RecursoUsuarioDao;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.RecursoUsuario;
import com.ransa.portal.util.Util;

public class RecursoUsuarioDaoImpl extends BaseDao implements RecursoUsuarioDao {
	
	public String getIdRecursoUsuarioPredeterminado(String idUsuario, int idTipoRecurso) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();			
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_RECURSOUSUARIO_PREDETERMINADO(?, ?)");
			cstm.setString(1, idUsuario);
			cstm.setInt(2, idTipoRecurso);
			rs = cstm.executeQuery();
			String idGrupo = Util.STRING_VACIO;
			if (rs.next()){
				idGrupo = rs.getString(1);
				if (rs.next()) {
					throw new EjecucionException("Existe mas de un valor retornado por GET_RECURSOUSUARIO_PREDETERMINADO", new Exception());
				}
			}
			return idGrupo;
		} catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el procedure GET_RECURSOUSUARIO_PREDETERMINADO", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
	public Collection<RecursoUsuario> getRecursoUsuario(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_RECURSOUSUARIO(?)");
			cstm.setString(1, idUsuario);
			rs = cstm.executeQuery();
			Collection<RecursoUsuario> recursosUsuario = new ArrayList<RecursoUsuario>();
			RecursoUsuario recursoUsuario = null;
			while (rs.next()) {
				recursoUsuario = new RecursoUsuario();
				recursoUsuario.setIdRecurso(rs.getString("IDRECURSO"));
				recursoUsuario.setIdTipoRecurso(rs.getInt("IDTIPORECURSO"));
				recursoUsuario.setFavorito(rs.getInt("FAVORITO"));
				recursosUsuario.add(recursoUsuario);
			}
			return recursosUsuario;
		} catch (SQLException e) {
			throw new EjecucionException("Error el ejecutar el procedure GET_RECURSOUSUARIO(?)", e);
		} finally {
			close(conn);
			close(cstm);
			close(rs);
		}
	}
	
}
