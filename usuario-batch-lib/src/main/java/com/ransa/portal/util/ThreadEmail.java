package com.ransa.portal.util;

import org.apache.log4j.Logger;

public class ThreadEmail extends Thread {

	private static Logger logger = Logger.getLogger(ThreadEmail.class);
	private String[] estructuraMensajeCorreoElectronico;
	private boolean envioFallido;
	
	public ThreadEmail(ThreadGroup grupoEnvioCorreos, String nombreThread,
			String[] estructuraMensajeCorreoElectronico) {
		super(grupoEnvioCorreos, nombreThread);
		logger.debug("ThreadEmail >> Constructor");
		this.estructuraMensajeCorreoElectronico = estructuraMensajeCorreoElectronico;
		envioFallido = false;
	}

	@Override
	public void run() {
		logger.debug("inicio de ejecucion de thread: " + getName());
		try {
			Util.enviarCorreoElectronico(estructuraMensajeCorreoElectronico[2], estructuraMensajeCorreoElectronico[3],
					estructuraMensajeCorreoElectronico[0], estructuraMensajeCorreoElectronico[1]);
			logger.debug("envio correcto de mensaje de correo electronico");
		} catch (Exception e) {
			logger.error("no se pudo enviar correo electronico a [" + estructuraMensajeCorreoElectronico[4] + 
					", "+ estructuraMensajeCorreoElectronico[1] + "]");
			envioFallido = true;
		}
		logger.debug("fin de ejecucion de thread: " + getName());
	}
	
	public String getIdUsuarioEnvioCorreoElectronico() {
		return estructuraMensajeCorreoElectronico[4];
	}
	
	public String getCorreoElectronicoUsuarioEnvio() {
		return estructuraMensajeCorreoElectronico[1];
	}

	public boolean isEnvioFallido() {
		return envioFallido;
	}	
	
}
