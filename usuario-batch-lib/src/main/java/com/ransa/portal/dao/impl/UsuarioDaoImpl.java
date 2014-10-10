package com.ransa.portal.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ransa.portal.dao.UsuarioDao;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.exception.UsuarioException;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Idioma;
import com.ransa.portal.model.Pais;
import com.ransa.portal.model.TipoDocumento;
import com.ransa.portal.model.TipoUsuario;
import com.ransa.portal.model.Usuario;
import com.ransa.portal.util.CodigoError;
import com.ransa.portal.util.Util;

public class UsuarioDaoImpl extends BaseDao implements UsuarioDao {

	private static Logger logger = Logger.getLogger(UsuarioDaoImpl.class);
	
	public String getNombreCompania(Integer tipo, String idCompania, int idPais, String CCMPN) {
		logger.debug("tipo=" + tipo);
		logger.debug("idCompania=" + idCompania);
		logger.debug("idPais=" +  idPais);
		logger.debug("CCMPN= " + CCMPN);
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_COMPANIA_X_TIPO(?, ?, ?, ?, ?, ?)");
			cstm.setInt(1, tipo);
			cstm.setString(2, idCompania);
			cstm.setInt(3, idPais);
			cstm.setString(4, CCMPN);
			cstm.registerOutParameter(5, Types.VARCHAR);
			cstm.registerOutParameter(6, Types.VARCHAR);
			cstm.executeUpdate();
			String nombreCompania = cstm.getString(6);
			if ("".equals(nombreCompania)) {				
				throw new EjecucionException("No se retorno ningun registro en GET_COMPANIA_X_TIPO", new Exception());
			}
			return nombreCompania;
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_07, e);		
		} catch (Exception e) {
			throw new EjecucionException("Error en getNombreCompania de UsuarioDao", e);
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}
	}
	
	public String getSolicitante(String idUsuario) {
		logger.debug("getSolicitante");
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_SOLICITANTE_X_USUARIO(?)");
			cstm.setString(1, idUsuario);
			rs = cstm.executeQuery();
			String solicitante = Util.STRING_VACIO;
			if (rs.next()){
				solicitante = rs.getString(1);
			}
			return solicitante;
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_07, e);
		} catch (Exception e) {
			throw new EjecucionException("Error en getNombreCompania de UsuarioDao", e);
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}		
	}
	
	public void actualizarConfiguracionUsuario(String idUsuario, int idTipoUsuario, String solicitante,
			String idUsuarioCreaModif, String indicadorCreacion, String idsGrupos, String idsRoles,
			String idsRecursos, String idsRecursosFavoritos, String estadoUsuario, int tipoActualizacion) {
		logger.info("==>"+"actualizarConfiguracionUsuario");
		Connection conn = null;
		CallableStatement cstm = null;
		try { 
			logger.info("=>"+idUsuario+"|"+idTipoUsuario+"|"+solicitante+"|"+idUsuarioCreaModif+"|"+indicadorCreacion+"|"+idsGrupos
					+"|"+idsRoles+"|"+idsRecursos+"|"+idsRecursosFavoritos+"|"+estadoUsuario+"|"+tipoActualizacion);
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".UPD_CONFIGURACION_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");			
			cstm.setString(1, idUsuario);//I_IDUSUARIO
			cstm.setInt(2, idTipoUsuario);//I_IDTIPOUSUARIO
			cstm.setString(3, solicitante);//I_SOLICITANTE
			cstm.setString(4, idUsuarioCreaModif);//I_USU_CREA_MODIF
			cstm.setString(5, indicadorCreacion);//I_IND_CREACION==>Si es 0=INGRESA,SINO se actualiza
			if(estadoUsuario!=null)//I_STSUSUARIO
				cstm.setString(6, estadoUsuario);
			else
				cstm.setNull(6, Types.VARCHAR);
			//Son 6 parametros
			cstm.setString(7, idsGrupos);
			cstm.setString(8, idsRoles);
			cstm.setString(9, idsRecursos);
			cstm.setString(10, idsRecursosFavoritos);
			cstm.setInt(11, tipoActualizacion);
			cstm.executeUpdate();			
		} catch (Exception e) {
			throw new EjecucionException("Error al ejecutar procedure UPD_CONFIGURACION_USUARIO", e);
		} finally {			
			close(cstm);
			close(conn);
		}
	}
	
	
	public void eliminarConfiguracionUsuario(String idUsuario, String idUsuarioCreaModif) {
		Connection conn = null;
		CallableStatement cstm = null;		
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".DEL_CONFIGURACION_USUARIO(?, ?)");
			cstm.setString(1, idUsuario);
			cstm.setString(2, idUsuarioCreaModif);
			cstm.executeUpdate();			
		} catch (Exception e) {
			throw new EjecucionException("Error en actualizarEstadoConfiguracionUsuario de UsuarioDao", e);
		} finally {			
			close(cstm);
			close(conn);
		}
	}
	
	public String getEstado(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;
		try {
			conn = getConnection();			
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_ESTADO_USUARIO(?)");
			cstm.setString(1, idUsuario);
			rs = cstm.executeQuery();
			String estadoUsuario = Util.STRING_VACIO;
			if (rs.next()) {
				estadoUsuario = rs.getString(1);
			}
			return estadoUsuario;
		} catch (Exception e) {			
			throw new EjecucionException("Error en getEstado de UsuarioDao", e);
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}
	}
	
	public String getIdTipoUsuario(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;		
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_IDTIPOUSUARIO(?)");
			cstm.setString(1, idUsuario);
			rs = cstm.executeQuery();
			String idTipoUsuario = Util.STRING_VACIO;
			if (rs.next()) {
				idTipoUsuario = rs.getString(1);
			}
			return idTipoUsuario;
		} catch (Exception e) {
			throw new EjecucionException("Error en getIdTipoUsuario de UsuarioDao", e);
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}
	}
	
	public Collection<Grupo> getGruposUsuario(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;	
		ArrayList<Grupo> listGrupo = new ArrayList<Grupo>();
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_GRUPOS_X_USUARIO(?)");
			if(idUsuario!=null)
				cstm.setString(1, idUsuario.trim());
			else
				cstm.setNull(1, Types.VARCHAR);
			
			rs =  cstm.executeQuery();
			Grupo grupo = null;
			while(rs.next()){
				grupo = new Grupo();
				grupo.setId(rs.getString(1));
				grupo.setDescripcion(rs.getString(2));
				listGrupo.add(grupo);
			}
			logger.trace("Numero de grupos " + listGrupo.size());
		} catch (Exception e) {
			throw new EjecucionException("Error al obtener los grupos del usuario" + idUsuario, e);		
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}		
		return listGrupo;
	}
	
	public Collection<String> getIdsUsuariosPorGrupos(String idsGrupos) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;	
		Collection<String> idsUsuarios = new ArrayList<String>();
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_USUARIOS_X_GRUPOS(?)");
			cstm.setString(1, idsGrupos);
			rs =  cstm.executeQuery();			
			while(rs.next()){
				idsUsuarios.add(rs.getString(1));
			}			
		} catch (Exception e) {
			throw new EjecucionException("Error al ejecutar GET_USUARIOS_X_GRUPOS", e);		
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}
		
		return idsUsuarios;
	}
	
