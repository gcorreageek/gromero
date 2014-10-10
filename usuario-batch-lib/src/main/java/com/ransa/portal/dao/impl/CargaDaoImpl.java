package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ransa.portal.dao.CargaDao;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.Carga;

public class CargaDaoImpl extends BaseDao implements CargaDao {
	
	private static Logger logger = Logger.getLogger(CargaDaoImpl.class);
	
	public int registrarCarga(String idUsuarios, String idUsuarioCreador, String indPortal) {
		logger.debug("registrarCargaUsuarios");
		CallableStatement statement = null;	
		try {
			getConnection();
			statement = connection.prepareCall("CALL " + SCHEMA_PORTAL + ".ADD_CARGA(?, ?, ?, ?)");
			statement.setString(1, idUsuarios);			
			statement.setString(2, idUsuarioCreador);
			statement.setString(3, indPortal);
			statement.registerOutParameter(4, Types.INTEGER);
			statement.executeUpdate();
			int idCarga = statement.getInt(4);
			return idCarga;
		} catch (Exception e) {
			throw new EjecucionException("no se pudo ejecutar correctamente ADD_CARGA(?, ?, ?, ?)", e);
		} finally {
			close(statement);
			close(connection);
		}
	}
	
	public void eliminarCarga(int idCarga, String idUsuarioModificador) {
		logger.debug("eliminarCarga");
		CallableStatement statement = null;	
		try {
			getConnection();
			statement = connection.prepareCall("CALL " + SCHEMA_PORTAL + ".DEL_CARGA(?, ?)");
			statement.setInt(1, idCarga);			
			statement.setString(2, idUsuarioModificador);
			statement.executeUpdate();			
		} catch (Exception e) {
			throw new EjecucionException("no se pudo ejecutar correctamente DEL_CARGA(?, ?)", e);
		} finally {
			close(statement);
			close(connection);
		}
	}
	
	public Collection<String> getIdUsuariosCargados(int idCarga) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;	
		Collection<String> idUsuariosCargados = new ArrayList<String>();
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_CARGAUSUARIO(?)");
			cstm.setInt(1, idCarga);
			rs =  cstm.executeQuery();			
			while(rs.next()){
				idUsuariosCargados.add(rs.getString(1));
			}
			return idUsuariosCargados;
		} catch (Exception e) {
			throw new EjecucionException("no se pudo ejecutar correctamente GET_CARGAUSUARIO(?)", e);		
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}	
	}
	
	public Collection<Carga> getCargas(String indPortal) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;	
		Collection<Carga> cargas = new ArrayList<Carga>();
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_CARGA(?)");
			cstm.setString(1, indPortal);
			rs =  cstm.executeQuery();
			Carga carga;
			while(rs.next()){
				carga = new Carga();				
				carga.setFechaCreacion(rs.getTimestamp("FECCREA"));
				carga.setNumeroUsuariosCargadosCorrectamente(rs.getInt("USUARIOSCORRECTOS"));
				carga.setNumeroUsuariosCargadosIncorrectamente(rs.getInt("USUARIOSINCORRECTOS"));
				carga.setIdCarga(rs.getInt("IDCARGA"));
				cargas.add(carga);
			}
			return cargas;
		} catch (Exception e) {
			throw new EjecucionException("no se pudo ejecutar correctamente GET_CARGA(?)", e);		
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}	
	}
	
}