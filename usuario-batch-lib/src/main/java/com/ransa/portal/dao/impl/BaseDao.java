package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.service.locator.PortletServiceLocator;
import com.ransa.portal.util.Util;

public abstract class BaseDao {

	private static Logger logger = Logger.getLogger(BaseDao.class);
	
	private static final String DATASOURCE_JNDI = "jdbc/PortalRANSA";
	protected static final String SCHEMA_PORTAL = "PORTAL";
	protected static final String SCHEMA_EBUSINLIB = "EBUSINLIB";
	protected Connection connection = null;
	protected boolean isTestDao = false;
	
	public Connection getConnection() {
		logger.debug("getConnection");		
		if (!isTestDao) {
			try {
				DataSource ds = PortletServiceLocator.getInstance().getFromJNDI(DATASOURCE_JNDI, DataSource.class);
				connection = ds.getConnection();
			} catch (SQLException e) {
				throw new EjecucionException("Error al obtener la conexion a la BD", e);
			}
		}		
		return connection;
	}
			
	public void setConnection(Connection connection) {
		logger.debug("setConnection");
		this.connection = connection;
	}
		
	public void setTestDao(boolean isTestDao) {
		this.isTestDao = isTestDao;
	}

	/**
	 * Cierra el Connection, en caso de fallo no interrumpe el flujo del
	 * aplicativo
	 */
	protected static void close(Connection connection) {		
		try {
			connection.close();
		} catch (Exception e) {
			Util.showStackTrace(e);
		}
	}

	/**
	 * Cierra el ResultSet, en caso de fallo no interrumpe el flujo del
	 * aplicativo
	 */
	protected static void close(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (Exception e) {
			Util.showStackTrace(e);
		}
	}

	/**
	 * Cierra el Statement, en caso de fallo no interrumpe el flujo del
	 * aplicativo
	 */
	protected static void close(Statement statement) {
		try {
			statement.close();
		} catch (Exception e) {
			Util.showStackTrace(e);
		}
	}

	public Object executeProcedure(ProcedureCallback procedureCallback) {
		Connection conn = null;
		try {
			conn = getConnection();
			return procedureCallback.execute(conn);
		} catch (SQLException e) {
			throw new EjecucionException("Error en executeProcedure en BaseDao", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Util.showStackTrace(e);
				}
			}
		}
	}

	public Object getDataResultSet(final String procedure, final String param, final ResultSetCallback resultSetCallback) {
		return executeProcedure(new ProcedureCallback() {
			public Object execute(Connection conn) throws SQLException {
				CallableStatement cstm = null;
				logger.debug("procedure " + procedure + "  param: " + param);
				if (param != null) {
					cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + "." + procedure + "(?)");
					cstm.setString(1, param);
				} else {
					cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + "." + procedure);
				}
				logger.debug("Antes de ejecutar el SP  " + procedure) ;
				ResultSet rs = cstm.executeQuery();
				logger.debug("Ejecutado el SP  " + procedure) ;
				return resultSetCallback.getData(rs);
			}
		});
	}

	public static interface ProcedureCallback {

		Object execute(Connection conn) throws SQLException;

	}

	public static interface ResultSetCallback {

		Object getData(ResultSet rs) throws SQLException;

	}

}
