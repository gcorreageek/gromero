package pe.com.silex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.silex.bean.ParameterBean;

public class ParameterMapper implements RowMapper<ParameterBean> {

	@Override
	public ParameterBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ParameterBean pBean = new ParameterBean();
		pBean.setCodParam(rs.getInt(1));
		pBean.setDescParam(rs.getString(2));
		pBean.setVal1(rs.getString(3));
		pBean.setVal2(rs.getString(4));
		pBean.setVal3(rs.getString(5));
		
		return pBean;
	}

}
