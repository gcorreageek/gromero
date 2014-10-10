package com.ransa.portal.portlet.administracion.usuario.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.ransa.portal.model.RolUsuario;
import com.ransa.portal.util.Util;

public class RolUsuarioConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String[] tempRolUsuario = value.split(Util.SEPARADOR);
		RolUsuario rolUsuario = new RolUsuario();
		rolUsuario.setIdAplicacion(tempRolUsuario[0]);
		rolUsuario.setIdRol(Util.parseInt(tempRolUsuario[1]));
		return rolUsuario;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
