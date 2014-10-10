package com.ransa.portal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.ransa.portal.dao.AplicacionDao;
import com.ransa.portal.dao.CargaDao;
import com.ransa.portal.dao.GrupoDao;
import com.ransa.portal.dao.RecursoUsuarioDao;
import com.ransa.portal.dao.RolAplicacionDao;
import com.ransa.portal.dao.RolUsuarioDao;
import com.ransa.portal.dao.TipoRecursoDao;
import com.ransa.portal.dao.UsuarioDao;
import com.ransa.portal.dao.UtilDao;
import com.ransa.portal.dao.factory.DaoFactory;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.exception.UsuarioException;
import com.ransa.portal.model.Carga;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Idioma;
import com.ransa.portal.model.Pais;
import com.ransa.portal.model.Recurso;
import com.ransa.portal.model.RolUsuario;
import com.ransa.portal.model.TipoArchivo;
import com.ransa.portal.model.TipoDocumento;
import com.ransa.portal.model.TipoPortal;
import com.ransa.portal.model.TipoRecurso;
import com.ransa.portal.model.TipoUsuario;
import com.ransa.portal.model.Usuario;
import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.portletservice.util.VariablesPortalRANSA;
import com.ransa.portal.service.GestionUsuarioGrupoPUMAService;
import com.ransa.portal.service.GestionUsuarioService;
import com.ransa.portal.service.factory.ServiceFactory;
import com.ransa.portal.util.CodigoError;
import com.ransa.portal.util.ControladorThreadEmail;
import com.ransa.portal.util.IndicadorTipoPortal;
import com.ransa.portal.util.Util;

public class GestionUsuarioServiceImpl implements GestionUsuarioService {

	private static Logger logger = Logger.getLogger(GestionUsuarioServiceImpl.class);
	private static String CORREO_ELECTRONICO_ADMINISTRADOR;
	private static String CORREO_ELECTRONICO_TESTER;
	private static String GRUPO_POLITICA_PASSWORD;
	private static int LIMITE_USUARIOS_POR_GRUPO;
	
	static {
		UtilDao utilDao = DaoFactory.getInstance().getUtilDao();
//		CORREO_ELECTRONICO_ADMINISTRADOR = utilDao.getParametroConfiguracion(1);
//		CORREO_ELECTRONICO_TESTER = utilDao.getParametroConfiguracion(9);
		GRUPO_POLITICA_PASSWORD = utilDao.getParametroConfiguracion(7);
//		LIMITE_USUARIOS_POR_GRUPO = Util.parseInt(utilDao.getParametroConfiguracion(15));
	}
	
	private UtilDao utilDao;
	private UsuarioDao usuarioDao;
	private AplicacionDao aplicacionDao;
	private RolAplicacionDao rolAplicacionDao;
	private RolUsuarioDao rolUsuarioDao;
	private TipoRecursoDao tipoRecursoDao;
	private RecursoUsuarioDao recursoUsuarioDao;
//	private WSSeguridadProxy proxy;
	private GrupoDao grupoDao;
	private CargaDao cargaDao;
	
	private GestionUsuarioGrupoPUMAService gestionUsuarioGrupoPUMAService;
	
	public GestionUsuarioServiceImpl() {
//		utilDao = DaoFactory.getInstance().getUtilDao();
		usuarioDao = DaoFactory.getInstance().getUsuarioDao();
//		aplicacionDao = DaoFactory.getInstance().getAplicacionDao();
//		rolAplicacionDao = DaoFactory.getInstance().getRolAplicacionDao();
//		rolUsuarioDao = DaoFactory.getInstance().getRolUsuarioDao();
//		tipoRecursoDao = DaoFactory.getInstance().getTipoRecursoDao();
//		grupoDao = DaoFactory.getInstance().getGrupoDao();
//		recursoUsuarioDao = DaoFactory.getInstance().getRecursoUsuarioDao();
//		cargaDao = DaoFactory.getInstance().getCargaDao();
//		gestionUsuarioGrupoPUMAService = ServiceFactory.getInstance().getGestionUsuarioGrupoPUMAService();
//		proxy = null;
	}
	
	private void actualizarConfiguracionUsuarioBD(Usuario usuario, String indicadorCreacionUsuario, PortletRequest portletRequest) {
		logger.debug("actualizarConfiguracionUsuarioBD");
		VariablesPortalRANSA variablesPortalRANSA = IndicadorTipoPortal.getInstance().getVariablesPortalRANSA(portletRequest);
		String idUsuarioCreaModif = variablesPortalRANSA.getIdUsuario();
		
		logger.debug("idUsuarioCreaModif=" + idUsuarioCreaModif);
		idUsuarioCreaModif = "wpsadmin";
		// Obteniendo ids de grupos
		String idsGrupos = Util.STRING_VACIO;
		boolean bandera;
		if (usuario.getGrupos() != null && usuario.getGrupos().length > 0) {
			bandera = true;
			for (Grupo grupo : usuario.getGrupos()) {
				if (bandera) {
					bandera = false;
					idsGrupos = grupo.getId();
				} else {
					idsGrupos = idsGrupos + Util.SEPARADOR2 + grupo.getId();
				}			
			}
		}		
		logger.debug("idsGrupos=" + idsGrupos);
		// Obteniendo ids de roles
		String idsRoles = Util.STRING_VACIO;
		if (usuario.getRoles() != null && usuario.getRoles().length > 0) {
			bandera = true;
			for (RolUsuario rolUsuario : usuario.getRoles()) {			
				if (bandera) {
					bandera = false;
					idsRoles = rolUsuario.toString();
				} else {
					idsRoles = idsRoles + Util.SEPARADOR2 + rolUsuario.toString();
				}
			}
		}		
		logger.debug("idsRoles=" + idsRoles);
		// Obteniendo ids de recursos
		String idsRecursos = Util.STRING_VACIO;		
		if (usuario.getRecursos() != null && usuario.getRecursos().length > 0) {
			bandera = true;
			for (String idRecursoUsuario : usuario.getRecursos()) {
				if (bandera) {
					bandera = false;
					idsRecursos = idRecursoUsuario;
				} else {
					idsRecursos = idsRecursos + Util.SEPARADOR2 + idRecursoUsuario;
				}
			}
		}		
		logger.debug("idsRecursos=" + idsRecursos);
		// Obteniendo ids de recursos favoritos
		String idsRecursosFavoritos = Util.STRING_VACIO;
		if (usuario.getRecursosFavoritos() != null && usuario.getRecursosFavoritos().length > 0) {
			bandera = true;		
			for (String idRecursoFavorito : usuario.getRecursosFavoritos()) {
				if (bandera) {
					bandera = false;
					idsRecursosFavoritos = idRecursoFavorito;
				} else {
					idsRecursosFavoritos = idsRecursosFavoritos + Util.SEPARADOR2 + idRecursoFavorito;
				} 
			}
		}		
		logger.debug("idsRecursosFavoritos=" + idsRecursosFavoritos);
		logger.debug("indicadoCreacionUsuario=" + indicadorCreacionUsuario);
		logger.debug("idUsuario=" + usuario.getId());
		logger.debug("solicitante=" + usuario.getSolicitante());
		logger.debug("estado=" + usuario.getEstado());
		logger.debug("tipoUsuario:"+usuario.getTipoUsuario().getId()); 
		int idTipoUsuario = Util.parseInt(usuario.getTipoUsuario().getId());
		logger.debug("tipoUsuario:"+idTipoUsuario);
		logger.debug("==>Actualizando");
		// Actualizando informacion de usuario en el bd
		String idUsuarioPaso = usuario.getId(); 
		int idTipoUsuarioPaso = idTipoUsuario;
		String solicitantePaso = usuario.getSolicitante();
		String idUsuarioCreaModifPaso = idUsuarioCreaModif;
		String indicadorCreacionPaso = indicadorCreacionUsuario;
		String idsGruposPaso = idsGrupos;
		String idsRolesPaso = idsRoles ;
		String idsRecursosPaso = idsRecursos;
		String idsRecursosFavoritosPaso = idsRecursosFavoritos ;
		String estadoUsuarioPaso = usuario.getEstado();
		int tipoActualizacionPaso = Util.ACTUALIZACION_NORMAL;
		logger.debug("=>"+idUsuarioPaso+"|"+idTipoUsuarioPaso+"|"+solicitantePaso+"|"+idUsuarioCreaModifPaso+"|"+indicadorCreacionPaso+"|"+idsGruposPaso
				+"|"+idsRolesPaso+"|"+idsRecursosPaso+"|"+idsRecursosFavoritosPaso+"|"+estadoUsuarioPaso+"|"+tipoActualizacionPaso);
		logger.info("=>"+usuarioDao);
		usuarioDao.actualizarConfiguracionUsuario(idUsuarioPaso, idTipoUsuarioPaso, solicitantePaso, idUsuarioCreaModifPaso, indicadorCreacionPaso, idsGruposPaso, 
				idsRolesPaso, idsRecursosPaso, idsRecursosFavoritosPaso, estadoUsuarioPaso, tipoActualizacionPaso);
	}
	
