package pe.com.ransa.portal.carga.viemodel;

import java.io.InputStream;
import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
 
public class FormCommand extends BaseCredencialesCommand implements Serializable{
 
	private static final long serialVersionUID = -1358035440486108364L;
	private String txtRuc;
	private String txtRazonSocial;
	private String txtCodAuxiliar;
	private String txtEstado; 
	 
	private Integer inicio; 
	private String mensajeError;
	private Integer cantidadRegistros;
	
	
	private boolean success;
	private String showIn;
    private String message;
    private String description; 
	
 
	public Integer getInicio() {
		return inicio;
	}
	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public Integer getCantidadRegistros() {
		return cantidadRegistros;
	}
	public void setCantidadRegistros(Integer cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getShowIn() {
		return showIn;
	}
	public void setShowIn(String showIn) {
		this.showIn = showIn;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTxtRuc() {
		return txtRuc;
	}
	public void setTxtRuc(String txtRuc) {
		this.txtRuc = txtRuc;
	}
	public String getTxtRazonSocial() {
		return txtRazonSocial;
	}
	public void setTxtRazonSocial(String txtRazonSocial) {
		this.txtRazonSocial = txtRazonSocial;
	}
	public String getTxtCodAuxiliar() {
		return txtCodAuxiliar;
	}
	public void setTxtCodAuxiliar(String txtCodAuxiliar) {
		this.txtCodAuxiliar = txtCodAuxiliar;
	}
	public String getTxtEstado() {
		return txtEstado;
	}
	public void setTxtEstado(String txtEstado) {
		this.txtEstado = txtEstado;
	}
 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
