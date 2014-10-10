package pe.com.ransa.portal.carga.service;

import java.math.BigInteger;
import java.util.List;

import pe.com.ransa.portal.carga.dto.Empresa;

/**
 * Servicio que implementa la funcionalidad asociada a los Clientes de Primax
 * @author dmirandat
 *
 */
public interface EmpresaService {

	Empresa registrar(Empresa empresa);
	Empresa actualizar(Empresa empresa);
	Empresa eliminar(Empresa empresa);
	
	public List<Empresa> listar(Empresa empresa,Integer inicio,Integer fin);
	public Integer total(Empresa empresa);
	 
	
	public Boolean idEmpresaUsado(BigInteger idEmpresa);
	public Boolean codigoUsado(String codigoEmpresa);
	
}
