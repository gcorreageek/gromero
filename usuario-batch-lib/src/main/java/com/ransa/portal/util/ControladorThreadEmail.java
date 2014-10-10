package com.ransa.portal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class ControladorThreadEmail {

	private static Logger logger = Logger.getLogger(ControladorThreadEmail.class);
	
	private Collection<String> idUsuarios;
	private String asunto;
	private Collection<String> contenidos;
	private String contenido;
	private String correoElectronicoRemitente;
	private Collection<String> correosElectronicosDestinatarios;
	private boolean envioDiferenteContenidos;
	@SuppressWarnings("unused")
	private boolean envioTerminado;
	private Iterator<String> iteratorIdUsuarios;
	private Iterator<String> iteratorContenidos;
	private Iterator<String> iteratorCorreosElectronicosDestinatarios;
	private Collection<String> idUsuariosEnvioCorreoElectronicoFallido;
	private ThreadGroup grupoEnvioCorreos;
	private ThreadEmail[] threads;
	
	public ControladorThreadEmail() {
		logger.debug("ThreadEmail >> Constructor");
		envioTerminado = false;
		idUsuariosEnvioCorreoElectronicoFallido = new ArrayList<String>();
		grupoEnvioCorreos = new ThreadGroup("Grupo de Envio Masivo de Correos");
	}
	
	public Collection<String> getIdUsuarios() {
		return idUsuarios;
	}

	public void setIdUsuarios(Collection<String> idUsuarios) {
		this.idUsuarios = idUsuarios;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Collection<String> getContenidos() {
		return contenidos;
	}

	public void setContenidos(Collection<String> contenidos) {
		this.contenidos = contenidos;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public String getCorreoElectronicoRemitente() {
		return correoElectronicoRemitente;
	}

	public void setCorreoElectronicoRemitente(String correoElectronicoRemitente) {
		this.correoElectronicoRemitente = correoElectronicoRemitente;
	}

	public Collection<String> getCorreosElectronicosDestinatarios() {
		return correosElectronicosDestinatarios;
	}

	public void setCorreosElectronicosDestinatarios(Collection<String> correosElectronicosDestinatarios) {
		this.correosElectronicosDestinatarios = correosElectronicosDestinatarios;
	}

	public boolean isEnvioDiferenteContenidos() {
		return envioDiferenteContenidos;
	}

	public void setEnvioDiferenteContenidos(boolean envioDiferenteContenidos) {
		this.envioDiferenteContenidos = envioDiferenteContenidos;
	}
	
	public Object[] iniciarEnvioMasivo() {
		logger.debug("iniciarEnvioMasivo");
		Object[] resultado = new Object[3];
		iteratorIdUsuarios = idUsuarios.iterator();
		logger.debug("numIdUsuarios=" + idUsuarios.size());
		if (envioDiferenteContenidos) {
			iteratorContenidos = contenidos.iterator();
			logger.debug("numContenidos=" + contenidos.size());
		}		
		iteratorCorreosElectronicosDestinatarios = correosElectronicosDestinatarios.iterator();
		logger.debug("numCorreosElectronicosDestinatarios=" + correosElectronicosDestinatarios.size());
		int tamanio = correosElectronicosDestinatarios.size();
		threads = new ThreadEmail[tamanio];
		logger.debug("iniciando envio masivo de correos electronicos...");
		for (int i = 0; i < tamanio; i++) {
			threads[i] = new ThreadEmail(grupoEnvioCorreos, "Envio " + (i + 1), getEstructuraMensajeCorreoElectronico());
			//threads[i].start();
			threads[i].run();
		}
		logger.debug("fin de envio masivo de correos electronicos");
		/*
		logger.debug("iniciando envio masivo de correos electronicos...");
		while (!envioTerminado) {			
			if (grupoEnvioCorreos.activeCount() == 1) {
				envioTerminado = true;
			}
		};
		logger.debug("fin de envio masivo de correos electronicos");
		logger.debug("numThreadActivos=" + grupoEnvioCorreos.activeCount());
		*/
		StringBuffer tempIdUsuariosEnvioCorreoElectronicoFallido = new StringBuffer();
		boolean bandera1 = true;
		StringBuffer tempIdUsuariosEnvioCorreoElectronicoExitoso = new StringBuffer();
		boolean bandera2 = true;
		for (int i = 0; i < tamanio; i++) {			
			if (threads[i].isEnvioFallido()) {
				String tempIdUsuario = threads[i].getIdUsuarioEnvioCorreoElectronico().toUpperCase();
				logger.debug("tempIdUsuario=" + tempIdUsuario);
				idUsuariosEnvioCorreoElectronicoFallido.add(tempIdUsuario);
				if (bandera1) {
					tempIdUsuariosEnvioCorreoElectronicoFallido.append(tempIdUsuario);
					bandera1 = false;
				} else {
					tempIdUsuariosEnvioCorreoElectronicoFallido.append(Util.SEPARADOR3);
					tempIdUsuariosEnvioCorreoElectronicoFallido.append(tempIdUsuario);
				}
			} else {				
				String usuarioEnvioCorreoElectronicoExitoso = threads[i].getIdUsuarioEnvioCorreoElectronico().toUpperCase() + Util.SEPARADOR3
					+ threads[i].getCorreoElectronicoUsuarioEnvio();
				if (bandera2) {
					tempIdUsuariosEnvioCorreoElectronicoExitoso.append(usuarioEnvioCorreoElectronicoExitoso);
					bandera2 = false;
				} else {
					tempIdUsuariosEnvioCorreoElectronicoExitoso.append(Util.SEPARADOR4);
					tempIdUsuariosEnvioCorreoElectronicoExitoso.append(usuarioEnvioCorreoElectronicoExitoso);
				}
			}
		}
		logger.debug("numIdUsuariosEnvioCorreoElectronicoFallido=" + idUsuariosEnvioCorreoElectronicoFallido.size());
		resultado[0] = idUsuariosEnvioCorreoElectronicoFallido;
		resultado[1] = tempIdUsuariosEnvioCorreoElectronicoExitoso.toString();
		resultado[2] = tempIdUsuariosEnvioCorreoElectronicoFallido.toString();
		return resultado;
	}
	
	private String[] getEstructuraMensajeCorreoElectronico() {
		logger.debug("getEstructuraMensajeCorreoElectronico");
		String[] estructuraMensajeCorreoElectronico = new String[5];
		logger.debug("obteniendo estructuraMensajeCorreoElectronico[0]");
		estructuraMensajeCorreoElectronico[0] = correoElectronicoRemitente;
		logger.debug("obteniendo estructuraMensajeCorreoElectronico[1]");
		estructuraMensajeCorreoElectronico[1] = iteratorCorreosElectronicosDestinatarios.next();
		logger.debug("obteniendo estructuraMensajeCorreoElectronico[2]");
		estructuraMensajeCorreoElectronico[2] = asunto;
		logger.debug("obteniendo estructuraMensajeCorreoElectronico[3]");
		if (envioDiferenteContenidos) {
			estructuraMensajeCorreoElectronico[3] = iteratorContenidos.next();
		} else {
			estructuraMensajeCorreoElectronico[3] = contenido;
		}
		estructuraMensajeCorreoElectronico[4] = iteratorIdUsuarios.next();
		logger.debug("estructuraMensajeCorreoElectronico[0]=" + estructuraMensajeCorreoElectronico[0]);
		logger.debug("estructuraMensajeCorreoElectronico[1]=" + estructuraMensajeCorreoElectronico[1]);
		logger.debug("estructuraMensajeCorreoElectronico[2]=" + estructuraMensajeCorreoElectronico[2]);
		logger.debug("estructuraMensajeCorreoElectronico[3]=" + estructuraMensajeCorreoElectronico[3]);
		logger.debug("estructuraMensajeCorreoElectronico[4]=" + estructuraMensajeCorreoElectronico[4]);
		return estructuraMensajeCorreoElectronico;
	}	
	
}
