package com.ransa.portal.exception;

import org.apache.log4j.Logger;

import com.ransa.portal.util.Util;

public class EjecucionException extends RuntimeException {

	private static final long serialVersionUID = 3229798042378087935L;
	private static Logger logger = Logger.getLogger(EjecucionException.class);
	private int parametro;
	
	public EjecucionException(String mensaje, Exception e){
		super(mensaje, e);
		logger.error("EjecucionException >> " + mensaje);		
		Util.showStackTrace(e);
	}
	
	public EjecucionException(String mensaje){
		super(mensaje);
		logger.error("EjecucionException >> " + mensaje);
	}
	
	public EjecucionException(String mensaje, int parametro){
		super(mensaje);
		logger.error("EjecucionException >> " + mensaje);
		this.parametro = parametro;
		logger.debug("parametro=" + getParametro());
	}

	public int getParametro() {
		return parametro;
	}
	
}