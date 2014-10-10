package pe.com.ransa.portal.carga.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.dao.AreaDao;
import pe.com.ransa.portal.carga.dao.TipoDocumentalDao;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
import pe.com.ransa.portal.carga.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	private static Logger logger = Logger.getLogger(AreaServiceImpl.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();

	@Autowired
	private AreaDao dao;
	@Autowired
	private TipoDocumentalDao daoTipoDocumental;

	public Area registrar(Area area) {
		try {
			if(codigoRepetido(area.getCodigo())){
				area.setCodigoError(ConstantesLib.CODERROR_AREA_CODIGO_REPETIDO);
				area.setSeGuardo(false);
				return area;
			}
			
			area = dao.ingresarArea(area);
			area = dao.ingresarEmpresaArea(area); 
			area.setSeGuardo(true);
		} catch (Exception e) {
			area.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			area.setThrowable(e);
			area.setSeGuardo(false);
			logger.error("[registrar]",e);
		} 
		return area;
	}

	private Boolean codigoRepetido(String codigo) throws Exception {
		Boolean existeCodigoArea = null;
		Area aHere = new Area();
		aHere.setIdArea(new BigInteger("0"));
		aHere.setCodigo(codigo);
		List<Area> lArea = dao.listarAreaXcodigo(aHere, 0, 10);
		if(lArea==null || lArea.isEmpty() || lArea.size()<1){
			existeCodigoArea = false;
		}else{
			existeCodigoArea = true;
		}
		return existeCodigoArea;
	}
	private Boolean codigoIdEsElMismo(String codigo, BigInteger idArea) throws Exception {
		Boolean existeCodigoArea = null;
		Area aHere = new Area();
		aHere.setIdArea(new BigInteger("0"));
		aHere.setCodigo(codigo);
		List<Area> lArea = dao.listarAreaXcodigo(aHere, 0, 10); 
		if(lArea==null || lArea.isEmpty() || lArea.size()<1){
			existeCodigoArea = false;
		}else{
			Boolean mismoIdArea = false;
			for (Area area : lArea) {
				if(idArea.compareTo(area.getIdArea())==0){
					mismoIdArea = true;
					break;
				}
			}
			if(mismoIdArea){
				existeCodigoArea = true;
			}else{
				existeCodigoArea = false;
			}
		}
		return existeCodigoArea;
	}

	public Area actualizar(Area area) {
		try {
			if(codigoRepetido(area.getCodigo())){ 
				if(codigoIdEsElMismo(area.getCodigo(),area.getIdArea())){
					area= dao.modificarArea(area);
					area.setSeGuardo(true);
				}else{
					area.setCodigoError(ConstantesLib.CODERROR_AREA_CODIGO_REPETIDO);
					area.setSeGuardo(false);
					return area;
				}
			}else{
				area= dao.modificarArea(area);
				area.setSeGuardo(true);
			}
		} catch (Exception e) {
			area.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			area.setThrowable(e);
			area.setSeGuardo(false);
			logger.error("[eliminar]",e);
		}
		return area;
	}

	public Area eliminar(Area area) {
		try {

			if(area.getEstado().equals(ConstantesLib.ACTIVO)){
				area= dao.eliminarArea(area);
				area = dao.eliminarEmpresaArea(area);
				area.setSeGuardo(true);
			}else{  
				if(tieneTipoDocActivos(area)){
					area.setCodigoError(ConstantesLib.CODERROR_AREA_TIENETIPODOCACTIVO);
					area.setSeGuardo(false);
					return area;
				}else{ 
					if(tieneUsuariosActivos(area)){
						area.setCodigoError(ConstantesLib.CODERROR_AREA_TIENEUSUARIOSACTIVO);
						area.setSeGuardo(false);
						return area;
					}else{ 
						area= dao.eliminarArea(area); 
						area = dao.eliminarEmpresaArea(area); 
						area.setSeGuardo(true);
					} 
				} 
			}  
		} catch (Exception e) {
			area.setCodigoError(ConstantesLib.CODERROR_INESPERADO);
			area.setThrowable(e);
			area.setSeGuardo(false);
			logger.error("[eliminar]",e);
		}
		return area;
	}

	private Boolean tieneUsuariosActivos(Area area) throws Exception {
		Boolean tieneUsuariosActivos = false;
		Area areaConsulta = area;
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario("");
		areaConsulta.setUsuarioDto(usuarioDto);
		List<Area>lArea =dao.listarEmpresaAreaUsuario(areaConsulta, 0, 10);
		for (Area area2 : lArea) {
			if(area2.getEstado().equals(ConstantesLib.ACTIVO)){
				tieneUsuariosActivos = true;
				break;
			}
		}
		return tieneUsuariosActivos;
	}

	private Boolean tieneTipoDocActivos(Area area) throws Exception {
		Boolean tieneTipoDocActivo = false;
		TipoDocumental td = new TipoDocumental();
		td.setIdTipoDocumental(new BigInteger("0"));
		td.setArea(area);
		List<TipoDocumental> lTipoDoc =daoTipoDocumental.listarEmpresaAreaTipoDoc(td, 0, 10);
		for (TipoDocumental tipoDocumental : lTipoDoc) {
			if(tipoDocumental.getEstado().equals(ConstantesLib.ACTIVO)){
				tieneTipoDocActivo = true;
				break;
			} 
		}
		return tieneTipoDocActivo;
	}

	public List<Area> listar(Area area, Integer inicio, Integer fin) {
		List<Area> lArea = null;
		try {
			lArea= dao.listarArea(area, inicio, fin);
		} catch (Exception e) {
			logger.error("[listar]",e);
		}
		return lArea;
	}

	public Integer total(Area area) {
		Integer total = null;
		try {
			total = dao.totalArea(area);
		} catch (Exception e) {
			logger.error("[total]",e);
		}
		return total;
	}

 
 	 
}
