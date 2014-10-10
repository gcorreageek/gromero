package pe.com.ransa.portal.carga.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.AreaDao;
import pe.com.ransa.portal.carga.dao.EmpresaDao;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
import pe.com.ransa.portal.carga.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	private static Logger logger = Logger.getLogger(EmpresaServiceImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();

	@Autowired
	private EmpresaDao dao;
	@Autowired
	private AreaDao daoArea;

	public Empresa registrar(Empresa empresa) {
		try {
			if(this.idEmpresaUsado(empresa.getIdEmpresa())){
				empresa.setCodigoError(ConstantesLib.CODERROR_EMPRESA_IDEMPRESA_REPETIDO);
				empresa.setSeGuardo(false); 
				return empresa;
			}
			if(this.codigoUsado(empresa.getCodigo())){
				empresa.setCodigoError(ConstantesLib.CODERROR_EMPRESA_CODIGO_REPETIDO);
				empresa.setSeGuardo(false); 
				return empresa;
			}
			
			
			empresa = dao.ingresarEmpresa(empresa);
			empresa.setSeGuardo(true); 
		} catch (Exception e) {
			empresa.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			empresa.setThrowable(e);
			empresa.setSeGuardo(false); 
			logger.error("[registrar]",e);
		}
		return empresa;
	}

	public Empresa actualizar(Empresa empresa) {
		try {  
			if(this.codigoUsado(empresa.getCodigo(),empresa.getIdEmpresa())){
				empresa.setCodigoError(ConstantesLib.CODERROR_EMPRESA_CODIGO_REPETIDO);
				empresa.setSeGuardo(false); 
				return empresa;
			}
			empresa = dao.modificarEmpresa(empresa);
			empresa.setSeGuardo(true); 
		} catch (Exception e) {
			empresa.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			empresa.setThrowable(e);
			empresa.setSeGuardo(false); 
			logger.error("[actualizar]",e);
		}
		return empresa;
	}

	public Empresa eliminar(Empresa empresa) {
		try {
			if(empresa.getEstado().equals(ConstantesLib.ACTIVO)){
				empresa = dao.eliminarEmpresa(empresa);
				empresa.setSeGuardo(true); 
			}else{ 
				if( tieneAreasActivas(empresa)){
					empresa.setCodigoError(ConstantesLib.CODERROR_EMPRESA_TIENEAREASACTIVAS);
					empresa.setSeGuardo(false);
				}else{ 
					if( tieneAsigandoUsuariosActivos(empresa) ){
						empresa.setCodigoError(ConstantesLib.CODERROR_EMPRESA_TIENEUSUARIOASINGNADOSACTIVAS);
						empresa.setSeGuardo(false);
					}else{
						empresa = dao.eliminarEmpresa(empresa);
						empresa.setSeGuardo(true); 
					} 
				} 
			}
			
		} catch (Exception e) {
			empresa.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			empresa.setThrowable(e);
			empresa.setSeGuardo(false); 
			logger.error("[eliminar]",e);
		}
		return empresa;
	}

	private boolean tieneAsigandoUsuariosActivos(Empresa empresa) throws Exception {
		boolean tieneEstadoActivo = false;
		Empresa empresaConsultar = empresa;
		UsuarioDTO usuarioConsulta = new UsuarioDTO();
		usuarioConsulta.setIdUsuario("");
		empresaConsultar.setUsuarioDto(usuarioConsulta);
		List<Empresa> lEmpresa = dao.listarEmpresaUsuario(empresa, 0, 10);
		for (Empresa empresa2 : lEmpresa) {
			if(empresa2.getEstado().equals(ConstantesLib.ACTIVO)){
				tieneEstadoActivo = true;
				break;
			}
		}
		return tieneEstadoActivo;
	}

	private boolean tieneAreasActivas(Empresa empresa) {
		Area area = new Area();
		area.setEmpresa(empresa);
		area.setIdArea(new BigInteger("0"));
		area.setEstado("");
		List<Area> lArea= null;
		try {
			lArea = daoArea.listarArea(area, 0, 10);
		} catch (Exception e) {
			logger.error("[tieneAreasActivas]",e);
		}
		boolean tieneEstadoActivado = false;
		for (Area area2 : lArea) {
//			logger.debug("==>"+area2.getIdArea()+"|"+area2.getNombre()+"|"+area2.getEstado()+"|"+area2.getEstadoEmpresaArea());
			if(area2.getEstadoEmpresaArea().equals(ConstantesLib.ACTIVO)){
				tieneEstadoActivado = true;
				break;
			} 
		}
		return tieneEstadoActivado;
	}

	public List<Empresa> listar(Empresa empresa, Integer inicio, Integer fin) {
		List<Empresa> l = null;
		try {
			l= dao.listarEmpresa(empresa, inicio, fin);
		} catch (Exception e) {
			logger.error("[listar]",e);
		}
		return l;
	}

	public Integer total(Empresa empresa) {
		Integer t = null;
		try {
			t= dao.totalEmpresa(empresa);
		} catch (Exception e) {
			logger.error("[total]",e);
		}
		return t;
	}

	public Boolean idEmpresaUsado(BigInteger idEmpresa) {
		Boolean idEmpresaUsado = null;
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(idEmpresa);
		empresa.setNombre("");
		empresa.setCodigo("");
		empresa.setEstado("");
		List<Empresa> lEmpresa = this.listar(empresa, 0, 10);
		if(lEmpresa==null || lEmpresa.isEmpty() || lEmpresa.size()<1){
			idEmpresaUsado = false;
		}else{
			idEmpresaUsado = true;
		} 	
		return idEmpresaUsado;
	}

	public Boolean codigoUsado(String codigoEmpresa) {
		Boolean codigoUsado = null;
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger("0"));
		empresa.setNombre("");
		empresa.setCodigo(codigoEmpresa);
		empresa.setEstado("");
		List<Empresa> lEmpresa = this.listar(empresa, 0, 10);
		if(lEmpresa==null || lEmpresa.isEmpty() || lEmpresa.size()<1){
			codigoUsado = false;
		}else{ 
			codigoUsado = true;
		} 	
		return codigoUsado;
	}
	public Boolean codigoUsado(String codigoEmpresa,BigInteger idEmpresa) {
		Boolean codigoUsado = null;
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger("0"));
		empresa.setNombre("");
		empresa.setCodigo(codigoEmpresa);
		empresa.setEstado("");
		List<Empresa> lEmpresa = this.listar(empresa, 0, 10);
		if(lEmpresa==null || lEmpresa.isEmpty() || lEmpresa.size()<1){
			codigoUsado = false;
		}else{
			Boolean esElMismoIdEmpresa = false;
			for (Empresa empresa2 : lEmpresa) {
				if(empresa2.getIdEmpresa().compareTo(idEmpresa)==0){
					esElMismoIdEmpresa = true;
					break; 
				}
			}
			if(esElMismoIdEmpresa){
				codigoUsado = false;
			}else{
				codigoUsado = true;
			}
			
		} 	
		return codigoUsado;
	}
 	 
}
