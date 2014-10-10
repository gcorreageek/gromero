package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Cuenta;

public class CuentaMapper implements RowMapper<Cuenta> {
	
	
	public Cuenta mapRow(ResultSet resultSet, int rowNum) throws SQLException { 
//ID_CUENTA,NRO_CUENTA,PLACA_VEHICULAR,NOMBRE_USUARIO,CODIGO_ES,ID_CLI,ESTADO,FECHA_CREACION,USUARIO_CREACION,FECHA_MODIFICACION,USUARIO_MODIFICACION
		Cuenta cuenta = new Cuenta(); 
		cuenta.setIdCuenta(resultSet.getBigDecimal("ID_CUENTA").toBigInteger()); 
		cuenta.setNumeroCuenta(resultSet.getString("NRO_CUENTA"));
		cuenta.setPlacaVehicular(resultSet.getString("PLACA_VEHICULAR"));
		cuenta.setNombreUsuario(resultSet.getString("NOMBRE_USUARIO"));
		cuenta.setCodigoEs(resultSet.getString("CODIGO_ES"));
		cuenta.setIdCliente(resultSet.getBigDecimal("ID_CLI").toBigInteger());
		cuenta.setEstado(resultSet.getString("ESTADO"));
		cuenta.setFechaCreacion(resultSet.getDate("FECHA_CREACION"));
		cuenta.setUsuarioCreacion(resultSet.getString("USUARIO_CREACION"));
		cuenta.setFechaModificacion(resultSet.getDate("FECHA_MODIFICACION"));
		cuenta.setUsuarioModificacion(resultSet.getString("USUARIO_MODIFICACION"));
		return cuenta;
	}
}