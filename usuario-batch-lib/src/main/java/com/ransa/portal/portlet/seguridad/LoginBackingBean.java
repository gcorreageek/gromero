package com.ransa.portal.portlet.seguridad;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.crypto.SecretKey;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.model.SelectItem;
import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.apache.log4j.Logger;

import com.ibm.wps.l2.urlspihelper.portletrequest.PortletURLHelper;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.exception.UsuarioException;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Usuario;
import com.ransa.portal.portlet.PageCodeBase;
import com.ransa.portal.portletservice.util.VariablesPortalRANSA;
import com.ransa.portal.service.GestionUsuarioGrupoPUMAService;
import com.ransa.portal.service.GestionUsuarioService;
import com.ransa.portal.service.SeguridadService;
import com.ransa.portal.service.factory.ServiceFactory;
import com.ransa.portal.util.CodigoError;
import com.ransa.portal.util.DesEncrypter;
import com.ransa.portal.util.IndicadorTipoPortal;
import com.ransa.portal.util.JSFUtil;
import com.ransa.portal.util.Util;

public class LoginBackingBean extends PageCodeBase {

	private static Logger logger = Logger.getLogger(LoginBackingBean.class);	
	
	private static final String ID_USUARIO_INTRANET = "txtIdUsuarioIntranet";
	private static final String ID_USUARIO_EXTRANET = "txtIdUsuarioExtranet";
	
	// Servicios
	private SeguridadService seguridadService;
	private GestionUsuarioGrupoPUMAService gestionUsuarioGrupoPUMAService;
	private GestionUsuarioService gestionUsuarioService;
	
	private String idUsuario;
	private String correoElectronicoUsuario;
	private String contraseniaUsuario;
	private String contraseniaAntigua;
	private String contraseniaNueva1;
	private String contraseniaNueva2;
	private String mensajeError;
	private String preguntaSecreta;
	private String respuestaSecreta;
	private String host;
	private boolean primerCambioContrasenia;
	private String idiomaSeleccionado;
	private Collection<SelectItem> idiomas;
	private boolean inicioSesionImplicito;
	
	protected HtmlForm form_CambioContrasenia1;
	protected HtmlInputText txtIdUsuarioIntranet;
	protected HtmlInputSecret txtContraseniaUsuario;
	protected HtmlInputSecret txtContraseniaNueva1;
	protected HtmlInputSecret txtContraseniaNueva2;
	protected HtmlInputSecret txtContraseniaAntigua;
	protected HtmlForm form_Login;
	protected HtmlInputText txtPreguntaSecreta;
	protected HtmlInputText txtRespuestaSecreta;
	protected HtmlForm form_CambioContrasenia2;
	protected HtmlInputText txtIdUsuarioExtranet;
	protected HtmlSelectOneMenu cmbIdioma;

	public LoginBackingBean() throws PortletException, IOException {
		logger.debug("Constructor");
		JSFUtil.setParametroSesion(Util.IS_LOGIN, new Boolean(false), null);
		seguridadService = ServiceFactory.getInstance().getSeguridadService();
		gestionUsuarioGrupoPUMAService = ServiceFactory.getInstance().getGestionUsuarioGrupoPUMAService();
		gestionUsuarioService = ServiceFactory.getInstance().getGestionUsuarioService();
		inicializar();
	}
	
	private void inicializar() {
		logger.info("inicializar");
		idUsuario = Util.STRING_VACIO;
		contraseniaUsuario = Util.STRING_VACIO;
		contraseniaAntigua = Util.STRING_VACIO;
		contraseniaNueva1 = Util.STRING_VACIO;
		contraseniaNueva2 = Util.STRING_VACIO;
		primerCambioContrasenia = false;
		inicioSesionImplicito = false;		
	}
		
