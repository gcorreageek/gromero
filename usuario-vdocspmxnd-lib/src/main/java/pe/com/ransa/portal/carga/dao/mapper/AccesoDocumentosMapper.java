package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
 
public class AccesoDocumentosMapper implements RowMapper<TipoDocumental> {
	public TipoDocumental mapRow(ResultSet resultSet, int rowNum) throws SQLException { 
//T.ID_EMP_E,T.NOMBRE_E,T.ID_AREA_A,T.NOMBRE_A,T.ID_TIP_DOC_T,T.NOMBRE_T,T.ESTADO_TEAU
		TipoDocumental td = new TipoDocumental(); 
		td.setIdEmpresa(resultSet.getBigDecimal("ID_EMP_E").toBigInteger());
		td.setIdArea(resultSet.getBigDecimal("ID_AREA_A").toBigInteger()); 
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(resultSet.getBigDecimal("ID_EMP_E").toBigInteger());
		empresa.setNombre(resultSet.getString("NOMBRE_E"));
		Area area = new Area();
		area.setIdArea(resultSet.getBigDecimal("ID_AREA_A").toBigInteger());
		area.setNombre(resultSet.getString("NOMBRE_A"));
		area.setEmpresa(empresa);
		td.setArea(area);
		td.setIdTipoDocumental(resultSet.getBigDecimal("ID_TIP_DOC_T").toBigInteger());
		td.setNombre(resultSet.getString("NOMBRE_T"));
		td.setEstado(resultSet.getString("ESTADO_TEAU")); 
		return td;
	}
}