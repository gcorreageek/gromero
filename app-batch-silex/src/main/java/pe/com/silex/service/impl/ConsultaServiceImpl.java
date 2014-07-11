package pe.com.silex.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.silex.dao.ConsultaDao;
import pe.com.silex.service.ConsultaService;

@Component
public class ConsultaServiceImpl implements ConsultaService {
	
	private ConsultaDao consultaDao;
	private static final Logger logger = Logger.getLogger(ConsultaServiceImpl.class);
	
	@Autowired
	public void setConsultaDao(ConsultaDao consultaDao) {
		this.consultaDao = consultaDao;
	}
}
