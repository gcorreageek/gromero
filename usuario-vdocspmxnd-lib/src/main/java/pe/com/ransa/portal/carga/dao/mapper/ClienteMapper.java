package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Cliente;

public class ClienteMapper implements RowMapper<Cliente> {
	
	
	public Cliente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//		Integer iii = 2052167051;
//		ID_CLI,RUC,RAZON_SOCIAL,CODIGO,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION
		Cliente cliente = new Cliente();  
		cliente.setId(resultSet.getBigDecimal("ID_CLI").toBigInteger());
		cliente.setRUC(resultSet.getString("RUC"));
		cliente.setRazonSocial(resultSet.getString("RAZON_SOCIAL"));
		cliente.setCodigo(resultSet.getString("CODIGO"));
		cliente.setEstado(resultSet.getString("ESTADO"));
		cliente.setFechaCreacion(resultSet.getDate("FECHA_CREACION"));
		cliente.setUsuarioCreacion(resultSet.getString("USUARIO_CREACION"));
		cliente.setFechaModificacion(resultSet.getDate("FECHA_MODIFICACION"));
		cliente.setUsuarioModificacion(resultSet.getString("USUARIO_MODIFICACION"));
		return cliente;
	}
}