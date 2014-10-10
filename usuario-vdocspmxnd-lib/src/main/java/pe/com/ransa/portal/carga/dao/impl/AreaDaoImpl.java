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

import pe.com.ransa.portal.carga.dao.AreaDao;
import pe.com.ransa.portal.carga.dao.mapper.AreaMapper;
import pe.com.ransa.portal.carga.dao.mapper.CantidadAPRMapper;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;

@Repository
public class AreaDaoImpl implements AreaDao {
	private static Logger logger = Logger.getLogger(AreaDaoImpl.class);
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
 
	public Area ingresarArea(Area area)throws Exception {
		logger.debug("==>"+area.getNombre()+"|"+area.getDescripcion()+"|"+area.getCodigo()+"|"+area.getEstado()+"|"+area.getFechaCreacion()
				+"|"+area.getUsuarioCreacion()+"|"+area.getFechaModificacion()+"|"+area.getUsuarioModificacion());
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("IN_NOMBRE", area.getNombre())
				.addValue("IN_DESCRIPCION", area.getDescripcion())
				.addValue("IN_CODIGO", area.getCodigo()) 
				.addValue("IN_ESTADO", area.getEstado())
				.addValue("IN_FECHA_CREACION", area.getFechaCreacion())
				.addValue("IN_USUARIO_CREACION", area.getUsuarioCreacion())
//				.addValue("IN_FECHA_MODIFICACION", new Date())
//				.addValue("IN_USUARIO_MODIFICACION", "DEFAULT");
				.addValue("IN_FECHA_MODIFICACION",area.getFechaModificacion())
				.addValue("IN_USUARIO_MODIFICACION", area.getUsuarioModificacion());
		
		Map<String, Object> out = null;
		out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_AREA").execute(in);
		logger.debug("MAP=>" + Arrays.toString(out.entrySet().toArray()));
		BigInteger id = new BigInteger(out.get("OUT_ID_AREA").toString());
		logger.debug("idArea:"+id);
		area.setIdArea(id);
		return area;
	}
 
	public Area modificarArea(Area area)throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
			.addValue("IN_NOMBRE", area.getNombre())
			.addValue("IN_DESCRIPCION", area.getDescripcion())
			.addValue("IN_CODIGO", area.getCodigo()) 
			
			.addValue("IN_ESTADO", area.getEstado())
			.addValue("IN_FECHA_CREACION", null)
			.addValue("IN_USUARIO_CREACION", null)
			.addValue("IN_FECHA_MODIFICACION", area.getFechaModificacion())
			.addValue("IN_USUARIO_MODIFICACION", area.getUsuarioModificacion())
			.addValue("IN_ID_AREA", area.getIdArea());
		
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_UPDATE_AREA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
 
