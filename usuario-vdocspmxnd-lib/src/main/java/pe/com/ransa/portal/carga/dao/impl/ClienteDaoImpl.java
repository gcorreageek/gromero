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
import pe.com.ransa.portal.carga.dao.ClienteDao;
import pe.com.ransa.portal.carga.dao.mapper.CantidadAPRMapper;
import pe.com.ransa.portal.carga.dao.mapper.ClienteMapper;
import pe.com.ransa.portal.carga.dao.mapper.ClienteUsuarioMapper;
import pe.com.ransa.portal.carga.dto.Cliente;

@Repository
public class ClienteDaoImpl implements ClienteDao {
	
	private static Logger logger = Logger.getLogger(ClienteDaoImpl.class);
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
 
	public Cliente ingresarCliente(Cliente cliente) throws Exception {
//		boolean seIngreso = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_CODIGO", Integer.parseInt(cliente.getCodigo()))
		.addValue("IN_RUC", cliente.getRUC())
		.addValue("IN_RAZON_SOCIAL", cliente.getRazonSocial())
		.addValue("IN_ESTADO", cliente.getEstado())
		.addValue("IN_FECHA_CREACION", cliente.getFechaCreacion())
		.addValue("IN_USUARIO_CREACION", cliente.getUsuarioCreacion())
		.addValue("IN_FECHA_MODIFICACION", cliente.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION", cliente.getUsuarioModificacion());
		 Map<String, Object> out  = null;
//		 try {
			out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_INSERT_CLIENTE").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
			BigInteger id =   new BigInteger(out.get("OUT_ID_CLI").toString());
			cliente.setId(id); 
//			seIngreso = true;
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//			seIngreso = false;
//		}finally{
//			cliente.setSeIngreso(seIngreso);
//		}
		return cliente;
	}  
	public Cliente ingresarUsuarioCliente(Cliente cliente,String usuario) throws Exception {
//		boolean seIngreso = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_IDUSUARIO", usuario)
		.addValue("IN_ID_CLI", cliente.getId())  
		.addValue("IN_ESTADO", ConstantesLib.Estado.ESTADO_A.estado)
		.addValue("IN_FECHA_CREACION", new Date())
		.addValue("IN_USUARIO_CREACION", "DEFAULT")
		.addValue("IN_FECHA_MODIFICACION", null)
		.addValue("IN_USUARIO_MODIFICACION", null);
		 Map<String, Object> out  = null;
//		try {
			out= jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_INSERT_USUARIOCLIENTE").execute(in);
//			seIngreso= true;
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//			seIngreso= false;
//		}finally{
//			cliente.setSeIngreso(seIngreso);
//		} 
		cliente.setUsuario(usuario);
		return cliente;
	}
	public BigInteger getIdClienteXruc(String ruc) throws Exception {
		BigInteger id = new BigInteger("0");
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource() 
		.addValue("IN_RUC", ruc);
		 Map<String, Object> out  = null;
//		 try {
			out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_GET_IDCLI_X_RUC").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
			id =   new BigInteger(out.get("OUT_ID_CLI").toString());
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//		} 
		return id;
	} 
	public List<Cliente> listarCliente(Cliente cliente, Integer inicio, Integer fin)throws Exception  {
		logger.debug("===>"+cliente.getRUC()+"|"+cliente.getRazonSocial()+"|"+cliente.getCodigo()+"|"+cliente.getEstado()+"|"+inicio+"|"+fin);
		List<Cliente> lCliente = null;
//		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCSPMXND")
			.withProcedureName("SP_LISTAR_CLIENTES")
			.returningResultSet("clientes", new ClienteMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_CLI", cliente.getId())
			.addValue("P_RUC", cliente.getRUC())
			.addValue("P_RAZONSOCIAL", cliente.getRazonSocial())
			.addValue("P_CODAUXILIAR", cliente.getCodigo())
			.addValue("P_ESTADO", cliente.getEstado())
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lCliente = (List<Cliente> ) resultado.get("clientes");	
			logger.debug("lCliente:"+lCliente.size());
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//		}  
		return lCliente;
	}
	public Integer totalCliente(Cliente cliente)throws Exception  {
		Integer totalClientes=null;
//		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCSPMXND")
			.withProcedureName("SP_GET_TOTAL_LISTAR_CLIENTES")
			.returningResultSet("totalClientes", new CantidadAPRMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_CLI", cliente.getId())
			.addValue("P_RUC", cliente.getRUC())
			.addValue("P_RAZONSOCIAL", cliente.getRazonSocial())
			.addValue("P_CODAUXILIAR", cliente.getCodigo())
			.addValue("P_ESTADO", cliente.getEstado());
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			List<Long> total = (List<Long>) resultado.get("totalClientes");
			totalClientes =  total.get(0).intValue();	
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//		} 
		logger.debug("totalClientes:"+totalClientes);
		return totalClientes;
	} 
	public Cliente modificarCliente(Cliente cliente) throws Exception {
//		boolean seModifico = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_CODIGO", Integer.parseInt(cliente.getCodigo()))
		.addValue("IN_RUC", cliente.getRUC())
		.addValue("IN_RAZON_SOCIAL", cliente.getRazonSocial()) 
		.addValue("IN_ESTADO", cliente.getEstado())
		.addValue("IN_FECHA_CREACION", cliente.getFechaCreacion())
		.addValue("IN_USUARIO_CREACION", cliente.getUsuarioCreacion())
		.addValue("IN_FECHA_MODIFICACION", cliente.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION", cliente.getUsuarioModificacion())
		.addValue("IN_ID_CLI", cliente.getId());
		 Map<String, Object> out  = null;
//		 try {
			out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_UPDATE_CLIENTE").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
//			seModifico = true;
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//			seModifico = false;
//		}finally{
//			cliente.setSeIngreso(seModifico);
//		}
		return cliente;
	} 
	public Cliente eliminarCliente(Cliente cliente)throws Exception  {
//		boolean seElimino = false;
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(this.jdbcTemplate);
		SqlParameterSource in = new MapSqlParameterSource()
		.addValue("IN_CODIGO", null)
		.addValue("IN_RUC", null)
		.addValue("IN_RAZON_SOCIAL", null)
		.addValue("IN_ESTADO", cliente.getEstado())
		.addValue("IN_FECHA_CREACION", cliente.getFechaCreacion())
		.addValue("IN_USUARIO_CREACION", cliente.getUsuarioCreacion())
		.addValue("IN_FECHA_MODIFICACION", cliente.getFechaModificacion())
		.addValue("IN_USUARIO_MODIFICACION", cliente.getUsuarioModificacion())
		.addValue("IN_ID_CLI", cliente.getId());
		logger.debug("==>"+cliente.getEstado()+"|"+cliente.getId());
		 Map<String, Object> out  = null;
//		 try {
			out = jdbcCall.withSchemaName("VDOCSPMXND").withProcedureName("SP_ELIMINAR_CLIENTE").execute(in);
			logger.debug("MAP=>"+ Arrays.toString(out.entrySet().toArray()));
//			seElimino = true;
//		} catch (Exception e) {
//			logger.error("[Exception]",e);
//			seElimino = false;
//		}finally{
//			cliente.setSeIngreso(seElimino);
//		}
		return cliente;
	}
	public List<Cliente> buscarClienteXruc(Cliente cliente, Integer inicio,
			Integer fin) throws Exception {
		logger.debug("===>"+cliente.getRUC()+"|"+inicio+"|"+fin);
		List<Cliente> lCliente = null;
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCSPMXND")
			.withProcedureName("SP_GET_CLIENTE_RUC")
			.returningResultSet("clientesXruc", new ClienteMapper());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_RUC", cliente.getRUC())
			.addValue("P_INICIO_RESULTADO", inicio)
			.addValue("P_FIN_RESULTADO", fin);
			Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
			lCliente = (List<Cliente> ) resultado.get("clientesXruc");	
			logger.debug("lCliente:"+lCliente.size());
		return lCliente;
	} 
	public List<Cliente> listarClienteUsuario(Cliente cliente) throws Exception {
		List<Cliente> lCliente = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("VDOCSPMXND")
			.withProcedureName("SP_LISTAR_USUARIOCLIENTE")
			.returningResultSet("clientesUsuario", new ClienteUsuarioMapper());
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue("P_ID_CLI", cliente.getId()) 
			.addValue("P_IDUSUARIO", cliente.getUsuario());
		Map<String, Object> resultado = simpleJdbcCall.execute(sqlParameterSource);
		lCliente = (List<Cliente> ) resultado.get("clientesUsuario");	
//		logger.debug("lCliente:"+lCliente.size());
		return lCliente;
	}
 
	 
 

}
