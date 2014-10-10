package pe.com.ransa.portal.carga.service;

import java.math.BigInteger;
import java.util.List;

import pe.com.ransa.portal.carga.dto.Cuenta;

public interface CuentaService {
	
	Cuenta registrar(Cuenta cuenta);
	Cuenta registrar2(Cuenta cuenta);
	Cuenta actualizar(Cuenta cuenta);
	Cuenta eliminar(Cuenta cuenta);
	
	public List<Cuenta> listarCliente(Cuenta cuenta,Integer inicio,Integer fin);
	public Integer totalCliente(Cuenta cuenta);
	Boolean tieneUsuarioCuentaActivos(BigInteger idCliente,BigInteger idCuenta );
	
	Cuenta registrarCuentaUsuario(Cuenta cuenta,String usuario);
	
	Object registrarCuentaUsuario(List<Cuenta> cuentas);
	
	
 
}