		return area;
	}  
	public Area eliminarArea(Area area)throws Exception {
		logger.debug("==>"+area.getEstado()+"|"+area.getFechaModificacion()+"|"+area.getUsuarioModificacion()+"|"+area.getIdArea());
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
		.addValue("IN_ESTADO", area.getEstado()) 
		.addValue("IN_FECHA_MODIFICACION", area.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  area.getUsuarioModificacion())
		.addValue("IN_ID_AREA", area.getIdArea()); 
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_AREA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return area; 
	}
 
	public List<Area> listarArea(Area area, Integer inicio, Integer fin) throws Exception{
		logger.debug("===>"+area.getEmpresa().getIdEmpresa()+"|"+area.getIdArea()+"|"+area.getEstadoEmpresaArea()+"|"+inicio+"|"+fin);
		List<Area> lArea = null;
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_LISTAR_AREAS")
			.returningResultSet("areas", new AreaMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_EMP", area.getEmpresa().getIdEmpresa())
			.addValue("P_ID_AREA", area.getIdArea()) 
			.addValue("P_ESTADO", area.getEstadoEmpresaArea())
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lArea = (List<Area> ) resultado.get("areas");	
		return lArea;
	} 
	public Integer totalArea(Area area)throws Exception {
		Integer totalAreas=null;
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_GET_TOTAL_LISTAR_AREAS")
			.returningResultSet("totalAreas", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_EMP", area.getEmpresa().getIdEmpresa())
			.addValue("P_ID_AREA", area.getIdArea()) 
			.addValue("P_ESTADO", area.getEstadoEmpresaArea()); 
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalAreas");
			totalAreas =  total.get(0).intValue();	
		logger.debug("totalAreas:"+totalAreas);
		return totalAreas;
	} 
 
	public Area ingresarEmpresaArea(Area area)throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
				.addValue("IN_ESTADO", area.getEstado())
				.addValue("IN_FECHA_CREACION", area.getFechaCreacion())
				.addValue("IN_USUARIO_CREACION", area.getUsuarioCreacion())
				.addValue("IN_FECHA_MODIFICACION", area.getFechaModificacion())
				.addValue("IN_USUARIO_MODIFICACION", area.getUsuarioModificacion())
				.addValue("IN_ID_AREA", area.getIdArea())
				.addValue("IN_ID_EMP", area.getEmpresa().getIdEmpresa());
		Map<String, Object> out = null;
		out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_EMPRESAAREA").execute(in);
		logger.debug("MAP=>" + Arrays.toString(out.entrySet().toArray()));
		return area;
	}
	public List<Area> listarAreaXcodigo(Area area, Integer inicio, Integer fin) throws Exception {
		List<Area> lArea = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_AREAS_X_CODIGO")
		.returningResultSet("areasXcodigo", new RowMapper<Area>() {
//			T.ID_AREA, T.NOMBRE, T.DESCRIPCION, T.ESTADO, T.FECHA_CREACION, T.USUARIO_CREACION, T.FECHA_MODIFICACION, T.USUARIO_MODIFICACION,T.CODIGO
			public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
				Area aHere = new Area();
				aHere.setIdArea(rs.getBigDecimal("ID_AREA").toBigInteger());
				aHere.setNombre(rs.getString("NOMBRE"));
				aHere.setDescripcion(rs.getString("DESCRIPCION"));
				aHere.setEstado(rs.getString("ESTADO"));
				aHere.setFechaCreacion(rs.getDate("FECHA_CREACION"));
				aHere.setUsuarioCreacion(rs.getString("USUARIO_CREACION"));
				aHere.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
				aHere.setUsuarioModificacion(rs.getString("USUARIO_MODIFICACION"));
				aHere.setCodigo(rs.getString("CODIGO"));
				return aHere;
			}
		});
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_ID_AREA", area.getEmpresa().getIdEmpresa())
		.addValue("P_CODIGO", area.getCodigo())  
		.addValue("P_INICIO_RESULTADO", inicio)
		.addValue("P_FIN_RESULTADO", fin);
		
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lArea = (List<Area> ) resultado.get("areasXcodigo");	
		return lArea;
	}
	public List<Area> listarEmpresaAreaUsuario(Area area, Integer inicio, Integer fin) throws Exception {
		List<Area> lArea = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_EMPRESAAREAUSUARIO")
		.returningResultSet("empresaAreaUsuario", new RowMapper<Area>() {
			public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
				Area area = new Area();
				UsuarioDTO usuarioDto = new UsuarioDTO();
				usuarioDto.setIdUsuario(rs.getString("IDUSUARIO")); 
				area.setUsuarioDto(usuarioDto);
				Empresa empresa = new Empresa();
				empresa.setIdEmpresa(rs.getBigDecimal("ID_EMP").toBigInteger());
				area.setEmpresa(empresa);
				area.setIdArea(rs.getBigDecimal("ID_AREA").toBigInteger());
				area.setEstado(rs.getString("ESTADO"));
				area.setFechaCreacion(rs.getDate("FECHA_CREACION"));
				area.setUsuarioCreacion(rs.getString("USUARIO_CREACION"));
				area.setFechaModificacion(rs.getDate("FECHA_MODIFICACION"));
				area.setUsuarioModificacion(rs.getString("USUARIO_MODIFICACION"));
				return area;
			}
		});
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_IDUSUARIO", area.getUsuarioDto().getIdUsuario())
		.addValue("P_ID_EMP", area.getEmpresa().getIdEmpresa())
		.addValue("P_ID_AREA", area.getIdArea())  
		.addValue("P_INICIO_RESULTADO", inicio)
		.addValue("P_FIN_RESULTADO", fin);
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lArea = (List<Area> ) resultado.get("empresaAreaUsuario");	
		return lArea;
	} 
	public Area eliminarEmpresaArea(Area area) throws Exception {
		logger.debug("==>"+ area.getEstado()+"|"+area.getFechaModificacion()+"|"+area.getUsuarioModificacion()+"|"+area.getIdArea()+"|"+area.getEmpresa().getIdEmpresa());
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
		.addValue("IN_ESTADO", area.getEstado()) 
		.addValue("IN_FECHA_MODIFICACION", area.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  area.getUsuarioModificacion())
		.addValue("IN_ID_AREA", area.getIdArea())
		.addValue("IN_ID_EMP", area.getEmpresa().getIdEmpresa());
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_EMPRESAAREA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return area; 
	}
	
 

}
