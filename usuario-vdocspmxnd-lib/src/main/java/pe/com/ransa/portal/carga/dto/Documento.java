package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class Documento implements Serializable {

//	 ID_DOC,ID_EMP,ID_AREA,ID_TIP_DOC,ESTADO,COD_LOTE,ID_REG FROM VDOCS.DOCUMENTO
	private BigInteger idDoc;
	private BigInteger idEmp;
	private BigInteger idArea;
	private BigInteger idTipDoc;
	private String estado;
	private String codLote;
	private BigInteger idReg;
	
	public BigInteger getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(BigInteger idDoc) {
		this.idDoc = idDoc;
	}
	public BigInteger getIdEmp() {
		return idEmp;
	}
	public void setIdEmp(BigInteger idEmp) {
		this.idEmp = idEmp;
	}
	public BigInteger getIdArea() {
		return idArea;
	}
	public void setIdArea(BigInteger idArea) {
		this.idArea = idArea;
	}
	public BigInteger getIdTipDoc() {
		return idTipDoc;
	}
	public void setIdTipDoc(BigInteger idTipDoc) {
		this.idTipDoc = idTipDoc;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCodLote() {
		return codLote;
	}
	public void setCodLote(String codLote) {
		this.codLote = codLote;
	}
	public BigInteger getIdReg() {
		return idReg;
	}
	public void setIdReg(BigInteger idReg) {
		this.idReg = idReg;
	}
	
	
	
	
}
