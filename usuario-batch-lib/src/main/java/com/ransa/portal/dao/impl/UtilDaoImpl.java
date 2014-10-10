package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ransa.portal.dao.UtilDao;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.Aplicacion;
import com.ransa.portal.model.Estado;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Idioma;
import com.ransa.portal.model.Negocio;
import com.ransa.portal.model.OpcionAplicacion;
import com.ransa.portal.model.Pais;
import com.ransa.portal.model.TipoArchivo;
import com.ransa.portal.model.TipoDocumento;
import com.ransa.portal.model.TipoEvento;
import com.ransa.portal.model.TipoPortal;
import com.ransa.portal.model.TipoUso;
import com.ransa.portal.model.TipoUsuario;
import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.portlet.administracion.usuario.util.LookAndFeelBean;
@Repository
public class UtilDaoImpl extends BaseDao implements UtilDao {
	private static Logger logger = Logger.getLogger(UtilDaoImpl.class);

	@SuppressWarnings("unchecked")
	private <T extends MetaData> Collection<T> getMetaData(String procedure, String param, final Class<T> clazz) {
		return (Collection<T>) getDataResultSet(procedure, param, new ResultSetCallback() {
			public Object getData(ResultSet rs) throws SQLException {
				Collection<T> collection = new ArrayList<T>();
				while (rs.next()) {
					logger.trace("Recuperando los datos revueltos");
					try {
						T t = clazz.newInstance();
						t.setValue(rs.getString(1));
						t.setLabel(rs.getString(2));
						collection.add(t);
					} catch (Exception e) {
						throw new EjecucionException("Error instanceando la clase " + clazz.getName(), e);
					}
				}
				return collection;
			}
		});
	}

