package pe.com.ransa.portal.carga.service;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import com.ransa.portal.model.Usuario;

import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;

/**
 * Servicio que implementa la funcionalidad asociada a los Clientes de Primax
 * @author dmirandat
 */
public interface UsuarioService {
 
	public boolean crudUsuarioInsert(Usuario usuario,PortletRequest portletRequest, ActionRequest actionRequest) ;
	
	public List<UsuarioDTO> listarUsuario(UsuarioDTO usuario,Integer inicio,Integer fin);
	public Integer totalUsuario(UsuarioDTO usuario);
	 
	public List<TipoDocumental> listarAccesoDocumental(TipoDocumental td,Integer inicio,Integer fin);
	public Integer totalAccesoDocumental(TipoDocumental td);
	
	public List<Cuenta> listarAccesoCuenta(Cuenta cuenta,Integer inicio,Integer fin);
	public Integer totalAccesoCuenta(Cuenta cuenta);
	
	public List<TipoDocumental> ingresarUsuarioTdEmpresaArea(String idUsuario,String[] ids);
	
	public List<Cuenta> ingresarUsuarioClienteCuenta(String idUsuario,String[] ids);
	
	public TipoDocumental eliminarTipoDocumentalAreaEmpresaUsuario(UsuarioDTO usuarioDto, Empresa empresa, Area area, TipoDocumental td);
	
	
	
	public List<Cuenta> listarClienteCuentaActivas(Cuenta cuenta);
	public Integer totalClienteCuentaActivas(Cuenta cuenta);

	public Cuenta eliminarClienteCuentaUsuario(UsuarioDTO usuarioDto,
			Cliente cliente, Cuenta cuenta);
	
	
}
