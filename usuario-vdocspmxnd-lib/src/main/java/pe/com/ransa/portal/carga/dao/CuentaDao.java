package pe.com.ransa.portal.carga.dao;

import java.util.List;

import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.Cuenta;

public interface CuentaDao {
	
	public Cuenta ingresarCuenta(Cuenta cuenta);
	public Cuenta ingresarCuenta2(Cuenta cuenta) throws Exception;
	public Cuenta modificarCuenta(Cuenta cuenta) throws Exception;
	public Cuenta eliminarCuenta(Cuenta cuenta) throws Exception;
	public List<Cuenta> listarCuentaUsuario(Cuenta cuenta ) throws Exception;
	public List<Cuenta> listarCuenta(Cuenta cuenta,Integer inicio,Integer fin) throws Exception;
	public Integer totalCuenta(Cuenta cuenta) throws Exception;
	
	public Cuenta ingresarUsuarioCuenta(Cuenta cuenta,String usuario);
	 
}