	private Collection<String> filtrarUsuarios(Grupo grupoSeleccionado, Collection<String> idsUsuarios) {
		logger.info("filtrarUsuarios");
		String tempNumero = grupoSeleccionado.getDescripcion().substring(grupoSeleccionado.getDescripcion().lastIndexOf("(") + 1,
				grupoSeleccionado.getDescripcion().lastIndexOf(")"));
		int numero = Util.parseInt(tempNumero);
		int limiteSuperior = LIMITE_USUARIOS_POR_GRUPO * numero;
		logger.debug("limiteSuperior=" + limiteSuperior);
		int limiteInferior = limiteSuperior - LIMITE_USUARIOS_POR_GRUPO;
		logger.debug("limiteInferior=" + limiteInferior);
		boolean bandera2 = true;
		int i = limiteInferior;
		String[] tempIdsUsuarios = new String[idsUsuarios.size()];
		tempIdsUsuarios = idsUsuarios.toArray(tempIdsUsuarios);
		Collection<String> tempIdsUsuarios2 = new ArrayList<String>();
		logger.debug("numTempIdsUsuarios=" + tempIdsUsuarios.length);
		while (bandera2) {
			if (i < tempIdsUsuarios.length) {
				tempIdsUsuarios2.add(tempIdsUsuarios[i]);
				logger.debug("usuarioFiltrado[" + i + "]=" + tempIdsUsuarios[i]);
				i++;
				if (i >= tempIdsUsuarios.length || i >= limiteSuperior)
					bandera2 = false;
			} else
				bandera2 = false;
		}
		return tempIdsUsuarios2;
	}
		
	public Grupo[] getGruposUsuario(String idUsuario){
		Grupo[] vecGrupos = null;
		
		ArrayList<Grupo> listGrupo = (ArrayList<Grupo>)usuarioDao.getGruposUsuario(idUsuario);
		
		if(listGrupo.size() > 0){
			vecGrupos = new Grupo[listGrupo.size()];
			for(int i=0; i <listGrupo.size(); i++ ){
				vecGrupos[i] = listGrupo.get(i);
			}
		}
		return vecGrupos;
	}
			
	public Collection<TipoDocumento> getTiposDocumento() {
		return utilDao.getTiposDocumento();
	}

	public Collection<TipoUsuario> getTiposUsuario(String indicador) {
		return utilDao.getTiposUsuario(indicador);
	}

	public Collection<TipoUsuario> getTipoUsuario() {
		return utilDao.getTiposUsuario();
	}
	
	public Collection<Pais> getPaises() {
		return utilDao.getPaises();
	}
	
	public Collection<Idioma> getIdiomas() {
		return utilDao.getIdiomas();
	}

	public String getNombreCompania(Integer tipo, String idCompania, int idPais, String CCMPN) {
		return usuarioDao.getNombreCompania(tipo, idCompania, idPais, CCMPN);
	}

//	public Collection<FilaAplicacionUsuarioBean> getFilasAplicacionesUsuario(Grupo[] grupos, String idUsuario, PortletRequest portletRequest) {
//		Collection<FilaAplicacionUsuarioBean> filasAplicacion = new ArrayList<FilaAplicacionUsuarioBean>();
//		String idsGrupos = Util.STRING_VACIO;
//		for (Grupo grupo : grupos) {
//			if (idsGrupos.equals(Util.STRING_VACIO)) {
//				idsGrupos = grupo.getId();
//			} else {
//				idsGrupos = grupo.getId() + Util.SEPARADOR2 + idsGrupos;
//			}			
//		}
//		Collection<Aplicacion> aplicaciones = aplicacionDao.getAplicacionesPorGrupos(idsGrupos);
//		for (Aplicacion aplicacion : aplicaciones) {
//			FilaAplicacionUsuarioBean filaAplicacion = new FilaAplicacionUsuarioBean();
//			int tempIdAplicacion = Util.parseInt(aplicacion.getId());
//			String tempIndPortal = IndicadorTipoPortal.getInstance().getIndPortal(portletRequest);
//			filaAplicacion.setNombresGrupos(grupoDao.getGruposPorAplicacion(tempIdAplicacion, tempIndPortal,
//					idsGrupos));
//			filaAplicacion.setIdAplicacion(aplicacion.getId());
//			filaAplicacion.setNombreNegocio(aplicacion.getNegocio().getNombre());
//			filaAplicacion.setNombreAplicacion(aplicacion.getNombre());
//			Collection<RolAplicacion> tempRolesAplicacion = rolAplicacionDao.getRolesAplicacion(Util.parseInt(aplicacion.getId()));
//			RolUsuario rolUsuario = new RolUsuario();
//			rolUsuario.setIdAplicacion(aplicacion.getId());
//			String tempIdRol = rolUsuarioDao.getRolUsuario(idUsuario, Util.parseInt(aplicacion.getId()));
//			if (tempIdRol.equals(Util.STRING_VACIO)) {
//				rolUsuario.setIdRol(Util.NUMERO_VACIO);
//				RolAplicacion rolAplicacionVacia = new RolAplicacion();
//				rolAplicacionVacia.setIdAplicacion(aplicacion.getId());
//				rolAplicacionVacia.setId(Util.STRING_VACIO + Util.NUMERO_VACIO);
//				rolAplicacionVacia.setNombre(Util.STRING_VACIO);
//				tempRolesAplicacion.add(rolAplicacionVacia);
//			} else {
//				rolUsuario.setIdRol(Util.parseInt(tempIdRol));
//				// Obteniendo nombre de rol de usuario
//				for (RolAplicacion tempRolAplicacion : tempRolesAplicacion) {
//					if (tempIdRol.equals(tempRolAplicacion.getId())) {
//						rolUsuario.setNombreRol(tempRolAplicacion.getNombre());
//						break;
//					}
//				}
//			}
//			filaAplicacion.setRolUsuario(rolUsuario);
//			filaAplicacion.setRolesAplicacion(JSFUtil.toSelectItems(tempRolesAplicacion, true));
//			filasAplicacion.add(filaAplicacion);
//		}
//		return filasAplicacion;
//	}

	public void setProxyWebService() {/*
		try {
			if (proxy == null) {
				proxy = new WSSeguridadProxy();
			}			
		} catch (Exception e) {
			throw new EjecucionException("Error el instancear WSSeguridadProxy", e);
		}*/
	}
//	public Collection<FilaTipoRecursoUsuarioBean> getFilasTiposRecursosUsuario(String idUsuario, RolUsuario[] rolesUsuario) {
//		logger.info("getFilasTiposRecursosUsuario");
//		String[] tempRolesUsuario = Util.concatenar(rolesUsuario);
//		logger.debug("idAplicaciones=" + tempRolesUsuario[0]);               
//		try {
//			Collection<FilaTipoRecursoUsuarioBean> filasTiposRecursos = new ArrayList<FilaTipoRecursoUsuarioBean>();
//			Collection<TipoRecurso> tiposRecursos = tipoRecursoDao.getTiposRecursos(tempRolesUsuario[0]);
//			logger.info("iterando tipos de recursos");
//			for (TipoRecurso tipoRecurso : tiposRecursos) {
//				FilaTipoRecursoUsuarioBean filaTipoRecurso = new FilaTipoRecursoUsuarioBean();
//				filaTipoRecurso.setNombreTipoRecurso(tipoRecurso.getNombre());
//				filaTipoRecurso.setIdTipoRecurso(Util.STRING_VACIO + tipoRecurso.getId());
//				logger.debug("nombreTipoRecurso=" + filaTipoRecurso.getNombreTipoRecurso());
//				logger.debug("idTipoRecurso=" + filaTipoRecurso.getIdTipoRecurso());
//				// Se establece vacio los recursos disponibles
//				Collection<SelectItem> recursosDisponibles = new ArrayList<SelectItem>();
//				filaTipoRecurso.setRecursosDisponibles(recursosDisponibles);
//				String tempIdRecursoUsuarioPredeterminado = recursoUsuarioDao.getIdRecursoUsuarioPredeterminado(idUsuario.toUpperCase(), tipoRecurso.getId());
//				if (!Util.STRING_VACIO.equals(tempIdRecursoUsuarioPredeterminado)) {
//					RecursoUsuario[] recursosUsuario = new RecursoUsuario[1];
//					RecursoUsuario recursoUsuario = new RecursoUsuario();
//					recursoUsuario.setIdTipoRecurso(tipoRecurso.getId());
//					recursoUsuario.setIdRecurso(tempIdRecursoUsuarioPredeterminado);
//					recursosUsuario[0] = recursoUsuario;
//					logger.debug("numRecursosAsignadosPredeterminados=" + recursosUsuario.length);
//					filaTipoRecurso.setRecursoAsignadoFavorito(recursosUsuario);
//				}
//				logger.debug("estableciendo recursos asignados");
//				filaTipoRecurso.setRecursosAsignados(JSFUtil.toSelectItems(getRecursosUsuario("" + tipoRecurso.getId(),
//						idUsuario.toUpperCase()), true));				
//				filasTiposRecursos.add(filaTipoRecurso);
//			}
//			logger.debug("iteracion terminada");
//			return filasTiposRecursos;
//		} catch (EjecucionException e) {	
//			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
//		}
//	}

	public Collection<Recurso> getRecursosDisponibles(String idTipoRecurso, String nombreRecursoDisponible,
			Collection<SelectItem> recursosAsignados) {
		return null;
	}

