package pe.com.ransa.portal.carga.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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

import com.ibm.ws.ssl.commands.SSLConfig.GetInheritedSSLConfig;

import pe.com.ransa.portal.carga.dao.UsuarioDTODao;
import pe.com.ransa.portal.carga.dao.mapper.AccesoCuentaMapper;
import pe.com.ransa.portal.carga.dao.mapper.AccesoDocumentosMapper;
import pe.com.ransa.portal.carga.dao.mapper.CantidadAPRMapper;
import pe.com.ransa.portal.carga.dao.mapper.UsuarioDTOMapper;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
 

@Repository
public class UsuarioDTODaoImpl implements UsuarioDTODao {
	private static Logger logger = Logger.getLogger(UsuarioDTODaoImpl.class);
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
	public List<UsuarioDTO> listar(UsuarioDTO usuario, Integer inicio, Integer fin) {
		logger.debug("es nesesario=>"+"listar");
		logger.debug("===>"+usuario.getIdUsuario()+"|"+usuario.getStsUsuario()+"|"+inicio+"|"+fin);
		List<UsuarioDTO> lUsuarios = null;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("PORTAL")
			.withProcedureName("SP_LISTAR_USUARIO")
			.returningResultSet("usuarios", new UsuarioDTOMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_IDUSUARIO", usuario.getIdUsuario())
			.addValue("P_IDTIPOUSUARIO", usuario.getIdTipoUsuario()) 
			.addValue("P_STSUSUARIO", usuario.getStsUsuario())
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lUsuarios = (List<UsuarioDTO> ) resultado.get("usuarios");	
			logger.debug("lUsuarios:"+lUsuarios.size());
		} catch (Exception e) {
			logger.error("[Exception]",e);
		}  
		return lUsuarios;
	}
	public Integer total(UsuarioDTO usuario) {
		Integer totalUsuarios=null;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("PORTAL")
			.withProcedureName("SP_GET_TOTAL_LISTAR_USUARIO")
			.returningResultSet("totalUsuarios", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_IDUSUARIO", usuario.getIdUsuario())
			.addValue("P_IDTIPOUSUARIO", usuario.getIdTipoUsuario()) 
			.addValue("P_STSUSUARIO", usuario.getStsUsuario()); 
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalUsuarios");
			totalUsuarios =  total.get(0).intValue();	
		} catch (Exception e) {
			logger.error("[Exception]",e);
		} 
		logger.debug("totalUsuarios:"+totalUsuarios);
		return totalUsuarios;
	}
	@SuppressWarnings("unchecked")
	public List<TipoDocumental> listar(TipoDocumental td, Integer inicio, Integer fin) {
		logger.debug("===>"+td.getUsuario().getIdUsuario()+"|"+inicio+"|"+fin);
		List<TipoDocumental> lTd = null;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_LISTAR_ACCESODOC")
			.returningResultSet("acceso_documentos", new AccesoDocumentosMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource() 
			.addValue("P_IDUSUARIO", td.getUsuario().getIdUsuario())
			.addValue("P_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
			.addValue("P_ID_AREA", td.getArea().getIdArea())
			.addValue("P_ID_TIPODOCUMENTAL", td.getIdTipoDocumental())
			.addValue("P_NOMBRE_E", td.getArea().getEmpresa().getNombre()) 
			.addValue("P_NOMBRE_A", td.getArea().getNombre())
			.addValue("P_NOMBRE_T", td.getNombre())
			.addValue("P_ESTADO", td.getEstado())
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lTd = (List<TipoDocumental> ) resultado.get("acceso_documentos");	
			logger.debug("lUsuarios:"+lTd.size());
		} catch (Exception e) {
			logger.error("[Exception]",e);
		}  
		return lTd;
	}
	public Integer total(TipoDocumental td) {
		Integer totalTD=null;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_GET_TOTAL_LISTAR_ACCESODOC")
			.returningResultSet("totalAccesoDocumental", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_IDUSUARIO", td.getUsuario().getIdUsuario())
			.addValue("P_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
			.addValue("P_ID_AREA", td.getArea().getIdArea())
			.addValue("P_ID_TIPODOCUMENTAL", td.getIdTipoDocumental())
			.addValue("P_NOMBRE_E", td.getArea().getEmpresa().getNombre()) 
			.addValue("P_NOMBRE_A", td.getArea().getNombre())
			.addValue("P_NOMBRE_T", td.getNombre())
			.addValue("P_ESTADO", td.getEstado());
			
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalAccesoDocumental");
			totalTD =  total.get(0).intValue();	
		} catch (Exception e) {
			logger.error("[Exception]",e);
		} 
		logger.debug("totalTD:"+totalTD);
		return totalTD;
	}
	public List<Cuenta> listar(Cuenta cuenta, Integer inicio, Integer fin) {
		logger.debug("===>"+cuenta.getUsuarioDto().getIdUsuario()+"|"+inicio+"|"+fin);
		List<Cuenta> lCuenta = null;
		try {//IN P_ID_CLI BIGINT,IN P_ID_CUENTA BIGINT
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_LISTAR_ACCESOCUENTAS")
			.returningResultSet("acceso_cuentas", new AccesoCuentaMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_IDUSUARIO", cuenta.getUsuarioDto().getIdUsuario()) 
			.addValue("P_ID_CLI", cuenta.getIdCliente()) 
			.addValue("P_ID_CUENTA", cuenta.getIdCuenta()) 
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lCuenta = (List<Cuenta> ) resultado.get("acceso_cuentas");	
			logger.debug("lCuenta:"+lCuenta.size());
		} catch (Exception e) {
			logger.error("[Exception]",e);
		}  
		return lCuenta;
	}
	public Integer total(Cuenta cuenta) {
		Integer totalTD=null;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
			.withProcedureName("SP_GET_TOTAL_LISTAR_ACCESOCUENTAS")
			.returningResultSet("totalAccesoCuentas", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_IDUSUARIO", cuenta.getUsuarioDto().getIdUsuario())
			.addValue("P_ID_CLI", cuenta.getIdCliente()) 
			.addValue("P_ID_CUENTA", cuenta.getIdCuenta()) ;
			
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalAccesoCuentas");
			totalTD =  total.get(0).intValue();	
		} catch (Exception e) {
			logger.error("[Exception]",e);
		} 
		logger.debug("totalTD:"+totalTD);
		return totalTD;
	}
	public List<TipoDocumental> listarTipoDocumentalEmpresaUsuario( TipoDocumental td, Integer inicio, Integer fin)throws Exception {
		List<TipoDocumental> lTipoDoc = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_TIPODOCUMENTALEMPRESAAREAUSUARIO")
		.returningResultSet("tipoDocumentalEmpresaAreaUsuario", new RowMapper<TipoDocumental>() {
			public TipoDocumental mapRow(ResultSet rs, int rowNum) throws SQLException {
				TipoDocumental td = new TipoDocumental(); 
				UsuarioDTO usuarioDto = new UsuarioDTO();
				usuarioDto.setIdUsuario(rs.getString("IDUSUARIO")); 
				td.setUsuario(usuarioDto);
				Area area = new Area();
				Empresa empresa = new Empresa();
				empresa.setIdEmpresa(rs.getBigDecimal("ID_EMP").toBigInteger());
				area.setEmpresa(empresa);
				area.setIdArea(rs.getBigDecimal("ID_AREA").toBigInteger());
				td.setArea(area);
				td.setIdTipoDocumental(rs.getBigDecimal("ID_TIP_DOC").toBigInteger());
				td.setEstado(rs.getString("ESTADO")); 
				return td;
			}
		});
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
		.addValue("P_ID_AREA", td.getArea().getIdArea())  
		.addValue("P_ID_TIP_DOC", td.getIdTipoDocumental())  
		.addValue("P_IDUSUARIO", td.getUsuario().getIdUsuario())
		.addValue("P_INICIO_RESULTADO", inicio)
		.addValue("P_FIN_RESULTADO", fin);
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lTipoDoc = (List<TipoDocumental> ) resultado.get("tipoDocumentalEmpresaAreaUsuario");	
		return lTipoDoc;
	}
	public List<TipoDocumental> listarUsuarioEmpresaAreaTipoDocumental( final TipoDocumental tdPasamos) {
		List<TipoDocumental> lTipoDoc = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCS")
		.withProcedureName("SP_LISTAR_USUARIOEMPRESAAREATD")
		.returningResultSet("listartipoDocumentalEmpresaAreaUsuario", new RowMapper<TipoDocumental>() {
			public TipoDocumental mapRow(ResultSet rs, int rowNum) throws SQLException {
				TipoDocumental td = new TipoDocumental(); 
				UsuarioDTO usuarioDto = new UsuarioDTO();
				usuarioDto.setIdUsuario(tdPasamos.getUsuario().getIdUsuario()); 
				td.setUsuario(usuarioDto);
				Area area = new Area();
				Empresa empresa = new Empresa();
				empresa.setNombre(rs.getString("E_NOMBRE"));
				empresa.setIdEmpresa(tdPasamos.getArea().getEmpresa().getIdEmpresa());
				area.setEmpresa(empresa);
				area.setIdArea(tdPasamos.getArea().getIdArea());
				area.setNombre(rs.getString("A_NOMBRE"));
				td.setArea(area);
				td.setIdTipoDocumental(tdPasamos.getIdTipoDocumental());
				td.setNombre(rs.getString("T_NOMBRE"));
				return td;
			}
		});
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
		.addValue("P_USUARIO", tdPasamos.getUsuario().getIdUsuario())
		.addValue("P_ID_EMP", tdPasamos.getArea().getEmpresa().getIdEmpresa())
		.addValue("P_ID_AREA", tdPasamos.getArea().getIdArea())  
		.addValue("P_ID_TIPODOCUMENTAL", tdPasamos.getIdTipoDocumental());
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lTipoDoc = (List<TipoDocumental> ) resultado.get("listartipoDocumentalEmpresaAreaUsuario");	
		return lTipoDoc;
	}
	public Empresa eliminarEmpresaUsuario(Empresa empresa) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
		.addValue("IN_ESTADO", empresa.getEstado()) 
		.addValue("IN_FECHA_MODIFICACION", empresa.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  empresa.getUsuarioModificacion())
		.addValue("IN_ID_EMP", empresa.getIdEmpresa())
		.addValue("IN_IDUSUARIO", empresa.getUsuarioDto().getIdUsuario());
		 Map<String, Object> out  = null;
		 out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_EMPRESAUSUARIO").execute(in);
		 logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return empresa; 
	}
	public Area eliminarEmpresaAreaUsuario(Area area) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
		.addValue("IN_ESTADO", area.getEstado()) 
		.addValue("IN_FECHA_MODIFICACION", area.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  area.getUsuarioModificacion())
		.addValue("IN_ID_EMP", area.getEmpresa().getIdEmpresa())
		.addValue("IN_ID_AREA", area.getIdArea())
		.addValue("IN_IDUSUARIO", area.getUsuarioDto().getIdUsuario());
		 Map<String, Object> out  = null;
		 out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_EMPRESAAREAUSUARIO").execute(in);
		 logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return area; 
	}
	public TipoDocumental eliminarEmpresaAreaTipoDocumentalUsuario( TipoDocumental td) throws Exception { 
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
		.addValue("IN_ESTADO", td.getEstado()) 
		.addValue("IN_FECHA_MODIFICACION", td.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  td.getUsuarioModificacion())
		.addValue("IN_ID_EMP", td.getArea().getEmpresa().getIdEmpresa())
		.addValue("IN_ID_AREA", td.getArea().getIdArea())
		.addValue("IN_ID_TIP_DOC", td.getIdTipoDocumental())
		.addValue("IN_IDUSUARIO", td.getUsuarioDto().getIdUsuario());
		 Map<String, Object> out  = null;
		 out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_ELIMINAR_EMPRESAAREATDUSUARIO").execute(in);
		 logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return td; 
	}
 
 
 

}
