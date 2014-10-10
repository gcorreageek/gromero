package pe.com.ransa.portal.carga.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;

public class EmpresaUsuarioMapper implements RowMapper<Empresa> {
	
	public Empresa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Empresa empresa = new Empresa(); 
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(resultSet.getString("IDUSUARIO"));
		empresa.setUsuarioDto(usuarioDto);
		empresa.setIdEmpresa(resultSet.getBigDecimal("ID_EMP").toBigInteger());
		empresa.setEstado(resultSet.getString("ESTADO")); 
		return empresa;
	}
}