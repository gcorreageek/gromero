package com.ransa.portal.exception;

import org.apache.log4j.Logger;

import com.ransa.portal.util.Util;

public class UsuarioException extends RuntimeException {

	private static final long serialVersionUID = 4347489184090891521L;
	private static Logger logger = Logger.getLogger(UsuarioException.class);
	private int parametro;
	
	public UsuarioException(String mensaje){
		super(mensaje);
		parametro = Util.NUMERO_VACIO;
		logger.error("UsuarioException >> " + mensaje + ", NUM_VACIO");
	}

	public UsuarioException(String mensaje, Exception e){
		super(mensaje, e);
		parametro = Util.NUMERO_VACIO;
		logger.error("UsuarioException >> " + mensaje + ", NUM_VACIO");
		Util.showStackTrace(e);
	}
	
	public UsuarioException(String mensaje, Exception e, int parametro){
		super(mensaje, e);
		this.parametro = parametro;
		logger.error("UsuarioException >> " + mensaje + Util.SEPARADOR2 + this.parametro);
		Util.showStackTrace(e);
	}

	public UsuarioException(String mensaje, int parametro){
		super(mensaje);
		this.parametro = parametro;
		logger.error("UsuarioException >> " + mensaje + Util.SEPARADOR2 + this.parametro);		
	}
	
	public int getParametro() {
		return parametro;
	}

	public void setParametro(int parametro) {
		this.parametro = parametro;
	}	
}
