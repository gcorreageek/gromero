package pe.com.silex.dao.impl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.com.silex.bean.ParameterBean;
import pe.com.silex.dao.ParameterDao;
import pe.com.silex.dao.mapper.ParameterMapper;

@Repository
public class ParameterDaoDB2 implements ParameterDao {

	private JdbcTemplate jdbcTemplate;
	private StringBuilder SQL;

	private static final Logger logger = Logger.getLogger(ParameterDaoDB2.class);
 
	@Autowired
	public void setDataSourceDB2(DataSource dataSourceDB2) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceDB2);
	}
	public DataSource getDataSourceDB2() {
		return this.jdbcTemplate.getDataSource();
	}  

//	@Autowired
//	public void setDataSourceSicex(DataSource dataSourceSicex) {
//		this.jdbcTemplate = new JdbcTemplate(dataSourceSicex);
//	} 
//	public DataSource getDataSourceSicex() {
//		return this.jdbcTemplate.getDataSource();
//	}

	private JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	@Override
	public ParameterBean getParameters(int codParam) throws Exception {
//		logger.info("Inicia metodo getParameters - codParam: " + codParam);

		SQL = new StringBuilder();

		SQL.append("SELECT CODPARAM, DESCPARAM, VAL1, VAL2, VAL3 FROM SICEX.TAM053 WHERE CODPARAM = ?");

//		logger.debug("SQL: " + SQL.toString());

		ParameterBean pBean = (ParameterBean) getJdbcTemplate().queryForObject(
				SQL.toString(), new Object[] { codParam },
				new ParameterMapper());

//		logger.debug("ParameterBean: " + pBean.toString());
		return pBean;
	}

}
