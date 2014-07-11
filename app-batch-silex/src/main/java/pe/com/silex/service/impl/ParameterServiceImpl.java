package pe.com.silex.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.silex.bean.ParameterBean;
import pe.com.silex.dao.ParameterDao;
import pe.com.silex.service.ParameterService;
import pe.com.silex.util.Constantes;
import pe.com.silex.util.Utilitarios;

@Component
public class ParameterServiceImpl implements ParameterService{
	
	private ParameterDao parameterDao;
	private static final Logger logger = Logger.getLogger(ParameterServiceImpl.class);
	
	@Autowired
	public void setParameterDao(ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	@Override
	public long getPeriodicity() throws Exception {
//		logger.info("Inicia metodo getPeriodicity");
		ParameterBean pBean = parameterDao.getParameters(Constantes.ID_PERIODICITY);
		long valPeriodicity = Utilitarios.convertStringToLong(pBean.getVal1());
//		logger.debug("valor de valPeriodicity: " + valPeriodicity);
		return valPeriodicity;
	}

	@Override
	public long getSleepThread() throws Exception {
		ParameterBean pBean = parameterDao.getParameters(Constantes.ID_SLEEP);
		long valPeriodicity = Utilitarios.convertStringToLong(pBean.getVal1());
		return valPeriodicity;
	}
	
	
	
	
}
