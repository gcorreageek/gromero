package pe.com.silex.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.silex.dao.TransaccionDao;
import pe.com.silex.service.TransaccionService;

@Component
public class TransaccionServiceImpl implements TransaccionService{
	
	private TransaccionDao transaccionDao;
	private static final Logger logger = Logger.getLogger(TransaccionServiceImpl.class);
	
	@Autowired
	public void setTransaccionDao(TransaccionDao transaccionDao) {
		this.transaccionDao = transaccionDao;
	}
}
