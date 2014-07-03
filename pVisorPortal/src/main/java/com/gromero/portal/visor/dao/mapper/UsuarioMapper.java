package com.gromero.portal.visor.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gromero.portal.visor.domain.Usuario;


public class UsuarioMapper implements RowMapper<Usuario>{

	@Override
	public Usuario mapRow(ResultSet rs, int i) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(rs.getInt("IDUSUARIO"));
		usuario.setNombres(rs.getString("NOMBRES"));
		usuario.setUserName(rs.getString("USER_NAME"));
		usuario.setUserPass(rs.getString("USER_PASS"));
		usuario.setRolD(rs.getInt("ROL_D"));
		return usuario;
	}

}
