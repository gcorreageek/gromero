package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;



public class CantidadAPRMapper implements RowMapper<Long> {
	public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Long cantidad = new Long(-1);
		cantidad = resultSet.getLong(1);
	  return cantidad;
	}
}