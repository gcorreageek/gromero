package pe.com.ransa.portal.carga.dto;

import java.math.BigInteger;

/**
 * Cuenta asociada a un {@link Cliente} de Primax
 * @author dmirandat
 *
 */
public class Cuenta  extends AuditoriaTabla {
	private BigInteger id;
	private String codigoCliente;
	private String numeroCuenta;
	private String placaVehicular;
	private String nombreUsuario;
	private String codigoEs;
	
	private BigInteger idCuenta;
	private BigInteger idCliente;
	
	private String ruc;

	private Cliente cliente;
	private UsuarioDTO usuarioDto;
	  
	
	private String usuario;
	private String[] usuarios;
	
	private boolean seIngreso;
	private boolean errorEnCuenta;
	
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getPlacaVehicular() {
		return placaVehicular;
	}
	public void setPlacaVehicular(String placaVehicular) {
		this.placaVehicular = placaVehicular;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getCodigoEs() {
		return codigoEs;
	}
	public void setCodigoEs(String codigoEs) {
		this.codigoEs = codigoEs;
	}
	public String getCodigoCliente() {
		return codigoCliente;
	}
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	public BigInteger getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(BigInteger idCuenta) {
		this.idCuenta = idCuenta;
	}
	public BigInteger getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(BigInteger idCliente) {
		this.idCliente = idCliente;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String[] getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(String[] usuarios) {
		this.usuarios = usuarios;
	}
	public boolean isSeIngreso() {
		return seIngreso;
	}
	public void setSeIngreso(boolean seIngreso) {
		this.seIngreso = seIngreso;
	}
	public boolean isErrorEnCuenta() {
		return errorEnCuenta;
	}
	public void setErrorEnCuenta(boolean errorEnCuenta) {
		this.errorEnCuenta = errorEnCuenta;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
