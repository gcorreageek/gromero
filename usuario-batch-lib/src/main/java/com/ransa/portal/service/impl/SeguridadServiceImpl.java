package com.ransa.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import org.apache.log4j.Logger;

import com.ibm.portal.auth.exceptions.AuthenticationException;
import com.ibm.portal.auth.exceptions.AuthenticationFailedException;
import com.ibm.portal.auth.exceptions.PasswordInvalidException;
import com.ibm.portal.auth.exceptions.PortletLoginDisabledException;
import com.ibm.portal.auth.exceptions.SessionTimeOutException;
import com.ibm.portal.auth.exceptions.SystemLoginException;
import com.ibm.portal.auth.exceptions.UserIDInvalidException;
import com.ibm.portal.portlet.service.login.LoginHome;
import com.ibm.portal.portlet.service.login.LoginService;
import com.ibm.websphere.security.WSSecurityException;

import com.ransa.portal.dao.factory.DaoFactory;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.exception.UsuarioException;
import com.ransa.portal.portletservice.util.VariablesPortalRANSA;
import com.ransa.portal.service.GestionUsuarioGrupoPUMAService;
import com.ransa.portal.service.SeguridadService;
import com.ransa.portal.service.factory.ServiceFactory;
import com.ransa.portal.service.locator.PortletServiceLocator;
import com.ransa.portal.util.CodigoError;
import com.ransa.portal.util.DesEncrypter;
import com.ransa.portal.util.IndicadorTipoPortal;
import com.ransa.portal.util.Util;
import com.ransa.portal.util.ConexionLDAPTDS;

public class SeguridadServiceImpl implements SeguridadService {

	private static Logger logger = Logger.getLogger(SeguridadServiceImpl.class);
	private static final String FORZAR_CAMBIAR_CONTRASENIA = "1";
	private static final String AUTENTICACION_INCORRECTA_USUARIO = "2";
		
	/** Variable para el servicio LOGIN */
	private LoginHome loginHome;
	
	private GestionUsuarioGrupoPUMAService gestionUsuarioGrupoPUMAService;
	
	public SeguridadServiceImpl() {
		try {
			// Obteniendo el home del servicio LOGIN
			loginHome = PortletServiceLocator.getInstance().getLoginHome();
		} catch (EjecucionException e) {
			throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_01, e);
		} catch (Exception e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}
				
