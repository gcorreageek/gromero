package pe.com.ransa.portal.carga.service.impl;
 
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.AtributoTipoDocumentalDao;
import pe.com.ransa.portal.carga.dao.DocumentoDao;
import pe.com.ransa.portal.carga.dao.TipoDocumentalDao;
import pe.com.ransa.portal.carga.dao.UsuarioDTODao;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.AtributoTipoDocumental;
import pe.com.ransa.portal.carga.dto.Documento;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
import pe.com.ransa.portal.carga.service.TipoDocumentalService;

@Service
public class TipoDocumentalServiceImpl implements TipoDocumentalService {
	private static Logger logger = Logger.getLogger(TipoDocumentalServiceImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();

	@Autowired
	private TipoDocumentalDao dao;
	@Autowired
	private UsuarioDTODao daoUsuario;
	@Autowired
	private DocumentoDao daoDocumento;
	@Autowired
	private AtributoTipoDocumentalDao daoAtributo;

	public TipoDocumental registrar(TipoDocumental td) {
		try {  
			if(codigoRepetido(td.getCodigo())){
				td.setCodigoError(ConstantesLib.CODERROR_TD_CODIGO_REPETIDO);
				td.setSeGuardo(false);
				return td;
			}
			if(tablaRepetida(td.getNombreTablaTipoDocEmpresaArea())){
				td.setCodigoError(ConstantesLib.CODERROR_TD_NOMBRETABLA_REPETIDO);
				td.setSeGuardo(false);
				return td;
			} 
			td = dao.ingresarTipoDoc(td);
			td= dao.ingresarTipoDocEmpresaArea(td);
			td.setSeGuardo(true);
		} catch (Exception e) {
			td.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			td.setThrowable(e);
			td.setSeGuardo(false);
			logger.error("[registrar]",e);
		}
		return td;
	}

	private Boolean tablaRepetida(String tabla) {
		Boolean tablaRepetida = true;
		TipoDocumental tdConsulta = new TipoDocumental();
		tdConsulta.setCodigo("");
		tdConsulta.setNombreTablaTipoDocEmpresaArea(tabla);
		tdConsulta.setIdTipoDocumental(new BigInteger("0") );
		Area area = new Area();
		area.setIdArea(new BigInteger("0"));
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger("0"));
		area.setEmpresa(empresa);
		tdConsulta.setArea(area);
		
		List<TipoDocumental> lTd = this.listar(tdConsulta, 0, 10);
		if(lTd==null || lTd.isEmpty() || lTd.size()<1){
			tablaRepetida = false;
		}
		logger.debug("la tabla es repetida:"+tablaRepetida);
		return tablaRepetida;
	}

	private Boolean codigoRepetido(String codigo) {
		Boolean codigoRepetido = true;
		TipoDocumental tdConsulta = new TipoDocumental();
		tdConsulta.setCodigo(codigo);
		tdConsulta.setNombreTablaTipoDocEmpresaArea("");
		tdConsulta.setIdTipoDocumental(new BigInteger("0") );
		Area area = new Area();
		area.setIdArea(new BigInteger("0"));
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger("0"));
		area.setEmpresa(empresa);
		tdConsulta.setArea(area);
		