//	SELECT VALOR INTO UNIQUE_NAME_BIENVENIDA FROM TABLAGENERAL WHERE IDTABLA = 11 AND ( ( I_TIPOPORTAL = PORTALINTRANET AND IDITEM = 11 ) OR ( I_TIPOPORTAL = PORTALEXTRANET AND IDITEM = 12 ) ) ;  INSERT INTO SESSION . TEMP SELECT DISTINCT OPA . IDAPLICACION , OPA . IDOPCION , OPA . UNIQUENAMEPORTAL FROM ROLUSUARIO RU , ROLAPP RA , OPCIONROL OPR , OPCIONAPP OPA WHERE RU . IDROL = RA . IDROL AND RU . IDAPLICACION = RA . IDAPLICACION AND RA . IDROL = OPR . IDROL AND RU . IDAPLICACION = OPR . IDAPLICACION AND OPR . IDOPCION = OPA . IDOPCION AND RU . IDAPLICACION = OPA . IDAPLICACION AND RU . IDUSUARIO = I_IDUSUARIO AND RU . STSROLUSU = 'A' AND RA . STSROL = 'A' AND OPR . STSOPCIONROL = 'A' AND OPA . STSOPCION = 'A' AND OPA . UNIQUENAMEPORTAL <> '' AND OPA . UNIQUENAMEPORTAL IS NOT NULL ;
	public String getUniqueNamePagina(String idUsuario, String indPortal) {
		logger.debug("idUsuario" + idUsuario);
		logger.debug("indPortal" + indPortal);
		Connection conn = null;
		CallableStatement cstm = null;		
		String uniqueNamePagina = Util.STRING_VACIO;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_UNIQUE_NAME_PAGINA(?, ?, ?)");
			cstm.setString(1, idUsuario);
			cstm.setString(2, indPortal);			
			cstm.registerOutParameter(3, Types.VARCHAR);
			cstm.executeUpdate();
			uniqueNamePagina = cstm.getString(3);						
		} catch (Exception e) {
			throw new EjecucionException("Error al ejecutar GET_UNIQUE_NAME_PAGINA(?, ?, ?)", e);		
		} finally {			
			close(cstm);
			close(conn);
		}		
		return uniqueNamePagina.trim();
	}
	
	public Collection<Usuario> getUsuarios(String tipoPortal) {
		Connection conn = null;
		CallableStatement cstm = null;
		ResultSet rs = null;	
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".GET_USUARIOS(?)");
			cstm.setString(1, tipoPortal);
			rs =  cstm.executeQuery();
			Usuario usuario = null;
			TipoDocumento tipoDocumento = null;
			TipoUsuario tipoUsuario = null;
			Pais pais = null;
			Idioma idioma = null;			
			while(rs.next()){
				usuario = new Usuario();
				usuario.setId(rs.getString("IDUSUARIO"));
				usuario.setNombres(rs.getString("NOMBRES"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setTrato(rs.getString("TRATO"));
				usuario.setCorreoElectronico(rs.getString("CORREOELECTRONICO"));
				usuario.setFechaNacimiento(rs.getString("FECHANACIMIENTO"));
				tipoDocumento = new TipoDocumento();
				tipoDocumento.setId(rs.getString("IDTIPODOCUMENTO"));
				usuario.setTipoDocumento(tipoDocumento);
				usuario.setNumeroDocumento(rs.getString("NUMDOCUMENTO"));
				usuario.setIdCompania(rs.getString("IDCOMPANIA"));
				usuario.setCompania(rs.getString("NOMBRECOMPANIA"));
				usuario.setTelefonoOficina(rs.getString("TELEFONOOFICINA"));
				usuario.setTelefonoPersonal(rs.getString("TELEFONOPERSONAL"));
				tipoUsuario = new TipoUsuario();
				tipoUsuario.setId(rs.getString("IDTIPOUSUARIO"));
				tipoUsuario.setNombre(rs.getString("TIPOUSUARIO"));
				usuario.setTipoUsuario(tipoUsuario);
				usuario.setArea(rs.getString("AREA"));
				usuario.setCargo(rs.getString("CARGO"));
				pais = new Pais();
				pais.setId(rs.getString("IDPAIS"));
				usuario.setPais(pais);
				idioma = new Idioma();
				idioma.setId(rs.getString("IDIDIOMA"));
				usuario.setIdioma(idioma);
				usuario.setIdLookAndFeel(rs.getString("IDLOOKANDFEEL"));
				usuario.setLookAndFeel(rs.getString("LOOKANDFEEL"));
				usuario.setSolicitante(rs.getString("SOLICITANTE"));
				usuario.setCompaniaSolicitante(rs.getString("COMPANIASOLICITANTE"));
				usuario.setAreaSolicitante(rs.getString("AREASOLICITANTE"));
				usuario.setIsCasaOrCompania(rs.getString("CCMPN").trim());
				if(rs.getString("CCMPN").trim().equals(Util.PARAM_CCPM_PERU)){
					usuario.setCargo(Util.PARAM_ALMA_PERU);
				}else if (rs.getString("CCMPN").trim().equals(Util.PARAM_CCPM_CASA)){
					usuario.setCargo(Util.PARAM_CASA);
				}
				
				usuarios.add(usuario);
			}
			logger.trace("Numero de usuario " + usuarios.size());
		} catch (Exception e) {
			throw new EjecucionException("Error al ejecutar procedure GET_USUARIOS(?)", e);		
		} finally {
			close(rs);
			close(cstm);
			close(conn);
		}
		
		return usuarios;
	}
	
	public void actualizarInformacionUsuarioCargado(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;		
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".UPD_INFOUSUARIOCARGADO(?)");
			cstm.setString(1, idUsuario);
			cstm.executeUpdate();			
		} catch (Exception e) {
			throw new EjecucionException("no se pudo ejecutar correctamente store procedure UPD_INFOUSUARIOCARGADO(?)", e);
		} finally {			
			close(cstm);
			close(conn);
		}
	}
	
	public boolean verificarUsuario(String idUsuario) {
		Connection conn = null;
		CallableStatement cstm = null;
		boolean existeUsuario = false;
		try {
			conn = getConnection();
			cstm = conn.prepareCall("CALL " + SCHEMA_PORTAL + ".VERIFICAR_USUARIO(?, ?)");
			cstm.setString(1, idUsuario);
			cstm.registerOutParameter(2, Types.INTEGER);
			cstm.executeUpdate();
			if (cstm.getInt(2) > 0)
				return true;			
		} catch (Exception e) {
			throw new EjecucionException("no se pudo ejecutar correctamente store procedure VERIFICAR_USUARIO(?, ?)", e);
		} finally {			
			close(cstm);
			close(conn);
		}
		return existeUsuario;
	}
	
}
