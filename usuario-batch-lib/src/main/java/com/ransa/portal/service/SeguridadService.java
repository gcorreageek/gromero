package com.ransa.portal.service;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

public interface SeguridadService {

	public static final String NOM_PREF_IND_TIPO_PORTAL = "IndicadorTipoPortal";
	public static final String TIPO_PORTLET_LOGIN = "Login";
	
	public void loginUsuario(String idUsuario, String contraseniaUsuario,
			PortletRequest portletRequest, PortletResponse portletResponse);
	
	public void cambiarContrasenia(String idUsuario, String contraseniaUsuario,
			String nuevaContraseniaUsuario, boolean isCambioContraseniaExterno,
			boolean isIntranet, PortletRequest portletRequest,
			ActionRequest actionRequest);
	
}