	public Collection<TipoDocumento> getTiposDocumento() {
		logger.debug("getTiposDocumento");
		try {
			return getMetaData("GET_TIPODOCUMENTO", "T", TipoDocumento.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTiposDocumento", e);
		}
	}
	
	public TipoDocumento getTipoDocumento(String idTipoDocumento) {
		logger.debug("getTipoDocumento");
		try {
			logger.debug("idTipoDocumento: " + idTipoDocumento);
			Collection<TipoDocumento> tiposDocumento = getMetaData("GET_TIPODOCUMENTO", idTipoDocumento, TipoDocumento.class);
			Iterator<TipoDocumento> iterator = tiposDocumento.iterator();
			return iterator.next();
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTipoDocumento", e);
		}
	}

	public TipoUsuario getTipoUsuarioEmpleado() {
		logger.debug("getTipoUsuarioEmpleado");
		try {
			Collection<TipoUsuario> tipoUsuarioEmpleado = getMetaData("GET_TIPOUSUARIO_EMPLEADO", null, TipoUsuario.class);
			Iterator<TipoUsuario> iterator = tipoUsuarioEmpleado.iterator();
			return iterator.next();
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTipoUsuarioEmpleado", e);
		}
	}

	public Collection<TipoUsuario> getTiposUsuario(String indicador) {
		logger.debug("getTiposUsuario");
		try {
			return getMetaData("GET_TIPOUSUARIO", indicador, TipoUsuario.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTiposUsuario", e);
		}
	}

	public Collection<Pais> getPaises() {
		logger.debug("getPaises");
		try {
			return getMetaData("GET_PAIS", "T", Pais.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getPaises", e);
		}
	}
	
	public Pais getPais(String idPais) {
		logger.debug("getPais");
		try {
			Collection<Pais> paises = getMetaData("GET_PAIS", idPais, Pais.class);
			Iterator<Pais> iterator = paises.iterator();
			return iterator.next();
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getPais", e);
		}
	}

	public Collection<Idioma> getIdiomas() {
		logger.debug("getIdiomas");
		try {
			return getMetaData("GET_IDIOMA", "T", Idioma.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getIdiomas", e);
		}
	}
	
	public Collection<LookAndFeelBean> getListLookAndFeel() {
		logger.debug("getListLookAndFeel");
		try {
			return getMetaData("GET_LOOK_AND_FEEL_PORTAL", "T", LookAndFeelBean.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getListLookAndFeel", e);
		}
	}
	
	public LookAndFeelBean getLookAndFeel(String idLookAndFeel) {
		logger.debug("getLookAndFeel");
		try {
			Collection<LookAndFeelBean> listLookAndFeel = getMetaData("GET_LOOK_AND_FEEL_PORTAL", idLookAndFeel, LookAndFeelBean.class);
			Iterator<LookAndFeelBean> iterator = listLookAndFeel.iterator();
			return iterator.next();
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getLookAndFeel", e);
		}
	}
	
	public Idioma getIdioma(String idIdioma) {
		logger.debug("getIdioma");		
		try {
			Collection<Idioma> idiomas = getMetaData("GET_IDIOMA", idIdioma, Idioma.class);
			logger.debug("numIdiomas=" + idiomas.size());
			Iterator<Idioma> iterator = idiomas.iterator();
			return iterator.next();
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getIdioma", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ransa.portal.dao.UtilDao#getTipoUsuario()
	 */
	public Collection<TipoUsuario> getTiposUsuario() {
		logger.debug("getTiposUsuario");
		try {
			return getMetaData("GET_TIPOUSUARIO", "T", TipoUsuario.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTiposUsuario", e);
		}		
	}

	public Collection<Estado> getEstadoAplicacion() {
		logger.debug("getEstadoAplicacion");
		try {
			return getMetaData("GET_ESTADOAPP", null, Estado.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getEstadoAplicacion", e);
		}
	}

	public Collection<Estado> getEstadoOpcionAplicacion() {
		logger.debug("getEstadoOpcionAplicacion");
		try {
			return getMetaData("GET_ESTADO_OPCIONAPP", null, Estado.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getEstadoOpcionAplicacion", e);
		}
	}

	public Collection<Estado> getEstadoRolAplicacion() {
		logger.debug("getEstadoRolAplicacion");
		try {
			return getMetaData("GET_ESTADO_ROLAPP", null, Estado.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getEstadoRolAplicacion", e);
		}
	}

	public Collection<Estado> getEstadoTipoRecurso() {
		logger.debug("getEstadoTipoRecurso");
		try {
			return getMetaData("GET_ESTADO_TIPORECURSO", null, Estado.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getEstadoTipoRecurso", e);
		}
	}

	public Collection<Negocio> getNegocioAplicacion() {
		logger.debug("getNegocioAplicacion");
		try {
			return getMetaData("GET_NEGOCIO_APP", null, Negocio.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getNegocioAplicacion", e);
		}
	}

	public Collection<Grupo> getGrupos(String intExtranet) {
		logger.debug("getGrupos");
		try {
			logger.trace("intExtranet: " + intExtranet);
			return getMetaData("GET_ALL_GRUPOS", intExtranet, Grupo.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getGrupos", e);
		}
	}
	
	public String getParametroConfiguracion(Integer idParametro){
		logger.debug("getParametroConfiguracion");
		CallableStatement statement = null;
		String valorParametro = null;
		try {
			getConnection();
			statement = connection.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_PARAMETRO_CONFIGURACION(?, ?)");
			
			if(idParametro!=null)
				statement.setInt(1, idParametro);
			else
				statement.setNull(1, Types.INTEGER);
			
			statement.registerOutParameter(2, Types.VARCHAR);
			statement.execute();
			
			valorParametro = statement.getString(2); 
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		} finally {
			close(statement);
			close(connection);
		}
		return valorParametro;
	}
	
	public Collection<TipoEvento> getComboTipoEvento() {
		logger.debug("getComboTipoEvento");
		return getCombo("TIPOEVENTO", null, null, null, null, null, null, TipoEvento.class);
	}

	public Collection<TipoUso> getComboTipoUso() {
		logger.debug("getComboTipoUso");
		return getCombo("TIPOUSO", null, null, null, null, null, null, TipoUso.class);
	}

	public Collection<Aplicacion> getComboAplicacion(Integer idNegocio) {
		logger.debug("getComboAplicacion");
		return getCombo("APLICACION", idNegocio, null, null, null, null, null, Aplicacion.class);
	}

	public Collection<OpcionAplicacion> getComboOpcionAplicacion(Integer idAplicacion) {
		logger.debug("getComboOpcionAplicacion");
		return getCombo("OPCIONAPLICACION", idAplicacion, null, null, null, null, null, OpcionAplicacion.class);
	}
	
	public Collection<Estado> getComboEstadoUsuario() {
		logger.debug("getComboEstadoUsuario");
		return getCombo("ESTADOUSUARIO", null, null, null, null, null, null, Estado.class);		
	}
	
	private <T extends MetaData> Collection<T> getCombo(String nombreCombo, Integer arg1, Integer arg2, Integer arg3, String arg4, String arg5, String arg6,
	        final Class<T> clazz) {
		logger.debug("getCombo");
		Collection<T> collection = new ArrayList<T>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		int p = 1;

		try {
			getConnection();
			statement = connection.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_COMBO(?,?,?,?,?,?,?)");
			statement.setString(p++, nombreCombo);

			if (arg1 != null) {
				statement.setInt(p++, arg1);
			} else {
				statement.setNull(p++, Types.INTEGER);
			}

			if (arg2 != null) {
				statement.setInt(p++, arg2);
			} else {
				statement.setNull(p++, Types.INTEGER);
			}

			if (arg3 != null) {
				statement.setInt(p++, arg3);
			} else {
				statement.setNull(p++, Types.INTEGER);
			}

			if (arg4 != null) {
				statement.setString(p++, arg4);
			} else {
				statement.setNull(p++, Types.VARCHAR);
			}

			if (arg5 != null) {
				statement.setString(p++, arg5);
			} else {
				statement.setNull(p++, Types.VARCHAR);
			}

			if (arg6 != null) {
				statement.setString(p++, arg6);
			} else {
				statement.setNull(p++, Types.VARCHAR);
			}

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				T object = clazz.newInstance();
				object.setValue(resultSet.getString("VALUE"));
				object.setLabel(resultSet.getString("LABEL"));
				collection.add(object);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		} finally {
			close(resultSet);
			close(statement);
			close(connection);
		}
		return collection;
	}
	
	public Collection<TipoPortal> getTiposPortal() {
		logger.debug("getTiposPortal");
		try {
			return getMetaData("GET_TIPOPORTAL", "T", TipoPortal.class);
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTiposPortal", e);
		}
	}
	
	public TipoPortal getTipoPortal(String idTipoPortal) {
		logger.debug("getTipoPortal");
		try {
			Collection<TipoPortal> tiposPortal = getMetaData("GET_TIPOPORTAL", idTipoPortal, TipoPortal.class);
			Iterator<TipoPortal> iterator = tiposPortal.iterator();
			return iterator.next();
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTipoPortal", e);
		}
	}
	
	public Collection<TipoArchivo> getTiposArchivo() {
		logger.debug("getTiposArchivo");
		try {
			return  getMetaData("GET_TIPOARCHIVO", "T", TipoArchivo.class);			
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("error en getTiposArchivo", e);
		}
	}
	
	public boolean verificarDominioEmail(String dominioEmail) {
		logger.debug("getParametroConfiguracion");
		CallableStatement statement = null;		
		boolean dominoEmailValido = false;
		try {
			getConnection();
			statement = connection.prepareCall("CALL " + SCHEMA_PORTAL + ".VERIFICAR_DOMINIO_EMAIL_RESTRINGIDO(?, ?)");
			statement.setString(1, dominioEmail);			
			statement.registerOutParameter(2, Types.INTEGER);
			statement.executeUpdate();			
			if (statement.getInt(2) > 0) {
				dominoEmailValido = true;
			}
		} catch (Exception e) {
			throw new EjecucionException("error en VERIFICAR_DOMINIO_EMAIL_RESTRINGIDO", e);
		} finally {
			close(statement);
			close(connection);
		}
		return dominoEmailValido;
	}
	
}
