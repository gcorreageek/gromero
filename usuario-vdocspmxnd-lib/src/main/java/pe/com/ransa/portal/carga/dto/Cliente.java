package pe.com.ransa.portal.carga.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.jetspeed.portlet.event.ActionEvent;

import com.ibm.portal.puma.User;
import com.ibm.wps.portlets.admin.search.Condition;
import com.ibm.wps.portlets.admin.search.Searchable;
import com.ibm.wps.portlets.admin.search.SearchableException;

/**
 * Cliente de Primax
 * @author dmirandat
 */

public class Cliente extends AuditoriaTabla implements Serializable {
	private BigInteger id;
	private String codigo;
	private String RUC;
	private String razonSocial;
	
	
	private String usuario;
	private String[] usuarios;
	private List<Cuenta> cuentas;
//	private BigInteger idCliente;
	
	private boolean seIngreso;
	private Integer errorEnCliente;
	private Integer errorCantidad;
	private String mensajeRuc;
	
	private int page;
	private int total;
	private int records; 
	private String success;
	
	private UsuarioDTO  usuarioDto;
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getRUC() {
		return RUC;
	}
	public void setRUC(String rUC) {
		RUC = rUC;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
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
	public List<Cuenta> getCuentas() {
		return cuentas;
	}
	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	public boolean isSeIngreso() {
		return seIngreso;
	}
	public void setSeIngreso(boolean seIngreso) {
		this.seIngreso = seIngreso;
	}
	public Integer getErrorEnCliente() {
		return errorEnCliente;
	}
	public void setErrorEnCliente(Integer errorEnCliente) {
		this.errorEnCliente = errorEnCliente;
	}
	public Integer getErrorCantidad() {
		return errorCantidad;
	}
	public void setErrorCantidad(Integer errorCantidad) {
		this.errorCantidad = errorCantidad;
	}
	public String getMensajeRuc() {
		return mensajeRuc;
	}
	public void setMensajeRuc(String mensajeRuc) {
		this.mensajeRuc = mensajeRuc;
	}
	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}
	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}
 
 
	
	
	 
	
}
