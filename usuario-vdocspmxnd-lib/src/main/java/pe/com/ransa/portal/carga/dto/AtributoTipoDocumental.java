package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class AtributoTipoDocumental implements Serializable {
	private BigInteger idAtr;
	private BigInteger idEmp;
	private BigInteger idArea;
	private BigInteger idTipDoc;
	private String nombre;
	private String nombreColumnaTabla;
	private String descripcion;
	private String tipoDato;
	private String filtro;
	private String reporte;
	private String tipoComportamiento; 
	private BigInteger idLista;
	private String estado;
	
	public BigInteger getIdAtr() {
		return idAtr;
	}
	public void setIdAtr(BigInteger idAtr) {
		this.idAtr = idAtr;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreColumnaTabla() {
		return nombreColumnaTabla;
	}
	public void setNombreColumnaTabla(String nombreColumnaTabla) {
		this.nombreColumnaTabla = nombreColumnaTabla;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	public String getReporte() {
		return reporte;
	}
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	public String getTipoComportamiento() {
		return tipoComportamiento;
	}
	public void setTipoComportamiento(String tipoComportamiento) {
		this.tipoComportamiento = tipoComportamiento;
	}
	public BigInteger getIdLista() {
		return idLista;
	}
	public void setIdLista(BigInteger idLista) {
		this.idLista = idLista;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	
 
	
	
}
