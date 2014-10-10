package pe.com.ransa.portal.carga.dto;

import java.math.BigInteger;
 

public class EmpresaAreaUsuario {
	private BigInteger idEmp; 
	private BigInteger idArea; 
	private String idUsuario;
	
	private boolean seIngreso;
	private boolean errorEnCliente;
	
	public BigInteger getIdEmp() {
		return idEmp;
	}
	public void setIdEmp(BigInteger idEmp) {
		this.idEmp = idEmp;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public boolean isSeIngreso() {
		return seIngreso;
	}
	public void setSeIngreso(boolean seIngreso) {
		this.seIngreso = seIngreso;
	}
	public boolean isErrorEnCliente() {
		return errorEnCliente;
	}
	public void setErrorEnCliente(boolean errorEnCliente) {
		this.errorEnCliente = errorEnCliente;
	}
	public BigInteger getIdArea() {
		return idArea;
	}
	public void setIdArea(BigInteger idArea) {
		this.idArea = idArea;
	}
	
 
	
	
	
}
