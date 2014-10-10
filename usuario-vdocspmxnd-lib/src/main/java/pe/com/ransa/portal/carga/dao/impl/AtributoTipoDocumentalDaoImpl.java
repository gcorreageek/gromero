package pe.com.ransa.portal.carga.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.com.ransa.portal.carga.dao.AtributoTipoDocumentalDao;
import pe.com.ransa.portal.carga.dao.DocumentoDao;
import pe.com.ransa.portal.carga.dto.AtributoTipoDocumental;
import pe.com.ransa.portal.carga.dto.Documento;

@Repository
public class AtributoTipoDocumentalDaoImpl implements AtributoTipoDocumentalDao {
	
	private static Logger logger = Logger.getLogger(AtributoTipoDocumentalDaoImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();
	
	private JdbcTemplate jdbcTemplate;
	private StringBuilder sql;
 
	
	@Autowired
	public void setDataSourceDB2(DataSource dataSourceDB2) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceDB2);
	}
	public DataSource getDataSourceDB2() {
		return this.jdbcTemplate.getDataSource();
	} 
	private JdbcTemplate getJdbcTemplate(){
		return this.jdbcTemplate;
	}
	public List<AtributoTipoDocumental> listaAtributoTipoDocumental(
			AtributoTipoDocumental atributo,Integer inicio, Integer fin) throws Exception {
		List<AtributoTipoDocumental> lDoc = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_ATRIBUTO_TIPO_DOCUMENTAL")
		.returningResultSet("atributoTipoDocumental", new RowMapper<AtributoTipoDocumental>() {
			public AtributoTipoDocumental mapRow(ResultSet rs, int rowNum) throws SQLException {
				AtributoTipoDocumental atributo = new AtributoTipoDocumental();
				atributo.setIdAtr(rs.getBigDecimal("ID_ATR").toBigInteger());
				atributo.setIdEmp(rs.getBigDecimal("ID_EMP").toBigInteger());
				atributo.setIdArea(rs.getBigDecimal("ID_AREA").toBigInteger());
				atributo.setIdTipDoc(rs.getBigDecimal("ID_TIP_DOC").toBigInteger());
				atributo.setNombre(rs.getString("NOMBRE"));
				atributo.setNombreColumnaTabla(rs.getString("NOMBRE_COLUMNA_TABLA"));
				atributo.setDescripcion(rs.getString("DESCRIPCION"));
				atributo.setTipoDato(rs.getString("TIPO_DATO"));
				atributo.setFiltro(rs.getString("FILTRO"));
				atributo.setReporte(rs.getString("REPORTE"));
				atributo.setTipoComportamiento(rs.getString("TIPO_COMPORTAMIENTO"));
				atributo.setIdLista(rs.getBigDecimal("ID_LISTA").toBigInteger()); 
				atributo.setEstado(rs.getString("ESTADO"));
				return atributo;
			}
		});
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_ID_EMP", atributo.getIdEmp())
		.addValue("P_ID_AREA", atributo.getIdArea())  
		.addValue("P_ID_TIP_DOC", atributo.getIdTipDoc())  
		.addValue("P_ID_ATR", atributo.getIdAtr())
		.addValue("P_INICIO_RESULTADO", inicio)
		.addValue("P_FIN_RESULTADO", fin);
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lDoc = (List<AtributoTipoDocumental> ) resultado.get("atributoTipoDocumental");	
		return lDoc;
	}
 
 

}
