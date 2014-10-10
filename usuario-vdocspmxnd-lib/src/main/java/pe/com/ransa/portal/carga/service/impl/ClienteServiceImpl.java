package pe.com.ransa.portal.carga.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.ClienteDao;
import pe.com.ransa.portal.carga.dao.CuentaDao;
import pe.com.ransa.portal.carga.dao.UsuarioDTODao;
import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
import pe.com.ransa.portal.carga.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	private static Logger logger = Logger.getLogger(ClienteServiceImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();

	@Autowired
	private ClienteDao clienteDao;
	@Autowired
	private CuentaDao cuentaDao;
	@Autowired
	private UsuarioDTODao usuarioDao;
	public Cliente registrar(Cliente cliente) {
		try { 
			Cliente clienteConsulta = new Cliente();
			clienteConsulta.setId(null);
			clienteConsulta.setRUC(cliente.getRUC());
			clienteConsulta.setRazonSocial("");
			clienteConsulta.setCodigo("");
			clienteConsulta.setEstado("");
			List<Cliente> lCliente = clienteDao.buscarClienteXruc(clienteConsulta, 0, 10);
			
			if(lCliente==null || lCliente.isEmpty() || lCliente.size()<1 ){
				cliente = clienteDao.ingresarCliente(cliente);
				cliente.setSeGuardo(true); 
			}else{
				cliente.setSeGuardo(false); 
				cliente.setCodigoError(ConstantesLib.CODERROR_CLIENTE_RUC_REPETIDO);
			}
		} catch (Exception e) {
			cliente.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			cliente.setThrowable(e);
			cliente.setSeGuardo(false); 
			logger.error("[registrar]",e);
		}
		return cliente;
	}
	
	public Cliente registrarUsuarioCliente(Cliente cliente,String usuario) {
		cliente = clienteDao.ingresarUsuarioCliente(cliente, usuario);
		return cliente;
	}  
	public Object registrarUsuarioCliente(List<Cliente> clientes) {
		List<Cliente> clientesErroneos = new ArrayList<Cliente>();
			for (Cliente cliente : clientes) { 
				cliente.setErrorCantidad(0);
				BigInteger id = this.getIdClienteXruc(cliente.getRUC());
				List<Cliente> usuariosErroneos = null;
				int errorRuc =0;
				int errorIngresoUsuario=0; 
				boolean noHuboErroresClienteUsuarioEncontradoRuc = true;
				if(id.equals(new BigInteger("0"))){
					errorRuc = 1;
					cliente.setMensajeRuc("No encontro RUC,ingreso cliente");
					cliente = this.registrar(cliente); 
					if(cliente.isSeIngreso()){
						errorIngresoUsuario = 1;
						usuariosErroneos = registrarUsuarioClienteYerrores(usuariosErroneos,cliente);
					}
					else{
						errorIngresoUsuario = 2;
						cliente.setErrorCantidad(1);
						clientesErroneos.add(cliente);
					}
				}else{
					cliente.setMensajeRuc("Encontro RUC,no ingreso cliente");
					errorRuc=2;   
					cliente.setId(id);
					usuariosErroneos = registrarUsuarioClienteYerrores(usuariosErroneos,cliente);
					if(usuariosErroneos ==null || usuariosErroneos.isEmpty() || usuariosErroneos.size()==0){
						noHuboErroresClienteUsuarioEncontradoRuc = false;
						Cliente clienteUsuarioConErrores = cliente;
						clienteUsuarioConErrores.setUsuarios(new String[]{});
						clientesErroneos.add(clienteUsuarioConErrores);
					}
				}
				if(noHuboErroresClienteUsuarioEncontradoRuc){
					//Entra si encuentra errores en ingresar cliente_usuario
					if(usuariosErroneos !=null && !usuariosErroneos.isEmpty() && usuariosErroneos.size()>0 ){
						Cliente clienteUsuarioConErrores = new Cliente();
						String[] usuariosErrores = new String[usuariosErroneos.size()];
						int contador = 0;
						for (Cliente ce : usuariosErroneos) {
							clienteUsuarioConErrores = ce; 
							usuariosErrores[contador] = ce.getUsuario();
							contador++;
						}  
						clienteUsuarioConErrores.setUsuarios(usuariosErrores);
						clientesErroneos.add(clienteUsuarioConErrores);
					}
				}
				
				 
			}
		return clientesErroneos;
	}

	private List<Cliente> registrarUsuarioClienteYerrores(List<Cliente> usuariosErroneos, Cliente cliente) {
		usuariosErroneos = new ArrayList<Cliente>();
		logger.debug("idCliente=>"+cliente.getId());
		String[] usuarios = cliente.getUsuarios();
		logger.debug("usuarios=>"+Arrays.toString(usuarios));
		for (String usuario : usuarios) {
			cliente = this.registrarUsuarioCliente(cliente, usuario.toUpperCase());
			if(!cliente.isSeIngreso()){ 
				Cliente clienteError = new Cliente();
				clienteError.setCodigo(cliente.getCodigo());
				clienteError.setId(cliente.getId());
				clienteError.setRazonSocial(cliente.getRazonSocial());
				clienteError.setRUC(cliente.getRUC()); 
				clienteError.setMensajeRuc(cliente.getMensajeRuc());
				clienteError.setUsuario(usuario);  
				clienteError.setErrorCantidad(2);
				usuariosErroneos.add(clienteError);
			}
		}
		return usuariosErroneos;
	}

	public BigInteger getIdClienteXruc(String ruc) {
		BigInteger id = null;
		try {
			id =  clienteDao.getIdClienteXruc(ruc);
		} catch (Exception e) {
			logger.error("[getIdClienteXruc]",e);
		}
		return id;
	}

	public List<Cliente> listarCliente(Cliente cliente, Integer inicio, Integer fin) {
		List<Cliente> lCliente = null;
		try {
			lCliente = clienteDao.listarCliente(cliente, inicio, fin);
		} catch (Exception e) {
			logger.error("[listarCliente]",e);
		}
		return lCliente;
	}
	public Integer totalCliente(Cliente cliente) {
		Integer total= null;
		try {
			total= clienteDao.totalCliente(cliente);
		} catch (Exception e) { 
			logger.error("[totalCliente]",e);
		}
		return total;
	}

	public Cliente actualizar(Cliente cliente) {
		try { 
			Cliente clienteConsulta = new Cliente();
			clienteConsulta.setId(null);
			clienteConsulta.setRUC(cliente.getRUC());
			clienteConsulta.setRazonSocial("");
			clienteConsulta.setCodigo("");
			clienteConsulta.setEstado("");
			List<Cliente> lCliente = clienteDao.buscarClienteXruc(clienteConsulta, 0, 10);
			if(lCliente==null || lCliente.isEmpty() || lCliente.size()<1 ){
				cliente = clienteDao.modificarCliente(cliente);
				cliente.setSeGuardo(true); 
			}else{
				if(lCliente.size()<2){
					Cliente clienteGet = lCliente.get(0);
					if(clienteGet.getRUC().equals(cliente.getRUC())){
						cliente = clienteDao.modificarCliente(cliente);
						cliente.setSeGuardo(true); 
					}else{
						cliente.setSeGuardo(false); 
						cliente.setCodigoError(ConstantesLib.CODERROR_CLIENTE_RUC_REPETIDO);
					}
				}else{
					cliente.setSeGuardo(false); 
					cliente.setCodigoError(ConstantesLib.CODERROR_CLIENTE_RUC_REPETIDO);
				}
			}
		} catch (Exception e) { 
			cliente.setThrowable(e);
			cliente.setSeGuardo(false);
			cliente.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			logger.error("[actualizar]",e);
		}
		return cliente;
	}

	public Cliente eliminar(Cliente cliente) {
		try {
			logger.debug("[eliminar]"+cliente.getEstado()+"|"+cliente.getId());
			if(cliente.getEstado().equals(ConstantesLib.ACTIVO)){
				cliente = clienteDao.eliminarCliente(cliente);
				cliente.setSeGuardo(true);  
			}else if(cliente.getEstado().equals(ConstantesLib.INACTIVO)){
				Cliente clienteConsulta = new Cliente();
				clienteConsulta.setId(cliente.getId());
				clienteConsulta.setRUC("");
				clienteConsulta.setRazonSocial("");
				clienteConsulta.setCodigo("");
				clienteConsulta.setEstado("");
				List<Cliente> lCliente = this.listarCliente(clienteConsulta, 0, 1);
				if(lCliente==null){
					cliente.setCodigoError(ConstantesLib.CODERROR_CLIENTE_NOENCONTRADO);
					cliente.setSeGuardo(false); 
					return cliente;
				} 
				Cuenta cuenta = new Cuenta();
				cuenta.setIdCuenta(null);
				cuenta.setIdCliente(cliente.getId());
				List<Cuenta> lCuenta = cuentaDao.listarCuenta(cuenta, 0, 10);
				if(lCuenta==null || lCuenta.isEmpty()){
					cliente = clienteDao.eliminarCliente(cliente);
					cliente.setSeGuardo(true);
				}else{
					boolean tieneCuentasActivas = false;
					for (Cuenta cuentasGet : lCuenta) {
						if(cuentasGet.getEstado().equals(ConstantesLib.ACTIVO)){
							tieneCuentasActivas = true;
							break;
						}
						
					}
					
					if(tieneCuentasActivas){
						cliente.setCodigoError(ConstantesLib.CODERROR_CLIENTE_TIENECUENTASAACTIVAS);
						cliente.setSeGuardo(false); 
						return cliente;
					}else{
						if(this.tieneUsuarioClienteActivos(cliente.getId())){
							cliente.setCodigoError(ConstantesLib.CODERROR_CLIENTE_TIENECUENTASUSUARIOAACTIVAS);
							cliente.setSeGuardo(false); 
							return cliente;
						}else{
							cliente = clienteDao.eliminarCliente(cliente);
							cliente.setSeGuardo(true);
						} 
					}
				}
			}
			
		} catch (Exception e) { 
			cliente.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			cliente.setThrowable(e);
			cliente.setSeGuardo(false); 
			logger.error("[eliminar]",e);
		}
		return cliente;
	}

	public Boolean tieneUsuarioClienteActivos(BigInteger idCliente) {
		boolean tieneUsuarioClienteActivos = false;
		try {
			Cliente cliente = new Cliente();
			cliente.setId(idCliente);
			List<Cliente> lCliente = clienteDao.listarClienteUsuario(cliente, 0, 10);
			for (Cliente cli : lCliente) {
				if(cli.getEstado().equals(ConstantesLib.ACTIVO)){
					tieneUsuarioClienteActivos = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error("[tieneUsuarioClienteActivos]",e);
		}
		return tieneUsuarioClienteActivos; 
	}
}
