package pe.com.ransa.portal.carga.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.EmpresaDao;
import pe.com.ransa.portal.carga.dao.mapper.CantidadAPRMapper;
import pe.com.ransa.portal.carga.dao.mapper.EmpresaMapper;
import pe.com.ransa.portal.carga.dao.mapper.EmpresaUsuarioMapper;
import pe.com.ransa.portal.carga.dto.Empresa;

@Repository
public class EmpresaDaoImpl implements EmpresaDao {
	private static Logger logger = Logger.getLogger(EmpresaDaoImpl.class);
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
 
	public Empresa ingresarEmpresa(Empresa empresa)throws Exception { 
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("IN_NOMBRE", empresa.getNombre())
				.addValue("IN_DESCRIPCION", empresa.getDescripcion())
				.addValue("IN_CODIGO", empresa.getCodigo())
				.addValue("IN_IMAGEN_LOGO", empresa.getImagenLogo())
				.addValue("IN_COLOR_CABECERA", empresa.getColorCabecera())
				
				.addValue("IN_ESTADO", empresa.getEstado())
				.addValue("IN_FECHA_CREACION", new Date())
				.addValue("IN_USUARIO_CREACION", empresa.getUsuarioCreacion())
				.addValue("IN_FECHA_MODIFICACION", null)
				.addValue("IN_USUARIO_MODIFICACION", null)
				.addValue("IN_ID_EMP", empresa.getIdEmpresa());
		Map<String, Object> out = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_EMPRESA").execute(in);
			logger.debug("MAP=>" + Arrays.toString(out.entrySet().toArray()));
		return empresa;
	}
 
	public Empresa modificarEmpresa(Empresa empresa) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_NOMBRE", empresa.getNombre())
		.addValue("IN_DESCRIPCION", empresa.getDescripcion())
		.addValue("IN_CODIGO", empresa.getCodigo())
		.addValue("IN_IMAGEN_LOGO", empresa.getImagenLogo())
		.addValue("IN_COLOR_CABECERA", empresa.getColorCabecera())
		
		.addValue("IN_ESTADO", empresa.getEstado())
		.addValue("IN_FECHA_CREACION", empresa.getFechaCreacion())
		.addValue("IN_USUARIO_CREACION", empresa.getUsuarioCreacion())
		.addValue("IN_FECHA_MODIFICACION", empresa.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION", empresa.getUsuarioModificacion())
		.addValue("IN_ID_EMP", empresa.getIdEmpresa());
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_UPDATE_EMPRESA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return empresa;
	}
 
	public Empresa eliminarEmpresa(Empresa empresa)throws Exception  {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
		.addValue("IN_ESTADO", empresa.getEstado()) 
		.addValue("IN_FECHA_MODIFICACION", empresa.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  empresa.getUsuarioModificacion())
		.addValue("IN_ID_EMP", empresa.getIdEmpresa());
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_EMPRESA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return empresa; 
	}  
	public List<Empresa> listarEmpresa(Empresa empresa, Integer inicio, Integer fin) throws Exception {
		logger.debug("===>"+empresa.getIdEmpresa()+"|"+empresa.getNombre()+"|"+empresa.getEstado()+"|"+inicio+"|"+fin);
		List<Empresa> lEmpresas = null;
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_LISTAR_EMPRESAS")
			.returningResultSet("empresas", new EmpresaMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_EMP", empresa.getIdEmpresa())
			.addValue("P_NOMBRE", empresa.getNombre()) 
			.addValue("P_CODIGO", empresa.getCodigo()) 
			.addValue("P_ESTADO", empresa.getEstado())
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lEmpresas = (List<Empresa> ) resultado.get("empresas");	
		return lEmpresas;
	} 
	public Integer totalEmpresa(Empresa empresa)throws Exception  {
		Integer totalEmpresas=null;
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_GET_TOTAL_LISTAR_EMPRESAS")
			.returningResultSet("totalEmpresas", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_EMP", empresa.getIdEmpresa())
			.addValue("P_NOMBRE", empresa.getNombre()) 
			.addValue("P_CODIGO", empresa.getCodigo()) 
			.addValue("P_ESTADO", empresa.getEstado()); 
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalEmpresas");
			totalEmpresas =  total.get(0).intValue();	
		return totalEmpresas;
	}
	public List<Empresa> listarEmpresaUsuario(Empresa empresa, Integer inicio,
			Integer fin) throws Exception {
		List<Empresa> lEmpresas = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_EMPRESAUSUARIO")
		.returningResultSet("empresasUsuario", new EmpresaUsuarioMapper());
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_ID_EMP", empresa.getIdEmpresa()) 
		.addValue("P_IDUSUARIO", empresa.getUsuarioDto().getIdUsuario())  
		.addValue("P_INICIO_RESULTADO", inicio)
		.addValue("P_FIN_RESULTADO", fin);
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lEmpresas = (List<Empresa> ) resultado.get("empresasUsuario");	
		return lEmpresas;
	}

 

}