	public String getHost() {
		logger.info("getHost");
		JSFUtil.setParametroSesion(Util.IS_LOGIN, new Boolean(false), null);
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getCorreoElectronicoUsuario() {
		return correoElectronicoUsuario;
	}

	public void setCorreoElectronicoUsuario(String correoElectronicoUsuario) {
		this.correoElectronicoUsuario = correoElectronicoUsuario;
	}

	public String getContraseniaUsuario() {
		return contraseniaUsuario;
	}

	public void setContraseniaUsuario(String constraseniaUsuario) {
		this.contraseniaUsuario = constraseniaUsuario;
	}
	
	public String getContraseniaAntigua() {
		return contraseniaAntigua;
	}
		
	public void setContraseniaAntigua(String contraseniaAntigua) {
		this.contraseniaAntigua = contraseniaAntigua;
	}
	
	public String getContraseniaNueva1() {
		return contraseniaNueva1;
	}

	public void setContraseniaNueva1(String contraseniaNueva1) {
		this.contraseniaNueva1 = contraseniaNueva1;
	}

	public String getContraseniaNueva2() {
		return contraseniaNueva2;
	}

	public void setContraseniaNueva2(String contraseniaNueva2) {
		this.contraseniaNueva2 = contraseniaNueva2;
	}

	public String getMensajeError() {
		return mensajeError;
	}
	
	public String getMensajeErrorCampoIdUsuarioObligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_02);
	}
	
	public String getMensajeErrorCampoContraseniaUsuarioObligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_03);
	}
	
	public String getMensajeErrorCampoContraseniaAntiguaObligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_08);
	}
	
	public String getMensajeErrorCampoContraseniaNueva1Obligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_09);
	}
	
	public String getMensajeErrorCampoContraseniaNueva2Obligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_10);
	}
	
	public String getMensajeErrorCampoPreguntaSecretaObligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_19);
	}
	
	public String getMensajeErrorCampoRespuestaSecretaObligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_20);
	}
	
	public String getPreguntaSecreta() {
		return preguntaSecreta;
	}

	public void setPreguntaSecreta(String preguntaSecreta) {
		this.preguntaSecreta = preguntaSecreta;
	}

	public String getRespuestaSecreta() {
		return respuestaSecreta;
	}

	public void setRespuestaSecreta(String respuestaSecreta) {
		this.respuestaSecreta = respuestaSecreta;
	}
		
	public String getIdiomaSeleccionado() {
		return idiomaSeleccionado;
	}

	public void setIdiomaSeleccionado(String idiomaSeleccionado) {
		this.idiomaSeleccionado = idiomaSeleccionado;
	}

	public Collection<SelectItem> getIdiomas() {
		return idiomas;
	}

	public boolean getIsExtranet() {
		PortletRequest portletRequest = (PortletRequest)getRequest();
		String tipoPortal = portletRequest.getPreferences().getValue(SeguridadService.NOM_PREF_IND_TIPO_PORTAL, Util.IND_PORTAL_INTRANET);
		if (Util.IND_PORTAL_EXTRANET.equals(tipoPortal)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getTxtIdUsuario() {
		if (getIsExtranet()) {
			return ID_USUARIO_EXTRANET;
		} else {
			return ID_USUARIO_INTRANET;
		}
	}
		
	public boolean isPrimerCambioContrasenia() {
		return primerCambioContrasenia;
	}

	public String getMensajeErrorCampoIdiomaObligatorio() {
		return getMensaje(CodigoError.ERROR_SEGURIDAD_21);
	}
	
	protected HtmlInputText getTxtPreguntaSecreta() {
		if (txtPreguntaSecreta == null) {
			txtPreguntaSecreta = (HtmlInputText) findComponentInRoot("txtPreguntaSecreta");
		}
		return txtPreguntaSecreta;
	}

	protected HtmlInputText getTxtRespuestaSecreta() {
		if (txtRespuestaSecreta == null) {
			txtRespuestaSecreta = (HtmlInputText) findComponentInRoot("txtRespuestaSecreta");
		}
		return txtRespuestaSecreta;
	}
	
	protected HtmlForm getForm_CambioContrasenia1() {
		if (form_CambioContrasenia1 == null) {
			form_CambioContrasenia1 = (HtmlForm) findComponentInRoot("form_CambioContrasenia1");
		}
		return form_CambioContrasenia1;
	}

	protected HtmlInputText getTxtIdUsuarioIntranet() {
		if (txtIdUsuarioIntranet == null) {
			txtIdUsuarioIntranet = (HtmlInputText) findComponentInRoot("txtIdUsuarioIntranet");
		}
		return txtIdUsuarioIntranet;
	}

	protected HtmlInputSecret getTxtContraseniaUsuario() {
		if (txtContraseniaUsuario == null) {
			txtContraseniaUsuario = (HtmlInputSecret) findComponentInRoot("txtContraseniaUsuario");
		}
		return txtContraseniaUsuario;
	}
	
	protected HtmlInputSecret getTxtContraseniaNueva1() {
		if (txtContraseniaNueva1 == null) {
			txtContraseniaNueva1 = (HtmlInputSecret) findComponentInRoot("txtContraseniaNueva1");
		}
		return txtContraseniaNueva1;
	}

	protected HtmlInputSecret getTxtContraseniaNueva2() {
		if (txtContraseniaNueva2 == null) {
			txtContraseniaNueva2 = (HtmlInputSecret) findComponentInRoot("txtContraseniaNueva2");
		}
		return txtContraseniaNueva2;
	}

	protected HtmlInputSecret getTxtContraseniaAntigua() {
		if (txtContraseniaAntigua == null) {
			txtContraseniaAntigua = (HtmlInputSecret) findComponentInRoot("txtContraseniaAntigua");
		}
		return txtContraseniaAntigua;
	}
	
	protected HtmlForm getForm_Login() {
		if (form_Login == null) {
			form_Login = (HtmlForm) findComponentInRoot("form_Login");
		}
		return form_Login;
	}
	
	protected HtmlForm getForm_CambioContrasenia2() {
		if (form_CambioContrasenia2 == null) {
			form_CambioContrasenia2 = (HtmlForm) findComponentInRoot("form_CambioContrasenia2");
		}
		return form_CambioContrasenia2;
	}
	
	protected HtmlInputText getTxtIdUsuarioExtranet() {
		if (txtIdUsuarioExtranet == null) {
			txtIdUsuarioExtranet = (HtmlInputText) findComponentInRoot("txtIdUsuarioExtranet");
		}
		return txtIdUsuarioExtranet;
	}
	
	protected HtmlSelectOneMenu getCmbIdioma() {
		if (cmbIdioma == null) {
			cmbIdioma = (HtmlSelectOneMenu) findComponentInRoot("cmbIdioma");
		}
		return cmbIdioma;
	}
	
	public String doLoginAction() {		
		logger.debug("doLoginAction");
		String resultado = "error";
//		String idUsuario = gestionUsuarioGrupoPUMAService.getIdUsuario(correoElectronicoUsuario, portletRequest, false);
//		seguridadService.loginUsuario(idUsuario, contraseniaUsuario, portletRequest, portletResponse);
		mensajeError = Util.STRING_VACIO;
		if (Util.STRING_VACIO.equals(mensajeError)) {
			
		}else{
			
		} 
		logger.debug("resultado=" + resultado);
		return resultado;
	}
//	public String doLoginAction() {		
//		logger.debug("doLoginAction");
//		String resultado = "error";
////		String idUsuario = gestionUsuarioGrupoPUMAService.getIdUsuario(correoElectronicoUsuario, portletRequest, false);
////		seguridadService.loginUsuario(idUsuario, contraseniaUsuario, portletRequest, portletResponse);
//		mensajeError = Util.STRING_VACIO;
//		if (Util.STRING_VACIO.equals(mensajeError)) {
//			
//		}else{
//			
//		}
//		
////		String resultado = "error";
////		PortletRequest portletRequest = (PortletRequest) getRequest();
////		PortletResponse portletResponse = (PortletResponse) getResponse();
////		try {
////			if (!inicioSesionImplicito) {
////				String tipoPortal = portletRequest.getPreferences().getValue(SeguridadService.NOM_PREF_IND_TIPO_PORTAL, Util.IND_PORTAL_INTRANET);
////				logger.debug("obteniendo uid de usuario segun correo electronico");
////				logger.debug("tipoPortal: " + tipoPortal);
////				logger.debug("Util.IND_PORTAL_EXTRANET: " + Util.IND_PORTAL_EXTRANET);
////				if (Util.IND_PORTAL_EXTRANET.equals(tipoPortal)) {
////					correoElectronicoUsuario = correoElectronicoUsuario.trim();
////					logger.debug("correoElectronicoUsuario=" + correoElectronicoUsuario + ".");
////					try {
////						idUsuario = gestionUsuarioGrupoPUMAService.getIdUsuario(correoElectronicoUsuario, portletRequest, false);
////						
////					} catch (Exception e) {					
////						logger.debug("idUsuario=" + idUsuario);					
////						idUsuario = correoElectronicoUsuario.substring(0, correoElectronicoUsuario.length() > 20 ? 20 : correoElectronicoUsuario.length());
////						logger.debug("idUsuario=" + idUsuario);
////						throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_06, e);
////					}
////				} else {
////					idUsuario = idUsuario.trim();
////					if (idUsuario.length() > 20) {
////						logger.debug("idUsuario=" + idUsuario);
////						idUsuario = idUsuario.substring(0, idUsuario.length() > 20 ? 20 : idUsuario.length());
////						logger.debug("idUsuario=" + idUsuario);
////						throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_06);
////					}
////				}
////			}			
////			logger.debug("idUsuario=" + idUsuario);
////			logger.debug("constraseniaUsuario=" + contraseniaUsuario);
////			logger.info("logueando...");
////			seguridadService.loginUsuario(idUsuario, contraseniaUsuario, portletRequest, portletResponse);
////		} catch (UsuarioException e) {
////			mensajeError = getMensaje(e.getMessage());
////			if (e.getParametro() == Util.FORZADO_CAMBIO_CONTRASENIA) {
////				logger.debug("guardando contraseniaUsuarioEncriptada en sesion");
////				portletRequest.getPortletSession().setAttribute(Util.CONTRASENIA_ENCRIPTADA,
////						Util.encriptar(contraseniaUsuario));
////				logger.debug("idUsuario=" + idUsuario);
////				Usuario usuario = gestionUsuarioGrupoPUMAService.getUsuarioSinLogin(idUsuario, true, portletRequest);
////				preguntaSecreta = usuario.getPreguntaSecreta();
////				respuestaSecreta = usuario.getRespuestaSecreta();
////				if (usuario.getIdioma() == null) {
////					idiomaSeleccionado = Util.STRING_VACIO;
////				} else {
////					idiomaSeleccionado = usuario.getIdioma().getId();
////				}				
////				if (Util.STRING_VACIO.equals(preguntaSecreta) || Util.STRING_VACIO.equals(respuestaSecreta)) {
////					primerCambioContrasenia = true;
////				} else {
////					primerCambioContrasenia = false;
////				}
////				resultado = "mostrarCambioContrasenia1";
////			}
////		} catch (Exception e) {
////			Util.showStackTrace(e);
////			mensajeError = getMensaje(CodigoError.ERROR_GENERAL_01);
////			inicializar();
////		}
////		logger.debug("mensajeError=" + mensajeError);
//		// Registrando Evento
////		RegistrarEventos registrarEventos = new RegistrarEventosProxy();
////		if (Util.STRING_VACIO.equals(mensajeError)) {
////			logger.debug("login correcto");			
////			try {
////				boolean registroEventoExitoso = registrarEventos.setEvento(4, ServiceFactory.getInstance().getAplicacionService().getIdAplicacionLogin(),
////						1, idUsuario.toUpperCase(), "No hubo error al iniciar sesion de usuario");
////				logger.debug("registroEventoExitoso=" + registroEventoExitoso);
////				logger.debug("evento registrado");
////			} catch (Exception e) {
////				Util.showStackTrace(e);
////				logger.error("no se pudo registrar evento de login correcto");	
////			}
////			redireccionarHaciaPaginaBienvenida(portletRequest, portletResponse);			
////		} else {
////			logger.debug("login incorrecto");
////			try {
////				boolean registroEventoExitoso = registrarEventos.setEvento(6, ServiceFactory.getInstance().getAplicacionService().getIdAplicacionLogin(),
////						1, idUsuario.toUpperCase(), "Mensaje de error: " + mensajeError);
////				logger.debug("registroEventoExitoso=" + registroEventoExitoso);
////				logger.debug("evento registrado");
////			} catch (Exception e) {
////				Util.showStackTrace(e);
////				logger.error("no se pudo registrar evento de login incorrecto");	
////			}
////		}
//		logger.debug("resultado=" + resultado);
//		return resultado;
//	}
	
	/**
	 * Metodo que se encarga de realizar la redireccion a la pagina
	 * de bienvenida
	 * @param portletRequest
	 * @param portletResponse
	 */
//	private static String[] gmineria = new String[]{ "GESolminWeb", "GESolgpWeb", "GESupervisorSIL" };
	
//	private boolean isMineriaUser(String idUsuario,PortletRequest portletRequest){
//		// Se debe redireccionar si y solo sí el usuario tiene por lo menos un grupo indicado en gmineria
//		
//		Grupo[] grupos = gestionUsuarioGrupoPUMAService.getGrupos(idUsuario, portletRequest);
//		
//		boolean isMineriaUser = false;
//		String[] gruposDescription = new String[grupos.length];
//		for(int i = 0 ; i < grupos.length ; i++){
//			logger.debug("moises grupo["+ i + "] = " + grupos[i].getDescripcion());
//			gruposDescription[i] = grupos[i].getDescripcion();
//		}
//		
//		Arrays.sort(gruposDescription);
//		
//		for(int i = 0 ; i < gmineria.length ; i++){
//			if(Arrays.binarySearch(gruposDescription,gmineria[i]) >= 0){
//				logger.debug("moises gmineria["+ i + "] = " + gmineria[i] + " is no a valid group");
//				isMineriaUser = true;
//				break;
//			}
//		}
//		
//		return isMineriaUser;
//	}
	
//	private void redireccionarHaciaPaginaBienvenida(PortletRequest portletRequest,PortletResponse portletResponse) {
//		logger.debug("verificando si se debe redireccionar");
//		
//		//-----------------------------------------------------/
//		// This line is used as a flag to redirect to a new URL only for the MINERIA users
//		boolean isMineriaUser = isMineriaUser(idUsuario, portletRequest);
//		logger.debug("moises isMineriaUser = " + isMineriaUser);
//		//-----------------------------------------------------*/
//		
//		
//		Object[] tempResultado = gestionUsuarioService.getUniqueNamePagina(idUsuario, portletRequest);
//		String uniqueNamePagina = (String) tempResultado[1];
//		String uniqueNamePaginaBienvenida = (String) tempResultado[2];
//		if (!Util.STRING_VACIO.equals(uniqueNamePagina)) {
//			logger.debug("uniqueNamePagina=" + uniqueNamePagina);				
//			try {
//				logger.info("obteniendo URL de pagina de portal");
//				String URL = PortletURLHelper.generateUrl(uniqueNamePagina, null, null, true, portletRequest, portletResponse);
//				String URLPaginaBienvenida = PortletURLHelper.generateUrl(uniqueNamePaginaBienvenida, null, null, true, portletRequest, portletResponse);
//				logger.debug("host=" + host);
//				if (host == null || Util.STRING_VACIO.equals(host)) {
//					throw new EjecucionException("host no valido");
//				}
//				URL = host + URL;
//				URLPaginaBienvenida = host + URLPaginaBienvenida;
//				URL = URL.replaceFirst("portal", "myportal");
//				URLPaginaBienvenida = URLPaginaBienvenida.replaceFirst("portal", "myportal");
//				
//				if(isMineriaUser){
//					URLPaginaBienvenida = URLPaginaBienvenida.replaceFirst("extranet", "solmin");
//					logger.debug("moises URLPaginaBienvenida = " + URLPaginaBienvenida);
//				}
//				
//				logger.debug("URL=" + URL);
//				logger.debug("URLPaginaBienvenida=" + URLPaginaBienvenida);
//				boolean bandera = (Boolean)tempResultado[0];
//				logger.debug("bandera=" + bandera);					
//				IndicadorTipoPortal indicadorTipoPortal = IndicadorTipoPortal.getInstance();
//				VariablesPortalRANSA variablesPortalRANSA = indicadorTipoPortal.getVariablesPortalRANSA(portletRequest);
//				if (bandera){
//					//TODO: N.A: Borrar
//					//variablesPortalRANSA.setURLPaginaInicial(null);
//					variablesPortalRANSA.setURLPaginaInicial(URL);
//				}else
//					variablesPortalRANSA.setURLPaginaInicial(URL);				
//				logger.debug("URLPaginaInicial=" + variablesPortalRANSA.getURLPaginaInicial());
//				indicadorTipoPortal.setIsIntranet(portletRequest, variablesPortalRANSA.getVariables());
//				JSFUtil.getFacesContext().responseComplete();
//				JSFUtil.getExternalContext().redirect(URLPaginaBienvenida);
//			} catch (Exception e) {
//				logger.error("Error: ",e);
//				Util.showStackTrace(e);
//				logger.error("no se pudo generar URL de pagina inicial para redireccionar");
//			}
//		}
//	}
	
//	public String doCargarAction() {
//		logger.info("doCargarAction");
//		logger.debug("idUsuario=" + idUsuario + ".");
//		mensajeError = Util.STRING_VACIO;
//		String resultado = "error";
//		PortletRequest portletRequest = (PortletRequest) getRequest();
//		PortletResponse portletResponse = (PortletResponse) getResponse();
//		String contraseniaUsuarioEncriptada = (String)portletRequest.getPortletSession().getAttribute(Util.CONTRASENIA_ENCRIPTADA);
//		logger.info("obteniendo contraseniaUsuarioEncriptada de sesion");
//		logger.debug("contraseniaUsuarioEncriptada=" + contraseniaUsuarioEncriptada + ".");
//		try {
//			if (!contraseniaUsuarioEncriptada.equals(Util.encriptar(contraseniaAntigua))) {			
//				throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_11, new Exception());
//			}
//			if (!contraseniaNueva1.equals(contraseniaNueva2)) {
//				throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_12, new Exception());
//			}
//			if (contraseniaUsuario.equals(contraseniaNueva1)) {
//				throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_13, new Exception());
//			}
//			if (contraseniaNueva1.length() < 8) {
//				throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_14, new Exception());
//			}
//			if (contraseniaUsuarioEncriptada.equals(Util.encriptar(contraseniaNueva1))) {
//				throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_15, new Exception());
//			}
//			logger.debug("obteniendo SecretKey");
//			SecretKey secretKey = (SecretKey)portletRequest.getPortletSession().getAttribute("SECRET_KEY", PortletSession.APPLICATION_SCOPE);
//			DesEncrypter desEncrypter  = new DesEncrypter(secretKey);
//			logger.debug("encriptando");
//			String contraseniaAntiguaEncriptada = desEncrypter.encrypt(contraseniaAntigua);
//			logger.debug("contraseniaAntiguaEncriptada=" + contraseniaAntiguaEncriptada);
//			portletRequest.getPortletSession().setAttribute(Util.CONTRASENIA_ENCRIPTADA, contraseniaAntiguaEncriptada);
//			String nuevaContraseniaEncriptada = desEncrypter.encrypt(contraseniaNueva1);
//			logger.debug("nuevaContraseniaEncriptada=" + nuevaContraseniaEncriptada);
//			portletRequest.getPortletSession().setAttribute(Util.NUEVA_CONTRASENIA_ENCRIPTADA, nuevaContraseniaEncriptada);
//			logger.debug("primerCambioContrasenia: "+primerCambioContrasenia);
//			if (primerCambioContrasenia) {
//				logger.debug("Dentro de primer cambio de contrasenia");
//				idiomas = JSFUtil.toSelectItems(gestionUsuarioService.getIdiomas(), false);				
//				if (Util.STRING_VACIO.equals(idiomaSeleccionado)) {
//					idiomas.add(new SelectItem(Util.STRING_VACIO, Util.STRING_VACIO));
//				}				
//				resultado = "mostrarCambioContrasenia2";
//			} else {		
//				logger.debug("No es el primer cambio de contrasenia");
//				ActionRequest actionRequest = (ActionRequest)getRequest();
//				// Guardando contrasenia
//				seguridadService.cambiarContrasenia(idUsuario, contraseniaAntigua, contraseniaNueva1, false,
//						IndicadorTipoPortal.getInstance().isIntranet(portletRequest), portletRequest, actionRequest);
//				
//				// actualizando el campo pwdReset a ""
//				gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario, GestionUsuarioGrupoPUMAService.PWD_RESET,
//						Util.STRING_VACIO, portletRequest, actionRequest);
//				// iniciando sesion
//				inicioSesionImplicito = true;
//				contraseniaUsuario = contraseniaNueva1;
//				/*doLoginAction();
//				resultado = "mostrarLogin";*/
//				redireccionarHaciaPaginaBienvenida(portletRequest, portletResponse);
//				//resultado ="";
//				resultado ="mostrarBienvenida";
//			}			
//		} catch (UsuarioException e) {
//			mensajeError = getMensaje(e.getMessage());			
//		} catch (Exception e) {
//			Util.showStackTrace(e);
//			mensajeError = getMensaje(CodigoError.ERROR_GENERAL_01);
//			inicializar();
//		}
//		return resultado;
//	}
	
//	public String doGuardarAction() {		
//		logger.debug("doGuardarAction");
//		String resultado = "error";
//		try {
//			if (Util.STRING_VACIO.equals(idUsuario)) {
//				throw new EjecucionException("idUsuario es vacio en sesion", new Exception());
//			}
//			logger.debug("idUsuario=" + idUsuario);
//			PortletRequest portletRequest = (PortletRequest)getRequest();
//			PortletResponse portletResponse = (PortletResponse) getResponse();
//			SecretKey secretKey = (SecretKey)portletRequest.getPortletSession().getAttribute("SECRET_KEY", PortletSession.APPLICATION_SCOPE);
//			DesEncrypter desEncrypter  = new DesEncrypter(secretKey);
//			String contraseniaAntiguaEncriptada = Util.getAtributo(portletRequest.getPortletSession().getAttribute(Util.CONTRASENIA_ENCRIPTADA));
//			String contraseniaAntigua = Util.STRING_VACIO;
//			if (Util.STRING_VACIO.equals(contraseniaAntiguaEncriptada)) {
//				throw new EjecucionException("contraseniaAntiguaEncriptada es vacio en sesion", new Exception());
//			} else {
//				logger.debug("contraseniaAntiguaEncriptada=" + contraseniaAntiguaEncriptada);
//				contraseniaAntigua = desEncrypter.decrypt(contraseniaAntiguaEncriptada);
//				if (Util.STRING_VACIO.equals(contraseniaAntigua)) {
//					throw new EjecucionException("contraseniaAntigua es vacio", new Exception());
//				} else {
//					logger.debug("contraseniaAntigua=" + contraseniaAntigua);
//				}					
//			}
//			String nuevaContraseniaEncriptada = Util.getAtributo(portletRequest.getPortletSession().getAttribute(Util.NUEVA_CONTRASENIA_ENCRIPTADA));
//			String nuevaContrasenia = Util.STRING_VACIO;
//			if (Util.STRING_VACIO.equals(nuevaContraseniaEncriptada)) {
//				throw new EjecucionException("nuevaContraseniaEncriptada es vacio en sesion", new Exception());
//			} else {
//				logger.debug("nuevaContraseniaEncriptada=" + nuevaContraseniaEncriptada);
//				nuevaContrasenia = desEncrypter.decrypt(nuevaContraseniaEncriptada);
//				if (Util.STRING_VACIO.equals(nuevaContrasenia)) {
//					throw new EjecucionException("nuevaContrasenia es vacio", new Exception());
//				} else {
//					logger.debug("nuevaContrasenia=" + nuevaContrasenia);
//				}					
//			}
//			preguntaSecreta = preguntaSecreta.trim();
//			logger.debug("preguntaSecreta=" + preguntaSecreta + ".");
//			respuestaSecreta = respuestaSecreta.trim();
//			logger.debug("respuestaSecreta=" + respuestaSecreta + ".");
//			logger.debug("obteniendo infoBean de session ");
//			ActionRequest actionRequest = (ActionRequest)getRequest();
//			// Guardando contrasenia
//			seguridadService.cambiarContrasenia(idUsuario, contraseniaAntigua, nuevaContrasenia, false,
//					IndicadorTipoPortal.getInstance().isIntranet(portletRequest), portletRequest, actionRequest);
//			
//			// Guardando pregunta secreta
//			logger.debug("preguntaSecreta=" + preguntaSecreta + ".");
//			gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario, GestionUsuarioGrupoPUMAService.PREGUNTA_SEGRETA,
//					preguntaSecreta, portletRequest, actionRequest);
//			// Guardando respuesta secreta
//			logger.debug("respuestaSecreta=" + respuestaSecreta + ".");
//			gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario, GestionUsuarioGrupoPUMAService.RESPUESTA_SECRETA,
//					respuestaSecreta, portletRequest, actionRequest);
//			
//			// Guardando idioma
//			logger.debug("idiomaSeleccionado=" + idiomaSeleccionado + ".");
//			gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario, GestionUsuarioGrupoPUMAService.ID_IDIOMA,
//					idiomaSeleccionado, portletRequest, actionRequest);
//			
//			// actualizando el campo pwdReset a ""
//			gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario, GestionUsuarioGrupoPUMAService.PWD_RESET,
//					Util.STRING_VACIO, portletRequest, actionRequest);
//			// iniciando sesion
//			inicioSesionImplicito = true;
//			contraseniaUsuario = nuevaContrasenia;
//			/*doLoginAction();			
//			resultado = "mostrarLogin";*/
//			redireccionarHaciaPaginaBienvenida(portletRequest, portletResponse);
//			resultado ="mostrarBienvenida";
//		} catch (UsuarioException e) {
//			mensajeError = getMensaje(e.getMessage());			
//		} catch (Exception e) {
//			Util.showStackTrace(e);
//			mensajeError = getMensaje(CodigoError.ERROR_GENERAL_01);
//			inicializar();
//		}		
//		return resultado;
//	}

//	public String doCancelarAction() {
//		inicializar();
//		mensajeError = Util.STRING_VACIO;
//		return "mostrarLogin";
//	}
	
}