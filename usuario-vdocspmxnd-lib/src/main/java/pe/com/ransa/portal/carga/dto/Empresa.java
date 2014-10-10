package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Cliente de Primax
 * @author dmirandat
 */
public class Empresa extends AuditoriaTabla implements Serializable {
	//ID_EMP,NOMBRE,DESCRIPCION,CODIGO,IMAGEN_LOGO,COLOR_CABECERA,ESTADO,FECHA_CREACION,USUARIO_CREACION
	
	private BigInteger idEmpresa;
	private String nombre;
	private String descripcion;
	private String codigo;
	private String imagenLogo;
	private String colorCabecera;  
	
	private UsuarioDTO usuarioDto;
	
	private boolean seIngreso;
	
	public BigInteger getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(BigInteger idEmpresa) {
		this.idEmpresa = idEmpresa;
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
	public String getImagenLogo() {
		return imagenLogo;
	}
	public void setImagenLogo(String imagenLogo) {
		this.imagenLogo = imagenLogo;
	}
	public String getColorCabecera() {
		return colorCabecera;
	}
	public void setColorCabecera(String colorCabecera) {
		this.colorCabecera = colorCabecera;
	}
	public boolean isSeIngreso() {
		return seIngreso;
	}
	public void setSeIngreso(boolean seIngreso) {
		this.seIngreso = seIngreso;
	}
	public UsuarioDTO getUsuarioDto() {
		if(usuarioDto==null)
			usuarioDto = new UsuarioDTO();
		return usuarioDto;
	}
	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	} 
	
	 
	
	
	
 
	
	 
	
}
