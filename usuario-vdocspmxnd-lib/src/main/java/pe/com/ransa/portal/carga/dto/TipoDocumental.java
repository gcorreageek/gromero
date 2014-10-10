package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Cliente de Primax
 * @author dmirandat
 */
public class TipoDocumental extends AuditoriaTabla implements Serializable { 
 //ID_TIP_DOC,NOMBRE,DESCRIPCION,CODIGO,ESTADO,FECHA_CREACION,USUARIO_CREACION
	
//	T.T_ID_TIP_DOC,T.T_NOMBRE,T.T_DESCRIPCION,T.T_CODIGO,T.T_ESTADO,T.A_ID_AREA,T.A_NOMBRE,T.A_DESCRIPCION,T.A_ESTADO,
//	T.E_ID_EMP,T.E_NOMBRE,T.E_ESTADO,T.EA_ESTADO,T.TEA_NOMBRE_TABLA,T.TEA_ESTADO 
	private BigInteger idTipoDocumental;
	private String nombre;
	private String descripcion;
	private String codigo; 
//	private String estado; 
	private Area area;
	private String nombreTablaTipoDocEmpresaArea;
	private String estadoTipoDocEmpresaArea;
	private boolean seIngreso;
	private UsuarioDTO usuario; 
	private BigInteger idEmpresa;
	private BigInteger idArea; 
	
	private UsuarioDTO usuarioDto;
	
	public BigInteger getIdTipoDocumental() {
		return idTipoDocumental;
	}
	public void setIdTipoDocumental(BigInteger idTipoDocumental) {
		this.idTipoDocumental = idTipoDocumental;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Area getArea() {
		if(area==null)
			return new Area();
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getNombreTablaTipoDocEmpresaArea() {
		return nombreTablaTipoDocEmpresaArea;
	}
	public void setNombreTablaTipoDocEmpresaArea(
			String nombreTablaTipoDocEmpresaArea) {
		this.nombreTablaTipoDocEmpresaArea = nombreTablaTipoDocEmpresaArea;
	}
	public String getEstadoTipoDocEmpresaArea() {
		return estadoTipoDocEmpresaArea;
	}
	public void setEstadoTipoDocEmpresaArea(String estadoTipoDocEmpresaArea) {
		this.estadoTipoDocEmpresaArea = estadoTipoDocEmpresaArea;
	}
	public boolean isSeIngreso() {
		return seIngreso;
	}
	public void setSeIngreso(boolean seIngreso) {
		this.seIngreso = seIngreso;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public BigInteger getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(BigInteger idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public BigInteger getIdArea() {
		return idArea;
	}
	public void setIdArea(BigInteger idArea) {
		this.idArea = idArea;
	}
	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}
	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}
	
 
	 
	
	

	
	

}
