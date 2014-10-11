package pe.com.ransa.portal.intranet.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;


public class ReporteIngresoBodega {
	private String nroDocumentoIngreso;
	private String nombreConsignatario;
	private BigInteger cartaAceptacion;
	private Calendar fechaIngreso;
	private Calendar fechaAbandono;
	private BigInteger descripcionComercial;
	private BigInteger cantidadBultos;
	private BigDecimal pesoNetoKgs;
	private String observaciones;
//	public static void main(String[] args) {
//		ReporteIngresoBodega r = new ReporteIngresoBodega();
//		r.setFechaAbandono(Calendar.getInstance());
////		r.setFechaAbandono(null);
//		r.setCartaAceptacion(2233333333333);
//		System.out.println(r.getFechaAbandono());
//	}
	public String getNroDocumentoIngreso() {
		return nroDocumentoIngreso;
	}
	public void setNroDocumentoIngreso(String nroDocumentoIngreso) {
		this.nroDocumentoIngreso = nroDocumentoIngreso;
	}
	public String getNombreConsignatario() {
		return nombreConsignatario;
	}
	public void setNombreConsignatario(String nombreConsignatario) {
		this.nombreConsignatario = nombreConsignatario;
	}
	public BigInteger getCartaAceptacion() {
		return cartaAceptacion;
	}
	public void setCartaAceptacion(BigInteger cartaAceptacion) {
		this.cartaAceptacion = cartaAceptacion;
	}
	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Calendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Calendar getFechaAbandono() {
		return fechaAbandono;
	}
	public void setFechaAbandono(Calendar fechaAbandono) {
		this.fechaAbandono = fechaAbandono;
	}
	public BigInteger getDescripcionComercial() {
		return descripcionComercial;
	}
	public void setDescripcionComercial(BigInteger descripcionComercial) {
		this.descripcionComercial = descripcionComercial;
	}
	public BigInteger getCantidadBultos() {
		return cantidadBultos;
	}
	public void setCantidadBultos(BigInteger cantidadBultos) {
		this.cantidadBultos = cantidadBultos;
	}
	public BigDecimal getPesoNetoKgs() {
		return pesoNetoKgs;
	}
	public void setPesoNetoKgs(BigDecimal pesoNetoKgs) {
		this.pesoNetoKgs = pesoNetoKgs;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	 
	
 
		
}
