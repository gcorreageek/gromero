package pe.com.ransa.portal.carga.dao.impl;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
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

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.AreaDao;
import pe.com.ransa.portal.carga.dao.EmpresaDao;
import pe.com.ransa.portal.carga.dao.TipoDocumentalDao;
import pe.com.ransa.portal.carga.dao.mapper.AreaMapper;
import pe.com.ransa.portal.carga.dao.mapper.CantidadAPRMapper;
import pe.com.ransa.portal.carga.dao.mapper.EmpresaMapper;
import pe.com.ransa.portal.carga.dao.mapper.TipoDocumentalMapper;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;

@Repository
public class TipoDocumentalDaoImpl implements TipoDocumentalDao {
	private static Logger logger = Logger.getLogger(TipoDocumentalDaoImpl.class);
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
	public TipoDocumental ingresarTipoDoc(TipoDocumental td)  throws Exception{
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("IN_NOMBRE", td.getNombre())
				.addValue("IN_DESCRIPCION", td.getDescripcion())
				.addValue("IN_CODIGO", td.getCodigo()) 
				
				.addValue("IN_ESTADO", td.getEstado())
				.addValue("IN_FECHA_CREACION", td.getFechaCreacion())
				.addValue("IN_USUARIO_CREACION", td.getUsuarioCreacion())
				.addValue("IN_FECHA_MODIFICACION", td.getFechaModificacion())
				.addValue("IN_USUARIO_MODIFICACION", td.getUsuarioModificacion());
//				.addValue("IN_ID_TIPO_DOC", td.getIdTipoDocumental());
		Map<String, Object> out = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_TIPODOCUMENTAL").execute(in);
			logger.debug("MAP=>" + Arrays.toString(out.entrySet().toArray()));
			BigInteger id = new BigInteger(out.get("OUT_ID_TIPO_DOC").toString());
			logger.debug("idTD:"+id);
			td.setIdTipoDocumental(id);
		return td;
	} 
	public TipoDocumental modificarTipoDoc(TipoDocumental td)  throws Exception{
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("IN_NOMBRE", td.getNombre())
			.addValue("IN_DESCRIPCION", td.getDescripcion())
			.addValue("IN_CODIGO", td.getCodigo()) 
			  
			.addValue("IN_FECHA_MODIFICACION", td.getFechaModificacion())
			.addValue("IN_USUARIO_MODIFICACION", td.getUsuarioModificacion())
			.addValue("IN_ID_TIPO_DOC", td.getIdTipoDocumental());
		
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_UPDATE_TIPODOCUMENTAL").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return td;
	}  
//	SP_ELIMINAR_TIPODOCUMENTAL
	public TipoDocumental eliminarTipoDoc(TipoDocumental td)throws Exception {
		logger.debug("==>"+td.getEstado()+"|"+td.getFechaModificacion()+"|"+td.getUsuarioModificacion()+"|"+td.getIdTipoDocumental());
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()  
			.addValue("IN_ESTADO", td.getEstado()) 
			.addValue("IN_FECHA_MODIFICACION",  td.getFechaModificacion())
			.addValue("IN_USUARIO_MODIFICACION",  td.getUsuarioModificacion())
			.addValue("IN_ID_TIPO_DOC", td.getIdTipoDocumental());
		
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_TIPODOCUMENTAL").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return td; 
	} 
	public List<TipoDocumental> listarTipoDoc(TipoDocumental td, Integer inicio, Integer fin)throws Exception {
//		logger.debug("SP_LISTAR_TIPODOCUMENTAL===>"+td.getCodigo()+"|"+td.getNombreTablaTipoDocEmpresaArea()+"|"+td.getArea().getEmpresa().getIdEmpresa()
//				+"|"+td.getArea().getIdArea()+"|" +td.getIdTipoDocumental()+"|" +td.getEstado()+"|" +inicio+"|"+fin);
		List<TipoDocumental> lTd = null; 
//		IN P_NOMBRE_EMP VARCHAR(100),IN P_NOMBRE_AREA  VARCHAR(100),IN P_NOMBRE_TD VARCHAR(100)
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_LISTAR_TIPODOCUMENTAL")
			.returningResultSet("tiposDocumentales", new TipoDocumentalMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_CODIGO", td.getCodigo())
			.addValue("P_NOMBRE_TABLA", td.getNombreTablaTipoDocEmpresaArea())
			.addValue("P_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
			.addValue("P_ID_AREA", td.getArea().getIdArea()) 
			.addValue("P_ID_TIPODOCUMENTAL", td.getIdTipoDocumental()) 
			.addValue("P_ESTADO", td.getEstado()) 
			.addValue("P_NOMBRE_EMP", td.getArea().getEmpresa().getNombre()) 
			.addValue("P_NOMBRE_AREA", td.getArea().getNombre()) 
			.addValue("P_NOMBRE_TD", td.getNombre()) 
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lTd = (List<TipoDocumental> ) resultado.get("tiposDocumentales");	
		return lTd;
	}
 
	public Integer totalTipoDoc(TipoDocumental td) throws Exception{
		Integer totalTD=null;
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_GET_TOTAL_LISTAR_TIPODOCUMENTAL")
			.returningResultSet("totalTD", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_CODIGO", td.getCodigo())
			.addValue("P_NOMBRE_TABLA", td.getNombreTablaTipoDocEmpresaArea())
			.addValue("P_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
			.addValue("P_ID_AREA", td.getArea().getIdArea()) 
			.addValue("P_ID_TIPODOCUMENTAL", td.getIdTipoDocumental()) 
			.addValue("P_ESTADO", td.getEstado())
			.addValue("P_NOMBRE_EMP", td.getArea().getEmpresa().getNombre()) 
			.addValue("P_NOMBRE_AREA", td.getArea().getNombre()) 
			.addValue("P_NOMBRE_TD", td.getNombre()) ; 
			
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalTD");
			totalTD =  total.get(0).intValue();	
		return totalTD;
	} 
	public TipoDocumental ingresarTipoDocEmpresaArea(TipoDocumental td) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
				.addValue("IN_NOMBRE_TABLA", td.getNombreTablaTipoDocEmpresaArea()) 
				.addValue("IN_ESTADO", td.getEstado())
				.addValue("IN_FECHA_CREACION", td.getFechaCreacion())
				.addValue("IN_USUARIO_CREACION", td.getUsuarioCreacion())
				.addValue("IN_FECHA_MODIFICACION", td.getFechaModificacion())
				
				.addValue("IN_USUARIO_MODIFICACION", td.getUsuarioModificacion())
				.addValue("IN_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
				.addValue("IN_ID_AREA", td.getArea().getIdArea())
				.addValue("IN_ID_TIPO_DOC", td.getIdTipoDocumental());
		Map<String, Object> out = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_TIPODOCUMENTALEMPRESAAREA").execute(in);
			logger.debug("MAP=>" + Arrays.toString(out.entrySet().toArray()));
		return td;
	}
	public List<TipoDocumental> listarEmpresaAreaTipoDoc(TipoDocumental td,
			Integer inicio, Integer fin) throws Exception {
		List<TipoDocumental> lTipoDoc = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_TIPODOCUMENTALEMPRESAAREA")
		.returningResultSet("tipoDocempresaArea", new RowMapper<TipoDocumental>() {
			public TipoDocumental mapRow(ResultSet rs, int rowNum) throws SQLException {
				TipoDocumental td = new TipoDocumental();
				Area area = new Area();
				Empresa empresa = new Empresa();
				empresa.setIdEmpresa(rs.getBigDecimal("ID_EMP").toBigInteger());
				area.setEmpresa(empresa);
				area.setIdArea(rs.getBigDecimal("ID_AREA").toBigInteger());
				td.setArea(area);
				td.setIdTipoDocumental(rs.getBigDecimal("ID_TIP_DOC").toBigInteger());
				td.setNombreTablaTipoDocEmpresaArea(rs.getString("NOMBRE_TABLA"));
				td.setEstado(rs.getString("ESTADO"));
				td.setFechaCreacion(rs.getDate("FECHA_CREACION"));
				td.setUsuarioCreacion(rs.getString("USUARIO_CREACION"));
				td.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
				td.setUsuarioModificacion(rs.getString("USUARIO_MODIFICACION"));
				return td;
			}
		});
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
		.addValue("P_ID_AREA", td.getArea().getIdArea())  
		.addValue("P_ID_TIP_DOC", td.getIdTipoDocumental())
		.addValue("P_INICIO_RESULTADO", inicio)
		.addValue("P_FIN_RESULTADO", fin);
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lTipoDoc = (List<TipoDocumental> ) resultado.get("tipoDocempresaArea");	
		return lTipoDoc;
	} 
	public TipoDocumental modificarTipoDocEmpresaArea(TipoDocumental td) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("IN_NOMBRE_TABLA", td.getNombreTablaTipoDocEmpresaArea()) 
			.addValue("IN_FECHA_MODIFICACION", td.getFechaModificacion())
			.addValue("IN_USUARIO_MODIFICACION", td.getUsuarioModificacion())
			.addValue("IN_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
			.addValue("IN_ID_AREA", td.getArea().getIdArea())
			.addValue("IN_ID_TIPO_DOC", td.getIdTipoDocumental());
		
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_UPDATE_TIPODOCUMENTAL_EMPRESA_AREA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return td;
	}  
	public TipoDocumental eliminarTipoDocEmpresaArea(TipoDocumental td) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()  
			.addValue("IN_ESTADO", td.getEstado()) 
			.addValue("IN_FECHA_MODIFICACION", td.getFechaModificacion())
			.addValue("IN_USUARIO_MODIFICACION",  td.getUsuarioModificacion())
			.addValue("IN_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
			.addValue("IN_ID_AREA", td.getArea().getIdArea())
			.addValue("IN_ID_TIPO_DOC", td.getIdTipoDocumental());
		
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_TIPODOCUMENTAL_EMPRESA_AREA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return td; 
	}
	
 
 

}
