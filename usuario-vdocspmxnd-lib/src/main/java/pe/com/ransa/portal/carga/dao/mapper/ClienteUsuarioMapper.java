package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Cliente;

public class ClienteUsuarioMapper implements RowMapper<Cliente> {
	
	public Cliente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Cliente cliente = new Cliente();  
		cliente.setId(resultSet.getBigDecimal("ID_CLI").toBigInteger());
		cliente.setUsuario(resultSet.getString("IDUSUARIO")) ;
		cliente.setEstado(resultSet.getString("ESTADO")); 
		return cliente;
	}
}