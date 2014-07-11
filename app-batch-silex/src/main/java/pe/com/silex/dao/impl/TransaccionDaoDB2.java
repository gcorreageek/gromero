package pe.com.silex.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import pe.com.silex.bean.RSap21;
import pe.com.silex.bean.TBN016;
import pe.com.silex.dao.TransaccionDao;
import pe.com.silex.util.ConstantesBD;

@Repository
public class TransaccionDaoDB2 implements TransaccionDao {
	private static final Logger logger = Logger.getLogger(TransaccionDaoDB2.class);
	
	private JdbcTemplate jdbcTemplate;
	private StringBuilder SQL;
	
	@Autowired
	public void setDataSourceDB2(DataSource dataSourceDB2) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceDB2);
	}
	public DataSource getDataSourceDB2() {
		return this.jdbcTemplate.getDataSource();
	}  
	
//	@Autowired //	public void setDataSourceDB2(DataSource dataSourceDB2){ //		this.jdbcTemplate = new JdbcTemplate(dataSourceDB2); //	} //	public DataSource getDataSourceDB2(){ //		return this.jdbcTemplate.getDataSource(); //	}
	
	private JdbcTemplate getJdbcTemplate(){
		return this.jdbcTemplate;
	}
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
