package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.ArrayList;
import java.util.Collection;

import com.ransa.portal.model.Grupo;

public class UtilUsuario {
	
	/**
	 * Convierte una Coleccion Objetos Grupo a FilaGrupoBean
	 * @param grupos
	 * @return
	 */
	public static Collection<FilaGrupoBean> convertGrupoToFilaGrupo(Collection<Grupo> grupos){
		Collection<FilaGrupoBean> listFilaGrupo = new ArrayList<FilaGrupoBean>();
		if(grupos!=null){
			ArrayList<Grupo> listGrupo =  (ArrayList<Grupo>)grupos;
			for(int i=0; i < listGrupo.size(); i++){
				listFilaGrupo.add(new FilaGrupoBean(listGrupo.get(i), false));				
			}			
		}
		return listFilaGrupo;
	}

}
