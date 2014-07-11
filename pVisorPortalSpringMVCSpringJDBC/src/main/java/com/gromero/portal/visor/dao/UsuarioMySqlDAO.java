package com.gromero.portal.visor.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.gromero.portal.visor.constantes.ConstantesComunes;
import com.gromero.portal.visor.dao.mapper.UsuarioMapper;
import com.gromero.portal.visor.domain.Usuario;

@Repository
public class UsuarioMySqlDAO implements UsuarioDAO {

	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@Override
	public Usuario insertarOrActualizar(Usuario a) {
		log.debug("here insert dao usuario"); 
		if(a.getIdUsuario()==null){
			String sql = "INSERT INTO USUARIO(IDUSUARIO, NOMBRES, USER_NAME, USER_PASS, ROL_D)  VALUES(?, ?, ?, ?, ?)";
			jdbcTemplate.update(sql,null,a.getNombres(),a.getUserName(),a.getUserPass(),a.getRolD());
		}else{
			String sql = "UPDATE USUARIO NOMBRES=?, USER_NAME=?, USER_PASS=?, ROL_D=? WHERE IDUSUARIO=? ";
			jdbcTemplate.update(sql,a.getNombres(),a.getUserName(),a.getUserPass(),a.getRolD(),a.getIdUsuario());
		} 
		return a;
	}

	@Override
	public List<Usuario> buscarXid(Usuario a) {
		List<Usuario> lstUsuario = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(ConstantesComunes.SCHEMADC)
					.withProcedureName("SP_USUARIO_BUSCAR_ID").returningResultSet("buscaUsuarioIdUsuario", new UsuarioMapper());
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("V_IDUSUARIO", a.getIdUsuario());//.addValue("", a.getUserName());
		Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
		lstUsuario = (List<Usuario>) result.get("buscaUsuarioIdUsuario");
		return lstUsuario;
	}

	@Override
	public List<Usuario> buscarXuserName(Usuario a) {
		List<Usuario> lstUsuario = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(ConstantesComunes.SCHEMADC)
					.withProcedureName("SP_USUARIO_BUSCAR_USERNAME").returningResultSet("buscaUsuarioUserName", new UsuarioMapper());
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("V_USERNAME", a.getUserName());//.addValue("", a.getUserName());
		Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
		lstUsuario = (List<Usuario>) result.get("buscaUsuarioUserName");
		return lstUsuario;
	}

}
