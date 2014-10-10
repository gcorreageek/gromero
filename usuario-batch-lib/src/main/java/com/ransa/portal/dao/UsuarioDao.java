package com.ransa.portal.dao;

import java.util.Collection;

import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Usuario;

public interface UsuarioDao {

	public String getNombreCompania(Integer tipo, String idCompania, int idPais, String CCMPN);
	
	public String getSolicitante(String idUsuario);

	public void actualizarConfiguracionUsuario(String idUsuario,
			int idTipoUsuario, String solicitante, String idUsuarioCreaModif,
			String indicadorCreacion, String idsGrupos, String idsRoles,
			String idsRecursos, String idsRecursosFavoritos,
			String estadoUsuario, int tipoActualizacion);
	
	public void eliminarConfiguracionUsuario(String idUsuario,
			String idUsuarioCreaModif);
	
	public String getEstado(String idUsuario);
	
	public String getIdTipoUsuario(String idUsuario);
	
	public Collection<Grupo> getGruposUsuario(String idUsuario);
	
	public Collection<String> getIdsUsuariosPorGrupos(String idsGrupos);
	
	public String getUniqueNamePagina(String idUsuario, String indPortal);
	
	public Collection<Usuario> getUsuarios(String tipoPortal);
	
	public void actualizarInformacionUsuarioCargado(String idUsuario);
	
	public boolean verificarUsuario(String idUsuario);
		
}