package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.util.Date;

public class AuditoriaTabla extends Resultado implements Serializable {
	  
	private static final long serialVersionUID = -5332071133730873565L;
	private Integer idAuditoria;
	private String estado;
	private Date fechaCreacion;
	private String usuarioCreacion;
	private Date fechaModificacion;
	private String usuarioModificacion;
	
	public Integer getIdAuditoria() {
		return idAuditoria;
	}
	public void setIdAuditoria(Integer idAuditoria) {
		this.idAuditoria = idAuditoria;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
 

}
