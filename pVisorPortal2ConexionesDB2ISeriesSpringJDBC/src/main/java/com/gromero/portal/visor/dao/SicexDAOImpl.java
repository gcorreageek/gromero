package com.gromero.portal.visor.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gromero.portal.visor.constantes.ConstantesBD;
import com.gromero.portal.visor.domain.TBN016;
@Repository
public class SicexDAOImpl implements SicexDAO {

	
	private JdbcTemplate jdbcTemplate;
	 
    @Autowired
	public void setJdbcTemplate1(DataSource jdbcTemplate2) {
		this.jdbcTemplate = new JdbcTemplate(jdbcTemplate2);
	}
//    SELECT UTLNUM,CLAVE FROM TAM044 ;

	@Override
	public BigDecimal actualizarObtenerNumerador(String codigo) {
		BigDecimal resp =  new BigDecimal(0); 
		List<String> NRFSAP =  this.jdbcTemplate.queryForList(ConstantesBD.TAM044_SELECT_LAST_ULTNUM,new Object[]{codigo},  String.class);
		if(!NRFSAP.isEmpty()){
			resp =new BigDecimal( NRFSAP.get(0).toString());
		}
		resp = resp.add(new BigDecimal(1));
		this.jdbcTemplate.update(ConstantesBD.TAM044_UPDATE_ULTNUM_X_CLAVE, resp, codigo);
		return resp; 
	}

	@Override
	public TBN016 actualizarTBN016Operaciones(TBN016 tbn016) {
		this.jdbcTemplate.update(ConstantesBD.TBN016_UPDATE_CON_NROPEDI_IDOPEPED,new Object[]{tbn016.getNROPEDI(),tbn016.getIDOPEPED()});
		return tbn016;
	}
	
	
}
