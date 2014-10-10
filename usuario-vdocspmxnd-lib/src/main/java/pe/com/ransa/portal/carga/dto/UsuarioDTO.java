package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.util.Date;

import com.ransa.portal.model.Usuario;

public class UsuarioDTO implements Serializable {
  
	private static final long serialVersionUID = 4728774986605984103L;
	private String idUsuario;
	private Integer idTipoUsuario;
	private String stsUsuario;
	private Date fecCrea;
	private String usrCrea;
	private Date fecModif;
	private String usrModif;
	private String solicitante;
	
	private com.ransa.portal.model.Usuario usuarioDTO;
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(Integer idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	public String getStsUsuario() {
		return stsUsuario;
	}
	public void setStsUsuario(String stsUsuario) {
		this.stsUsuario = stsUsuario;
	}
	public Date getFecCrea() {
		return fecCrea;
	}
	public void setFecCrea(Date fecCrea) {
		this.fecCrea = fecCrea;
	}
	public String getUsrCrea() {
		return usrCrea;
	}
	public void setUsrCrea(String usrCrea) {
		this.usrCrea = usrCrea;
	}
	public Date getFecModif() {
		return fecModif;
	}
	public void setFecModif(Date fecModif) {
		this.fecModif = fecModif;
	}
	public String getUsrModif() {
		return usrModif;
	}
	public void setUsrModif(String usrModif) {
		this.usrModif = usrModif;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public com.ransa.portal.model.Usuario getUsuarioDTO() {
		if(usuarioDTO==null)
			usuarioDTO = new com.ransa.portal.model.Usuario();
		return usuarioDTO;
	}
	public void setUsuarioDTO(com.ransa.portal.model.Usuario usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}
	
	
	
	
}
