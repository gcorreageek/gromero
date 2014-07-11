package com.gromero.portal.visor.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gromero.portal.visor.domain.Area;
import com.gromero.portal.visor.domain.Empresa;
import com.gromero.portal.visor.domain.EmpresaArea;
import com.gromero.portal.visor.domain.Usuario;
import com.gromero.portal.visor.domain.UsuarioEmpresaArea;


public class UsuarioEmpresaAreaMapper implements RowMapper<UsuarioEmpresaArea>{

	@Override
	public UsuarioEmpresaArea mapRow(ResultSet rs, int i) throws SQLException {
		UsuarioEmpresaArea uea = new UsuarioEmpresaArea();
		uea.setIdUsuarioEmpresaArea(rs.getInt("idUsuarioEmpresasArea"));
		uea.setIdEmpresaArea(rs.getInt("idEmpresasArea"));
		uea.setIdUsuario(rs.getInt("idUsuario"));
		
		EmpresaArea ea = new EmpresaArea();
		ea.setIdEmpresaArea(rs.getInt("idEmpresasArea"));
		ea.setIdArea(rs.getInt("idArea"));
		ea.setIdEmpresa(rs.getInt("idEmpresa"));
		
		Area a = new Area();
		a.setIdArea(rs.getInt("idArea"));
		a.setArea(rs.getString("area"));
		a.setTipoD(rs.getInt("tipo_d"));
		ea.setArea(a);
		
		Empresa e = new Empresa();
		e.setIdEmpresa(rs.getInt("idEmpresa"));
		e.setEmpresa(rs.getString("empresa"));
		ea.setEmpresa(e);
		
		Usuario u = new Usuario();
		u.setIdUsuario(rs.getInt("idUsuario"));
		u.setNombres(rs.getString("nombres"));
		
		uea.setEmpresaArea(ea);
		uea.setUsuario(u); 
		return uea;
	}

}
