package pe.com.ransa.portal.carga.dao.impl;

import java.util.Arrays;
import java.util.Date;
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
import pe.com.ransa.portal.carga.dao.VDocsDao;
import pe.com.ransa.portal.carga.dto.EmpresaAreaUsuario;
import pe.com.ransa.portal.carga.dto.EmpresaUsuario;
import pe.com.ransa.portal.carga.dto.TipoDocEmpresaAreaUsuario;
@Repository
public class VDocsDaoImpl implements VDocsDao {
	private static Logger logger = Logger.getLogger(VDocsDaoImpl.class);
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
	
	public EmpresaUsuario ingresaEmpresaUsuario(EmpresaUsuario empresaUsuario) throws Exception {
//		boolean seIngreso = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_IDEMP", empresaUsuario.getIdEmp())
		.addValue("IN_IDUSUARIO", empresaUsuario.getIdUsuario())
		.addValue("IN_ESTADO", ConstantesLib.Estado.ESTADO_A.estado)
		.addValue("IN_FECHA_CREACION", new Date())
		.addValue("IN_USUARIO_CREACION", "DEFAULT")
		.addValue("IN_FECHA_MODIFICACION", null)
		.addValue("IN_USUARIO_MODIFICACION", null);
		 Map<String, Object> out  = null;
//		 try {
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_EMPRESAUSUARIO").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
//			seIngreso = true;
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//			seIngreso = false;
//		}finally{
//			empresaUsuario.setSeIngreso(seIngreso);
//		}
		return empresaUsuario;
	}

	public EmpresaAreaUsuario ingresaEmpresaAreaUsuario( EmpresaAreaUsuario empresaAreaUsuario)throws Exception {
//		boolean seIngreso = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_IDEMP", empresaAreaUsuario.getIdEmp())
		.addValue("IN_IDUSUARIO", empresaAreaUsuario.getIdUsuario())
		.addValue("IN_ID_AREA", empresaAreaUsuario.getIdArea())
		.addValue("IN_ESTADO", ConstantesLib.Estado.ESTADO_A.estado)
		.addValue("IN_FECHA_CREACION", new Date())
		.addValue("IN_USUARIO_CREACION", "DEFAULT")
		.addValue("IN_FECHA_MODIFICACION", null)
		.addValue("IN_USUARIO_MODIFICACION", null);
		 Map<String, Object> out  = null;
//		 try {
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_EMPRESAAREAUSUARIO").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
//			seIngreso = true;
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//			seIngreso = false;
//		}finally{
//			empresaAreaUsuario.setSeIngreso(seIngreso);
//		}
		return empresaAreaUsuario;
	}
	public TipoDocEmpresaAreaUsuario ingresaTipoDocEmpresaAreaUsuario( TipoDocEmpresaAreaUsuario tipoDocempresaAreaUsuario) throws Exception{
//		boolean seIngreso = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_IDEMP", tipoDocempresaAreaUsuario.getIdEmp())
		.addValue("IN_IDUSUARIO", tipoDocempresaAreaUsuario.getIdUsuario())
		.addValue("IN_ID_AREA", tipoDocempresaAreaUsuario.getIdArea())
		.addValue("IN_ID_TIP_DOC", tipoDocempresaAreaUsuario.getIdTipoDoc())
		.addValue("IN_ESTADO", ConstantesLib.Estado.ESTADO_A.estado)
		.addValue("IN_FECHA_CREACION", new Date())
		.addValue("IN_USUARIO_CREACION", "DEFAULT")
		.addValue("IN_FECHA_MODIFICACION", null)
		.addValue("IN_USUARIO_MODIFICACION", null);
		 Map<String, Object> out  = null;
//		 try {
			out = jdbcCall.withSchemaName("VDOCS").withProcedureName("SP_INSERT_TIPODOCEMPRESAAREAUSUARIO").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
//			seIngreso = true;
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//			seIngreso = false;
//		}finally{
//			tipoDocempresaAreaUsuario.setSeIngreso(seIngreso);
//		}
		return tipoDocempresaAreaUsuario;
	}

}
