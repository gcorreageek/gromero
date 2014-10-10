package pe.com.ransa.portal.carga.dao.mapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Empresa;

public class AreaMapper implements RowMapper<Area> {
	
	
	public Area mapRow(ResultSet resultSet, int rowNum) throws SQLException { 
//		 T.A_ID_AREA,T.A_NOMBRE,T.A_DESCRIPCION,T.A_CODIGO,T.A_ESTADO,T.E_ID_EMP,T.E_NOMBRE,T.E_ESTADO
		Area area = new Area(); 
		area.setIdArea(resultSet.getBigDecimal("A_ID_AREA").toBigInteger());//(resultSet.getBigDecimal("ID_EMP").toBigInteger());
		area.setNombre(resultSet.getString("A_NOMBRE"));
		area.setDescripcion(resultSet.getString("A_DESCRIPCION")); 
		area.setCodigo(resultSet.getString("A_CODIGO")); 
		area.setEstado(resultSet.getString("A_ESTADO"));
		
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(resultSet.getBigDecimal("E_ID_EMP").toBigInteger());
		empresa.setNombre(resultSet.getString("E_NOMBRE")); 
		area.setEmpresa(empresa);
		area.setEstadoEmpresaArea(resultSet.getString("EA_ESTADO")); 
		return area;
	}
}