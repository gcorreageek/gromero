package pe.com.ransa.portal.carga.dao;

import java.math.BigInteger;
import java.util.List;

import pe.com.ransa.portal.carga.dto.Cliente;


public interface ClienteDao {
 
//	public List<Cliente> getClientesXUsuario (String idUsuario);
//	public List<Cliente> getClientes();
	public Cliente ingresarCliente(Cliente cliente) throws Exception;
	public Cliente modificarCliente(Cliente cliente) throws Exception;
	public Cliente eliminarCliente(Cliente cliente) throws Exception;
	public List<Cliente> listarClienteUsuario(Cliente cliente ) throws Exception;
	public List<Cliente> listarCliente(Cliente cliente,Integer inicio,Integer fin) throws Exception;
	public List<Cliente> buscarClienteXruc(Cliente cliente,Integer inicio,Integer fin) throws Exception;
	public Integer totalCliente(Cliente cliente) throws Exception;
	public Cliente ingresarUsuarioCliente(Cliente cliente,String usuario)  throws Exception;
	public BigInteger getIdClienteXruc(String ruc) throws Exception;
//	public Object ingresarUsuarioCliente(List<Cliente> clientes);
}
