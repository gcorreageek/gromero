package pe.com.ransa.portal.carga.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.ClienteDao;
import pe.com.ransa.portal.carga.dao.CuentaDao;
import pe.com.ransa.portal.carga.dao.UsuarioDTODao;
import pe.com.ransa.portal.carga.dao.VDocsDao;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.EmpresaAreaUsuario;
import pe.com.ransa.portal.carga.dto.EmpresaUsuario;
import pe.com.ransa.portal.carga.dto.TipoDocEmpresaAreaUsuario;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
import pe.com.ransa.portal.carga.service.UsuarioService;

import com.ransa.portal.model.Usuario;
import com.ransa.portal.service.GestionUsuarioService;
import com.ransa.portal.service.factory.ServiceFactory;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	private static Logger logger = Logger.getLogger(UsuarioServiceImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();

	@Autowired
	private UsuarioDTODao dao;
//	listarTipoDoc
	@Autowired
	private VDocsDao vDocsDao;
	@Autowired
	private ClienteDao daoCliente;
	@Autowired
	private CuentaDao daoCuenta;

	public List<UsuarioDTO> listarUsuario(UsuarioDTO usuario, Integer inicio, Integer fin) {
		logger.debug("<==============================>");
		logger.debug("usuario=>"+usuario.getIdUsuario());
		return dao.listar(usuario, inicio, fin);
	}

	public Integer totalUsuario(UsuarioDTO usuario) {
		return dao.total(usuario);
	}

	public List<TipoDocumental> listarAccesoDocumental(TipoDocumental td, Integer inicio, Integer fin) {
		return dao.listar(td, inicio, fin);
	}

	public Integer totalAccesoDocumental(TipoDocumental td) {
		return dao.total(td);
	}

	public List<Cuenta> listarAccesoCuenta(Cuenta cuenta, Integer inicio, Integer fin) {
		return dao.listar(cuenta, inicio, fin);
	}

	public Integer totalAccesoCuenta(Cuenta cuenta) {
		return dao.total(cuenta);
	}
	GestionUsuarioService gestionUsuarioService = ServiceFactory.getInstance().getGestionUsuarioService();
	public boolean crudUsuarioInsert(Usuario usuario,PortletRequest portletRequest, ActionRequest actionRequest) {
//		logger.debug("<================INICIO1===================>");
//		logger.debug("crudUsuarioInsert=>"+usuario.getId());
		try { 
			gestionUsuarioService.crearConfiguracionUsuario(usuario, portletRequest, actionRequest);
//			logger.debug("<================FIN1===================>");
		} catch (Exception e) {
			logger.error("[Exception]",e);
			return false;
		} 
		return true;
	}

	public List<TipoDocumental> ingresarUsuarioTdEmpresaArea(String idUsuario, String[] idEmpresaidAreaidTipoDocumental) {
		List<TipoDocumental> ltd = new ArrayList<TipoDocumental>();
			for (String string : idEmpresaidAreaidTipoDocumental) {
				String[] ids =  string.split("-");
				String idEmpresa = ids[0];
				String idArea = ids[1];
				String idTipoDocumental = ids[2];
				TipoDocumental  td = new TipoDocumental();
				td.setIdTipoDocumental(new BigInteger(idTipoDocumental));
				td.setIdArea(new BigInteger(idArea));
				td.setIdEmpresa(new BigInteger(idEmpresa));
				try {
					boolean estaRegistradoEnEmpresaUsuario = estaRegistradoEnEmpresaUsuario( idUsuario, idEmpresa);
					if(!estaRegistradoEnEmpresaUsuario){
						EmpresaUsuario empresaUsuario = new EmpresaUsuario();
						empresaUsuario.setIdEmp(new BigInteger(idEmpresa));
						empresaUsuario.setIdUsuario(idUsuario);
						empresaUsuario = vDocsDao.ingresaEmpresaUsuario(empresaUsuario);
					}
					boolean estaRegistradoEnEmpresaAreaUsuario = estaRegistradoEnEmpresaAreaUsuario( idUsuario, idEmpresa ,idArea);
					if(!estaRegistradoEnEmpresaAreaUsuario){
						EmpresaAreaUsuario empresaAreaUsuario = new EmpresaAreaUsuario();
						empresaAreaUsuario.setIdEmp(new BigInteger(idEmpresa));
						empresaAreaUsuario.setIdArea(new BigInteger(idArea));
						empresaAreaUsuario.setIdUsuario(idUsuario);
						empresaAreaUsuario = vDocsDao.ingresaEmpresaAreaUsuario(empresaAreaUsuario);
					}
					boolean estaRegistradoEnEmpresaAreaTipoDocUsuario = estaRegistradoEnEmpresaAreaTipoDocUsuario( idUsuario, idEmpresa ,idArea,idTipoDocumental);
					if(!estaRegistradoEnEmpresaAreaTipoDocUsuario){
						TipoDocEmpresaAreaUsuario tipoDocEmpresaAreaUsuario = new TipoDocEmpresaAreaUsuario();
						tipoDocEmpresaAreaUsuario.setIdEmp(new BigInteger(idEmpresa));
						tipoDocEmpresaAreaUsuario.setIdArea(new BigInteger(idArea));
						tipoDocEmpresaAreaUsuario.setIdTipoDoc(new BigInteger(idTipoDocumental));
						tipoDocEmpresaAreaUsuario.setIdUsuario(idUsuario);
						tipoDocEmpresaAreaUsuario = vDocsDao.ingresaTipoDocEmpresaAreaUsuario(tipoDocEmpresaAreaUsuario);
					}
					td.setSeGuardo(true); 
				} catch (Exception e) {
					td.setCodigoError(ConstantesLib.CODERROR_INESPERADO); 
					td.setThrowable(e);
					td.setSeGuardo(false); 
					logger.error("[ingresarUsuarioTdEmpresaArea]",e);
				}
				ltd.add(td);
			} 
		return ltd;
	}

	private boolean estaRegistradoEnEmpresaUsuario(String idUsuario, String idEmpresa) {
		boolean estaRegistrado = true;
		TipoDocumental td= new TipoDocumental();
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger(idEmpresa));
		Area area= new Area();
		area.setIdArea(new BigInteger("0"));
		area.setEmpresa(empresa);
		td.setArea(area);
		td.setIdTipoDocumental(new BigInteger("0"));
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(idUsuario); 
		td.setUsuario(usuario);
		
		List<TipoDocumental> ltd = dao.listarUsuarioEmpresaAreaTipoDocumental(td);
		if(ltd == null ||  ltd.isEmpty() || ltd.size()<1){
			estaRegistrado = false;
		}
		return estaRegistrado;
	}
	private boolean estaRegistradoEnEmpresaAreaUsuario(String idUsuario, String idEmpresa,String idArea) {
		boolean estaRegistrado = true;
		TipoDocumental td= new TipoDocumental();
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger(idEmpresa));
		Area area= new Area();
		area.setIdArea(new BigInteger(idArea));
		area.setEmpresa(empresa);
		td.setArea(area);
		td.setIdTipoDocumental(new BigInteger("0"));
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(idUsuario); 
		td.setUsuario(usuario);
		
		List<TipoDocumental> ltd = dao.listarUsuarioEmpresaAreaTipoDocumental(td);
		if(ltd == null ||  ltd.isEmpty() || ltd.size()<1){
			estaRegistrado = false;
		}
		return estaRegistrado;
	}
 	 
	private boolean estaRegistradoEnEmpresaAreaTipoDocUsuario(String idUsuario, String idEmpresa,String idArea,String idTipoDoc) {
		boolean estaRegistrado = true;
		TipoDocumental td= new TipoDocumental();
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger(idEmpresa));
		Area area= new Area();
		area.setIdArea(new BigInteger(idArea));
		area.setEmpresa(empresa);
		td.setArea(area);
		td.setIdTipoDocumental(new BigInteger(idTipoDoc));
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(idUsuario); 
		td.setUsuario(usuario);
		
		List<TipoDocumental> ltd = dao.listarUsuarioEmpresaAreaTipoDocumental(td);
		if(ltd == null ||  ltd.isEmpty() || ltd.size()<1){
			estaRegistrado = false;
		}
		return estaRegistrado;
	}

	public TipoDocumental eliminarTipoDocumentalAreaEmpresaUsuario(UsuarioDTO usuarioDto, Empresa empresa, Area area, TipoDocumental td) {
		TipoDocumental tdRespuesta = new TipoDocumental();
		try {
			dao.eliminarEmpresaUsuario(empresa);
			dao.eliminarEmpresaAreaUsuario(area);
			dao.eliminarEmpresaAreaTipoDocumentalUsuario(td);
			
			tdRespuesta.setSeGuardo(true); 
		} catch (Exception e) {
			tdRespuesta.setCodigoError(ConstantesLib.CODERROR_INESPERADO); 
			tdRespuesta.setThrowable(e);
			tdRespuesta.setSeGuardo(false); 
			logger.error("[eliminarTipoDocumentalAreaEmpresaUsuario]",e);
		}
		return tdRespuesta;
	}
	
	public List<Cuenta> listarClienteCuentaActivas(Cuenta cuenta ) {
		List<Cuenta> lCuentaNuevo = new ArrayList<Cuenta>();
		try { 
			cuenta.setEstado(ConstantesLib.ACTIVO);
			Integer total = dao.totalClienteCuenta(cuenta);
			List<Cuenta> lCuenta = dao.listarClienteCuenta(cuenta, 1, total);
			for (Cuenta cuenta2 : lCuenta) {
				logger.debug("ClienteCuentaActivos:"+cuenta2.getCliente().getId()+"|"+cuenta2.getIdCuenta()+"|"+cuenta.getUsuarioDto().getIdUsuario());
				String usuario = cuenta.getUsuarioDto().getIdUsuario();
				BigInteger idCliente = cuenta2.getCliente().getId();
				Cliente cliente = new Cliente();
				cliente.setUsuario(usuario);
				cliente.setId(idCliente);
				
				List<Cliente> lCliente = daoCliente.listarClienteUsuario(cliente);
				if(lCliente != null && !lCliente.isEmpty() && lCliente.size() >= 1){
					BigInteger idCuenta = cuenta2.getIdCuenta();
					Cuenta cuentaConsulta = new Cuenta();
					cuentaConsulta.setIdCuenta(idCuenta);
					cuentaConsulta.setIdCliente(idCliente);
					cuentaConsulta.setUsuario(usuario);
					
					List<Cuenta> lCuentaGet = daoCuenta.listarCuentaUsuario(cuentaConsulta);
					if(lCuentaGet == null || lCuentaGet.isEmpty() || lCuentaGet.size() < 1){
						lCuentaNuevo.add(cuenta2);
					}  
				}else{
					lCuentaNuevo.add(cuenta2);
				} 
			}
			
		} catch (Exception e) {
			logger.error("[listarClienteCuentaActivas]",e);
		}
		return lCuentaNuevo;
	}

	public Integer totalClienteCuentaActivas(Cuenta cuenta) {
		cuenta.setEstado(ConstantesLib.ACTIVO);
		return dao.total(cuenta);
	}

	public List<Cuenta> ingresarUsuarioClienteCuenta(String idUsuario, String[] idClienteidCuenta) {
		List<Cuenta>  lCuentaRetorno = new ArrayList<Cuenta>();
		for (String string : idClienteidCuenta) {
			String[] ids = string.split("-");
			String idCliente = ids[0];
			String idCuenta = ids[1];
			Cliente cliente = new Cliente();
			cliente.setId(new BigInteger(idCliente));
			cliente.setUsuario(idUsuario);
			
			Cuenta cuenta = new Cuenta();
			cuenta.setCliente(cliente);
			cuenta.setUsuario(idUsuario);
			cuenta.setId(new BigInteger(idCuenta));
			cuenta.setIdCuenta(new BigInteger(idCuenta));
			cuenta.setIdCliente(new BigInteger(idCliente));
			
			try {
				List<Cliente> lClienteEstaRegistrado = daoCliente.listarClienteUsuario(cliente);
				if(lClienteEstaRegistrado==null || lClienteEstaRegistrado.isEmpty() || lClienteEstaRegistrado.size()>1){
					cliente = daoCliente.ingresarUsuarioCliente(cliente, idUsuario);
				}  
				List<Cuenta> lCuentaEstaRegistrado = daoCuenta.listarCuentaUsuario(cuenta);
				if(lCuentaEstaRegistrado==null || lCuentaEstaRegistrado.isEmpty() || lCuentaEstaRegistrado.size()>1){
					cuenta = daoCuenta.ingresarUsuarioCuenta(cuenta, idUsuario);
				}
			} catch (Exception e) {
				cuenta.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
				cuenta.setThrowable(e);
				cuenta.setSeGuardo(false); 
				lCuentaRetorno.add(cuenta);
				logger.error("[ingresarUsuarioClienteCuenta]",e);
			}
		}
		return lCuentaRetorno;
	}

	public Cuenta eliminarClienteCuentaUsuario(UsuarioDTO usuarioDto, Cliente cliente, Cuenta cuenta) {
		Cuenta cuentaRespuesta = new Cuenta();
		try {
			dao.eliminarClienteUsuario(cliente);
			dao.eliminarCuentaUsuario(cuenta);
			
			cuentaRespuesta.setSeGuardo(true); 
		} catch (Exception e) {
			cuentaRespuesta.setCodigoError(ConstantesLib.CODERROR_INESPERADO); 
			cuentaRespuesta.setThrowable(e);
			cuentaRespuesta.setSeGuardo(false); 
			logger.error("[eliminarClienteCuentaUsuario]",e);
		}
		return cuentaRespuesta;
	}
	
	
	
}
