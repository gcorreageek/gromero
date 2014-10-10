package com.ransa.portal.portlet.administracion.usuario.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.log4j.Logger;

import com.ransa.portal.model.RecursoUsuario;
import com.ransa.portal.util.Util;

public class RecursoUsuarioConverter implements Converter {

	private static Logger logger = Logger.getLogger(RecursoUsuarioConverter.class);
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		logger.debug("getAsObject: value=" + value);
		String[] tempRecursoUsuario = value.split(Util.SEPARADOR);
		RecursoUsuario recursoUsuario = new RecursoUsuario();
		recursoUsuario.setIdTipoRecurso(Util.parseInt(tempRecursoUsuario[0]));
		recursoUsuario.setIdRecurso(tempRecursoUsuario[1]);
		return recursoUsuario;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		logger.debug("getAsString: value=" + value.toString());
		return value.toString();
	}

}