	public void crearConfiguracionUsuario(Usuario usuario, PortletRequest portletRequest, ActionRequest actionRequest) {
		GestionUsuarioGrupoPUMAService gestionUsuarioGrupoPUMAService  = ServiceFactory.getInstance().getGestionUsuarioGrupoPUMAService();
		logger.info("crearConfiguracionUsuario");
		// Creando usuario y actualizando su informacion en el ldap
		try {	
			//La contrasenia viene del excel
			//usuario.setContrasenia(Util.generarContrasenia());	
			if(logger.isDebugEnabled())
				logger.debug("usuario: "+ToStringBuilder.reflectionToString(usuario, ToStringStyle.MULTI_LINE_STYLE));
			gestionUsuarioGrupoPUMAService.crearUsuario(usuario, portletRequest, actionRequest);
		} catch (UsuarioException e) {
			logger.info("no se pudo completar la creacion de usuario en portal... eliminando usuario con uid=" + usuario.getId());
			try {
				gestionUsuarioGrupoPUMAService.eliminarUsuarioLDAP(usuario.getId(), portletRequest, actionRequest);
			} catch (Exception ex) {
				throw new EjecucionException(ex.getMessage(), ex);
			}
			throw e;
		}		
		logger.info("agregando usuario a nuevos grupos en ldap!!!!!!!!!!!");
		logger.debug("=>"+usuario.getGrupos()); 
		if (usuario.getGrupos() != null && usuario.getGrupos().length > 0) {
			String[] nombresGruposParaAgregar = new String[usuario.getGrupos().length];
			int i = 0;
			for (Grupo grupo : usuario.getGrupos()) {
				nombresGruposParaAgregar[i++] = grupo.getDescripcion();
			}
			logger.debug("=>"+Arrays.toString(nombresGruposParaAgregar));
			gestionUsuarioGrupoPUMAService.agregarUsuarioGrupo(usuario.getId(), nombresGruposParaAgregar,portletRequest, actionRequest);
		}
		String[] nombresGruposParaAgregar = {GRUPO_POLITICA_PASSWORD};
		gestionUsuarioGrupoPUMAService.agregarUsuarioGrupo(usuario.getId(), nombresGruposParaAgregar, portletRequest, actionRequest);
		try {
			logger.info("Here INSERT 1!!");
			actualizarConfiguracionUsuarioBD(usuario, Util.CREAR_USUARIO, portletRequest);//Util.CREAR_USUARIO=0, para que lo ingrese el sp
			logger.info("Here INSERT 2!!");
		} catch (Exception e1) {
			try {
				gestionUsuarioGrupoPUMAService.eliminarUsuarioLDAP(usuario.getId(), portletRequest, actionRequest);
			} catch (Exception e2) {
				throw new EjecucionException("Error en eliminar configuracion de usuario en ldap con uid=" + usuario.getId(), e1);
			}
			logger.error("GustavoException:", e1);
			throw new EjecucionException("Error en actualizar configuracion de usuario en bd con uid=" + usuario.getId(), e1);
			
		}			
	}
	
	public void actualizarParcialmenteConfiguracionUsuario(Usuario usuario,	PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.debug("actualizarParcialmenteConfiguracionUsuario");
		// Actualizando informacion de usuario en el ldap
		try {			
			gestionUsuarioGrupoPUMAService.actualizarUsuario(usuario, portletRequest, actionRequest);
		} catch (UsuarioException e) { throw e;	}
				
		try {
			VariablesPortalRANSA variablesPortalRANSA = IndicadorTipoPortal.getInstance().getVariablesPortalRANSA(portletRequest);
			String idUsuarioCreaModif = variablesPortalRANSA.getIdUsuario();
			logger.debug("idUsuarioCreaModif=" + idUsuarioCreaModif);
			logger.debug("estado=" + usuario.getEstado());
			if (Util.STRING_VACIO.equals(usuario.getEstado()) || Util.ESTADO_USUARIO_INACTIVO.equals(usuario.getEstado())) {
				usuario.setEstado(Util.ESTADO_USUARIO_ACTIVO);
			}
			int idTipoUsuario = Util.parseInt(usuario.getTipoUsuario().getId());
			logger.debug("idTipoUsuario=" + idTipoUsuario);
			// Actualizando informacion de usuario en el bd
			usuarioDao.actualizarConfiguracionUsuario(usuario.getId(), idTipoUsuario,
					usuario.getSolicitante(), idUsuarioCreaModif, Util.STRING_VACIO,
					Util.STRING_VACIO, Util.STRING_VACIO, Util.STRING_VACIO,
					Util.STRING_VACIO, usuario.getEstado(), Util.ACTUALIZACION_PARCIAL);
		} catch (Exception e) {
			throw new EjecucionException("Error en actualizar configuracion de usuario en bd con uid=" + usuario.getId(), e);
		}
	}
	
	public void actualizarConfiguracionUsuario(Usuario usuario, Grupo[] gruposUsuarioActuales, PortletRequest portletRequest,
			ActionRequest actionRequest) {
		logger.debug("actualizarConfiguracionUsuario");		
		// Actualizando informacion de usuario en el ldap
		try {			
			gestionUsuarioGrupoPUMAService.actualizarUsuario(usuario, portletRequest, actionRequest);
		} catch (UsuarioException e) { throw e;	}
		
		// Actualizando informacion de grupos de usuario en el ldap
		Collection<Grupo> gruposParaEliminar = new ArrayList<Grupo>();
		boolean grupoEncontrado;
		// Obteniendo grupos de los cuales el usuario sera eliminado
		for (Grupo grupo1 : gruposUsuarioActuales) {
			grupoEncontrado = false;
			for (Grupo grupo2 : usuario.getGrupos()) {
				if (grupo1.equals(grupo2)) {
					grupoEncontrado = true;
					break;
				}
			}
			if (!grupoEncontrado) {
				gruposParaEliminar.add(grupo1);
			}
		}
		// Eliminando usuario de los grupos a los que ya no pertenece
		if (gruposParaEliminar.size() > 0) {
			String[] nombresGruposParaEliminar = new String[gruposParaEliminar.size()];
			int i = 0;
			for (Grupo grupo : gruposParaEliminar) {
				nombresGruposParaEliminar[i++] = grupo.getDescripcion();
			}
			gestionUsuarioGrupoPUMAService.removerUsuarioGrupo(usuario.getId(), nombresGruposParaEliminar,
					portletRequest, actionRequest);
		}		
		// Obteniendo grupos a los cuales se le agregara el usuario
		Collection<Grupo> gruposParaAgregar = new ArrayList<Grupo>();
		for (Grupo grupo1 : usuario.getGrupos()) {
			grupoEncontrado = false;
			for (Grupo grupo2 : gruposUsuarioActuales) {
				if (grupo1.equals(grupo2)) {
					grupoEncontrado = true;
					break;
				}
			}
			if (!grupoEncontrado) {
				gruposParaAgregar.add(grupo1);
			}
		}
		// Agregando usuario a nuevos grupos
		if (gruposParaAgregar.size() > 0) {
			String[] nombresGruposParaAgregar = new String[gruposParaAgregar.size()];
			int i = 0;
			for (Grupo grupo : gruposParaAgregar) {
				nombresGruposParaAgregar[i++] = grupo.getDescripcion();
			}
			gestionUsuarioGrupoPUMAService.agregarUsuarioGrupo(usuario.getId(), nombresGruposParaAgregar,
					portletRequest, actionRequest);
		}		
		String indicadorCreacionUsuarioBD = Util.STRING_VACIO;
		if (Util.STRING_VACIO.equals(usuario.getEstado())) {
			indicadorCreacionUsuarioBD = Util.CREAR_USUARIO;
		} else if (Util.ESTADO_USUARIO_INACTIVO.equals(usuario.getEstado())) {
			usuario.setEstado(Util.ESTADO_USUARIO_ACTIVO);
		}
		try {
			logger.info("Here UPDATE!!");
			actualizarConfiguracionUsuarioBD(usuario, indicadorCreacionUsuarioBD, portletRequest);
		} catch (Exception e) {
			throw new EjecucionException("Error en actualizar configuracion de usuario en bd con uid=" + usuario.getId(), e);
		}		
	}

	public void eliminarConfiguracionUsuario(String idUsuario, String idUsuarioCreaModif) {
		try {
			logger.debug("idUsuario=" + idUsuario);
			logger.debug("idUsuarioCreaModif=" + idUsuarioCreaModif);
			usuarioDao.eliminarConfiguracionUsuario(idUsuario, idUsuarioCreaModif);
			logger.debug("metodo eliminarConfiguracionUsuario exitoso");
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_21, e);
		}		
	}

