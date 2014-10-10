package com.ransa.portal.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ibm.portal.um.Group;
import com.ibm.portal.um.PumaController;
import com.ibm.portal.um.PumaLocator;
import com.ibm.portal.um.PumaProfile;
import com.ibm.portal.um.User;
import com.ibm.portal.um.exceptions.PumaAttributeException;
import com.ibm.portal.um.exceptions.PumaMissingAccessRightsException;
import com.ibm.portal.um.exceptions.PumaSystemException;
import com.ibm.portal.um.portletservice.PumaHome;
import com.ransa.portal.dao.GrupoDao;
import com.ransa.portal.dao.factory.DaoFactory;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.exception.UsuarioException;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Usuario;
import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.service.GestionUsuarioGrupoPUMAService;
import com.ransa.portal.service.locator.PortletServiceLocator;
import com.ransa.portal.util.CodigoError;
import com.ransa.portal.util.IndicadorTipoPortal;
import com.ransa.portal.util.Util;

public class GestionUsuarioGrupoPUMAServiceImpl implements GestionUsuarioGrupoPUMAService {

	private static String GRUPO_PORTAL_INTRANET;
	private static String GRUPO_PORTAL_EXTRANET;
	
	static {
//		UtilDao utilDao = DaoFactory.getInstance().getUtilDao();
//		GRUPO_PORTAL_INTRANET = utilDao.getParametroConfiguracion(5);
//		GRUPO_PORTAL_EXTRANET = utilDao.getParametroConfiguracion(6);
	}
	
	// Nombres de atributos de grupos en el portal
	private static final String ID_GRUPO = "cn";
	
	private static Logger logger = Logger.getLogger(GestionUsuarioGrupoPUMAServiceImpl.class);
		
	/** Variable para el servicio PUMA */
	private PumaHome pumaHome;
	
	public GestionUsuarioGrupoPUMAServiceImpl() {
		try {
			// Obteniendo el home del servicio PUMA
			pumaHome = PortletServiceLocator.getInstance().getPumaHome();
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_02, e);
		} catch (Exception e) {
			// Usar Log4j
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
	}
	
	/**
	 * @return Nombre del Grupo que contiene a los grupos del Portal de Intranet o Extranet
	 */
	private String getGrupoPadrePortal(PortletRequest portletRequest) {
		if (IndicadorTipoPortal.getInstance().isIntranet(portletRequest)) {
			return GRUPO_PORTAL_INTRANET;
		} else {
			return GRUPO_PORTAL_EXTRANET;
		}
	}
	
	/**
	 * Obtiene usuario del repositorio LDAP segun el uid.
	 * 
	 * @param 	idUsuario			parametro por el cual se busca al usuario
	 * @param 	pumaLocator	permite la busqueda de usuarios
	 * @return	usuario obtenido segun uid		
	 * @throws 	PumaMissingAccessRightsException 
	 * @throws 	PumaAttributeException 
	 * @throws 	PumaSystemException 
	 */
	@SuppressWarnings("unchecked")
	private User getUsuarioLDAP(String atributo, String valorAtributo, PumaLocator pumaLocator,
			boolean validacionNormal, PortletRequest portletRequest) throws PumaSystemException, PumaAttributeException,
			PumaMissingAccessRightsException {
		logger.info("getUsuarioLDAP");
		logger.debug("atributo=" + atributo);
		logger.debug("valorAtributo=" + valorAtributo);
		User usuario;
		// Buscando a usuario de LDAP
		List usuarios = pumaLocator.findUsersByAttribute(atributo, valorAtributo);
		int tamanio = usuarios.size();
		logger.debug("tam:"+tamanio);
		if (tamanio == 0) {			
			// No existe ningun usuario de LDAP encontrado			
			if (validacionNormal) {
				if (IndicadorTipoPortal.getInstance().isIntranet(portletRequest)) {
					logger.debug("validacionNormal=>"+CodigoError.ERROR_USUARIO_03);
					throw new UsuarioException(CodigoError.ERROR_USUARIO_03);
				} else {
					logger.debug("validacionNormal=>"+CodigoError.ERROR_USUARIO_03+"|"+ Util.CREACION_NUEVO_USUARIO_EXTRANET);
					throw new UsuarioException(CodigoError.ERROR_USUARIO_03, Util.CREACION_NUEVO_USUARIO_EXTRANET);
				}
			} else {
				logger.debug("No existe usuario con " + atributo + "=" + valorAtributo + Util.NO_EXISTE_USUARIO);
				throw new EjecucionException("No existe usuario con " + atributo + "=" + valorAtributo, Util.NO_EXISTE_USUARIO);
			}
		} else if (tamanio > 1) {
			logger.debug("Existe mas de un usuario de ldap encontrado con " + atributo + "=" + valorAtributo + Util.EXISTE_MAS_DE_UN_USUARIO);
			throw new EjecucionException("Existe mas de un usuario de ldap encontrado con " + atributo + "=" + valorAtributo, Util.EXISTE_MAS_DE_UN_USUARIO);
		} else {
			// Obteniendo usuario de LDAP encontrado
			logger.debug("usuario");
			usuario = (User)usuarios.get(0);
		}
		logger.debug("usuario=>"+usuario.toString());
		return usuario;
	}
	
