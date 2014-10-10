package com.ransa.portal.portlet.administracion.usuario.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.ransa.portal.model.Grupo;
import com.ransa.portal.util.Util;

public class GrupoConverter implements Converter {
			
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String[] tempGrupo = value.split(Util.SEPARADOR);
		Grupo grupo = new Grupo();
		grupo.setId(tempGrupo[0]);
		grupo.setDescripcion(tempGrupo[1]);
		return grupo;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}