		List<TipoDocumental> lTd = this.listar(tdConsulta, 0, 10);
		if(lTd==null || lTd.isEmpty() || lTd.size()<1){
			codigoRepetido = false;
		}
		return codigoRepetido;
	}

	public TipoDocumental actualizar(TipoDocumental td) {
		try {
			if(codigoRepetido(td.getCodigo())){
				if(!tieneElMismoIdConCodigo(td.getCodigo(), td.getIdTipoDocumental())){
					td.setCodigoError(ConstantesLib.CODERROR_TD_CODIGO_REPETIDO);
					td.setSeGuardo(false);
					return td;
				}
			} 
			if(tablaRepetida(td.getNombreTablaTipoDocEmpresaArea())){
				if(!tieneElMismoIdConTabla(td.getNombreTablaTipoDocEmpresaArea(), td.getIdTipoDocumental())){
					td.setCodigoError(ConstantesLib.CODERROR_TD_NOMBRETABLA_REPETIDO);
					td.setSeGuardo(false);
					return td;
				}
			}
			
			td = dao.modificarTipoDoc(td);
			td = dao.modificarTipoDocEmpresaArea(td);
			td.setSeGuardo(true);
			
		} catch (Exception e) {
			td.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			td.setThrowable(e);
			td.setSeGuardo(false);
			logger.error("[actualizar]",e);
		}
		return td;
	}

	private boolean tieneElMismoIdConTabla(String nombreTablaTipoDocEmpresaArea, BigInteger idTipoDocumental) {
		Boolean mismoId = false;
		TipoDocumental tdConsulta = new TipoDocumental();
		tdConsulta.setCodigo("");
		tdConsulta.setNombreTablaTipoDocEmpresaArea(nombreTablaTipoDocEmpresaArea);
		tdConsulta.setIdTipoDocumental(new BigInteger("0") );
		Area area = new Area();
		area.setIdArea(new BigInteger("0"));
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger("0"));
		area.setEmpresa(empresa);
		tdConsulta.setArea(area);
		
		List<TipoDocumental> lTd = this.listar(tdConsulta, 0, 10);
		for (TipoDocumental tipoDocumental : lTd) {
			if(tipoDocumental.getIdTipoDocumental().compareTo(idTipoDocumental)==0){
				mismoId = true;
				break;
			}
		}
		logger.debug("Tiene el mismo id"+mismoId);
		return mismoId;
	}

	private Boolean tieneElMismoIdConCodigo(String codigo, BigInteger idTipoDocumental) {
		Boolean mismoId = false;
		TipoDocumental tdConsulta = new TipoDocumental();
		tdConsulta.setCodigo(codigo);
		tdConsulta.setNombreTablaTipoDocEmpresaArea("");
		tdConsulta.setIdTipoDocumental(new BigInteger("0") );
		Area area = new Area();
		area.setIdArea(new BigInteger("0"));
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger("0"));
		area.setEmpresa(empresa);
		tdConsulta.setArea(area);
		
		List<TipoDocumental> lTd = this.listar(tdConsulta, 0, 10);
		for (TipoDocumental tipoDocumental : lTd) {
			if(tipoDocumental.getIdTipoDocumental().compareTo(idTipoDocumental)==0){
				mismoId = true;
				break;
			}
		}
		return mismoId;
	}

	public TipoDocumental eliminar(TipoDocumental td) {
		try {
			if(td.getEstado().equals(ConstantesLib.ACTIVO)){
				td= dao.eliminarTipoDoc(td);
				td= dao.eliminarTipoDocEmpresaArea(td);
				td.setSeGuardo(true);
			}else{
				BigInteger idTipoDoc = td.getIdTipoDocumental();
				BigInteger idArea = td.getArea().getIdArea();
				BigInteger idEmpresa = td.getArea().getEmpresa().getIdEmpresa();
				if(tieneUsuariosActivos( idTipoDoc, idArea, idEmpresa)){
					td.setCodigoError(ConstantesLib.CODERROR_TD_TIENEUSUARIOACTIVO);
					td.setSeGuardo(false);
					return td;
				} 
				if(tieneDocumentosActivos(idTipoDoc, idArea, idEmpresa)){
					td.setCodigoError(ConstantesLib.CODERROR_TD_TIENEDOCUMENTOACTIVO);
					td.setSeGuardo(false);
					return td;
				}
				if(tieneAtributosDocumentoActivos( idTipoDoc, idArea, idEmpresa)){
					td.setCodigoError(ConstantesLib.CODERROR_TD_TIENEATRIBUTOTIPODOCACTIVO);
					td.setSeGuardo(false);
					return td;
				}
				td= dao.eliminarTipoDoc(td);
				td= dao.eliminarTipoDocEmpresaArea(td);
				td.setSeGuardo(true);
			}
			
			
		} catch (Exception e) {
			td.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			td.setThrowable(e);
			td.setSeGuardo(false);
			logger.error("[eliminar]",e);
		}
		return td;
	}

	private Boolean tieneAtributosDocumentoActivos(BigInteger idTipoDoc,
			BigInteger idArea, BigInteger idEmpresa) throws Exception {
		Boolean tieneAtributosDocumentosActivos = false;
		AtributoTipoDocumental atributo = new AtributoTipoDocumental();
		atributo.setIdEmp(idEmpresa);
		atributo.setIdArea(idArea);
		atributo.setIdTipDoc(idTipoDoc);
		atributo.setIdAtr(new BigInteger("0"));
		
		List<AtributoTipoDocumental> l = daoAtributo.listaAtributoTipoDocumental(atributo, 0, 10);
		for (AtributoTipoDocumental atributoTipoDocumental : l) {
			if(atributoTipoDocumental.getEstado().equals(ConstantesLib.ACTIVO)){
				tieneAtributosDocumentosActivos = true;
				break;
			}
		}
		return tieneAtributosDocumentosActivos;
	}

	private Boolean tieneDocumentosActivos(BigInteger idTipoDoc,
			BigInteger idArea, BigInteger idEmpresa) throws Exception {
		Boolean tieneDocumentosActivos = false;
		Documento documento = new Documento();
		documento.setIdEmp(idEmpresa);
		documento.setIdArea(idArea);
		documento.setIdTipDoc(idTipoDoc); 
		documento.setIdDoc(new BigInteger("0"));

		List<Documento>  lDoc = daoDocumento.listaDocumento(documento, 0, 10);
		for (Documento documento2 : lDoc) {
			if(documento2.getEstado().equals(ConstantesLib.ACTIVO)){
				tieneDocumentosActivos = true;
				break;
			}
		}
		return tieneDocumentosActivos;
	}

	private Boolean tieneUsuariosActivos(BigInteger idTipoDoc,
			BigInteger idArea, BigInteger idEmpresa) throws Exception {
		Boolean tieneAsignadoUsuarioActivo =false;
		TipoDocumental tdConsulta = new TipoDocumental();
		tdConsulta.setIdTipoDocumental(idTipoDoc);
		Area area = new Area();
		area.setIdArea(idArea);
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(idEmpresa);
		area.setEmpresa(empresa);
		tdConsulta.setArea(area);
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario("");
		tdConsulta.setUsuario(usuario);
		 
		List<TipoDocumental> lTd = daoUsuario.listarTipoDocumentalEmpresaUsuario(tdConsulta, 0, 10);
		for (TipoDocumental tipoDocumental : lTd) {
			if(tipoDocumental.getEstado().equals(ConstantesLib.ACTIVO)){
				tieneAsignadoUsuarioActivo = true;
				break;
			}
		}
		return tieneAsignadoUsuarioActivo;
	}

	public List<TipoDocumental> listar(TipoDocumental td, Integer inicio, Integer fin) {
		List<TipoDocumental> l = null;
		try {
			l = dao.listarTipoDoc(td, inicio, fin);
		} catch (Exception e) {
			logger.error("[listar]",e);
		}
		return l;
	}

	public Integer total(TipoDocumental td) {
		Integer total = null;
		try {
			total = dao.totalTipoDoc(td);
		} catch (Exception e) {
			logger.error("[total]",e);
		}
		return total;
	}

	public List<TipoDocumental> listarTipoDocumentalActivos(String idUsuario,String txtEmpresaFiltro,String txtAreaFiltro,String txtTdFiltro) {
		List<TipoDocumental>  lNuevoTipoDocumentalActivos = new ArrayList<TipoDocumental>();
		try { 
			
			TipoDocumental td = new TipoDocumental();
			td.setEstado(ConstantesLib.ACTIVO);
			td.setCodigo("");
			td.setNombre(txtTdFiltro);
			td.setNombreTablaTipoDocEmpresaArea("");
			Area areaVacia = new Area();
			areaVacia.setIdArea(new BigInteger("0"));
			areaVacia.setNombre(txtAreaFiltro);
			Empresa empresaVacia = new Empresa();
			empresaVacia.setIdEmpresa(new BigInteger("0"));
			empresaVacia.setNombre(txtEmpresaFiltro);
			areaVacia.setEmpresa(empresaVacia);
			td.setArea(areaVacia);
			td.setIdTipoDocumental(new BigInteger("0"));
			
			Integer total = dao.totalTipoDoc(td);
			List<TipoDocumental>  lTd = dao.listarTipoDoc(td, 0, total);
			for (TipoDocumental tipoDocumental : lTd) {
				BigInteger idEmpresa = tipoDocumental.getArea().getEmpresa().getIdEmpresa();
				BigInteger idArea = tipoDocumental.getArea().getIdArea();
				BigInteger idTd = tipoDocumental.getIdTipoDocumental();
				if(!tieneTipoDocumentalEmpresaAreaUsuario( idUsuario, idEmpresa, idArea, idTd)){
					lNuevoTipoDocumentalActivos.add(tipoDocumental);
				}
			}
		} catch (Exception e) {
			logger.error("[listarTipoDocumentalActivos]",e);
		}
		return lNuevoTipoDocumentalActivos;
	}

	private boolean tieneTipoDocumentalEmpresaAreaUsuario(String idUsuario, BigInteger idEmpresa, BigInteger idArea, BigInteger idTd) {
		TipoDocumental tipoDocumentalUsuarioConsulta  = new TipoDocumental();
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(idEmpresa);
		empresa.setNombre("");
		Area area = new Area();
		area.setIdArea(idArea);
		area.setEmpresa(empresa);
		area.setNombre("");
		
		tipoDocumentalUsuarioConsulta.setArea(area);
		tipoDocumentalUsuarioConsulta.setIdTipoDocumental(idTd);
		UsuarioDTO  usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(idUsuario);
		tipoDocumentalUsuarioConsulta.setNombre("");
		tipoDocumentalUsuarioConsulta.setUsuario(usuarioDto);
		tipoDocumentalUsuarioConsulta.setEstado("");
		
		Integer totalUsuarioTd = daoUsuario.total(tipoDocumentalUsuarioConsulta);
		boolean estaRegistradoEnTDEmpresaAreaUsuario = false;
		if(totalUsuarioTd >= 1){
			estaRegistradoEnTDEmpresaAreaUsuario = true;
		}
		return estaRegistradoEnTDEmpresaAreaUsuario;
	}

 
 
 	 
}
