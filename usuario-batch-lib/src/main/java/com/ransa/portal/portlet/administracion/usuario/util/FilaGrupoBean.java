package com.ransa.portal.portlet.administracion.usuario.util;

import com.ransa.portal.model.Grupo;

public class FilaGrupoBean extends Grupo{
	private boolean selected = false;
	
	public FilaGrupoBean(Grupo grupo, boolean selected){
		setId(grupo.getId());
		setDescripcion(grupo.getDescripcion());
		setSelected(selected);
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	} 
}
