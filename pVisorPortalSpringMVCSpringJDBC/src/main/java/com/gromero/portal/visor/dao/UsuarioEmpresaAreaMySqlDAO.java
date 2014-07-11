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
import com.gromero.portal.visor.dao.mapper.UsuarioEmpresaAreaMapper;
import com.gromero.portal.visor.domain.UsuarioEmpresaArea;

@Repository
public class UsuarioEmpresaAreaMySqlDAO implements UsuarioEmpresaAreaDAO {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public List<UsuarioEmpresaArea> buscarXidUsuario(UsuarioEmpresaArea a) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate; 
	
//	@Autowired
//    public void setProjectsDataSource(@Qualifier("projectsDataSource") DataSource projectsDataSource) {
//        this.jdbcTemplate = new JdbcTemplate(projectsDataSource);
//    }
 
//	@Override
//	public List<UsuarioEmpresaArea> buscarXidUsuario(UsuarioEmpresaArea a) {
//		List<UsuarioEmpresaArea> lstUsuarioEmpresaArea = null;
//		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName(ConstantesComunes.SCHEMADC)
//					.withProcedureName("SP_USUARIOEMPRESAAREA_BUSCAR_ID").returningResultSet("buscaUsuarioEmpresaAreaIdUsuario", new UsuarioEmpresaAreaMapper());
//		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("V_IDUSUARIO", a.getIdUsuario());//.addValue("", a.getUserName());
//		Map<String,Object> result = simpleJdbcCall.execute(sqlParameterSource);
//		lstUsuarioEmpresaArea = (List<UsuarioEmpresaArea>) result.get("buscaUsuarioEmpresaAreaIdUsuario");
//		return lstUsuarioEmpresaArea; 
//	}
 

}