//	public Collection<LookAndFeelBean> getListLookAndFeel() {		
//		return utilDao.getListLookAndFeel();
//	}

	public void enviarCorreoElectronicoCreacionUsuario(String subject, String body) {
		logger.debug("enviarCorreoElectronicoCreacionUsuario");
		try {			
			Util.enviarCorreoElectronico(subject, body, CORREO_ELECTRONICO_ADMINISTRADOR,
					CORREO_ELECTRONICO_TESTER);
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_23, e);
		}		
	}
			
	public void notificarCreacionUsuario(String idUsuario, String nuevaContrasenia, String subject, String body,
			String correoElectronico, PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.debug("verificando correo electronico");
		if (Util.STRING_VACIO.equals(correoElectronico)) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_34, new Exception());			
		}		
		logger.debug("estableciendo nueva contraseña");
		try {
			gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario, GestionUsuarioGrupoPUMAService.CONTRASENIA,
					nuevaContrasenia, portletRequest, actionRequest);
			
			// actualizando el campo pwdReset a "TRUE"
			gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario, GestionUsuarioGrupoPUMAService.PWD_RESET,
					Util.PWD_RESET_TRUE, portletRequest, actionRequest);
		} catch (Exception e) {			
			throw new UsuarioException(CodigoError.ERROR_USUARIO_35, e);
		}
		logger.debug("enviando correo");
		try {
			Util.enviarCorreoElectronico(subject, body, CORREO_ELECTRONICO_ADMINISTRADOR, correoElectronico);
		} catch (Exception e) {			
			throw new UsuarioException(CodigoError.ERROR_USUARIO_36, e);
		}
	}
	
	public void notificarRecuperacionDeContraseniaUsuario(String idUsuario, String nuevaContrasenia, String subject, String body,
			String correoElectronico, PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.debug("<==== Inicio Method notificarRecuperacionDeContraseniaUsuario ====>");
		logger.debug("verificando correo electronico");
		if (Util.STRING_VACIO.equals(correoElectronico)) {
			throw new UsuarioException(CodigoError.ERROR_USUARIO_34, new Exception());			
		}		
		logger.debug("enviando correo");
		try {
			Util.enviarCorreoElectronico(subject, body, CORREO_ELECTRONICO_ADMINISTRADOR, correoElectronico);
		} catch (Exception e) {			
			throw new UsuarioException(CodigoError.ERROR_USUARIO_36, e);
		}
	}
	
	public void notificarCreacionUsuario(String idUsuario, String nuevaContrasenia, String subject, String body,
			PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.debug("notificarCreacionUsuario");		
		logger.debug("obteniendo direccion de correo electronico de usuario");
		String correoElectronico = gestionUsuarioGrupoPUMAService.getAtributoUsuario(idUsuario, 
				GestionUsuarioGrupoPUMAService.CORREO_ELECTRONICO, portletRequest);
		logger.debug("correoElectronico=" + correoElectronico);
		notificarCreacionUsuario(idUsuario, nuevaContrasenia, subject, body, correoElectronico,
				portletRequest, actionRequest);		
	}
	
