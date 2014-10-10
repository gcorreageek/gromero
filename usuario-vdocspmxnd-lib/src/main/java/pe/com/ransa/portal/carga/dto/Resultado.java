package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;

public class Resultado implements Serializable{
	private static final long serialVersionUID = 8530385139776997056L;
	
	private Integer idResultado;
	private Object objeto;
	private Object[] objectoMatriz;
	private Throwable throwable;
	private String mensajeError;
	private String codigoError;
	private boolean seGuardo;
	public Integer getIdResultado() {
		return idResultado;
	}
	public void setIdResultado(Integer idResultado) {
		this.idResultado = idResultado;
	}
	public Object getObjeto() {
		return objeto;
	}
	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
	public Object[] getObjectoMatriz() {
		return objectoMatriz;
	}
	public void setObjectoMatriz(Object[] objectoMatriz) {
		this.objectoMatriz = objectoMatriz;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	} 
	public boolean isSeGuardo() {
		return seGuardo;
	}
	public void setSeGuardo(boolean seGuardo) {
		this.seGuardo = seGuardo;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public String getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
 
	
	
	

}
