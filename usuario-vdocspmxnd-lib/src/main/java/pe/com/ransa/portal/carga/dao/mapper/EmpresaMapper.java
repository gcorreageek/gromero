package pe.com.ransa.portal.carga.dao.mapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Empresa;

public class EmpresaMapper implements RowMapper<Empresa> {
	
	
	public Empresa mapRow(ResultSet resultSet, int rowNum) throws SQLException { 
//T.ID_EMP,T.NOMBRE,T.DESCRIPCION,T.CODIGO,T.IMAGEN_LOGO,T.COLOR_CABECERA,T.ESTADO,T.FECHA_CREACION,T.USUARIO_CREACION,T.FECHA_MODIFICACION,T.USUARIO_MODIFICACION 
		Empresa empresa = new Empresa(); 
		empresa.setIdEmpresa(resultSet.getBigDecimal("ID_EMP").toBigInteger());//(resultSet.getBigDecimal("ID_CLI").toBigInteger());
		empresa.setNombre(resultSet.getString("NOMBRE"));
		empresa.setDescripcion(resultSet.getString("DESCRIPCION")); 
		empresa.setCodigo(resultSet.getString("CODIGO"));
		empresa.setImagenLogo(resultSet.getString("IMAGEN_LOGO"));
		empresa.setColorCabecera(resultSet.getString("COLOR_CABECERA"));  
		empresa.setEstado(resultSet.getString("ESTADO"));
		empresa.setFechaCreacion(resultSet.getDate("FECHA_CREACION"));
		empresa.setUsuarioCreacion(resultSet.getString("USUARIO_CREACION"));
		empresa.setFechaModificacion(resultSet.getDate("FECHA_MODIFICACION"));
		empresa.setUsuarioModificacion(resultSet.getString("USUARIO_MODIFICACION"));
		return empresa;
	}
}