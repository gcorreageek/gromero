package com.ransa.portal.model;

import java.io.Serializable;

import com.ransa.portal.util.Util;

/**
 * Clase que representa al usuario del portal con todos sus atributos
 * respectivos, los cuales no todos son obtenidos directamente.
 * 
 * @author Christian D. Nuñez del Prado Rodriguez
 */
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = -300950926368052296L;
	
	private String id;
	private String cn;
	private TipoDocumento tipoDocumento;
	private String numeroDocumento;
	private String apellidos;
	private String nombres;
	private String telefonoOficina;
	private String telefonoPersonal;
	private Pais pais;
	private String idCompania;
	private String compania;
	private String idLookAndFeel;
	private String lookAndFeel;
	private Idioma idioma;
	private String correoElectronico;
	private String trato;
	private String fechaNacimiento;
	private TipoUsuario tipoUsuario;	
	private String area;
	private String cargo;		
	private String estado;
	private String solicitante;
	private String companiaSolicitante;
	private String areaSolicitante;	
	private String preguntaSecreta;
	private String respuestaSecreta;
	private String contrasenia;
	private RolUsuario[] roles;
	private String[] recursos;
	private String[] recursosFavoritos;
	private Grupo[] grupos;
	private String dn;
	private String isCasaOrCompania;
			
	public Usuario() {
		this.id = Util.STRING_VACIO;
		this.cn = Util.STRING_VACIO;
		this.dn = Util.STRING_VACIO;
		this.tipoDocumento = new TipoDocumento();
		this.numeroDocumento = Util.STRING_VACIO;
		this.apellidos = Util.STRING_VACIO;
		this.nombres = Util.STRING_VACIO;
		this.telefonoOficina = Util.STRING_VACIO;
		this.telefonoPersonal = Util.STRING_VACIO;
		this.pais = new Pais();
		this.idCompania = Util.STRING_VACIO;
		this.compania = Util.STRING_VACIO;
		this.idioma = new Idioma();
		this.correoElectronico = Util.STRING_VACIO;
		this.trato = Util.STRING_VACIO;
		this.fechaNacimiento = Util.STRING_VACIO;
		this.tipoUsuario = new TipoUsuario();
		this.tipoUsuario.setId(Util.STRING_VACIO);
		this.tipoUsuario.setNombre(Util.STRING_VACIO);	
		this.area = Util.STRING_VACIO;
		this.cargo = Util.STRING_VACIO;
		this.solicitante = Util.STRING_VACIO;
		this.companiaSolicitante = Util.STRING_VACIO;
		this.areaSolicitante = Util.STRING_VACIO;
		this.estado = Util.STRING_VACIO;
		this.preguntaSecreta = Util.STRING_VACIO;
		this.respuestaSecreta = Util.STRING_VACIO;
		this.contrasenia = Util.STRING_VACIO;
		this.isCasaOrCompania = Util.STRING_VACIO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCN() {
		return cn;
	}

	public void setCN(String cn) {
		this.cn = cn;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefonoOficina() {
		return telefonoOficina;
	}

	public void setTelefonoOficina(String telefonoOficina) {
		this.telefonoOficina = telefonoOficina;
	}

	public String getTelefonoPersonal() {
		return telefonoPersonal;
	}

	public void setTelefonoPersonal(String telefonoPersonal) {
		this.telefonoPersonal = telefonoPersonal;
	}
	
	public String getIdCompania() {
		return idCompania;
	}

	public void setIdCompania(String idCompania) {
		this.idCompania = idCompania;
	}

	public String getCompania() {
		return compania;
	}

	public void setCompania(String compañia) {
		this.compania = compañia;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTrato() {
		return trato;
	}

	public void setTrato(String trato) {
		this.trato = trato;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public RolUsuario[] getRoles() {
		return roles;
	}

	public void setRoles(RolUsuario[] roles) {
		this.roles = roles;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String[] getRecursos() {
		return recursos;
	}

	public void setRecursos(String[] recursos) {
		this.recursos = recursos;
	}

	public Grupo[] getGrupos() {
		return grupos;
	}

	public void setGrupos(Grupo[] grupos) {
		this.grupos = grupos;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPreguntaSecreta() {
		return preguntaSecreta;
	}

	public void setPreguntaSecreta(String preguntaSecreta) {
		this.preguntaSecreta = preguntaSecreta;
	}

	public String getRespuestaSecreta() {
		return respuestaSecreta;
	}

	public void setRespuestaSecreta(String respuestaSecreta) {
		this.respuestaSecreta = respuestaSecreta;
	}
	
	public String[] getRecursosFavoritos() {
		return recursosFavoritos;
	}

	public void setRecursosFavoritos(String[] recursosFavoritos) {
		this.recursosFavoritos = recursosFavoritos;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public String getIdLookAndFeel() {
		return idLookAndFeel;
	}

	public void setIdLookAndFeel(String idLookAndFeel) {
		this.idLookAndFeel = idLookAndFeel;
	}

	public String getLookAndFeel() {
		return lookAndFeel;
	}

	public void setLookAndFeel(String lookAndFeel) {
		this.lookAndFeel = lookAndFeel;
	}

	public String getCompaniaSolicitante() {
		return companiaSolicitante;
	}

	public void setCompaniaSolicitante(String companiaSolicitante) {
		this.companiaSolicitante = companiaSolicitante;
	}

	public String getAreaSolicitante() {
		return areaSolicitante;
	}

	public void setAreaSolicitante(String areaSolicitante) {
		this.areaSolicitante = areaSolicitante;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getIsCasaOrCompania() {
		return isCasaOrCompania;
	}

	public void setIsCasaOrCompania(String isCasaOrCompania) {
		this.isCasaOrCompania = isCasaOrCompania;
	}	
	
	
}
