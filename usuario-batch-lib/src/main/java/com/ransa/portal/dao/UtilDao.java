package com.ransa.portal.dao;

import java.sql.Connection;
import java.util.Collection;

import com.ransa.portal.model.Aplicacion;
import com.ransa.portal.model.Estado;
import com.ransa.portal.model.Grupo;
import com.ransa.portal.model.Idioma;
import com.ransa.portal.model.Negocio;
import com.ransa.portal.model.OpcionAplicacion;
import com.ransa.portal.model.Pais;
import com.ransa.portal.model.TipoArchivo;
import com.ransa.portal.model.TipoDocumento;
import com.ransa.portal.model.TipoEvento;
import com.ransa.portal.model.TipoPortal;
import com.ransa.portal.model.TipoUso;
import com.ransa.portal.model.TipoUsuario;
//import com.ransa.portal.portlet.administracion.usuario.util.LookAndFeelBean;

public interface UtilDao {

	public Collection<TipoDocumento> getTiposDocumento();
	
	public TipoDocumento getTipoDocumento(String idTipoDocumento);
	
	public TipoUsuario getTipoUsuarioEmpleado();

	public Collection<TipoUsuario> getTiposUsuario(String indicador);

	public Collection<Pais> getPaises();
	
	public Pais getPais(String idPais);
	
	public Collection<Idioma> getIdiomas();
	
	public Idioma getIdioma(String idIdioma);
	
	public Collection<Estado> getEstadoAplicacion();

	public Collection<Negocio> getNegocioAplicacion();

	public Collection<Estado> getEstadoOpcionAplicacion();

	public Collection<Estado> getEstadoRolAplicacion();

	public Collection<Estado> getEstadoTipoRecurso();

	public Collection<Estado> getComboEstadoUsuario();
	
	public String getParametroConfiguracion(Integer idParametro);
	
	/**
	 * 
	 * @param Indica
	 *            si es grupo de Intranet o Extranet
	 * @return {@link UtilDao}
	 */
	public Collection<Grupo> getGrupos(String intExtranet);

	/**
	 * Entrega lista de tipos de usuario
	 */
	public Collection<TipoUsuario> getTiposUsuario();

	/**
	 * Entrega lista de tipos de eventos para cargar en los ComboBox
	 */
	public Collection<TipoEvento> getComboTipoEvento();

	/**
	 * Entrega lista de tipos de uso para cargar en los ComboBox
	 */
	public Collection<TipoUso> getComboTipoUso();

	/**
	 * Entrega lista de aplicaciones filtardo por negocio
	 */
	public Collection<Aplicacion> getComboAplicacion(Integer idNegocio);

	/**
	 * Entrega lista de opciones de aplicacion filtrado por su aplicaciones
	 */
	public Collection<OpcionAplicacion> getComboOpcionAplicacion(Integer idAplicacion);
	
//	public Collection<LookAndFeelBean> getListLookAndFeel();
	
//	public LookAndFeelBean getLookAndFeel(String idLookAndFeel);
	
	public Connection getConnection();
	
	public Collection<TipoPortal> getTiposPortal();
	
	public TipoPortal getTipoPortal(String idTipoPortal);
	
	public Collection<TipoArchivo> getTiposArchivo();
	
	public boolean verificarDominioEmail(String dominioEmail);
	
}
