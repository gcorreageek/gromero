package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Cuenta;

public class CuentaUsuarioMapper implements RowMapper<Cuenta> {
	
	
	public Cuenta mapRow(ResultSet resultSet, int rowNum) throws SQLException { 
		Cuenta cuenta = new Cuenta(); 
		cuenta.setIdCliente(resultSet.getBigDecimal("ID_CLI").toBigInteger()); 
		cuenta.setIdCuenta(resultSet.getBigDecimal("ID_CUENTA").toBigInteger());
		cuenta.setUsuario(resultSet.getString("IDUSUARIO")); 
		cuenta.setEstado(resultSet.getString("ESTADO")); 
		return cuenta;
	}
}