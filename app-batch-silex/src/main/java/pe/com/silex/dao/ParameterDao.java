package pe.com.silex.dao;

import pe.com.silex.bean.ParameterBean;


public interface ParameterDao {
	
	public ParameterBean getParameters(int codParam) throws Exception; 

}
