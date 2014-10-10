package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;

public class TipoDocumentalMapper implements RowMapper<TipoDocumental> {
	
	
	public TipoDocumental mapRow(ResultSet resultSet, int rowNum) throws SQLException {   
		TipoDocumental td = new TipoDocumental(); 
		td.setIdTipoDocumental(resultSet.getBigDecimal("T_ID_TIP_DOC").toBigInteger());//(resultSet.getBigDecimal("ID_EMP").toBigInteger());
		td.setIdEmpresa(resultSet.getBigDecimal("E_ID_EMP").toBigInteger());
		td.setIdArea(resultSet.getBigDecimal("A_ID_AREA").toBigInteger());
		td.setNombre(resultSet.getString("T_NOMBRE"));
		td.setDescripcion(resultSet.getString("T_DESCRIPCION")); 
		td.setCodigo(resultSet.getString("T_CODIGO")); 
		td.setEstado(resultSet.getString("T_ESTADO"));
		td.setNombreTablaTipoDocEmpresaArea(resultSet.getString("TEA_NOMBRE_TABLA"));
		
		Area area = new Area();
		area.setIdArea(resultSet.getBigDecimal("A_ID_AREA").toBigInteger());
		area.setNombre(resultSet.getString("A_NOMBRE"));  
		
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(resultSet.getBigDecimal("E_ID_EMP").toBigInteger());
		empresa.setNombre(resultSet.getString("E_NOMBRE")); 
		area.setEmpresa(empresa); 
		td.setArea(area);
		return td;
	}
}