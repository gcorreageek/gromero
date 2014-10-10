package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
 
public class AccesoCuentaMapper implements RowMapper<Cuenta> {
	public Cuenta mapRow(ResultSet resultSet, int rowNum) throws SQLException { 
//T.ID_CLI,T.RUC,T.RAZON_SOCIAL,T.ID_CUENTA,T.NRO_CUENTA,T.ESTADO
		Cuenta cuenta = new Cuenta(); 
		Cliente cliente = new Cliente();
		cliente.setId(resultSet.getBigDecimal("ID_CLI").toBigInteger());
		cliente.setRUC(resultSet.getString("RUC"));
		cliente.setRazonSocial(resultSet.getString("RAZON_SOCIAL"));
		cuenta.setCliente(cliente);
		cuenta.setId(resultSet.getBigDecimal("ID_CUENTA").toBigInteger());
		cuenta.setNumeroCuenta(resultSet.getString("NRO_CUENTA"));
		cuenta.setEstado(resultSet.getString("ESTADO")); 
		return cuenta;
	}
}