package com.ransa.portal.portlet.administracion.usuario.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.ransa.portal.model.Negocio;
import com.ransa.portal.util.Util;

public class NegocioConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String[] tempNegocio = value.split(Util.SEPARADOR);
		Negocio negocio = new Negocio();
		negocio.setId(tempNegocio[0]);
		negocio.setNombre(tempNegocio[1]);
		return negocio;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
