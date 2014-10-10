package pe.com.ransa.portal.carga.service;

import java.math.BigInteger;
import java.util.List;

import pe.com.ransa.portal.carga.dto.Cliente;

/**
 * Servicio que implementa la funcionalidad asociada a los Clientes de Primax
 * @author dmirandat
 *
 */
public interface ClienteService {

	Cliente registrar(Cliente cliente);
	Cliente actualizar(Cliente cliente);
	Cliente eliminar(Cliente cliente);
	
	Boolean tieneUsuarioClienteActivos(BigInteger idCliente );
	public List<Cliente> listarCliente(Cliente cliente,Integer inicio,Integer fin);
	public Integer totalCliente(Cliente cliente);
	
	Cliente registrarUsuarioCliente(Cliente cliente,String usuario);
	Object registrarUsuarioCliente(List<Cliente> clientes);
	public BigInteger getIdClienteXruc(String ruc);
	
	
	
	
}
