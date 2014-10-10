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

import pe.com.ransa.portal.carga.dao.DocumentoDao;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Documento;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;

@Repository
public class DocumentoDaoImpl implements DocumentoDao {
	
	private static Logger logger = Logger.getLogger(DocumentoDaoImpl.class);
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
	public List<Documento> listaDocumento(Documento documento,Integer inicio, Integer fin)throws Exception {
		List<Documento> lDoc = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_DOCUMENTO")
		.returningResultSet("documentoLista", new RowMapper<Documento>() {
			public Documento mapRow(ResultSet rs, int rowNum) throws SQLException {
				Documento documento = new Documento();
				documento.setIdDoc(rs.getBigDecimal("ID_DOC").toBigInteger());
				documento.setIdEmp(rs.getBigDecimal("ID_EMP").toBigInteger());
				documento.setIdArea(rs.getBigDecimal("ID_AREA").toBigInteger());
				documento.setIdTipDoc(rs.getBigDecimal("ID_TIP_DOC").toBigInteger());
				documento.setEstado(rs.getString("ESTADO")); 
				documento.setCodLote(rs.getString("COD_LOTE")); 
				documento.setIdReg(rs.getBigDecimal("ID_REG").toBigInteger());
				return documento;
			}
		});
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_ID_EMP", documento.getIdEmp())
		.addValue("P_ID_AREA", documento.getIdArea())  
		.addValue("P_ID_TIP_DOC", documento.getIdTipDoc())  
		.addValue("P_ID_DOC", documento.getIdDoc())
		.addValue("P_INICIO_RESULTADO", inicio)
		.addValue("P_FIN_RESULTADO", fin);
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lDoc = (List<Documento> ) resultado.get("documentoLista");	
		return lDoc;
	}
 
 
	 
 

}
