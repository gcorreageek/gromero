package com.gromero.portal.visor.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.gromero.portal.visor.constantes.ConstantesBD;
import com.gromero.portal.visor.domain.RSap21;
@Repository
public class RnsLibDAOImpl implements RnsLibDAO {
	
	
	private JdbcTemplate jdbcTemplate;
	 
    @Autowired
	public void setJdbcTemplate1(DataSource jdbcTemplate1) {
		this.jdbcTemplate = new JdbcTemplate(jdbcTemplate1);
	}

	@Override
	public String getNRFSAPPorNROOPG(BigDecimal nroFact) {
		List<String> NRFSAP =  this.jdbcTemplate.queryForList(ConstantesBD.RSAP21_SELECT_NRFSAP_X_NUMOPG,new Object[]{nroFact},  String.class);
		if(!NRFSAP.isEmpty()){ 
			return NRFSAP.get(0).toString();
		}  
		return null;
	}

	@Override
	public RSap21 rsap21Insert(RSap21 rsap21) {
		this.jdbcTemplate.update(ConstantesBD.RSAP21_INSERT,
		rsap21.getP1(),rsap21.getP2(),rsap21.getP3(),rsap21.getP4(),rsap21.getP5(),rsap21.getP6(),rsap21.getP7(),rsap21.getP8(),rsap21.getP9(),rsap21.getP10(),
		rsap21.getP11(),rsap21.getP12(),rsap21.getP13(),rsap21.getP14(),rsap21.getP15(),rsap21.getP16(),rsap21.getP17(),rsap21.getP18(),rsap21.getP19(),rsap21.getP20(),
		rsap21.getP21(),rsap21.getP22(),rsap21.getP23(),rsap21.getP24(),rsap21.getP25(),rsap21.getP26(),rsap21.getP27(),rsap21.getP28(),rsap21.getP29(),rsap21.getP30(),
		rsap21.getP31(),rsap21.getP32()
		);
		return rsap21;
	}
	
	@Override
	public void ejecutaProcedimientoSILEX21C(final Object... o) {
		this.jdbcTemplate.execute(ConstantesBD.PROCEDURE_SILEX21C, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
						ps.setString(1, o[0].toString());
						ps.setString(2, o[1].toString());
						ps.setString(3, o[2].toString());
						ps.setString(4, o[3].toString());
						ps.setString(5, o[4].toString());
						ps.setString(6, o[5].toString());
						ps.setString(7, o[6].toString());
						ps.setString(8, o[7].toString());
				return ps.execute();
			}
		});
	}
    
}
