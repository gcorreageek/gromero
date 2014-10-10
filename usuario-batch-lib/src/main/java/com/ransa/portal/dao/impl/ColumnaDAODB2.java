package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ransa.portal.dao.ColumnaDAO;
import com.ransa.portal.domain.Columna;
import com.ransa.portal.exception.EjecucionException;


public class ColumnaDAODB2 extends BaseDao implements ColumnaDAO {
	
	private static final Log logger = LogFactory.getLog(ColumnaDAODB2.class);
	public static final String SCHEMADC = "DC@RNSLIB";

	
	public List<Columna> listaColumnas(int idAplicacion, String idUsuario, int codPantalla) {
		List<Columna> columnas = new ArrayList<Columna>();
		Columna columna = null;
		CallableStatement cstm = null;
		Connection conn = null;
		ResultSet rs = null;
		try{
			conn = super.getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMADC + ".SP_PA_INVMOV_LISTA_COLUMNAS_USUARIO(?,?,?)");
			cstm.setInt(1, idAplicacion);
			cstm.setInt(2, codPantalla);
			cstm.setString(3, idUsuario);
			
			rs = cstm.executeQuery();
			while(rs.next()){
				columna = new Columna();
				columna.setId(rs.getRow());
				columna.setIdAplicacion(rs.getInt("CDAPPT"));
				columna.setCodPantalla(rs.getInt("CDCNAP"));
				columna.setVista(rs.getString("TCCNAP"));
				columna.setCodigoColumna(rs.getInt("CDCLNA"));
				columna.setNombreColumna(rs.getString("TCMCLA"));
				columna.setValor(rs.getString("VALOR"));
				
				columnas.add(columna);
			}
		}catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el StoreProcedure SP_PA_INVMOV_LISTA_COLUMNAS_USUARIO", e);
		}finally{
			close(rs);
			close(cstm);
			close(conn);
		}
		return columnas;
	}
	
	public void registrarColumnas(int idAplicacion, int codPantalla, String codsColumna, String idUsuario){
		CallableStatement cstm = null;
		Connection conn = null;
		try{
			conn = super.getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMADC + ".SP_PA_INVMOV_REGISTRA_COLUMNAS_USUARIO_APP2(?,?,?,?)");
			cstm.setInt(1, idAplicacion);
			cstm.setInt(2, codPantalla);
			cstm.setString(3, codsColumna);
			cstm.setString(4, idUsuario);
			
			cstm.executeUpdate();
		}catch (SQLException e) {
			throw new EjecucionException("Error al ejecutar el StoreProcedure SP_PA_INVMOV_REGISTRA_COLUMNAS_USUARIO_APP2", e);
		}finally{
			close(cstm);
			close(conn);
		}
		logger.info("registro exitoso ");
	}	
}
