package com.ransa.portal.service;

import java.util.Collection;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import com.ransa.portal.model.Carga;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Idioma;
import com.ransa.portal.model.Pais;
import com.ransa.portal.model.Recurso;
import com.ransa.portal.model.RolUsuario;
import com.ransa.portal.model.TipoArchivo;
import com.ransa.portal.model.TipoDocumento;
import com.ransa.portal.model.TipoPortal;
import com.ransa.portal.model.TipoRecurso;
import com.ransa.portal.model.TipoUsuario;
import com.ransa.portal.model.Usuario;

public interface GestionUsuarioService {
	//public Collection<Usuario> getUsuarios(String tipoPortal);
	public Collection<Usuario> getUsuarios(String tipoPortal);
	
	public Grupo[] getGruposUsuario(String idUsuario);
	
	public Collection<TipoDocumento> getTiposDocumento();
	
	public Collection<TipoUsuario> getTiposUsuario(String indicador);
	
	public Collection<TipoUsuario> getTipoUsuario();
	
	public Collection<Pais> getPaises();
	
	public Collection<Idioma> getIdiomas();
	
	public String getNombreCompania(Integer tipo, String idCompania, int idPais, String CCMPN);
	
//	public Collection<FilaAplicacionUsuarioBean> getFilasAplicacionesUsuario(Grupo[] grupos,
//			String idUsuario, PortletRequest portletRequest);
	
	public void setProxyWebService();
	
	public Collection<Recurso> getRecursosUsuario(String idTipoRecurso, String idUsuario);
	
//	public Collection<FilaTipoRecursoUsuarioBean> getFilasTiposRecursosUsuario(String idUsuario, RolUsuario[] rolesUsuario);
	
	public Collection<Recurso> getRecursosDisponibles(String idTipoRecurso, String nombreRecursoDisponible,
			Collection<SelectItem> recursosAsignados);
	
	public void crearConfiguracionUsuario(Usuario usuario, PortletRequest portletRequest,
			ActionRequest actionRequest);
	
	public void actualizarConfiguracionUsuario(Usuario usuario, Grupo[] gruposUsuarioActuales,
			PortletRequest portletRequest, ActionRequest actionRequest);
	
	public void eliminarConfiguracionUsuario(String idUsuario, String idUsuarioCreaModif);
	
//	public Collection<LookAndFeelBean> getListLookAndFeel();
	
	public void enviarCorreoElectronicoCreacionUsuario(String subject, String body);
	
//	public Collection<FilaTipoRecursoPreferenciaBean> getTipoRecursoPreferencia(String idUsuario);
	
	public void notificarCreacionUsuario(String idUsuario, String nuevaContrasenia, String subject,
			String body, String correoElectronico, PortletRequest portletRequest, ActionRequest actionRequest);
	
	public void notificarRecuperacionDeContraseniaUsuario(String idUsuario, String nuevaContrasenia, String subject,
			String body, String correoElectronico, PortletRequest portletRequest, ActionRequest actionRequest);
	
	public void notificarCreacionUsuario(String idUsuario, String nuevaContrasenia, String subject,
			String body, PortletRequest portletRequest, ActionRequest actionRequest);
	
//	public String generarFichaCreacionUsuario(String tipoPortal, String tipoArchivo, String idsNegocios,
//			Collection<FirmasNegocioBean> firmas);
	
	public Collection<TipoPortal> getTiposPortal();
	
	public Collection<TipoArchivo> getTiposArchivo();	
	
	public Collection<TipoRecurso> getTiposRecursos();
	
	public String[] getIdsTiposRecursosPorAplicacion(int idAplicacion);
	
	public void updateTiposRecursosPorAplicacion(int idAplicacion, String idsTiposRecursos);
	
	public Collection<String> enviarMensajeUsuarios(String asunto, String contenido, Grupo grupoSeleccionado,
			PortletRequest portletRequest);
	
	public Collection<String> getIdsUsuariosPorGrupos(Grupo grupo);
	
	public Object[] enviarContraseniaUsuarios(Collection<String> idsUsuarios, String query,
			String asunto, Map<String, String> tempContenidos, Map<String, String> nuevasContrasenias,
			PortletRequest portletRequest, ActionRequest actionRequest);
	
	public Object[] getUniqueNamePagina(String idUsuario, PortletRequest portletRequest);
	
	public boolean verificarDominioEmail(String dominioEmail);
	
	public Object[] cargarConfiguracionUsuarios(String indPortal, String idUsuarioCreador, PortletRequest portletRequest, ActionRequest actionRequest);
	
	public void updatePreferencias(String idUsuario, String tipos_recurso[], String recursos[]);
	
	public Collection<Carga> getCargas(String indPortal);
	
	public void eliminarCarga(String indPortal, int idCarga, String idUsuarioModificador, PortletRequest portletRequest,
			ActionRequest actionRequest);
	
	public Object[] getInfoUsuario(String idUsuario, PortletRequest portletRequest);
	
	public void actualizarParcialmenteConfiguracionUsuario(Usuario usuario,	PortletRequest portletRequest, ActionRequest actionRequest);
	
	public String generarIdUsuario(String correoElectronico, PortletRequest portletRequest);
	
	public boolean existeUsuarioBD(String idUsuario);
	
	public void enviarCorreoElectronicoResumen(String asunto, String contenido);
	
	public RolUsuario[] getRolesUsuario(String idUsuario);
	
	public RolUsuario[] getRolesUsuarioCargaMasiva(String idUsuario);
	
	public Collection<Grupo> dividirGrupos(Collection<Grupo> grupos);
	
}