	private String getAtributoGeneralUsuario(String nombreAtributoBusqueda, String atributoBusqueda,
			String nombreAtributoRequerido, PortletRequest portletRequest, boolean validacionNormal) {
		logger.info("getAtributoGeneralUsuario");
		logger.debug("nombreAtributoBusqueda=" + nombreAtributoBusqueda);
		logger.debug("atributoBusqueda=" + atributoBusqueda);
		logger.debug("nombreAtributoRequerido=" + nombreAtributoRequerido);
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		PumaProfile pumaProfile = pumaHome.getProfile(portletRequest);
		User usuarioLDAP;
		try {
			usuarioLDAP = getUsuarioLDAP(nombreAtributoBusqueda, atributoBusqueda, pumaLocator, validacionNormal, portletRequest);
		} catch (EjecucionException e) {
			throw e;
		} catch (UsuarioException e) {
			throw e;
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}			
		// Estableciendo los atributos a obtener del usuario
		List<String> listaNombresAtributos = new ArrayList<String>();
		listaNombresAtributos.add(nombreAtributoRequerido);
		// Obteniendo valores de atributos de usuario
		Map<String, Object> listaValoresAtributos;
		try {
			listaValoresAtributos = pumaProfile.getAttributes(usuarioLDAP, listaNombresAtributos);
		} catch (Exception e) {
			throw new EjecucionException("Error al obtener atributos de usuario de ldap", e);
		}				
		return Util.getAtributo(listaValoresAtributos.get(nombreAtributoRequerido));
	}
	
	/**
	 * Busca un usuario con el correo especificado
	 * @param correoElectronico Email con que se busca un usuario
	 * @param pumaLocator
	 * @param validacionNormal
	 * @return true si existe un usuario con el correo proporcionado 
	 * @throws PumaMissingAccessRightsException 
	 * @throws PumaAttributeException 
	 * @throws PumaSystemException 
	 * @throws PumaSystemException
	 * @throws PumaAttributeException
	 * @throws PumaMissingAccessRightsException
	 */
	@SuppressWarnings("unchecked")
	public boolean isCorreoAsignadoUsuario( String correoElectronico, PortletRequest portletRequest) throws PumaSystemException, PumaAttributeException, PumaMissingAccessRightsException {
		boolean isCorreoAsignado = false;
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		// Buscando a usuario de LDAP
		List usuarios = pumaLocator.findUsersByAttribute(GestionUsuarioGrupoPUMAService.CORREO_ELECTRONICO, correoElectronico);		
		int tamanio = usuarios.size();
		if (tamanio == 0) {			
			// No existe ningun usuario de LDAP con el correo
			isCorreoAsignado = false;
		} else {
			isCorreoAsignado = true;
		}
		return isCorreoAsignado;
	}
		