		gestionUsuarioGrupoPUMAService = ServiceFactory.getInstance().getGestionUsuarioGrupoPUMAService();
	}
	
	@SuppressWarnings("unchecked")
	public void loginUsuario(String idUsuario, String contraseniaUsuario, PortletRequest portletRequest,
			PortletResponse portletResponse) {
		logger.debug("loginUsuario");
		logger.debug("idUsuario=" + idUsuario + ".");
		logger.debug("contraseniaUsuario=" + contraseniaUsuario + ".");
		logger.debug("loginUsuario");
		boolean cambiarContrasenia = false;
		String mensajeErrorException = Util.STRING_VACIO;
		String mensajeErrorUsuario = Util.STRING_VACIO;
		String indicadorTipoPortal = portletRequest.getPreferences().getValue(NOM_PREF_IND_TIPO_PORTAL, Util.IND_PORTAL_INTRANET);
		logger.debug("indicadorTipoPortal=" + indicadorTipoPortal);
		// Verificando si existe usuario
		String identifier = gestionUsuarioGrupoPUMAService.existeUsuarioLDAP(idUsuario, portletRequest);
		logger.debug("identifier:"+identifier);
		if (!Util.STRING_VACIO.equals(identifier)) {			
			// Obteniendo estado de usuario
			logger.debug("obteniendo estado de usuario");
			String tempEstadoUsuario = Util.STRING_VACIO;
			try {				
				tempEstadoUsuario = DaoFactory.getInstance().getUsuarioDao().getEstado(idUsuario);
			} catch(Exception e1) {
				logger.debug("Error al obtener estado de usuario por primer vez");
			}
			logger.debug("tempEstadoUsuario1:"+tempEstadoUsuario);
			if (Util.STRING_VACIO.equals(tempEstadoUsuario)) {
				logger.debug("Intentando por segunda vez obtener estado de usuario");
				tempEstadoUsuario = DaoFactory.getInstance().getUsuarioDao().getEstado(idUsuario);
			}			
			logger.debug("tempEstadoUsuario2:"+tempEstadoUsuario);
			if (Util.ESTADO_USUARIO_ACTIVO.equals(tempEstadoUsuario)) {
				logger.debug("El estado de usuario es activo");
				LoginService loginService = (LoginService) loginHome.getLoginService(portletRequest, portletResponse);
				logger.debug("loginService obtenido");
				try {
					// Estableciendo variable de sesion
					logger.debug("guardando variable infoBean en sesion");
					VariablesPortalRANSA variablesPortalRANSA = new VariablesPortalRANSA(new HashMap<String, Object>());
					variablesPortalRANSA.setTipoPortal(indicadorTipoPortal);
					variablesPortalRANSA.setIdUsuario(idUsuario);
					logger.debug("encriptando contrasenia");
					Object object = portletRequest.getPortletSession().getAttribute("SECRET_KEY", PortletSession.APPLICATION_SCOPE);
					SecretKey secretKey;
					if (object == null) {
						logger.info("SECRET_KEY es null");
						secretKey = DesEncrypter.createSecretKey();
						portletRequest.getPortletSession().setAttribute("SECRET_KEY", secretKey, PortletSession.APPLICATION_SCOPE);
					} else {
						secretKey = (SecretKey)object;
					}					
					DesEncrypter desEncrypter  = new DesEncrypter(secretKey);					
					String contraseniaEncriptada = desEncrypter.encrypt(contraseniaUsuario);
					logger.debug("contraseniaEncriptada=" + contraseniaEncriptada);
					
					variablesPortalRANSA.setContraseniaUsuario(contraseniaEncriptada);
//					IndicadorTipoPortal.getInstance().setIsIntranet(portletRequest, variablesPortalRANSA.getVariables());
//					if (indicadorTipoPortal.equals(Util.IND_PORTAL_EXTRANET)) {
//						logger.debug("validando flag pwdReset en el portal de extranet");
//						String pwdReset = gestionUsuarioGrupoPUMAService.getAtributoUsuario(idUsuario,
//		    					GestionUsuarioGrupoPUMAService.PWD_RESET, portletRequest);
//		    			logger.debug("pwdReset=" + pwdReset);		    			
//		    			if (pwdReset != null && pwdReset.toUpperCase().trim().equals("TRUE")) {
//		    				throw new EjecucionException("Usuario con uid=" + idUsuario + " de TDS debe cambiar su contrasenia" ,
//		    						new Exception());
//		    			}
//		    		}					
					Map contextMap = new HashMap();
					contextMap.put(LoginService.DO_RESUME_SESSION_KEY, new Boolean(false));					
					// Ejecutamos Login
					logger.debug("ejecutando login");
					loginService.login(idUsuario, contraseniaUsuario.toCharArray(), contextMap, null);					
					logger.debug("login ejecutado");
			    } catch(PortletLoginDisabledException plde) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: PortletLoginDisabledException");
					mensajeErrorException = Util.getStackTrace(plde);
		        } catch(PasswordInvalidException pie) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: PasswordInvalidException");
					mensajeErrorException = Util.getStackTrace(pie);
		        } catch(SessionTimeOutException ste) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: SessionTimeOutException");
					mensajeErrorException = Util.getStackTrace(ste);
		        } catch(UserIDInvalidException uie) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: UserIDInvalidException");
					mensajeErrorException = Util.getStackTrace(uie);
				} catch (AuthenticationFailedException afe) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: AuthenticationFailedException");
					mensajeErrorException = Util.getStackTrace(afe);
				} catch(AuthenticationException ae) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: AuthenticationException");
					mensajeErrorException = Util.getStackTrace(ae);
		        } catch(SystemLoginException sle) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: SystemLoginException");
					mensajeErrorException = Util.getStackTrace(sle);
		        } catch(com.ibm.portal.auth.exceptions.LoginException le) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: com.ibm.portal.auth.exceptions.LoginException");
					mensajeErrorException = Util.getStackTrace(le);
				} catch(javax.security.auth.login.LoginException le2) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: javax.security.auth.login.LoginException");
					mensajeErrorException = Util.getStackTrace(le2);
				} catch(WSSecurityException wse) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: WSSecurityException");
					mensajeErrorException = Util.getStackTrace(wse);
				} catch(EjecucionException ee) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: EjecucionException");
					mensajeErrorException = AUTENTICACION_INCORRECTA_USUARIO;
