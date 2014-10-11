package pe.com.ransa.portal.carga.dao.impl;

import java.math.BigInteger;
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
import pe.com.ransa.portal.carga.dao.CuentaDao;
import pe.com.ransa.portal.carga.dao.mapper.CantidadAPRMapper;
import pe.com.ransa.portal.carga.dao.mapper.CuentaMapper;
import pe.com.ransa.portal.carga.dao.mapper.CuentaUsuarioMapper;
import pe.com.ransa.portal.carga.dto.Cuenta;

@Repository
public class CuentaDaoImpl implements CuentaDao{
	private static Logger logger = Logger.getLogger(CuentaDaoImpl.class);
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
	public Cuenta ingresarCuenta(Cuenta cuenta) {
		logger.debug("=>"+cuenta.getRuc()+"|"+cuenta.getNumeroCuenta()+"|"+
				cuenta.getPlacaVehicular()+"|"+cuenta.getNombreUsuario()+"|"+cuenta.getCodigoEs()+"|"+ConstantesLib.Estado.ESTADO_A.estado);
		boolean seIngreso = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_RUC", cuenta.getRuc())
		.addValue("IN_NRO_CUENTA", cuenta.getNumeroCuenta())
		.addValue("IN_PLACA_VEHICULAR",  cuenta.getPlacaVehicular())
		.addValue("IN_NOMBRE_USUARIO", cuenta.getNombreUsuario())
		.addValue("IN_CODIGO_ES", cuenta.getCodigoEs())
		.addValue("IN_ESTADO", ConstantesLib.Estado.ESTADO_A.estado)
		.addValue("IN_FECHA_CREACION", new Date())
		.addValue("IN_USUARIO_CREACION", "DEFAULT")
		.addValue("IN_FECHA_MODIFICACION", null)
		.addValue("IN_USUARIO_MODIFICACION", null); 
		 Map<String, Object> out  = null;
		 try {
			out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_INSERT_CUENTA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
			BigInteger idCuenta =   new BigInteger(out.get("OUT_ID_CUENTA").toString());
			BigInteger idCli =   new BigInteger(out.get("OUT_ID_CLI").toString());
			cuenta.setIdCliente(idCli);
			cuenta.setIdCuenta(idCuenta);
			seIngreso = true;
		} catch (Exception e) {
			logger.error("[Exception]",e);
			seIngreso = false;
		}finally{
			cuenta.setSeIngreso(seIngreso);
		}
		return cuenta;
	} 
	public Cuenta ingresarUsuarioCuenta(Cuenta cuenta, String usuario) {
		boolean seIngreso = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_IDUSUARIO", usuario)
		.addValue("IN_ID_CUENTA", cuenta.getIdCuenta())  
		.addValue("IN_ID_CLI", cuenta.getIdCliente())  
		
		.addValue("IN_ESTADO", ConstantesLib.Estado.ESTADO_A.estado)
		.addValue("IN_FECHA_CREACION", new Date())
		.addValue("IN_USUARIO_CREACION", "DEFAULT")
		.addValue("IN_FECHA_MODIFICACION", null)
		.addValue("IN_USUARIO_MODIFICACION", null);
		 Map<String, Object> out  = null;
		try {
			out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_INSERT_USUARIOCUENTA").execute(in);
			seIngreso= true;
		} catch (Exception e) {
			logger.error("[Exception]",e);
			seIngreso= false;
		}finally{
			cuenta.setSeIngreso(seIngreso);
		} 
		cuenta.setUsuario(usuario);
		return cuenta;
	}
	public Cuenta modificarCuenta(Cuenta cuenta) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_NRO_CUENTA", cuenta.getNumeroCuenta())
		.addValue("IN_PLACA_VEHICULAR", cuenta.getPlacaVehicular())
		.addValue("IN_NOMBRE_USUARIO",cuenta.getNombreUsuario())
		.addValue("IN_CODIGO_ES", cuenta.getCodigoEs())
		
