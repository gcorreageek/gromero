package com.ransa.portal.service;

import java.util.Collection;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import com.ibm.portal.um.exceptions.PumaAttributeException;
import com.ibm.portal.um.exceptions.PumaMissingAccessRightsException;
import com.ibm.portal.um.exceptions.PumaSystemException;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Usuario;
import com.ransa.portal.model.util.MetaData;

public interface GestionUsuarioGrupoPUMAService {
	
	// Nombres de atributos de usuarios en el portal
	public static final String ID1_USUARIO = "uid";
	public static final String ID2_USUARIO = "cn";
	public static final String CONTRASENIA = "password";
	public static final String NOMBRES = "givenName";
	public static final String APELLIDOS = "sn";
	public static final String TRATO = "title";
	public static final String CORREO_ELECTRONICO = "ibm-primaryEmail";
	public static final String FECHA_NACIMIENTO = "laFechaNacimiento"; 
	public static final String ID_TIPO_DOCUMENTO = "laIdTipoDoc";
	public static final String NUMERO_DOCUMENTO = "laNumDoc";
	public static final String ID_COMPANIA = "laIdCompania";
	public static final String COMPANIA = "laCompania";
	public static final String TELEFONO_OFICINA = "laTelefonoOficina";
	public static final String TELEFONO_PERSONAL = "laTelefonoPersonal";
	public static final String TIPO_USUARIO = "laTipo";
	public static final String ID_LOOK_AND_FEEL_PORTAL = "laIdLookAndFeel";
	public static final String LOOK_AND_FEEL_PORTAL = "laLookAndFeel";
	public static final String AREA = "laArea";
	public static final String CARGO = "laCargo";
	public static final String ID_PAIS = "laIdPais";	
	public static final String ID_IDIOMA = "preferredLanguage";	
	public static final String COMPANIA_SOLICITANTE = "laCompaniaSolicitante";
	public static final String AREA_SOLICITANTE = "laAreaSolicitante";
	public static final String PREGUNTA_SEGRETA = "laPreguntaSecreta";
	public static final String RESPUESTA_SECRETA = "laRespuestaSecreta";
	public static final String PWD_RESET = "pwdReset";	
		
	public Usuario getUsuario(String atributo, String valorAtributo, boolean validarEstado, boolean eliminarUsuario, boolean mostrarSoloAtributosLDAP, PortletRequest portletRequest);

	public Usuario getUsuario(String idUsuario, boolean validarEstado, PortletRequest portletRequest);
	
	public Usuario getUsuarioSinLogin(String idUsuario, boolean validarEstado, PortletRequest portletRequest);
		
	public Usuario getUsuarioEliminar(String idUsuario, boolean validarEstado, PortletRequest portletRequest);
		
	public Collection<Grupo> getGrupos(PortletRequest portletRequest);
	
	public Grupo[] getGrupos(String idUsuario, PortletRequest portletRequest);
	
	public void agregarUsuarioGrupo(String idUsuario, String nombresGrupo[],
			PortletRequest portletRequest, ActionRequest actionRequest);
	
	public void removerUsuarioGrupo(String idUsuario, String nombresGrupo[],
			PortletRequest portletRequest, ActionRequest actionRequest);
	
	public void crearUsuario(Usuario usuario, PortletRequest portletRequest, ActionRequest actionRequest);
	
	public void actualizarUsuario(Usuario usuario, PortletRequest portletRequest,
			ActionRequest actionRequest);
	
	public void actualizarAtributoUsuarioLDAP(String idUsuario, String nombreAtributoUsuario,
			String valorAtributoUsuario, PortletRequest portletRequest,
			ActionRequest actionRequest);
	
	public String existeUsuarioLDAP(String idUsuario, PortletRequest portletRequest);
		
	public String getAtributoUsuario(String idUsuario, String nombreAtributoUsuario,
			PortletRequest portletRequest);
	
	public String getAtributoUsuario(String idUsuario, String nombreAtributoUsuario,
			PortletRequest portletRequest, boolean validacionNormal);
	
	public String getIdUsuario(String correoElectronico, PortletRequest portletRequest, boolean mostrarErrorDetallado);
	
	public boolean isCorreoAsignadoUsuario( String correoElectronico, PortletRequest portletRequest)
		throws PumaSystemException, PumaAttributeException, PumaMissingAccessRightsException;
	
	public Collection<MetaData> getIdUsuariosPorQueryParaEnvioContrasenia(String query,
			PortletRequest portletRequest);
	
	public void eliminarUsuarioLDAP(String idUsuario, PortletRequest portletRequest,
			ActionRequest actionRequest);
	
}
