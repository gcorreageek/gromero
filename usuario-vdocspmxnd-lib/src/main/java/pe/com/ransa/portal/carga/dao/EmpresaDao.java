package pe.com.ransa.portal.carga.dao;

import java.util.List;

import pe.com.ransa.portal.carga.dto.Empresa;


public interface EmpresaDao { 
	public Empresa ingresarEmpresa(Empresa empresa) throws Exception;
	public Empresa modificarEmpresa(Empresa empresa)throws Exception;
	public Empresa eliminarEmpresa(Empresa empresa)throws Exception; 
	public List<Empresa> listarEmpresa(Empresa empresa,Integer inicio,Integer fin)throws Exception;
	public Integer totalEmpresa(Empresa empresa)throws Exception;
	
	public List<Empresa> listarEmpresaUsuario(Empresa empresa,Integer inicio,Integer fin)throws Exception;
}
