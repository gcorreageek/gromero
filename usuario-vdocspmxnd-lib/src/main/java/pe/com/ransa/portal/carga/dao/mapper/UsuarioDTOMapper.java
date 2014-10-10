package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.UsuarioDTO;
 
public class UsuarioDTOMapper implements RowMapper<UsuarioDTO> {
	public UsuarioDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException { 
//SELECT T.IDUSUARIO,T.IDTIPOUSUARIO,T.STSUSUARIO,T.FECCREA,T.USRCREA,T.FECMODIF,T.USRMODIF,T.SOLICITANTE
		UsuarioDTO usuario = new UsuarioDTO(); 
		usuario.setIdUsuario(resultSet.getString("IDUSUARIO")); 
		usuario.setIdTipoUsuario(resultSet.getInt("IDTIPOUSUARIO"));
		usuario.setStsUsuario(resultSet.getString("STSUSUARIO"));
		usuario.setFecCrea(resultSet.getDate("FECCREA"));
		usuario.setUsrCrea(resultSet.getString("USRCREA"));
		usuario.setFecModif(resultSet.getDate("FECMODIF"));
		usuario.setUsrModif(resultSet.getString("USRMODIF"));
		usuario.setSolicitante(resultSet.getString("SOLICITANTE")); 
		return usuario;
	}
}