//					ConexionLDAPTDS conexionLDAP = new ConexionLDAPTDS(identifier);
//					if (conexionLDAP.autenticacionUsuarioLDAP(contraseniaUsuario)) {
//						mensajeErrorException = FORZAR_CAMBIAR_CONTRASENIA;
//					} else {
//						mensajeErrorException = AUTENTICACION_INCORRECTA_USUARIO;
//					}										
				} catch (Exception e) {
					logger.debug("Ha ocurrido una excepcion al iniciar sesion: Exception");
					logger.error("[Exception]",e);
					e.printStackTrace();
					mensajeErrorException = Util.getStackTrace(e);
				} finally {
					logger.debug("entrando a finally");
					if (!Util.STRING_VACIO.equals(mensajeErrorException)) {
						if (Util.IND_PORTAL_INTRANET.equals(indicadorTipoPortal)) {
							// Validando para portal de intranet
							logger.debug("validando usuario de intranet con uid=" + idUsuario);		    				
							// Buscando codigo de error
							String codigoErrorLDAP = Util.getCodigoErrorLDAP(mensajeErrorException);
							if (!Util.STRING_VACIO.equals(codigoErrorLDAP)) {
								logger.debug("codigoErrorLDAP=" + codigoErrorLDAP);			    			
				    			if (CodigoError.CODIGO_ERROR_AD_USUARIO_NO_ENCONTRADO.equals(codigoErrorLDAP)) {
				    				logger.debug("cuenta de usuario no encontrada");
				    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_06;
				    			} else if (CodigoError.CODIGO_ERROR_AD_CREDENCIALES_INVALIDAS.equals(codigoErrorLDAP)) {
				    				logger.debug("credenciales invalidas");
				    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_06;
				    			} else if (CodigoError.CODIGO_ERROR_AD_NO_PERMITIDO_LOGUEARSE_EN_ESTE_TIEMPO.equals(codigoErrorLDAP)) {
				    				logger.debug("no esta permitido loguearse en este tiempo");
				    				mensajeErrorUsuario = CodigoError.ERROR_GENERAL_01;
				    			} else if (CodigoError.CODIGO_ERROR_AD_NO_PERMITIDO_LOGUEARSE_DESDE_ESTA_ESTACION_TRABAJO.equals(codigoErrorLDAP)) {
				    				logger.debug("no esta permitido loguearse desde esta estacion de trabajo");
				    				mensajeErrorUsuario = CodigoError.ERROR_GENERAL_01;
				    			} else if (CodigoError.CODIGO_ERROR_AD_PASSWORD_EXPIRADO.equals(codigoErrorLDAP)) {
				    				logger.debug("contrasenia expirada");
				    				cambiarContrasenia = true;
				    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_07;
				    			} else if (CodigoError.CODIGO_ERROR_AD_CUENTA_DESHABILITADA.equals(codigoErrorLDAP)) {
				    				logger.debug("cuenta de usuario deshabilitada");
				    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_16;
				    			} else if (CodigoError.CODIGO_ERROR_AD_CUENTA_EXPIRADA.equals(codigoErrorLDAP)) {
				    				logger.debug("cuenta expirada");
				    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_17;
				    			} else if (CodigoError.CODIGO_ERROR_AD_USUARIO_DEBE_RESETEAR_PASSWORD.equals(codigoErrorLDAP)) {
				    				logger.debug("usuario debe cambiar contrasenia");
				    				cambiarContrasenia = true;
				    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_07;
				    			} else if (CodigoError.CODIGO_ERROR_AD_CUENTA_USUARIO_BLOQUEADA.equals(codigoErrorLDAP)) {
				    				logger.debug("cuenta de usuario bloqueada");
				    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_18;
				    			} else if (mensajeErrorException.indexOf(CodigoError.CODIGO_ERROR_LDAP_CREDENCIALES_INVALIDAS) != -1) {
									logger.debug("contrasenia incorrecta para wpsadmin");
									mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_06;
				    			} else {
				    				logger.debug("codigoErrorLDAP no soportado en los casos establecidos");
				    				mensajeErrorUsuario = CodigoError.ERROR_GENERAL_01;
				    			}							
							} else {
								logger.debug("codigoErrorLDAP vacio");
			    				mensajeErrorUsuario = CodigoError.ERROR_GENERAL_01;
							}
						} else {
							// Validando para portal de extranet
							logger.debug("validando usuario de extranet con uid=" + idUsuario);
							if (FORZAR_CAMBIAR_CONTRASENIA.equals(mensajeErrorException)) {
								logger.debug("usuario debe cambiar contrasenia (pwdReset)");
			    				cambiarContrasenia = true;
			    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_07;
							} else if (AUTENTICACION_INCORRECTA_USUARIO.equals(mensajeErrorException) ||
									mensajeErrorException.indexOf(CodigoError.CODIGO_ERROR_TDS_CREDENCIALES_INVALIDAS) != -1) {
								logger.debug("credenciales invalidas");
			    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_06;
			    			} else if (mensajeErrorException.indexOf(CodigoError.CODIGO_ERROR_TDS_CUENTA_BLOQUEADA) != -1) {
			    				logger.debug("cuenta de usuario bloqueada");
			    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_18;
			    			} else if (mensajeErrorException.indexOf(CodigoError.CODIGO_ERROR_TDS_PASSWORD_EXPIRADO) != -1) {
			    				logger.debug("contrasenia expirada");
			    				cambiarContrasenia = true;
			    				mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_07;
			    			} else if (mensajeErrorException.indexOf(CodigoError.CODIGO_ERROR_LDAP_CREDENCIALES_INVALIDAS) != -1) {
								logger.debug("contrasenia incorrecta para wpsadmin");
								mensajeErrorUsuario = CodigoError.ERROR_SEGURIDAD_06;
			    			} else {
			    				logger.debug("codigoErrorLDAP no soportado en los casos establecidos");
			    				mensajeErrorUsuario = CodigoError.ERROR_GENERAL_01;
			    			}
						}
					}					
			    }
				logger.debug("mensajeErrorUsuario=" + mensajeErrorUsuario);
				if (!Util.STRING_VACIO.equals(mensajeErrorUsuario)) {
					if (cambiarContrasenia && Util.IND_PORTAL_EXTRANET.equals(indicadorTipoPortal)) {
						throw new UsuarioException(mensajeErrorUsuario, new Exception(),
								Util.FORZADO_CAMBIO_CONTRASENIA);
					} else {
						throw new UsuarioException(mensajeErrorUsuario, new Exception());
					}
				}				
			} else if (Util.ESTADO_USUARIO_INACTIVO.equals(tempEstadoUsuario)) {
				throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_04, new Exception());
			} else {
				throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_05, new Exception());
			}
		} else {
			logger.debug("no existe usuario en el ldap");
			throw new UsuarioException(CodigoError.ERROR_SEGURIDAD_06, new Exception());
		}		
	}

	public void cambiarContrasenia(String idUsuario, String contraseniaUsuario, String nuevaContraseniaUsuario,
			boolean isCambioContraseniaExterno, boolean isIntranet, PortletRequest portletRequest, ActionRequest actionRequest) {		
		logger.debug("cambiarContrasenia");
		try {
			if (isIntranet) {
				gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario,
						GestionUsuarioGrupoPUMAService.CONTRASENIA, nuevaContraseniaUsuario, portletRequest, actionRequest);
			} else {
				String identifier = gestionUsuarioGrupoPUMAService.existeUsuarioLDAP(idUsuario, portletRequest);
				try {					
					if (Util.STRING_VACIO.equals(identifier)) {
						throw new EjecucionException("identifier es vacio", new Exception());
					} 
					//TODO: N.A: Volver a usar el ldap
					/*ConexionLDAPTDS conexionLDAP = new ConexionLDAPTDS(identifier);
					conexionLDAP.cambiarContrasenia(contraseniaUsuario, nuevaContraseniaUsuario);*/
					gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario,
							GestionUsuarioGrupoPUMAService.CONTRASENIA, nuevaContraseniaUsuario, portletRequest, actionRequest);
				} catch (EjecucionException ee) {
					logger.debug("mensajeError=" + ee.getMessage());
					logger.debug("mensajeError ee=",ee);
					if (CodigoError.CODIGO_ERROR_TDS_PASSWORD_EXPIRADO.equals(ee.getMessage())) {
						logger.debug("Intentando por segunda cambiar contrasenia");
						try {
							String tempContraseniaUsuario = Util.generarContrasenia();
							gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario,
									GestionUsuarioGrupoPUMAService.CONTRASENIA, tempContraseniaUsuario, portletRequest, actionRequest);
							//TODO: N.A: Volver a usar el ldap
							/*ConexionLDAPTDS conexionLDAP = new ConexionLDAPTDS(identifier);
							conexionLDAP.cambiarContrasenia(tempContraseniaUsuario, nuevaContraseniaUsuario);*/
							gestionUsuarioGrupoPUMAService.actualizarAtributoUsuarioLDAP(idUsuario,
									GestionUsuarioGrupoPUMAService.CONTRASENIA, nuevaContraseniaUsuario, portletRequest, actionRequest);
						} catch(Exception e) {
							logger.error("Error: ",e);
							throw new EjecucionException("No se pudo cambiar contrasenia del usuario " + idUsuario + " por segunda vez", ee);
						}
					} else {
						throw new EjecucionException("No se pudo cambiar contrasenia del usuario " + idUsuario, ee);
					}
				}
			}						
		} catch(Exception e) {
			throw new UsuarioException(CodigoError.ERROR_GENERAL_01, e);
		}		
	}

}