	/**
	 * Obtiene lista de usuarios de ldap segun uid(que solo sera 1)
	 * 
	 * @param 	idUsuario	id de usuario de ldap
	 * @param 	pumaLocator	permite encontrar usuarios de ldap
	 * @return 	lista de usuarios de ldap segun uid(que solo sera 1)
	 */
	@SuppressWarnings("unchecked")
	private List getUsuariosLDAP(String idUsuario, PumaLocator pumaLocator) {
		try {
			// Buscando a usuario de LDAP
			List usuariosLDAP = pumaLocator.findUsersByAttribute(ID1_USUARIO, idUsuario);
			if (usuariosLDAP.size() == 0) {
				throw new EjecucionException("No se encontro ningun usuario ldap con uid=" + idUsuario, new Exception());
			}
			return usuariosLDAP;
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			// Usar Log4j
			throw new EjecucionException("Error al obtener usuario ldap con uid=" + idUsuario, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List getUsuariosLDAPPorQuery(String query, PumaLocator pumaLocator, boolean validacionNormal ) {
		logger.debug("getUsuariosLDAPPorQuery");
		try {
			List<String> queries = new ArrayList<String>();			
			int num = StringUtils.countMatches(query, Util.SEPARADOR_OR);
			int pos = -1;
			while (num >= 500) {
				pos = StringUtils.ordinalIndexOf(query, Util.SEPARADOR_OR, 2);
				queries.add(query.substring(0, pos - 1));
				query = query.substring(pos + 3);			
				num = StringUtils.countMatches(query, Util.SEPARADOR_OR);
			}
			queries.add(query);
			// Buscando usuarios de LDAP por query
			List usuariosLDAP = null;
			boolean bandera = true;
			for (String query2 : queries) {
				if (bandera) {
					usuariosLDAP = pumaLocator.findUsersByQuery(query2);
					bandera = false;
				} else {
					List tempUsuariosLDAP = pumaLocator.findUsersByQuery(query2);
					usuariosLDAP.addAll(tempUsuariosLDAP);
				}					
			}
			if (usuariosLDAP.size() == 0) {
				if (validacionNormal) {
					throw new UsuarioException(CodigoError.ERROR_USUARIO_03, new Exception());
				} else {
					throw new EjecucionException("No se encontro ningun usuario ldap segun query=" + query, new Exception());
				}				
			}
			return usuariosLDAP;
		} catch (EjecucionException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("Error al obtener usuarios de ldap segun query=" + query, e);
		}
	}
	
	/**
	 * Obtiene grupo del portal segun cn
	 * 
	 * @param 	uid			parametro por el cual se busca al usuario
	 * @param 	pumaLocator	permite la busqueda de grupos
	 * @return	usuario obtenido segun uid		
	 * @throws 	PumaMissingAccessRightsException 
	 * @throws 	PumaAttributeException 
	 * @throws 	PumaSystemException 
	 */
	@SuppressWarnings("unchecked")
	private Group getGrupoPortal(String cn, PumaLocator pumaLocator)
			throws PumaSystemException, PumaAttributeException,
			PumaMissingAccessRightsException {
		Group grupo;
		// Buscando a grupo de portal
		List grupos = pumaLocator.findGroupsByAttribute(ID_GRUPO, cn);
		int tamanio = grupos.size();
		if (tamanio == 0) {
			// No existe grupo de portal encontrado
			throw new EjecucionException("No existe grupo de portal con cn=" + cn, new Exception());
		} else if (tamanio > 1) {
			throw new EjecucionException("Existe mas de un grupo de portal encontrado con cn=" + cn, new Exception());
		} else {
			// Obteniendo grupo de portal encontrado
			grupo = (Group)grupos.get(0);
		}
		return grupo;
	}
		
	/**
	 * Establece atributos de los grupos del portal obtenidos
	 * 
	 * @param grupos		grupos a retornar
	 * @param gruposPortal	grupos del portal obtenidos
	 * @param pumaProfile	permite obtener los atributos de los grupos del portal
	 */
	@SuppressWarnings("unchecked")
	private void setGrupos(Collection<Grupo> grupos, List gruposPortal, PumaProfile pumaProfile, PortletRequest portletRequest) {
		logger.trace("setGrupos");
		int numGrupos = gruposPortal.size();
		// Estableciendo los atributos a obtener del grupo
		List<String> listaNombresAtributos = new ArrayList<String>();
		listaNombresAtributos.add(ID_GRUPO);
		for (int i = 0; i < numGrupos; i++) {
			Map<String, Object> listaValoresAtributos = null; 
			try {
				Object object = gruposPortal.get(i);
				logger.trace("verificando object");
				if (object instanceof Group) {
					logger.trace("object es Group");
					listaValoresAtributos = pumaProfile.getAttributes((Group)object, listaNombresAtributos);
				}
			} catch (Exception e) {
				throw new EjecucionException("Error al obtener atributos de grupo de portal", e);
			}
			if (listaValoresAtributos != null) {
				logger.trace("listaValoresAtributos no es null");
				Grupo grupo = new Grupo();
				grupo.setDescripcion(Util.getAtributo(listaValoresAtributos.get(ID_GRUPO)));
				try {
					GrupoDao grupoDao = DaoFactory.getInstance().getGrupoDao();
					String tempIdGrupo = grupoDao.getIdGrupo(grupo.getDescripcion(), IndicadorTipoPortal.getInstance().getIndPortal(portletRequest));
					if (tempIdGrupo.equals(Util.STRING_VACIO)) {
						grupoDao.addGrupo(grupo.getDescripcion(), IndicadorTipoPortal.getInstance().getIndPortal(portletRequest));
						tempIdGrupo = grupoDao.getIdGrupo(grupo.getDescripcion(), IndicadorTipoPortal.getInstance().getIndPortal(portletRequest));
					}
					grupo.setId(tempIdGrupo);
				} catch (EjecucionException e) {
					// Usar Log4j
					throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
				}
				logger.trace("agregando grupo");
				grupos.add(grupo);
			}			
		}
	}
		
	private void setAtributosUsuarioLDAP(Map<String, Object> atributos, Usuario usuario, String indicador, PortletRequest portletRequest) {
		logger.debug("setAtributosUsuario");
		atributos.put(ID1_USUARIO, usuario.getId().trim());
		logger.debug("uid=" + atributos.get(ID1_USUARIO) + ".");		
		List<String> tempNombres = new ArrayList<String>();
		tempNombres.add(usuario.getNombres());		
		atributos.put(APELLIDOS, usuario.getApellidos().trim());
		logger.debug("sn=" + atributos.get(APELLIDOS) + ".");	
//		if (!IndicadorTipoPortal.getInstance().isIntranet(portletRequest)) {
			atributos.put(NOMBRES, tempNombres);		
			logger.debug("givenName=" + atributos.get(NOMBRES) + ".");
			List<String> tempTrato = new ArrayList<String>();
			tempTrato.add(usuario.getTrato().trim());
			atributos.put(TRATO, tempTrato);
			logger.debug("title=" + atributos.get(TRATO) + ".");
			atributos.put(CORREO_ELECTRONICO, usuario.getCorreoElectronico().trim());
			logger.debug("ibm-primaryEmail=" + atributos.get(CORREO_ELECTRONICO) + ".");
//		}
		atributos.put(FECHA_NACIMIENTO, usuario.getFechaNacimiento().trim());
		logger.debug("laFechaNacimiento=" + atributos.get(FECHA_NACIMIENTO) + ".");
		atributos.put(ID_TIPO_DOCUMENTO, usuario.getTipoDocumento().getId().trim());
		logger.debug("laIdTipoDoc=" + atributos.get(ID_TIPO_DOCUMENTO) + ".");
		atributos.put(NUMERO_DOCUMENTO, usuario.getNumeroDocumento().trim());
		logger.debug("laNumDoc=" + atributos.get(NUMERO_DOCUMENTO) + ".");
		atributos.put(ID_COMPANIA, usuario.getIdCompania().trim());
		logger.debug("laIdCompania=" + atributos.get(ID_COMPANIA) + ".");		
		atributos.put(COMPANIA, usuario.getCompania().trim());
		logger.debug("laCompania=" + atributos.get(COMPANIA) + ".");
		atributos.put(TELEFONO_OFICINA, usuario.getTelefonoOficina().trim());
		logger.debug("laTelefonoOficina=" + atributos.get(TELEFONO_OFICINA) + ".");
		atributos.put(TELEFONO_PERSONAL, usuario.getTelefonoPersonal().trim());
		logger.debug("laTelefonoPersonal=" + atributos.get(TELEFONO_PERSONAL) + ".");
		atributos.put(TIPO_USUARIO, usuario.getTipoUsuario().getNombre().trim());
		logger.debug("laTipo=" + atributos.get(TIPO_USUARIO) + ".");
		atributos.put(AREA, usuario.getArea().trim());
		logger.debug("laArea=" + atributos.get(AREA) + ".");
		atributos.put(CARGO, usuario.getCargo().trim());
		logger.debug("laCargo=" + atributos.get(CARGO) + ".");
		atributos.put(ID_PAIS, usuario.getPais().getId().trim());
		logger.debug("laIdPais=" + atributos.get(ID_PAIS) + ".");
		atributos.put(ID_IDIOMA, usuario.getIdioma().getId().trim());
		logger.debug("laIdIdioma=" + atributos.get(ID_IDIOMA) + ".");
		atributos.put(ID_LOOK_AND_FEEL_PORTAL, usuario.getIdLookAndFeel());
		logger.debug("laIdLookAndFeel=" + atributos.get(ID_LOOK_AND_FEEL_PORTAL) + ".");
		atributos.put(LOOK_AND_FEEL_PORTAL, usuario.getLookAndFeel());
		logger.debug("laLookAndFeel=" + atributos.get(LOOK_AND_FEEL_PORTAL) + ".");		
		atributos.put(COMPANIA_SOLICITANTE, usuario.getCompaniaSolicitante());
		logger.debug("laCompaniaSolicitante=" + atributos.get(COMPANIA_SOLICITANTE) + ".");
		atributos.put(AREA_SOLICITANTE, usuario.getAreaSolicitante());
		logger.debug("laAreaSolicitante=" + atributos.get(AREA_SOLICITANTE) + ".");
		if (Util.CREAR_USUARIO.equals(indicador)) {				
			atributos.put(ID2_USUARIO, atributos.get(ID1_USUARIO));
			logger.debug("cn=" + atributos.get("cn") + ".");
			atributos.put(CONTRASENIA, usuario.getContrasenia());
			logger.debug("password=" + atributos.get(CONTRASENIA) + ".");						
		} else {
			atributos.put(ID2_USUARIO, usuario.getCN().trim());
			logger.debug("cn=" + atributos.get("cn") + ".");
		}
	}
	
	@SuppressWarnings("unchecked")
	private Grupo[] getGruposLDAP(String idUsuario, PortletRequest portletRequest) {
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		PumaProfile pumaProfile = pumaHome.getProfile(portletRequest);
		Collection<Grupo> tempGrupos = new ArrayList<Grupo>();
		User usuarioLDAP;
		try {
			usuarioLDAP = getUsuarioLDAP(ID1_USUARIO, idUsuario, pumaLocator, true, portletRequest);
		} catch (UsuarioException e) {
			throw e;
		} catch (Exception e) {
			// Usar Log4j
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
		// Se obtiene grupos del grupo padre(Grupos de Portal Intranet o Extranet)
		List gruposPortal;
		try {
			// Se establece false para encontrar solo los grupos directos a los que pertenece el usuario
			gruposPortal = pumaLocator.findGroupsByPrincipal(usuarioLDAP, false);
		} catch (Exception e) {
			throw new EjecucionException("Error al obtener grupos a los que pertenece el usuario con uid=" + idUsuario, e);
		}
		// Asignando los valores de los atributos de los grupos encontrados
		try {
			setGrupos(tempGrupos, gruposPortal, pumaProfile, portletRequest);
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		} catch (UsuarioException e) {
			throw e;
		}
		// Obteniendo array de grupos
		Grupo[] grupos = new Grupo[tempGrupos.size()];
		return tempGrupos.toArray(grupos);
	}
	
//	public Usuario getUsuario(String atributo, String valorAtributo, boolean validarEstado, boolean eliminarUsuario,
//			boolean mostrarSoloAtributosLDAP, PortletRequest portletRequest) {
//		logger.debug("atributo=" + atributo);
//		logger.debug("valorAtributo=" + valorAtributo);
//		logger.debug("validarEstado=" + validarEstado);
//				
//		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
//		User usuarioLDAP;
//		try {
//			usuarioLDAP = getUsuarioLDAP(atributo, valorAtributo, pumaLocator, true, portletRequest);
//		} catch (UsuarioException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
//		}		
//		// Estableciendo los atributos a obtener del usuario
//		List<String> listaNombresAtributos = new ArrayList<String>();
//		listaNombresAtributos.add(ID1_USUARIO);
//		listaNombresAtributos.add(ID2_USUARIO);
//		listaNombresAtributos.add(NOMBRES);
//		listaNombresAtributos.add(APELLIDOS);
//		listaNombresAtributos.add(TRATO);
//		listaNombresAtributos.add(CORREO_ELECTRONICO);
//		listaNombresAtributos.add(FECHA_NACIMIENTO); 
//		listaNombresAtributos.add(ID_TIPO_DOCUMENTO);
//		listaNombresAtributos.add(NUMERO_DOCUMENTO);
//		listaNombresAtributos.add(ID_COMPANIA);
//		listaNombresAtributos.add(COMPANIA);
//		listaNombresAtributos.add(TELEFONO_OFICINA);
//		listaNombresAtributos.add(TELEFONO_PERSONAL);
//		listaNombresAtributos.add(TIPO_USUARIO);
//		listaNombresAtributos.add(ID_LOOK_AND_FEEL_PORTAL);
//		listaNombresAtributos.add(LOOK_AND_FEEL_PORTAL);
//		listaNombresAtributos.add(AREA);
//		listaNombresAtributos.add(CARGO);
//		listaNombresAtributos.add(ID_PAIS);	
//		listaNombresAtributos.add(ID_IDIOMA);		
//		listaNombresAtributos.add(AREA_SOLICITANTE);	
//		listaNombresAtributos.add(COMPANIA_SOLICITANTE);	
//		listaNombresAtributos.add(PREGUNTA_SEGRETA);
//		listaNombresAtributos.add(RESPUESTA_SECRETA);
//		// Obteniendo valores de atributos de usuario		
//		PumaProfile pumaProfile = pumaHome.getProfile(portletRequest);
//		Map<String, Object> listaValoresAtributos;
//		try {
//			listaValoresAtributos = pumaProfile.getAttributes(usuarioLDAP, listaNombresAtributos);
//		} catch (Exception e) {
//			throw new EjecucionException("Error al obtener atributos de usuario de ldap", e);
//		}
//		
//		String idusuario = (String) listaValoresAtributos.get(ID1_USUARIO);
//		
////		UtilDao utilDao = DaoFactory.getInstance().getUtilDao();
//		UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
//		// Validando estado de usuario
//		String estadoUsuario = usuarioDao.getEstado(idusuario);
//		logger.debug("Estado de Usuario " + idusuario +" = "+estadoUsuario);
//		if (validarEstado) {
//			logger.debug("validando estado de usuario...");
//			if (Util.ESTADO_USUARIO_INACTIVO.equals(estadoUsuario)) {
//				// Si mostrarSoloAtributosLDAP es true indica que no se debe consultar a IndicadorTipoPortal
//				if (!mostrarSoloAtributosLDAP) {
//					throw new UsuarioException(CodigoError.ERROR_USUARIO_04, new Exception(),
//							Util.ACTIVACION_USUARIO);
//				} else {
//					throw new UsuarioException(CodigoError.ERROR_USUARIO_04, new Exception());
//				}				
//			} else if (eliminarUsuario && Util.STRING_VACIO.equals(estadoUsuario)) {
//				throw new UsuarioException(CodigoError.ERROR_USUARIO_29, new Exception());
//			}				
//		}
//
//		try {
//			Usuario usuario = new Usuario();
//			usuario.setId(idusuario);			
//			logger.debug("atributo=" + ID2_USUARIO);
//			usuario.setCN(Util.getAtributo(listaValoresAtributos.get(ID2_USUARIO)));
//			logger.debug("atributo=" + NOMBRES);
//			usuario.setNombres(Util.getAtributo(listaValoresAtributos.get(NOMBRES)));
//			logger.debug("atributo=" + APELLIDOS);
//			usuario.setApellidos(Util.getAtributo(listaValoresAtributos.get(APELLIDOS)));
//			logger.debug("atributo=" + TRATO);
//			usuario.setTrato(Util.getAtributo(listaValoresAtributos.get(TRATO)));
//			logger.debug("atributo=" + CORREO_ELECTRONICO);
//			usuario.setCorreoElectronico(Util.getAtributo(listaValoresAtributos.get(CORREO_ELECTRONICO)));
//			logger.debug("atributo=" + FECHA_NACIMIENTO);
//			usuario.setFechaNacimiento(Util.getAtributo(listaValoresAtributos.get(FECHA_NACIMIENTO)));
//			logger.debug("atributo=" + ID_TIPO_DOCUMENTO);
//			String tempIdTipoDocumento = Util.getAtributo(listaValoresAtributos.get(ID_TIPO_DOCUMENTO));
//			if (!Util.STRING_VACIO.equals(tempIdTipoDocumento)) {
//				usuario.setTipoDocumento(utilDao.getTipoDocumento(tempIdTipoDocumento));
//			}
//			logger.debug("tipoDocumento=" + usuario.getTipoDocumento().getId() + Util.SEPARADOR2 + usuario.getTipoDocumento().getNombre());
//			logger.debug("atributo=" + NUMERO_DOCUMENTO);
//			usuario.setNumeroDocumento(Util.getAtributo(listaValoresAtributos.get(NUMERO_DOCUMENTO)));
//			logger.debug("atributo=" + TIPO_USUARIO);			
//			usuario.setTipoUsuario(new TipoUsuario(Util.getAtributo(listaValoresAtributos.get(TIPO_USUARIO))));
//			logger.debug("idNombreTipoUsuario=" + usuario.getTipoUsuario().getNombre());
//			if (Util.STRING_VACIO.equals(usuario.getTipoUsuario().getNombre()) && !mostrarSoloAtributosLDAP) {				
//				if (IndicadorTipoPortal.getInstance().isIntranet(portletRequest)) {				
//					usuario.setTipoUsuario(utilDao.getTipoUsuarioEmpleado());
//				} else {
//					usuario.getTipoUsuario().setId(usuarioDao.getIdTipoUsuario(idusuario));
//					if (!Util.STRING_VACIO.equals(usuario.getTipoUsuario().getId())) {
//						Collection<TipoUsuario> tiposUsuarios = utilDao.getTiposUsuario(usuario.getTipoUsuario().getId());
//						usuario.getTipoUsuario().setNombre(tiposUsuarios.iterator().next().getNombre());
//					}				
//				}
//			} else {
//				if (IndicadorTipoPortal.getInstance().isIntranet(portletRequest)) {
//					usuario.setTipoUsuario(utilDao.getTipoUsuarioEmpleado());
//				} else {
//					usuario.getTipoUsuario().setId(usuarioDao.getIdTipoUsuario(idusuario));
//				}
//			}
//			logger.debug("atributo=" + ID_LOOK_AND_FEEL_PORTAL);
//			usuario.setIdLookAndFeel(Util.getAtributo(listaValoresAtributos.get(ID_LOOK_AND_FEEL_PORTAL)));
//			logger.debug("atributo=" + LOOK_AND_FEEL_PORTAL);
//			usuario.setLookAndFeel(Util.getAtributo(listaValoresAtributos.get(LOOK_AND_FEEL_PORTAL)));
//			logger.debug("tipoUsuario=" + usuario.getTipoUsuario().getId() + Util.SEPARADOR2 + usuario.getTipoUsuario().getNombre());
//			logger.debug("atributo=" + ID_COMPANIA);
//			usuario.setIdCompania(Util.getAtributo(listaValoresAtributos.get(ID_COMPANIA)));
//			logger.debug("atributo=" + COMPANIA);
//			usuario.setCompania(Util.getAtributo(listaValoresAtributos.get(COMPANIA)));
//			logger.debug("atributo=" + TELEFONO_OFICINA);
//			usuario.setTelefonoOficina(Util.getAtributo(listaValoresAtributos.get(TELEFONO_OFICINA)));
//			logger.debug("atributo=" + TELEFONO_PERSONAL);
//			usuario.setTelefonoPersonal(Util.getAtributo(listaValoresAtributos.get(TELEFONO_PERSONAL)));
//			logger.debug("atributo=" + AREA);
//			usuario.setArea(Util.getAtributo(listaValoresAtributos.get(AREA)));
//			logger.debug("atributo=" + CARGO);
//			usuario.setCargo(Util.getAtributo(listaValoresAtributos.get(CARGO)));
//			logger.debug("atributo=" + ID_PAIS);
//			String temIdPais = Util.getAtributo(listaValoresAtributos.get(ID_PAIS));
//			if (!Util.STRING_VACIO.equals(temIdPais)) {
//				usuario.setPais(utilDao.getPais(temIdPais));
//			}
//			logger.debug("pais=" + usuario.getPais().getId() + Util.SEPARADOR2 + usuario.getPais().getNombre());
//			logger.debug("atributo=" + ID_IDIOMA);
//			String temIdIdioma = Util.getAtributo(listaValoresAtributos.get(ID_IDIOMA));
//			if (!Util.STRING_VACIO.equals(temIdIdioma)) {
//				logger.debug("temIdIdioma=" + temIdIdioma);
//				usuario.setIdioma(utilDao.getIdioma(temIdIdioma));
//			}
//			logger.debug("idioma=" + usuario.getIdioma().getId() + Util.SEPARADOR2 + usuario.getIdioma().getNombre());
//			usuario.setSolicitante(usuarioDao.getSolicitante(idusuario));
//			usuario.setAreaSolicitante(Util.getAtributo(listaValoresAtributos.get(AREA_SOLICITANTE)));
//			logger.debug("areaSolicitante=" + usuario.getAreaSolicitante());
//			usuario.setCompaniaSolicitante(Util.getAtributo(listaValoresAtributos.get(COMPANIA_SOLICITANTE)));
//			logger.debug("companiaSolicitante=" + usuario.getCompaniaSolicitante());
//			usuario.setEstado(estadoUsuario);
//			logger.debug("atributo=" + PREGUNTA_SEGRETA);
//			usuario.setPreguntaSecreta(Util.getAtributo(listaValoresAtributos.get(PREGUNTA_SEGRETA)));
//			logger.debug("atributo=" + RESPUESTA_SECRETA);
//			usuario.setRespuestaSecreta(Util.getAtributo(listaValoresAtributos.get(RESPUESTA_SECRETA)));
//			return usuario;
//		} catch (EjecucionException e) {
//			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e); 
//		}		
//	}

	@SuppressWarnings("unchecked")
	public Collection<Grupo> getGrupos(PortletRequest portletRequest) {
		logger.debug("getGrupos");
		String nombreGrupo = getGrupoPadrePortal(portletRequest);
		logger.debug("nombreGrupo=" + nombreGrupo);
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		PumaProfile pumaProfile = pumaHome.getProfile(portletRequest);
		Collection<Grupo> grupos = new ArrayList<Grupo>();
		// Se obtiene grupo padre(Grupos de Portal Intranet o Extranet)
		Group grupoPortal;
		try {
			grupoPortal = getGrupoPortal(nombreGrupo, pumaLocator);
		} catch (UsuarioException e) {
			throw e;
		} catch (Exception e) {
			// Usar Log4j
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
		// Se obtiene grupos del grupo padre(Grupos de Portal Intranet o Extranet)
		List gruposPortal;
		try {
			// Se establece false para que solo encuentre los miembros(grupos) directos
			gruposPortal = pumaLocator.findMembersByGroup(grupoPortal, false);
		} catch (Exception e) {
			throw new EjecucionException("Error al obtener miembros(grupos de portal) del grupo con cn=" + nombreGrupo, e);
		}
		// Asignando los valores de los atributos de los grupos encontrados
		try {
			setGrupos(grupos, gruposPortal, pumaProfile, portletRequest);
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		} catch (UsuarioException e) {
			throw e;
		}
		return grupos;
	}
	
	public Grupo[] getGrupos(String idUsuario, PortletRequest portletRequest) {
		Collection<Grupo> gruposPortal = getGrupos(portletRequest);
		Grupo[] gruposLDAPUsuario = getGruposLDAP(idUsuario, portletRequest);
		Collection<Grupo> tempGruposUsuario = new ArrayList<Grupo>();
		for (Grupo grupoLDAPUsuario : gruposLDAPUsuario) {
			for (Grupo grupoPortal : gruposPortal) {
				if (grupoLDAPUsuario.equals(grupoPortal)) {
					tempGruposUsuario.add(grupoLDAPUsuario);
					break;
				}
			}
		}
		Grupo[] gruposUsuario = new Grupo[tempGruposUsuario.size()];
		gruposUsuario = tempGruposUsuario.toArray(gruposUsuario);
		return gruposUsuario;
	}
	
	@SuppressWarnings("unchecked")
	public void agregarUsuarioGrupo(String idUsuario, String nombresGrupo[], PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.info("agregarUsuarioGrupo");
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		PumaController pumaController = pumaHome.getController(actionRequest);
		try {
			logger.debug("==>"+idUsuario);
			List usuariosLDAP = getUsuariosLDAP(idUsuario, pumaLocator);
			for (int i = 0; i < nombresGrupo.length; i++) {
				logger.debug("[]"+nombresGrupo[i]);
				Group grupoPortal = getGrupoPortal(nombresGrupo[i], pumaLocator);
				// Agregando usuario LDAP a grupo de portal
				pumaController.addToGroup(grupoPortal, usuariosLDAP);
			}
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		} catch (Exception e) {
			// Usar Log4j
			throw new EjecucionException("Error al agregar usuario a grupo", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removerUsuarioGrupo(String idUsuario, String nombresGrupo[], PortletRequest portletRequest, ActionRequest actionRequest) {
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		PumaController pumaController = pumaHome.getController(actionRequest);
		try {
			List usuariosLDAP = getUsuariosLDAP(idUsuario, pumaLocator);
			for (int i = 0; i < nombresGrupo.length; i++) {
				Group grupoPortal = getGrupoPortal(nombresGrupo[i], pumaLocator);
				// Agregando usuario LDAP a grupo de portal
				pumaController.removeFromGroup(grupoPortal, usuariosLDAP);
			}
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		} catch (Exception e) {
			// Usar Log4j
			throw new EjecucionException("Error al remover usuario de grupo", e);
		}
	}

	public void crearUsuario(Usuario usuario, PortletRequest portletRequest, ActionRequest actionRequest) {				
		logger.debug("GestionUsuarioGrupoPUMAServiceImpl >> crearUsuario");
		// Estableciendo los atributos ha actualizar del usuario ha crear
		Map<String, Object> atributos = new HashMap<String, Object>();
		setAtributosUsuarioLDAP(atributos, usuario, Util.CREAR_USUARIO, portletRequest);
		
		atributos.put(PWD_RESET, Util.PWD_RESET_TRUE);
		logger.debug("pwdReset=" + atributos.get(PWD_RESET) + ".");
		
		//Creando usuario con sus atributos respectivos
		PumaController pumaController = pumaHome.getController(actionRequest);
		try {
			logger.debug("Creando usuario");
			pumaController.createUser(usuario.getId(), null, atributos);
			logger.debug("Usuario creado correctamente");
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_17, e);
		}
	}
	
	public void actualizarUsuario(Usuario usuario, PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.info("actualizarUsuario");
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		// Buscando usuario en el LDAP
		User userLDAP;
		try {
			userLDAP = getUsuarioLDAP(ID1_USUARIO, usuario.getId(), pumaLocator, true, portletRequest);
		} catch (UsuarioException e) {
			throw e;
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
		// Estableciendo los atributos ha actualizar del usuario
		Map<String, Object> atributos = new HashMap<String, Object>();
		setAtributosUsuarioLDAP(atributos, usuario, Util.STRING_VACIO, portletRequest);
		// Actualizando atributos de usuario
		PumaController pumaController = pumaHome.getController(actionRequest);
		try {
			/*Usuario prevUsuario = getUsuario(usuario.getId(), true, portletRequest);
			logger.info("CORREO ANTERIOR: " + prevUsuario.getCorreoElectronico() + ".");
			logger.info("CORREO NUEVO: " + atributos.get(CORREO_ELECTRONICO) + ".");
			*/
			logger.debug("Actualizando usuario");
			pumaController.setAttributes(userLDAP, atributos);
			logger.debug("Usuario actualizado correctamente");
			
			if(!Util.ESTADO_USUARIO_INACTIVO.equals(usuario.getEstado())){
				Usuario tempUsuario = getUsuario(usuario.getId(), true, portletRequest);
				logger.info("CORREO ACTUALIZADO: " + tempUsuario.getCorreoElectronico() + ".");
				
				if (!tempUsuario.getCorreoElectronico().equals(usuario.getCorreoElectronico())) {
					logger.info("No se actualizó correctamente el correo electronico");
					throw new UsuarioException(CodigoError.ERROR_USUARIO_59);
				}
			}
			
		} catch (PumaSystemException e) {
			logger.debug("verificando si se actualizo correo electronico");
			Usuario tempUsuario = getUsuario(usuario.getId(), true, portletRequest);
			if (!tempUsuario.getCorreoElectronico().equals(usuario.getCorreoElectronico())) {
				throw new UsuarioException(CodigoError.ERROR_USUARIO_18, e);
			}			
		} catch (Exception e) {
			logger.error("<=====Error:"+e.getMessage(), e);
			if (e instanceof UsuarioException) {
				throw new UsuarioException(e.getMessage(), e);
			}
			throw new UsuarioException(CodigoError.ERROR_USUARIO_18, e);
		}		
	}
	
	public String existeUsuarioLDAP(String idUsuario, PortletRequest portletRequest) {
		logger.debug("existeUsuario");
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		// Buscando a usuario de LDAP
		List<User> usuarios;
		try {
			usuarios = pumaLocator.findUsersByAttribute(ID1_USUARIO, idUsuario);
		} catch (Exception e) {
			throw new EjecucionException("Error al buscar usuario para verificar si existe o no con uid=" + idUsuario, e);
		}
		int tamanio = usuarios.size();
		if (tamanio == 0) {			
			// No existe ningun usuario de LDAP encontrado
			return Util.STRING_VACIO;		
		} else if (tamanio > 1) {
			throw new EjecucionException("Existe mas de un usuario de ldap encontrado con uid=" + idUsuario, new Exception());
		} else {
			// Existe usuario de LDAP encontrado
			User user = usuarios.get(0);
			PumaProfile pumaProfile = pumaHome.getProfile(portletRequest);
			String identifier = Util.STRING_VACIO;
			try {
				identifier = pumaProfile.getIdentifier(user);
			} catch (Exception e) {
				Util.showStackTrace(e);
			}
			logger.debug("identifier=" + identifier);
			return identifier;
		}
	}
	
	public String getAtributoUsuario(String idUsuario, String nombreAtributoUsuario,
			PortletRequest portletRequest) {
		try {
			return getAtributoGeneralUsuario(ID1_USUARIO, idUsuario, nombreAtributoUsuario, portletRequest, false);
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01);
		}
	}
	
	public String getAtributoUsuario(String idUsuario, String nombreAtributoUsuario,
			PortletRequest portletRequest, boolean validacionNormal) {
		return getAtributoGeneralUsuario(ID1_USUARIO, idUsuario, nombreAtributoUsuario, portletRequest, validacionNormal);
		
	}
	
	public String getIdUsuario(String correoElectronico, PortletRequest portletRequest,
			boolean mostrarErrorDetallado) {
		logger.info("getIdUsuario");
		logger.debug("mostrarErrorDetallado=" + mostrarErrorDetallado);
		if (mostrarErrorDetallado) {
			return getAtributoGeneralUsuario(CORREO_ELECTRONICO, correoElectronico,
					ID1_USUARIO, portletRequest, false);
		} else {
			try {
				return getAtributoGeneralUsuario(CORREO_ELECTRONICO, correoElectronico,
						ID1_USUARIO, portletRequest, false);
			} catch (EjecucionException e) {
				throw new UsuarioException(CodigoError.ERROR_GENERAL_01);
			}
		}			
	}
	
	public Usuario getUsuario(String valorAtributo, boolean validarEstado, PortletRequest portletRequest) {
		return getUsuario(ID1_USUARIO, valorAtributo, validarEstado, false, false, portletRequest);
	}

	public void actualizarAtributoUsuarioLDAP(String idUsuario, String nombreAtributoUsuario, 
			String valorAtributoUsuario, PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.debug("actualizarAtributoUsuarioLDAP");
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		logger.debug("buscando usuario en el LDAP");
		User userLDAP;
		try {
			userLDAP = getUsuarioLDAP(ID1_USUARIO, idUsuario, pumaLocator, false, portletRequest);
		} catch (UsuarioException e) {
			throw e;
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}		
		logger.debug("obteniendo valores de atributos de usuario");
		List<String> listaNombresAtributos = new ArrayList<String>();		
		listaNombresAtributos.add(ID2_USUARIO);
		PumaProfile pumaProfile = pumaHome.getProfile(portletRequest);
		Map<String, Object> atributos;
		try {
			atributos = pumaProfile.getAttributes(userLDAP, listaNombresAtributos);
		} catch (Exception e) {
			throw new EjecucionException("error al obtener atributos de usuario de ldap", e);
		}		 
		logger.debug("estableciendo el atributo ha actualizar del usuario");
		//atributos.put(ID1_USUARIO, idUsuario);
		//logger.debug("uid=" + atributos.get(ID1_USUARIO));
		atributos.put(nombreAtributoUsuario, valorAtributoUsuario);
		logger.debug(nombreAtributoUsuario + "=" + atributos.get(nombreAtributoUsuario));
		//if (IndicadorTipoPortal.isIntranet(portletRequest)) {			
		//	logger.debug("cn=" + atributos.get(ID2_USUARIO));
		//}		
		logger.debug("actualizando atributo de usuario");
		PumaController pumaController = pumaHome.getController(actionRequest);
		try {
			logger.debug("actualizando el atributo " + nombreAtributoUsuario + "=" + valorAtributoUsuario + 
					" del usuario con uid=" + idUsuario);
			pumaController.setAttributes(userLDAP, atributos);
			logger.debug("atributo actualizado correctamente");
		} catch (Exception e) {
			logger.error("Error: ",e);
			throw new EjecucionException("no se pudo actualizar el atributo " + nombreAtributoUsuario + 
					"=" + valorAtributoUsuario + " del usuario con uid=" + idUsuario, e);
		}		
	}

	public Usuario getUsuarioEliminar(String idUsuario, boolean validarEstado, PortletRequest portletRequest) {
		return getUsuario(ID1_USUARIO, idUsuario, validarEstado, true, false, portletRequest);
	}

	public Usuario getUsuarioSinLogin(String idUsuario, boolean validarEstado, PortletRequest portletRequest) {
		return getUsuario(ID1_USUARIO, idUsuario, validarEstado, false, true, portletRequest);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MetaData> getIdUsuariosPorQueryParaEnvioContrasenia(String query, PortletRequest portletRequest) {
		logger.debug("getUsuariosPorQueryParaEnvioContrasenia");
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		List usuariosLDAP;		
		try {
			usuariosLDAP = getUsuariosLDAPPorQuery(query, pumaLocator, true);
		} catch (UsuarioException e) {
			throw e;
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
		logger.debug("estableciendo atributos a devolver");
		List<String> listaNombresAtributos = new ArrayList<String>();
		listaNombresAtributos.add(ID1_USUARIO);		
		listaNombresAtributos.add(CORREO_ELECTRONICO);
		listaNombresAtributos.add(TRATO);
		listaNombresAtributos.add(NOMBRES);
		listaNombresAtributos.add(APELLIDOS);
		logger.debug("obteniendo atributos de usuarios");
		PumaProfile pumaProfile = pumaHome.getProfile(portletRequest);
		Map<String, Object> listaValoresAtributos;
		Collection<MetaData> idUsuarios = new ArrayList<MetaData>();		
		String idUsuario;
		String correoElectronico;
		String trato;
		String nombres;
		String apellidos;
		try {
			for (Object object : usuariosLDAP) {
				User usuarioLDAP = (User)object;
				listaValoresAtributos = pumaProfile.getAttributes(usuarioLDAP, listaNombresAtributos);
				idUsuario = Util.getAtributo(listaValoresAtributos.get(ID1_USUARIO));
				correoElectronico = Util.getAtributo(listaValoresAtributos.get(CORREO_ELECTRONICO));
				trato = Util.getAtributo(listaValoresAtributos.get(TRATO));
				if (!Util.STRING_VACIO.equals(trato)) {
					trato = " " + trato;
				}
				logger.debug("trato=" + trato);
				nombres = Util.getAtributo(listaValoresAtributos.get(NOMBRES));
				logger.debug("nombres=" + nombres);
				apellidos = Util.getAtributo(listaValoresAtributos.get(APELLIDOS));
				logger.debug("apellidos=" + apellidos);
				logger.debug("idUsuario=" + idUsuario);
				MetaData metaData = new MetaData();
				metaData.setValue(idUsuario + Util.SEPARADOR + correoElectronico + Util.SEPARADOR + trato + 
						Util.SEPARADOR + nombres.trim().toUpperCase() + " " + apellidos.trim().toUpperCase());
				metaData.setLabel(idUsuario);
				idUsuarios.add(metaData);								
			}			
		} catch (Exception e) {
			throw new EjecucionException("Error al obtener atributos de usuario de ldap", e);
		}
		return idUsuarios;
	}
	
	public void eliminarUsuarioLDAP(String idUsuario, PortletRequest portletRequest, ActionRequest actionRequest) {
		PumaLocator pumaLocator = pumaHome.getLocator(portletRequest);
		PumaController pumaController = pumaHome.getController(actionRequest);
		User user;
		try {
			user = getUsuarioLDAP(GestionUsuarioGrupoPUMAService.ID1_USUARIO, idUsuario, pumaLocator, false, portletRequest);
		} catch (Exception e) {
			user = null;
		}
		if (user != null) {
			try {
				pumaController.deleteUser(user);
			} catch (Exception e) {
				throw new EjecucionException("no se pudo eliminar usuario de ldap", e);
			}
		}		
	}

	public Usuario getUsuario(String atributo, String valorAtributo,
			boolean validarEstado, boolean eliminarUsuario,
			boolean mostrarSoloAtributosLDAP, PortletRequest portletRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