//	@SuppressWarnings("unchecked")
//	public String generarFichaCreacionUsuario(String tipoPortal, String tipoArchivo, String idsNegocios,
//			Collection<FirmasNegocioBean> firmas) {		
//		try {
//			// Obteniendo grupos segun negocioss y tipo de portal
//			logger.debug("obteniendo grupos");
//			GruposFichaCreacionUsuarioLoader gruposFichaCreacionUsuarioLoader = new GruposFichaCreacionUsuarioLoader();
//			Collection<Grupo> grupos = grupoDao.getGruposPorNegocio(tipoPortal, idsNegocios);
//			if (grupos.size() == 0) {
//				throw new UsuarioException(CodigoError.ERROR_USUARIO_38, new Exception());
//			}
//			gruposFichaCreacionUsuarioLoader.setGrupos(grupos);
//			logger.debug("verificando archivos para generar reporte (.jasper)");			
//			String reportWebPath = utilDao.getParametroConfiguracion(10);			
//			InputStream inputStreamSubReporteDatosUsuario = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/SubReporteDatosUsuario.jasper");
//			// Verificando si se encontro SubReporteDatosUsuario
//			if (!(inputStreamSubReporteDatosUsuario.available() > 0)) {
//				throw new EjecucionException("SubReporteDatosUsuario no encontrado", new Exception());
//			}
//			logger.debug("SubReporteDatosUsuario obtenido");
//			InputStream inputStreamSubReporteGrupos = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/SubReporteGrupos.jasper");
//			// Verificando si se encontro SubReporteGrupos
//			if (!(inputStreamSubReporteGrupos.available() > 0)) {
//				throw new EjecucionException("SubReporteGrupos no encontrado", new Exception());
//			}
//			logger.debug("SubReporteGrupos obtenido");
//			InputStream inputStreamSubReporteTiposUsuario = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/SubReporteTiposUsuario.jasper");
//			// Verificando si se encontro SubReporteTiposUsuario
//			if (!(inputStreamSubReporteTiposUsuario.available() > 0)) {
//				throw new EjecucionException("SubReporteTiposUsuario no encontrado", new Exception());
//			}
//			logger.debug("SubReporteGrupos obtenido");
//			InputStream inputStreamSubReporteAplicaciones = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/SubReporteAplicaciones.jasper");
//			// Verificando si se encontro SubReporteAplicaciones
//			if (!(inputStreamSubReporteAplicaciones.available() > 0)) {
//				throw new EjecucionException("SubReporteAplicaciones no encontrado", new Exception());
//			}
//			logger.debug("SubReporteAplicaciones obtenido");
//			InputStream inputStreamSubReporteRoles = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/SubReporteRoles.jasper");
//			// Verificando si se encontro SubReporteRoles
//			if (!(inputStreamSubReporteRoles.available() > 0)) {
//				throw new EjecucionException("SubReporteRoles no encontrado", new Exception());
//			}
//			logger.debug("SubReporteRoles obtenido");
//			InputStream inputStreamSubReporteTiposRecursos = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/SubReporteTiposRecursos.jasper");
//			// Verificando si se encontro SubReporteTiposRecursos
//			if (!(inputStreamSubReporteTiposRecursos.available() > 0)) {
//				throw new EjecucionException("SubReporteTiposRecursos no encontrado", new Exception());
//			}
//			logger.debug("SubReporteTiposRecursos obtenido");
//			InputStream inputStreamSubReporteNegocios = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/SubReporteNegocios.jasper");
//			// Verificando si se encontro SubReporteNegocios
//			if (!(inputStreamSubReporteNegocios.available() > 0)) {
//				throw new EjecucionException("SubReporteNegocios no encontrado", new Exception());
//			}
//			logger.debug("SubReporteNegocios obtenido");
//			StringBuffer tempPathArchivo = new StringBuffer();
//			tempPathArchivo.append(JSFUtil.getRealPath("/"));
//			tempPathArchivo.append(reportWebPath);
//			tempPathArchivo.append("/");					
//			logger.debug("estableciendo parametros");
//			// Estableciendo atributos para reporte
//			Map parametros = new HashMap();
//			parametros.put("subReporteDatosUsuario", inputStreamSubReporteDatosUsuario);
//			parametros.put("subReporteGrupos", inputStreamSubReporteGrupos);
//			parametros.put("subReporteTiposUsuario", inputStreamSubReporteTiposUsuario);
//			parametros.put("subReporteAplicaciones", inputStreamSubReporteAplicaciones);
//			parametros.put("subReporteRoles", inputStreamSubReporteRoles);
//			parametros.put("subReporteTiposRecursos", inputStreamSubReporteTiposRecursos);
//			parametros.put("subReporteNegocios", inputStreamSubReporteNegocios);
//			parametros.put("indTipoPortal", tipoPortal);
//			TipoRecursoLoader tipoRecursoLoader = new TipoRecursoLoader();
//			StringBuilder idsGrupos = new StringBuilder();
//			boolean bandera = true;
//			for (Grupo grupo : grupos) {
//				if (bandera) {
//					idsGrupos.append(grupo.getId());
//					bandera = false;
//				} else {
//					idsGrupos.append(Util.SEPARADOR2);
//					idsGrupos.append(grupo.getId());
//				}
//			}
//			logger.debug("idsGrupos=" + idsGrupos.toString());	
//			tipoRecursoLoader.setTiposRecursos(tipoRecursoDao.getTiposRecursosPorGrupos(idsGrupos.toString()));
//			parametros.put("tiposRecursos", new JRBeanCollectionDataSource(tipoRecursoLoader.createBeanCollection()));
//			FirmasNegocioLoader firmasNegocioLoader = new FirmasNegocioLoader();
//			firmasNegocioLoader.setFirmas(firmas);
//			parametros.put("firmas", new JRBeanCollectionDataSource(firmasNegocioLoader.createBeanCollection()));
//			parametros.put("REPORT_CONNECTION", utilDao.getConnection());
//			parametros.put("grupos", new JRBeanCollectionDataSource(gruposFichaCreacionUsuarioLoader.createBeanCollection()));
//			parametros.put("idSubReporteDatosUsuario", Util.ID_SUBREPORTE_DATOS_USUARIO);
//			parametros.put("idSubReporteGrupos", Util.ID_SUBREPORTE_GRUPOS);
//			parametros.put("idSubReporteTiposRecursos", Util.ID_SUBREPORTE_TIPOS_RECURSOS);
//			parametros.put("idSubReporteNegocios", Util.ID_SUBREPORTE_NEGOCIOS);
//			TipoSubReporteLoader tipoSubReporteLoader1 = new TipoSubReporteLoader();
//			tipoSubReporteLoader1.setSubReportes(true);
//			parametros.put("tempSubReporteDatosUsuario", new JRBeanCollectionDataSource(tipoSubReporteLoader1.createBeanCollection()));
//			TipoSubReporteLoader tipoSubReporteLoader2 = new TipoSubReporteLoader();
//			tipoSubReporteLoader2.setSubReportes(false);
//			String nombreArchivo;
//			if (Util.ID_ARCHIVO_PDF.equals(tipoArchivo)) {
//				InputStream inputStreamReporteFichaCreacionUsuario = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/ReporteFichaCreacionUsuarioPDF.jasper");				
//				// Verificando si se encontro ReporteFichaCreacionUsuario
//				if (!(inputStreamReporteFichaCreacionUsuario.available() > 0)) {
//					throw new EjecucionException("ReporteFichaCreacionUsuario no encontrado", new Exception());
//				}
//				logger.debug("ReporteFichaCreacionUsuario obtenido");
//				nombreArchivo = Util.getNombreArchivoFichaCreacionUsuarioPDF();
//				tempPathArchivo.append(nombreArchivo);
//				logger.debug("tempPathArchivo=" + tempPathArchivo.toString());
//				logger.debug("generando PDF");
//				FileOutputStream fileOutputStream = new FileOutputStream(tempPathArchivo.toString());
//				logger.debug("llenando datos");
//				JasperRunManager.runReportToPdfStream(inputStreamReporteFichaCreacionUsuario, fileOutputStream, parametros,
//						new JRBeanCollectionDataSource(tipoSubReporteLoader2.createBeanCollection()));
//				logger.debug("reporte llenado");
//				logger.debug("PDF generado");
//				fileOutputStream.close();				
//			} else {
//				InputStream inputStreamReporteFichaCreacionUsuario = JSFUtil.getExternalContext().getResourceAsStream(reportWebPath  + "/ReporteFichaCreacionUsuarioXLS.jasper");				
//				// Verificando si se encontro ReporteFichaCreacionUsuario
//				if (!(inputStreamReporteFichaCreacionUsuario.available() > 0)) {
//					throw new EjecucionException("ReporteFichaCreacionUsuario no encontrado", new Exception());
//				}
//				logger.debug("ReporteFichaCreacionUsuario obtenido");
//				logger.debug("generando Excel");
//				logger.debug("llenando datos");
//				JasperPrint jasperPrint = JasperFillManager.fillReport(inputStreamReporteFichaCreacionUsuario, parametros,
//						new JRBeanCollectionDataSource(tipoSubReporteLoader2.createBeanCollection()));
//				logger.debug("reporte llenado");
//				nombreArchivo = Util.getNombreArchivoFichaCreacionUsuarioXLS();
//				tempPathArchivo.append(nombreArchivo);
//				logger.debug("tempPathArchivo=" + tempPathArchivo.toString());
//				OutputStream ouputStream = new FileOutputStream(new File(tempPathArchivo.toString()));
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				logger.debug("creando exporterXLS");
//				JRXlsExporter exporterXLS = new JRXlsExporter();
//				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
//				logger.debug("exportando");
//				exporterXLS.exportReport();
//				ouputStream.write(byteArrayOutputStream.toByteArray()); 
//				ouputStream.flush();
//				ouputStream.close();
//				logger.debug("Excel generado");				
//			}
//			return nombreArchivo;
//		} catch (UsuarioException e) {			
//			throw e;
//		} catch (Exception e) {
//			e.printStackTrace();
//			Util.showStackTrace(e);			
//			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
//		}
//	}
	
	public Collection<TipoPortal> getTiposPortal() {
		return utilDao.getTiposPortal();
	}
	
	public Collection<TipoArchivo> getTiposArchivo() {
		return utilDao.getTiposArchivo();
	}	
	
	public Collection<TipoRecurso> getTiposRecursos() {
		return tipoRecursoDao.getTiposRecursos();
	}
		
	public String[] getIdsTiposRecursosPorAplicacion(int idAplicacion) {		
		return tipoRecursoDao.getIdsTiposRecursosPorAplicacion(idAplicacion);
	}
	
	public void updateTiposRecursosPorAplicacion(int idAplicacion, String idsTiposRecursos) {
		tipoRecursoDao.updateTiposRecursosPorAplicacion(idAplicacion, idsTiposRecursos);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<String> enviarMensajeUsuarios(String asunto, String contenido, Grupo grupoSeleccionado, PortletRequest portletRequest) {
		logger.info("enviarMensajeUsuarios");				
		try {
			logger.debug("obteniendo ids de usuarios");
			Collection<String> idsUsuarios = usuarioDao.getIdsUsuariosPorGrupos(grupoSeleccionado.getId());
			if (idsUsuarios.size() == 0) {
				throw new UsuarioException(CodigoError.ERROR_USUARIO_49, new Exception());
			}
			idsUsuarios = filtrarUsuarios(grupoSeleccionado, idsUsuarios);
			if (idsUsuarios.size() == 0) {
				throw new UsuarioException(CodigoError.ERROR_USUARIO_49, new Exception());
			}
			Collection<String> correosElectronicos = new ArrayList<String>();
			logger.debug("armando query");
			StringBuffer query = new StringBuffer();
			boolean bandera = true;
			for (String idUsuario : idsUsuarios) {						
				logger.debug("idUsuario=" + idUsuario);
				if (bandera) {
					query.append("uid='");
					query.append(idUsuario);
					query.append("'");
					bandera = false;
				} else {
					query.append(" or uid='");
					query.append(idUsuario);
					query.append("'");
				}				
			}
			logger.debug("query=" + query.toString());
			logger.debug("obteniendo correos electronicos de usuarios");
			Collection<MetaData> tempCorreosElectronicos = gestionUsuarioGrupoPUMAService.getIdUsuariosPorQueryParaEnvioContrasenia(query.toString(),
					portletRequest);
			if (tempCorreosElectronicos.size() != idsUsuarios.size()) {
				throw new EjecucionException("no coincide el numero de correos electronicos obtenidos con los ids de usuarios",
						new Exception());
			}			
			logger.debug("verificando direccion de correo electronico de usuarios");
			idsUsuarios.clear();
			String[] tempValue = null;
			String tempCorreoElectronico = Util.STRING_VACIO;
			String tempIdUsuario = Util.STRING_VACIO;			
			for(MetaData metaData : tempCorreosElectronicos) {
				tempValue = metaData.getValue().split(Util.SEPARADOR);				
				tempIdUsuario = tempValue[0];
				logger.debug("tempIdUsuario=" + tempIdUsuario);
				tempCorreoElectronico = tempValue[1];				
				logger.debug("tempCorreoElectronico=" + tempCorreoElectronico);
				if (Util.STRING_VACIO.equals(tempCorreoElectronico)) {
					throw new EjecucionException("corre electronico de " + tempIdUsuario + " es vacio", new Exception());
				}
				// agregando id de usuario
				idsUsuarios.add(tempIdUsuario);
				// agregando correo electronico
				correosElectronicos.add(tempCorreoElectronico);				
			}			
			logger.debug("estableciendo contralador de thread para el envio masivo");
			ControladorThreadEmail threadEmail = new ControladorThreadEmail();
			threadEmail.setEnvioDiferenteContenidos(false);			
			threadEmail.setIdUsuarios(idsUsuarios);
			threadEmail.setAsunto(asunto);
			threadEmail.setContenido(contenido);
			threadEmail.setCorreoElectronicoRemitente(CORREO_ELECTRONICO_ADMINISTRADOR);
			threadEmail.setCorreosElectronicosDestinatarios(correosElectronicos);
			Object[] resultado = threadEmail.iniciarEnvioMasivo();
			return (Collection<String>) resultado[0];			
		} catch (UsuarioException e) {			
			throw e;
		} catch (Exception e) {			
			Util.showStackTrace(e);			
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
	}
		
	public Collection<String> getIdsUsuariosPorGrupos(Grupo grupo) {		
		return filtrarUsuarios(grupo, usuarioDao.getIdsUsuariosPorGrupos(grupo.getId()));
	}

	public Object[] enviarContraseniaUsuarios(Collection<String> idsUsuarios, String query,
			String asunto, Map<String, String> tempContenidos, Map<String, String> nuevasContrasenias,
			PortletRequest portletRequest, ActionRequest actionRequest) {
		logger.debug("enviarContraseniaUsuarios");		
		try {						
			logger.debug("query=" + query);
			logger.debug("obteniendo correos electronicos de usuarios");
			Collection<MetaData> tempCorreosElectronicos = gestionUsuarioGrupoPUMAService.getIdUsuariosPorQueryParaEnvioContrasenia(query,
					portletRequest);
			if (tempCorreosElectronicos.size() != idsUsuarios.size()) {
				throw new EjecucionException("no coincide el numero de correos electronicos obtenidos con los ids de usuarios",
						new Exception());
			}
			logger.debug("verificando direccion de correo electronico de usuarios");
			Collection<String> correosElectronicos = new ArrayList<String>();
			Collection<String> contenidos = new ArrayList<String>();
			idsUsuarios.clear();
			String[] tempValue = null;
			String tempCorreoElectronico = Util.STRING_VACIO;
			String tempIdUsuario = Util.STRING_VACIO;
			String tempNuevaContrasenia = Util.STRING_VACIO;			
			String tempContenido = Util.STRING_VACIO;
			for(MetaData metaData : tempCorreosElectronicos) {
				tempValue = metaData.getValue().split(Util.SEPARADOR);
				tempIdUsuario = tempValue[0];
				logger.debug("tempIdUsuario=" + tempIdUsuario);
				logger.debug("cambiando contrasenia de usuario");
				tempNuevaContrasenia = nuevasContrasenias.get(tempIdUsuario.toUpperCase());
				logger.debug("tempNuevaContrasenia=" + tempNuevaContrasenia);
				try {
					gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(tempIdUsuario,
							GestionUsuarioGrupoPUMAService.CONTRASENIA, tempNuevaContrasenia,
							portletRequest, actionRequest);
				} catch (Exception e) {			
					throw new EjecucionException("no se pudo cambiar contrasenia de usuario " + tempIdUsuario, e);
				}
				tempCorreoElectronico = tempValue[1];				
				logger.debug("tempCorreoElectronico=" + tempCorreoElectronico);
				if (Util.STRING_VACIO.equals(tempCorreoElectronico)) {
					throw new EjecucionException("corre electronico de " + tempIdUsuario + " es vacio", new Exception());
				}				
				idsUsuarios.add(tempIdUsuario);
				correosElectronicos.add(tempCorreoElectronico);
				tempContenido = tempContenidos.get(tempIdUsuario.toUpperCase());
				logger.debug("tempContenido=" + tempContenido);
				tempContenido = tempContenido.replaceFirst("#" + tempIdUsuario.toUpperCase() + "#", tempValue[1]);
				logger.debug("tempContenido=" + tempContenido);
				tempContenido = tempContenido.replaceFirst("#TRATO#", tempValue[2]);
				logger.debug("tempContenido=" + tempContenido);
				tempContenido = tempContenido.replaceFirst("#NOMBRECOMPLETO#", tempValue[3]);
				logger.debug("tempContenido=" + tempContenido);
				contenidos.add(tempContenido);				
			}			
			logger.info("estableciendo contralador de thread para el envio masivo");
			ControladorThreadEmail threadEmail = new ControladorThreadEmail();
			threadEmail.setEnvioDiferenteContenidos(true);			
			threadEmail.setIdUsuarios(idsUsuarios);
			threadEmail.setAsunto(asunto);
			threadEmail.setContenidos(contenidos);
			threadEmail.setCorreoElectronicoRemitente(CORREO_ELECTRONICO_ADMINISTRADOR);
			threadEmail.setCorreosElectronicosDestinatarios(correosElectronicos);
			return threadEmail.iniciarEnvioMasivo();
		} catch (UsuarioException e) {			
			throw e;
		} catch (Exception e) {			
			Util.showStackTrace(e);			
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
	}
	
	public Object[] getUniqueNamePagina(String idUsuario, PortletRequest portletRequest) {
		Object[] resultado = new Object[3];
		String tipo =  IndicadorTipoPortal.getInstance().getIndPortal(portletRequest);
		logger.debug("==>"+idUsuario+"|"+tipo);
//		6_HOSVSKV0ACLT70ASJASDP11081
		resultado[1] = usuarioDao.getUniqueNamePagina(idUsuario,tipo);
		int idUniqueNamePaginaBienvenida;
		if (IndicadorTipoPortal.getInstance().isIntranet(portletRequest))
			idUniqueNamePaginaBienvenida = 11;
		else
			idUniqueNamePaginaBienvenida = 12;	
		resultado[2] = utilDao.getParametroConfiguracion(idUniqueNamePaginaBienvenida).trim();
//		resultado[1] = "6_HOSVSKV0ACLT70ASJASDP11081";
//		resultado[2] = "6_HOSVSKV0ACLT70ASJASDP11081";
		if(resultado[1].equals(resultado[2]))
			resultado[0] = new Boolean(true);
		else
			resultado[0] = new Boolean(false);
		if(logger.isDebugEnabled()){
			logger.debug("resultado[0]: ["+resultado[0]+"]");
			logger.debug("resultado[1]: ["+resultado[1]+"]");
			logger.debug("resultado[2]: ["+resultado[2]+"]");
		}
		return resultado;
	}
	
	public boolean verificarDominioEmail(String dominioEmail) {
		return utilDao.verificarDominioEmail(dominioEmail);
	}
	
//	public Object[] cargarConfiguracionUsuarios(String indPortal, String idUsuarioCreador, PortletRequest portletRequest,
//			ActionRequest actionRequest) {
//		logger.info("cargarConfiguracionUsuarios");
//		try {
//			logger.info("obteniendo usuarios para ser cargados");
//			Object[] resultados = new Object[4];
//			Collection<Usuario> usuarios = usuarioDao.getUsuarios(IndicadorTipoPortal.getInstance().getIndPortal(portletRequest));
//			int numUsuarios = usuarios.size();
//			logger.debug("numUsuarios=" + numUsuarios);
//			int numUsuariosCargados = 0;
//			Collection<Grupo> tempGrupos = null;
//			Grupo[] grupos = new Grupo[0];
//			String tempIdGrupo = "";
//			Collection<RecursoUsuario> tempRecursosUsuario = null;
//			String[] recursosUsuario = new String[0];
//			String[] recursosFavoritoUsuario = new String[0];
//			Collection<Grupo> gruposPortal = gestionUsuarioGrupoPUMAService.getGrupos(portletRequest);
//			Collection<FilaUsuarioBean> usuariosNoCargados = new ArrayList<FilaUsuarioBean>();
//			FilaUsuarioBean usuarioNoCargado = null;
//			Collection<String[]> usuariosCargados = new ArrayList<String[]>();
//			String[] usuarioCargado = null;
//			StringBuilder idUsuariosCarga = new StringBuilder();
//			boolean existeGrupoEnPortal;
//			boolean isIntranet = IndicadorTipoPortal.getInstance().isIntranet(portletRequest);
//			if (numUsuarios > 0) {
//				for (Usuario usuario : usuarios) {
//					logger.debug("usuario=" + usuario.getId() + ", " + usuario.getNombres()
//							 + ", " + usuario.getApellidos() + ", " + usuario.getTipoUsuario().getId());						
//					try {
//						logger.info("validando usuario");
//						if (usuario.getId() == null) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo id es null");
//						}
//						if ("".equals(usuario.getId())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo id es vacio");
//						}
//						String identifier = gestionUsuarioGrupoPUMAService.existeUsuarioLDAP(usuario.getId(), portletRequest);
//						if (isIntranet) {
//							if ("".equals(identifier)) {							
//								throw new EjecucionException("no se pudo crear configuracion de usuario porque no existe en ldap");
//							}
//						} else {
//							if (!"".equals(identifier)) {
//								throw new EjecucionException("no se pudo crear configuracion de usuario porque ya existe en ldap");
//							}
//						}
//						if (existeUsuarioBD(usuario.getId())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque ya existe en base de datos");
//						}
//						if ("".equals(usuario.getNombres())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo nombres es vacio");
//						}
//						if ("".equals(usuario.getApellidos())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo apellidos es vacio");
//						}
//						if ("".equals(usuario.getTrato())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo trato es vacio");
//						}
//						if ("".equals(usuario.getCorreoElectronico())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo correoElectronico es vacio");
//						}
//						if ("".equals(usuario.getTipoDocumento().getId())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo tipoDocumento es vacio");
//						}
//						if ("".equals(usuario.getNumeroDocumento())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo numeroDocumento es vacio");
//						}
//						if ("".equals(usuario.getTipoUsuario().getId())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo tipoUsuario es vacio");
//						}
//						if ("".equals(usuario.getPais().getId())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo idPais es vacio");
//						}
//						if ("".equals(usuario.getIdCompania())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo idCompania es vacio");
//						}
//						if ("".equals(usuario.getCompania())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo compania es vacio");
//						}
//						if ("".equals(usuario.getIdLookAndFeel())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo idLookAndFeel es vacio");
//						}
//						if ("".equals(usuario.getLookAndFeel())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo lookAndFeel es vacio");
//						}
//						if ("".equals(usuario.getSolicitante())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo solicitante es vacio");
//						}
//						if ("".equals(usuario.getCompaniaSolicitante())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo companiaSolicitante es vacio");
//						}
//						if ("".equals(usuario.getAreaSolicitante())) {
//							throw new EjecucionException("no se pudo crear configuracion de usuario porque atributo areaSolicitante es vacio");
//						}
//						logger.info("obteniendo grupos de usuario");
//						tempGrupos = grupoDao.getGruposPorUsuario(usuario.getId());
//						if (tempGrupos.size() > 0) {
//							grupos = new Grupo[tempGrupos.size()];
//							int i = 0;
//							for (Grupo grupo : tempGrupos) {									
//								existeGrupoEnPortal = false;
//								for (Grupo grupoPortal : gruposPortal) {
//									if (grupoPortal.getDescripcion().equals(grupo.getDescripcion())) {
//										existeGrupoEnPortal = true;
//										break;
//									}
//								}
//								if (!existeGrupoEnPortal) {
//									throw new EjecucionException("no se pudo crear configuracion de usuario " + usuario.getId()
//											+ " porque grupo "+ grupo.getDescripcion() +" no existe en portal");
//								}
//								tempIdGrupo = grupoDao.getIdGrupo(grupo.getDescripcion(), IndicadorTipoPortal.getInstance().getIndPortal(portletRequest));
//								if ("".equals(tempIdGrupo)) {
//									throw new EjecucionException("no se pudo crear configuracion de usuario " + usuario.getId()
//											+ " porque id de grupo "+ grupo.getDescripcion() +" es vacio");
//								}									
//								grupo.setId(tempIdGrupo);
//								grupos[i++] = grupo;																	
//							}
//							
//						}
//						usuario.setGrupos(grupos);
//						logger.debug("numGrupos=" + usuario.getGrupos().length);
//						logger.info("obteniendo roles de usuario");
//						usuario.setRoles(getRolesUsuarioCargaMasiva(usuario.getId()));
//						logger.debug("numRolesUsuario=" + usuario.getRoles().length);
//						logger.info("obteniendo recursos de usuario");
//						tempRecursosUsuario = recursoUsuarioDao.getRecursoUsuario(usuario.getId());
//						if (tempRecursosUsuario.size() > 0) {
//							recursosUsuario = new String[tempRecursosUsuario.size()];
//							int i = 0;
//							int j = 0;
//							for (RecursoUsuario recursoUsuario : tempRecursosUsuario) {	
//								recursosUsuario[i++] = recursoUsuario.toString();
//								if (recursoUsuario.getFavorito() == 1) {
//									j++;
//								}
//							}
//							logger.info("obteniendo recursos favoritos");
//							recursosFavoritoUsuario = new String[j];
//							j = 0;
//							for (RecursoUsuario recursoUsuario : tempRecursosUsuario) {									
//								if (recursoUsuario.getFavorito() == 1) {
//									recursosFavoritoUsuario[j++] = recursoUsuario.toString();
//								}
//							}
//						}
//						usuario.setRecursos(recursosUsuario);
//						logger.debug("numRecursos=" + usuario.getRecursos().length);
//						usuario.setRecursosFavoritos(recursosFavoritoUsuario);
//						logger.debug("numRecursosFavoritos=" + usuario.getRecursosFavoritos().length);
//						logger.debug("nombreTipoUsuario=" + usuario.getTipoUsuario().getNombre());
//						if (isIntranet) {					
//							logger.info("carga de usuario para intranet");
//							Grupo[] gruposUsuarioActuales = gestionUsuarioGrupoPUMAService.getGrupos(usuario.getId(), portletRequest);
//							for (Grupo grupoUsuarioActual : gruposUsuarioActuales) {
//								logger.debug("grupoUsuarioActual=" + grupoUsuarioActual.getId() + ", " + grupoUsuarioActual.getDescripcion());
//							}
//							Usuario tempUsuario = gestionUsuarioGrupoPUMAService.getUsuarioSinLogin(usuario.getId(), false, portletRequest);
//							usuario.setId(tempUsuario.getId());
//							usuario.setCN(tempUsuario.getCN());
//							usuario.setEstado(Util.STRING_VACIO);
//							actualizarConfiguracionUsuario(usuario, gruposUsuarioActuales, portletRequest, actionRequest);						
//						} else {
//							logger.info("carga de usuario para extranet");
//							crearConfiguracionUsuario(usuario, portletRequest, actionRequest);				// VERIFICAR			
//						}						
//						usuarioDao.actualizarInformacionUsuarioCargado(usuario.getId());						
//						if (isIntranet) {
//							usuarioCargado = new String[1];
//							usuarioCargado[0] = usuario.getId();
//						} else {
//							usuarioCargado = new String[2];
//							usuarioCargado[0] = usuario.getCorreoElectronico();
//							usuarioCargado[1] = usuario.getContrasenia();
//						}
//						usuariosCargados.add(usuarioCargado);
//						if (!"".equals(idUsuariosCarga.toString())) {
//							idUsuariosCarga.append(Util.SEPARADOR2);							
//						}
//						idUsuariosCarga.append(usuario.getId().toUpperCase());
//						numUsuariosCargados++;
//						logger.info("usuario creado");
//					} catch (Exception e) {			
//						Util.showStackTrace(e);
//						usuarioNoCargado = new FilaUsuarioBean();
//						usuarioNoCargado.setIdUsuario(usuario.getId());
//						usuarioNoCargado.setNombresUsuario(usuario.getNombres());
//						usuarioNoCargado.setApellidosUsuario(usuario.getApellidos());
//						usuarioNoCargado.setCorreoElectronicoUsuario(usuario.getCorreoElectronico());
//						usuarioNoCargado.setMensajeError(e.getMessage());
//						if (!"".equals(idUsuariosCarga.toString())) {
//							idUsuariosCarga.append(Util.SEPARADOR2);							
//						}
//						idUsuariosCarga.append(usuario.getId().toUpperCase());
//						idUsuariosCarga.append(Util.SEPARADOR);
//						idUsuariosCarga.append(usuarioNoCargado.getMensajeError());
//						usuariosNoCargados.add(usuarioNoCargado);						
//					}
//					//logger.info("forzando fin de loop");
//					//break;
//				}				
//			}	
//			logger.debug("numUsuariosCargados=" + numUsuariosCargados);
//			logger.debug("idUsuariosCargados=" + idUsuariosCarga.toString());
//			int idCarga = Util.NUMERO_VACIO;
//			if (!"".equals(idUsuariosCarga.toString())) {
//				logger.info("registrando carga de usuarios");
//				idCarga = cargaDao.registrarCarga(idUsuariosCarga.toString(), idUsuarioCreador, indPortal);
//			}
//			String[] parametrosResultado = {"" + numUsuariosCargados, "" + numUsuarios};
//			resultados[0] = parametrosResultado;
//			resultados[1] = usuariosNoCargados;
//			resultados[2] = usuariosCargados;
//			resultados[3] = new Integer(idCarga);
//			return resultados;
//		} catch (UsuarioException e) {			
//			throw e;
//		} catch (Exception e) {			
//			Util.showStackTrace(e);			
//			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
//		}		
//	}
	
//	public Collection<FilaTipoRecursoPreferenciaBean> getTipoRecursoPreferencia(String idUsuario) {		
//		logger.debug("getTipoRecursoPreferencia");
//		logger.debug("idUsuario: " + idUsuario);				
//		Collection<FilaTipoRecursoPreferenciaBean> filasTipoRecursoPreferencia = new ArrayList<FilaTipoRecursoPreferenciaBean>();
//		setProxyWebService();
//		for (TipoRecurso tipoRecurso : DaoFactory.getInstance().getTipoRecursoDao().getTiposRecursosPorUsuario(idUsuario)) {
//			FilaTipoRecursoPreferenciaBean filaTipoRecursoPreferenciaBean = new FilaTipoRecursoPreferenciaBean();
//			filaTipoRecursoPreferenciaBean.setIdTipoRecurso(tipoRecurso.getId());
//			filaTipoRecursoPreferenciaBean.setNombreTipoRecurso(tipoRecurso.getNombre());
//			logger.debug("idTipoRecurso" + tipoRecurso.getId());
//			filaTipoRecursoPreferenciaBean.setRecursoFavorito(DaoFactory.getInstance().getRecursoUsuarioDao().getIdRecursoUsuarioPredeterminado(idUsuario.toUpperCase(), tipoRecurso.getId()));
//			logger.debug("RecursoFavorito: " + filaTipoRecursoPreferenciaBean.getRecursoFavorito());
//			filaTipoRecursoPreferenciaBean.setRecursosAsignados(JSFUtil.toSelectItems(getRecursosUsuario(Util.STRING_VACIO + tipoRecurso.getId(),
//					idUsuario.toUpperCase()), false));
//			filasTipoRecursoPreferencia.add(filaTipoRecursoPreferenciaBean);
//		}
//		logger.debug("filasTipoRecursoPreferencia=" + filasTipoRecursoPreferencia.size());
//		return filasTipoRecursoPreferencia;
//	}

//	public void updatePreferencias(String idUsuario, String[] tipos_recurso, String[] recursos) {
//		logger.debug("idUsuario: " + idUsuario);
//		logger.debug("tipos_recurso: " + tipos_recurso.length);
//		logger.debug("recursos: " + recursos.length);
//		
//		if (DaoFactory.getInstance().getTipoRecursoDao().updatePreferencias(idUsuario, tipos_recurso, recursos)) {
//			throw new EjecucionException("No se pudo actualizar preferencias del usuario " + idUsuario, new Exception());
//		}
//	}
	
	public Collection<Carga> getCargas(String indPortal) {
		return cargaDao.getCargas(indPortal);
	}
	
	public void eliminarCarga(String indPortal, int idCarga, String idUsuarioModificador, PortletRequest portletRequest,
			ActionRequest actionRequest) {
		logger.info("eliminarCarga");
		logger.debug("idCarga=" + idCarga);
		logger.info("obteniendo id de usuarios cargados masivamente a eliminar en ldap");
		Collection<String> idUsuariosCargadosParaEliminar = cargaDao.getIdUsuariosCargados(idCarga);
		logger.debug("numUsuariosCargadosParaEliminar: " + idUsuariosCargadosParaEliminar.size());
		logger.info("eliminando usuarios cargados masivamente de base de datos");
		cargaDao.eliminarCarga(idCarga, idUsuarioModificador);
		if (Util.IND_PORTAL_EXTRANET.equals(indPortal)) {			
			logger.info("eliminando usuarios cargados masivamente de ldap");
			for (String idUsuarioCargadosParaEliminar : idUsuariosCargadosParaEliminar) {
				logger.debug("idUsuarioCargadosParaEliminar=" + idUsuarioCargadosParaEliminar);
				gestionUsuarioGrupoPUMAService.eliminarUsuarioLDAP(idUsuarioCargadosParaEliminar,
						portletRequest, actionRequest);
			}
		}		
	}
	
//	public Object[] getInfoUsuario(String idUsuario, PortletRequest portletRequest) {
//		logger.info("getInfoUsuario");
//		Object[] infoUsuario = new Object[3];
//		try {
//			logger.info("obteniendo usuario");
//			Usuario usuario = gestionUsuarioGrupoPUMAService.getUsuario(idUsuario, true, portletRequest);
//			logger.info("obteniendo grupos de usuario");
//			usuario.setGrupos(gestionUsuarioGrupoPUMAService.getGrupos(idUsuario, portletRequest));
//			logger.info("obteniendo roles de usuario");
//			infoUsuario[0] = usuario;
//			infoUsuario[1] = getFilasAplicacionesUsuario(usuario.getGrupos(), idUsuario, portletRequest);
//			logger.info("obteniendo recursos de usuario");
//			Collection<FilaResumenTipoRecursoUsuarioBean> filasResumenTiposRecursos = new ArrayList<FilaResumenTipoRecursoUsuarioBean>();
//			FilaResumenTipoRecursoUsuarioBean filaResumenTipoRecursoUsuarioBean = null;
//			Collection<TipoRecurso> tiposRecurso = tipoRecursoDao.getTiposRecursosPorUsuario(idUsuario);
//			StringBuffer nombreRecursosUsuario = null;
//			boolean bandera;
//			if (tiposRecurso.size() > 0) {
//				setProxyWebService();			
//				for (TipoRecurso tipoRecurso : tiposRecurso) {
//					logger.debug("tipoRecurso=" + tipoRecurso.getValue() + ", " + tipoRecurso.getNombre());
//					filaResumenTipoRecursoUsuarioBean = new FilaResumenTipoRecursoUsuarioBean();
//					filaResumenTipoRecursoUsuarioBean.setNombreTipoRecurso(tipoRecurso.getNombre());
//					nombreRecursosUsuario = new StringBuffer();
//					bandera = true;
//					try {
//						for(Recurso recurso : getRecursosUsuario(tipoRecurso.getValue(), idUsuario.toUpperCase())) {
//							logger.debug("recurso=" + recurso.getId() + ", " + recurso.getNombre());
//							if (bandera) {
//								nombreRecursosUsuario.append(recurso.getNombre());
//								bandera = false;
//							} else {
//								nombreRecursosUsuario.append(Util.SEPARADOR2);
//								nombreRecursosUsuario.append(recurso.getNombre());
//							}						
//						}					
//					} catch (Exception e) {
//						throw new EjecucionException("no se pudo obtener recursos de usuario " + idUsuario +
//								" del tipo de recurso " + filaResumenTipoRecursoUsuarioBean.getNombreTipoRecurso(), e);					
//					}
//					filaResumenTipoRecursoUsuarioBean.setNombresRecursosAsignados(nombreRecursosUsuario.toString());
//					filasResumenTiposRecursos.add(filaResumenTipoRecursoUsuarioBean);
//				}
//			}
//			infoUsuario[2] = filasResumenTiposRecursos;
//			return infoUsuario;
//		} catch (Exception e) {						
//			throw new UsuarioException(CodigoError.ERROR_USUARIO_55, e);
//		}		
//	}
	
	public String generarIdUsuario(String correoElectronico, PortletRequest portletRequest) {
		logger.info("generarIdUsuario");
		try {
			int posicion = correoElectronico.indexOf("@");
			if (posicion == -1) {
				throw new EjecucionException("correo electronico no valido");
			}
			String idUsuarioGenerado = correoElectronico.substring(0, posicion);
			if(!Util.STRING_VACIO.equals(gestionUsuarioGrupoPUMAService.existeUsuarioLDAP(idUsuarioGenerado, portletRequest))) {
				Collection<MetaData> idUsuarios = gestionUsuarioGrupoPUMAService.getIdUsuariosPorQueryParaEnvioContrasenia("uid='" + idUsuarioGenerado + "*'", portletRequest);
				logger.debug("numeroUsuarios=" + idUsuarios.size());
				Collection<Integer> contadores = new ArrayList<Integer>();				
				String tempContador;
				int contador;
				logger.info("obteniendo numeros de autogenerados");
				for (MetaData idUsuario : idUsuarios) {
					logger.debug("idUsuario=" + idUsuario);
					tempContador = idUsuario.getLabel().replace(idUsuarioGenerado, Util.STRING_VACIO);
					if (!Util.STRING_VACIO.equals(tempContador)) {
						contador = Util.parseInt(tempContador);
						if (Util.NUMERO_VACIO != contador) {
							logger.debug("contador=" + contador);
							contadores.add(contador);
						}
					}
				}
				logger.info("obteniendo el numero mayor");
				if (contadores.size() > 0) {
					Iterator<Integer> iteratorContadores = contadores.iterator();
					int mayor = iteratorContadores.next();
					while (iteratorContadores.hasNext()) {
						contador = iteratorContadores.next();
						if (mayor < contador) {
							mayor = contador;
						}
					}
					logger.debug("mayor=" + mayor);
					idUsuarioGenerado = idUsuarioGenerado + (mayor + 1);
				} else {
					idUsuarioGenerado = idUsuarioGenerado + 1;
				}			
			}
			logger.debug("idUsuarioGenerado=" + idUsuarioGenerado);
			return idUsuarioGenerado;
		} catch (EjecucionException e) {
			throw e;
		} catch (UsuarioException e) {
			throw e;
		} catch (Exception e) {
			throw new EjecucionException("no se pudo generar id de usuario");
		}		
	}	

	public boolean existeUsuarioBD(String idUsuario) {
		logger.info("existeUsuarioBD");
		return usuarioDao.verificarUsuario(idUsuario);
	}	
	
	public void enviarCorreoElectronicoResumen(String asunto, String contenido) {
		logger.debug("enviarCorreoElectronicoResumen");
		try {
			Util.enviarCorreoElectronico(asunto, contenido, CORREO_ELECTRONICO_ADMINISTRADOR, CORREO_ELECTRONICO_TESTER);
		} catch (Exception e) {			
			logger.error("no se pudo enviar correo electronico de resumen");
		}
	}
	
	public RolUsuario[] getRolesUsuario(String idUsuario) {
		logger.info("getRolesUsuario");
		Collection<RolUsuario> rolesUsuario = rolUsuarioDao.getRolUsuario(idUsuario);
		RolUsuario[] tempRolesUsuario = new RolUsuario[0];
		if (rolesUsuario.size() > 0) {
			tempRolesUsuario = new RolUsuario[rolesUsuario.size()];
			tempRolesUsuario = rolesUsuario.toArray(tempRolesUsuario);
		}
		logger.debug("numRolesUsuario=" + tempRolesUsuario.length);
		return tempRolesUsuario;
	}
	
	public RolUsuario[] getRolesUsuarioCargaMasiva(String idUsuario) {
		logger.info("getRolesUsuarioCargaMasiva");
		Collection<RolUsuario> rolesUsuario = rolUsuarioDao.getRolUsuarioCargaMasiva(idUsuario);
		RolUsuario[] tempRolesUsuario = new RolUsuario[0];
		if (rolesUsuario.size() > 0) {
			tempRolesUsuario = new RolUsuario[rolesUsuario.size()];
			tempRolesUsuario = rolesUsuario.toArray(tempRolesUsuario);
		}
		logger.debug("numRolesUsuario=" + tempRolesUsuario.length);
		return tempRolesUsuario;
	}
	
	public Collection<Grupo> dividirGrupos(Collection<Grupo> grupos) {
		logger.info("dividirGrupos");
		Collection<Grupo> tempGrupos = new ArrayList<Grupo>();		
		int numSubGrupos = -1;
		Collection<String> usuarios = null;
		String tempDescripcion = null;
		for (Grupo grupo : grupos) {
			logger.debug("grupo=" + grupo.getDescripcion());
			usuarios = usuarioDao.getIdsUsuariosPorGrupos(grupo.getId());
			numSubGrupos = usuarios.size() / LIMITE_USUARIOS_POR_GRUPO;
			if (usuarios.size() % LIMITE_USUARIOS_POR_GRUPO > 0)
				numSubGrupos++;
			logger.debug("numSubGrupos=" + numSubGrupos);
			if (numSubGrupos == 0) {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(grupo.getDescripcion());
				stringBuffer.append("(1)");
				grupo.setDescripcion(stringBuffer.toString());				
				tempGrupos.add(grupo);
			} else {
				tempDescripcion = grupo.getDescripcion();
				for (int i = 1; i <= numSubGrupos; i++) {				
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append(tempDescripcion);
					stringBuffer.append("(");
					stringBuffer.append(i);
					stringBuffer.append(")");
					grupo.setDescripcion(stringBuffer.toString());
					Grupo tempGrupo = new Grupo();
					tempGrupo.setId(grupo.getId());
					tempGrupo.setDescripcion(grupo.getDescripcion());
					tempGrupo.setIndPortal(grupo.getIndPortal());
					tempGrupo.setLabel(grupo.getLabel());
					tempGrupo.setValue(grupo.getValue());
					logger.debug("tempGrupo=" + tempGrupo.getDescripcion());
					tempGrupos.add(tempGrupo);
				}
			}
		}
		return tempGrupos;
	}

	public Object[] cargarConfiguracionUsuarios(String indPortal,
			String idUsuarioCreador, PortletRequest portletRequest,
			ActionRequest actionRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getInfoUsuario(String idUsuario,
			PortletRequest portletRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updatePreferencias(String idUsuario, String[] tipos_recurso,
			String[] recursos) {
		// TODO Auto-generated method stub
		
	}

	public Collection<Recurso> getRecursosUsuario(String idTipoRecurso,
			String idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Usuario> getUsuarios(String tipoPortal) {
		return usuarioDao.getUsuarios(tipoPortal);
	}
	
}