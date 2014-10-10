package pe.com.ransa.portal.carga.dao;

import java.util.List;

import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
 


public interface UsuarioDTODao {  
	public List<UsuarioDTO> listar(UsuarioDTO usuario,Integer inicio,Integer fin);
	public Integer total(UsuarioDTO usuario);
	
	public List<TipoDocumental> listar(TipoDocumental td,Integer inicio,Integer fin);
	public List<TipoDocumental> listarTipoDocumentalEmpresaUsuario(TipoDocumental td,Integer inicio,Integer fin) throws Exception;
	public Integer total(TipoDocumental td);
	
	public List<Cuenta> listar(Cuenta cuenta,Integer inicio,Integer fin);
	public Integer total(Cuenta cuenta);
	
	
	
	public List<TipoDocumental> listarUsuarioEmpresaAreaTipoDocumental(TipoDocumental td);
	
	
	public Empresa eliminarEmpresaUsuario(Empresa empresa)throws Exception; 
	public Area eliminarEmpresaAreaUsuario(Area area)throws Exception; 
	public TipoDocumental eliminarEmpresaAreaTipoDocumentalUsuario(TipoDocumental td)throws Exception; 
	
}
