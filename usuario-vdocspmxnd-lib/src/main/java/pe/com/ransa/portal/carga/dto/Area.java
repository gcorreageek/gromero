package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Cliente de Primax
 * @author dmirandat
 */
public class Area extends AuditoriaTabla implements Serializable { 
//	ID_AREA,NOMBRE,DESCRIPCION,CODIGO,ESTADO,FECHA_CREACION,USUARIO_CREACION
//	A.ID_AREA A_ID_AREA,A.NOMBRE A_NOMBRE,A.DESCRIPCION A_DESCRIPCION,A.CODIGO A_CODIGO,A.ESTADO A_ESTADO,
	
//	E.ID_EMP E_ID_EMP,E.NOMBRE E_NOMBRE,
//	EA.ESTADO E_ESTADO FROM VDOCS.AREA A,VDOCS.EMPRESA E,VDOCS.EMPRESA_AREA EA '||
//	' WHERE  1=1 AND A.ID_AREA=EA.ID_AREA AND E.ID_EMP=EA.ID_EMP ';
	private BigInteger idArea;
	private String nombre;
	private String descripcion;
	private String codigo; 
	private Empresa empresa; 
	private UsuarioDTO usuarioDto; 
	private String estadoEmpresaArea; 
	private boolean seIngreso;
	
	
	public BigInteger getIdArea() {
		return idArea;
	}
	public void setIdArea(BigInteger idArea) {
		this.idArea = idArea;
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
	public Empresa getEmpresa() {
		if(empresa==null)
			return new Empresa();
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getEstadoEmpresaArea() {
		return estadoEmpresaArea;
	}
	public void setEstadoEmpresaArea(String estadoEmpresaArea) {
		this.estadoEmpresaArea = estadoEmpresaArea;
	}
	public boolean isSeIngreso() {
		return seIngreso;
	}
	public void setSeIngreso(boolean seIngreso) {
		this.seIngreso = seIngreso;
	}
	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}
	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}
	

	
	

}
