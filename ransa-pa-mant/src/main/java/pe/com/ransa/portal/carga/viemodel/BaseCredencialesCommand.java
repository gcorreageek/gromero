package pe.com.ransa.portal.carga.viemodel;

import java.io.Serializable;

/**
 * 
 * @author naudanter
 *
 */
public class BaseCredencialesCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1464650283225290922L;
	
	/************* Inicio: Credenciales de Usuario *****************/
	private String docIdentidad;
	private String idRol;
	private String username;
	private String idGrupoSelected;
	/************* Fin: Credenciales de Usuario ********************/
	private String idRolGrupo;
	
	private String correo;
	
	private String idUsuario;
	private Integer idAplicacion;
	private String permiso;
	private String idRecursoFavorito;	
	
	/**
	 * @return the docIdentidad
	 */
	public String getDocIdentidad() {
		return docIdentidad;
	}
	/**
	 * @param docIdentidad the docIdentidad to set
	 */
	public void setDocIdentidad(String docIdentidad) {
		this.docIdentidad = docIdentidad;
	}
	/**
	 * @return the idRol
	 */
	public String getIdRol() {
		return idRol;
	}
	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the idGrupoSelected
	 */
	public String getIdGrupoSelected() {
		return idGrupoSelected;
	}
	/**
	 * @param idGrupoSelected the idGrupoSelected to set
	 */
	public void setIdGrupoSelected(String idGrupoSelected) {
		this.idGrupoSelected = idGrupoSelected;
	}
	
	/**
	 * @return the idRolGrupo
	 */
	public String getIdRolGrupo() {
		return idRolGrupo;
	}
	/**
	 * @param idRolGrupo the idRolGrupo to set
	 */
	public void setIdRolGrupo(String idRolGrupo) {
		this.idRolGrupo = idRolGrupo;
	}
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdAplicacion() {
		return idAplicacion;
	}
	public void setIdAplicacion(Integer idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	public String getPermiso() {
		return permiso;
	}
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}
	public String getIdRecursoFavorito() {
		return idRecursoFavorito;
	}
	public void setIdRecursoFavorito(String idRecursoFavorito) {
		this.idRecursoFavorito = idRecursoFavorito;
	}	
	
	
}
