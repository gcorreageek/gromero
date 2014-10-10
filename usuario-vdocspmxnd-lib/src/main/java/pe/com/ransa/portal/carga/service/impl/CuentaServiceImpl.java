package pe.com.ransa.portal.carga.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.CuentaDao;
import pe.com.ransa.portal.carga.dao.UsuarioDTODao;
import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.service.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService {

	private static Logger logger = Logger.getLogger(CuentaServiceImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();
	
	@Autowired
	private CuentaDao cuentaDao;
	@Autowired
	private UsuarioDTODao usuarioDao;

	public Cuenta registrar(Cuenta cuenta) {
		return cuentaDao.ingresarCuenta(cuenta);
	}
	public Cuenta registrarCuentaUsuario(Cuenta cuenta, String usuario) {
		return cuentaDao.ingresarUsuarioCuenta(cuenta, usuario);
	}

	public Object registrarCuentaUsuario(List<Cuenta> cuentas) {
		List<Cuenta> cuentaErroneos = new ArrayList<Cuenta>();
		for (Cuenta cuenta : cuentas) {
			cuenta = this.registrar(cuenta); 
			if(cuenta.isSeIngreso()){
				List<Cuenta> usuariosErroneos = new ArrayList<Cuenta>();
				logger.info("idCuenta=>"+cuenta.getIdCuenta()+"|"+cuenta.getIdCliente());
				String[] usuarios = cuenta.getUsuarios();
				logger.info("usuarios=>"+Arrays.toString(usuarios));
				for (String usuario : usuarios) {
					cuenta = this.registrarCuentaUsuario(cuenta, usuario.toUpperCase());
					if(!cuenta.isSeIngreso()){ 
						Cuenta cuentaE = cuenta;
						cuentaE.setUsuario(usuario);
						usuariosErroneos.add(cuentaE);  
					}
				}
				//Entra si encuentra errores en ingresar cliente_usuario
				if(usuariosErroneos !=null && !usuariosErroneos.isEmpty() && usuariosErroneos.size()>0 ){
					Cuenta cuentaUsuarioConErrores = new Cuenta();
					String[] usuariosErrores = new String[usuariosErroneos.size()];
					int contador = 0;
					for (Cuenta ce : usuariosErroneos) {
						cuentaUsuarioConErrores = ce;
						usuariosErrores[contador] = ce.getUsuario();
						contador++;
					}
					cuentaUsuarioConErrores.setErrorEnCuenta(false);
					cuentaUsuarioConErrores.setUsuarios(usuariosErrores);
					cuentaErroneos.add(cuentaUsuarioConErrores);
				}
			}else{ 
				cuenta.setErrorEnCuenta(true);
				cuentaErroneos.add(cuenta);
			} 
		}
		return cuentaErroneos;
	}
	public Cuenta registrar2(Cuenta cuenta) {
		try {

			cuenta = cuentaDao.ingresarCuenta2(cuenta);
			cuenta.setSeGuardo(true);
		} catch (Exception e) {
			cuenta.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			cuenta.setThrowable(e);
			cuenta.setSeGuardo(false); 
			logger.error("[registrar2]",e);
		}
		return cuenta;
	}
	public Cuenta actualizar(Cuenta cuenta) {
		try {
			cuenta = cuentaDao.modificarCuenta(cuenta);
			cuenta.setSeGuardo(true);
		} catch (Exception e) {
			cuenta.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			cuenta.setThrowable(e);
			cuenta.setSeGuardo(false);
			logger.error("[eliminar]",e);
		}
		return cuenta;
	}
	public Cuenta eliminar(Cuenta cuenta) {
		try {
			if(cuenta.getEstado().equals(ConstantesLib.ACTIVO)){
				cuenta = cuentaDao.eliminarCuenta(cuenta);
				cuenta.setSeGuardo(true); 
			}else if(cuenta.getEstado().equals(ConstantesLib.INACTIVO)){
				logger.debug("==>"+cuenta.getIdCliente()+"|"+cuenta.getIdCuenta());
					if(this.tieneUsuarioCuentaActivos(cuenta.getIdCliente(),cuenta.getIdCuenta())){
						cuenta.setCodigoError(ConstantesLib.CODERROR_CUENTA_TIENECUENTASUSUARIOAACTIVAS);
						cuenta.setSeGuardo(false); 
						return cuenta;
					}else{
						cuenta = cuentaDao.eliminarCuenta(cuenta);
						cuenta.setSeGuardo(true);
					}
			}
		} catch (Exception e) {
			cuenta.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			cuenta.setThrowable(e);
			cuenta.setSeGuardo(false);
			logger.error("[eliminar]",e);
		}
		return cuenta;
	}
	public List<Cuenta> listarCliente(Cuenta cuenta, Integer inicio, Integer fin) {
		List<Cuenta>  lista = null;
		try {
			lista = cuentaDao.listarCuenta(cuenta, inicio, fin);
		} catch (Exception e) {
			logger.error("[listarCliente]",e);
		}
		return lista;
	}
	public Integer totalCliente(Cuenta cuenta) {
		Integer total = null;
		try {
			total = cuentaDao.totalCuenta(cuenta);
		} catch (Exception e) {  
			logger.error("[totalCliente]",e);
		}
		return total;
	}
	public Boolean tieneUsuarioCuentaActivos(BigInteger idCliente, BigInteger idCuenta) {
		boolean tieneUsuarioCuentaActivos = false;
		try {
			Cuenta cuenta = new Cuenta();
			cuenta.setIdCliente(idCliente);
			cuenta.setIdCuenta(idCuenta);
			List<Cuenta> lCuenta = cuentaDao.listarCuentaUsuario(cuenta, 0, 10);
			for (Cuenta cue : lCuenta) {
				if(cue.getEstado().equals(ConstantesLib.ACTIVO)){
					tieneUsuarioCuentaActivos = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error("[tieneUsuarioCuentaActivos]",e);
		}
		return tieneUsuarioCuentaActivos; 
	}
	
 
	
	 

}