		.addValue("IN_ESTADO", cuenta.getEstado())
		.addValue("IN_FECHA_CREACION", cuenta.getFechaCreacion())
		.addValue("IN_USUARIO_CREACION", cuenta.getUsuarioCreacion())
		.addValue("IN_FECHA_MODIFICACION", cuenta.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  cuenta.getUsuarioModificacion())
		.addValue("IN_ID_CUENTA", cuenta.getIdCuenta())
		.addValue("IN_ID_CLI", cuenta.getIdCliente());
		 Map<String, Object> out  = null;
			out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_UPDATE_CUENTA").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return cuenta;
	}
 
	public Cuenta eliminarCuenta(Cuenta cuenta) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_ESTADO", cuenta.getEstado())
		.addValue("IN_FECHA_CREACION", cuenta.getFechaCreacion())
		.addValue("IN_USUARIO_CREACION", cuenta.getUsuarioCreacion())
		.addValue("IN_FECHA_MODIFICACION", cuenta.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION",  cuenta.getUsuarioModificacion())
		.addValue("IN_ID_CUENTA", cuenta.getIdCuenta())
		.addValue("IN_ID_CLI", cuenta.getIdCliente());
		Map<String, Object> out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_ELIMINAR_CUENTA").execute(in);
		logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return cuenta;
	}
	public List<Cuenta> listarCuenta(Cuenta cuenta, Integer inicio, Integer fin) throws Exception{
		logger.debug("===>"+cuenta);
		logger.debug("===>"+cuenta.getIdCuenta()+"|"+cuenta.getIdCliente()+"|"+inicio+"|"+fin);
		List<Cuenta> lCuenta = null;
//		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCSPMXND")
			.withProcedureName("SP_LISTAR_CUENTAS")
			.returningResultSet("cuentas", new CuentaMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_CUENTA", cuenta.getIdCuenta())
			.addValue("P_ID_CLI", cuenta.getIdCliente())  
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lCuenta = (List<Cuenta> ) resultado.get("cuentas");	
			logger.debug("lCuenta:"+lCuenta.size());
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//		}  
		return lCuenta;
	}
	public Integer totalCuenta(Cuenta cuenta) throws Exception{
		Integer totalClientes=null;
//		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCSPMXND")
			.withProcedureName("SP_GET_TOTAL_LISTAR_CUENTAS")
			.returningResultSet("totalCuentas", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_CUENTA", cuenta.getIdCuenta())
			.addValue("P_ID_CLI", cuenta.getIdCliente())
			.addValue("P_ESTADO", cuenta.getEstado());
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalCuentas");
			totalClientes =  total.get(0).intValue();	
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//		} 
		logger.debug("totalClientes:"+totalClientes);
		return totalClientes;
	}
	public Cuenta ingresarCuenta2(Cuenta cuenta) throws Exception {
		logger.debug("=>"+cuenta.getIdCliente()+"|"+cuenta.getNumeroCuenta()+"|"+
				cuenta.getPlacaVehicular()+"|"+cuenta.getNombreUsuario()+"|"+
				cuenta.getCodigoEs()+"|"+cuenta.getEstado());
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_ID_CLI", cuenta.getIdCliente())
		.addValue("IN_NRO_CUENTA", cuenta.getNumeroCuenta())
		.addValue("IN_PLACA_VEHICULAR",  cuenta.getPlacaVehicular())
		.addValue("IN_NOMBRE_USUARIO", cuenta.getNombreUsuario())
		.addValue("IN_CODIGO_ES", cuenta.getCodigoEs())
		.addValue("IN_ESTADO", cuenta.getEstado())
		.addValue("IN_FECHA_CREACION", cuenta.getFechaCreacion())
		.addValue("IN_USUARIO_CREACION", cuenta.getUsuarioCreacion())
		.addValue("IN_FECHA_MODIFICACION", cuenta.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION", cuenta.getUsuarioModificacion()); 
		 Map<String, Object> out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_INSERT_CUENTA2").execute(in);
		 logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
		return cuenta;
	}
	public List<Cuenta> listarCuentaUsuario(Cuenta cuenta) throws Exception {
//		logger.debug("==>"+cuenta.getIdCliente()+"|"+cuenta.getIdCuenta()+"|"+cuenta.getUsuario());
		List<Cuenta> lCuenta = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCSPMXND")
			.withProcedureName("SP_LISTAR_USUARIOCUENTA")
			.returningResultSet("usuarioCuenta", new CuentaUsuarioMapper());
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_CLI", cuenta.getIdCliente()) 	
			.addValue("P_ID_CUENTA", cuenta.getIdCuenta())
			.addValue("P_IDUSUARIO", cuenta.getUsuario());
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lCuenta = (List<Cuenta> ) resultado.get("usuarioCuenta");	
//		logger.debug("lCuentaUsuario:"+lCuenta.size());
		return lCuenta;
	}
 
 

}
