package pe.com.ransa.portal.carga.controller;

import static pe.com.ransa.portal.carga.common.ConstantesMant.MODE_VIEW;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import pe.com.ransa.portal.carga.common.ConstantesLib;
import pe.com.ransa.portal.carga.common.ConstantesMant;
import pe.com.ransa.portal.carga.common.EstadoEliminacion;
import pe.com.ransa.portal.carga.common.Mappings;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.JqGridDataArea;
import pe.com.ransa.portal.carga.dto.JqGridDataEmpresa;
import pe.com.ransa.portal.carga.dto.JqGridDataTipoDocumental;
import pe.com.ransa.portal.carga.dto.Resultado;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.service.AreaService;
import pe.com.ransa.portal.carga.service.EmpresaService;
import pe.com.ransa.portal.carga.service.TipoDocumentalService;
import pe.com.ransa.portal.carga.util.Util;
import pe.com.ransa.portal.carga.viemodel.FormCommand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping(MODE_VIEW)
@SessionAttributes(ConstantesMant.MANTPRIMAX_EMPRESAAREATD_COMMAND_CLASS)
public class MantPrimaxEmpresaAreaTDController { 

	private static Logger logger = Logger.getLogger(MantPrimaxEmpresaAreaTDController.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();
	 
	@Autowired
	AreaService areaService;
	@Autowired
	EmpresaService empresaService;
	@Autowired
	TipoDocumentalService tdService;
	 
	private void escribeResultado(ResourceResponse response,  Resultado resultado) {
		Gson gson = new GsonBuilder().create();
		try {
			response.getWriter().write(gson.toJson(resultado));
		} catch (IOException e) {
			logger.error("[escribeResultado]",e);
		}
	}
	@RequestMapping
	public String index(RenderRequest renderRequest, RenderResponse renderResponse, Model model){
		logger.info("index()");    
		FormCommand form = new FormCommand();  
		model.addAttribute(ConstantesMant.MANTPRIMAX_EMPRESAAREATD_COMMAND_CLASS, form);
		return ConstantesMant.VIEW_MANTPRIMAX_EMPRESAAREATD;
	} 
	@ResourceMapping(value=Mappings.LISTAR_EMPRESAS_RESOURCE)
	public void obtenerListaEmpresasResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtId") String id,
			@RequestParam("txtEmpresa") String empresa, 
			@RequestParam("txtEstado") String estado){
		logger.debug("JSON:obtenerListaEmpresasResource");
		logger.debug("page=>"+page+"rows=>"+limit+"id=>"+id+"empresa=>"+empresa+"estado=>"+estado); 
		
		BigInteger idBigInteger = Util.parseStringToBigInteger(id);
		if(idBigInteger== null){
			idBigInteger = new BigInteger("0"); 
		}
		int rows = limit; 
		
		Empresa empresaDto = new Empresa();
		empresaDto.setIdEmpresa(idBigInteger);
		empresaDto.setNombre(empresa==null?"":empresa);
		empresaDto.setEstado(estado==null?"":estado);
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		
		List<Empresa> lEmpresa = empresaService.listar(empresaDto, fromRegistro, toRegistro);
		Integer cantidadRegistros = (Integer)empresaService.total(empresaDto);
		JqGridDataEmpresa objJqGrid = new JqGridDataEmpresa();
		if(lEmpresa != null && !lEmpresa.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lEmpresa);
			objJqGrid.setSuccess(ConstantesMant.ESTADO_OK);
		}else{
			objJqGrid.setSuccess(ConstantesMant.ESTADO_ERR);
		} 
		Gson gson = new GsonBuilder().create();
		try {
			response.getWriter().write(gson.toJson(objJqGrid));
		} catch (IOException e) {
			logger.error("error al mandar gson:", e);
		}
	}
	@ResourceMapping(value=Mappings.GUARDAR_EMPRESA)
	public void guardarEmpresa(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdEmpresa") String id,@RequestParam("txtNombreEmpresa") String nombreEmpresa,
			@RequestParam("txtDescripcionEmpresa") String descripcionEmpresa,@RequestParam("txtCodigoEmpresa") String txtCodigoEmpresa,//txtCodigoEmpresa
			@RequestParam("cboEstadoEmpresa") String estado,@RequestParam("tipoCRUDEmpresa") String crud ){
		logger.info("JSON:guardarEmpresa");  
		Resultado resultado = new Resultado();
		BigInteger idBigInteger = null;
		if(id.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_EMPRESA_IDEMPRESA);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}else if(Util.parseStringToBigInteger(id)==null){
			resultado.setCodigoError(ConstantesLib.CODERROR_EMPRESA_IDEMPRESA);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}else{
			idBigInteger = Util.parseStringToBigInteger(id);
		}
		if(nombreEmpresa.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_EMPRESA_NOMBRE);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(descripcionEmpresa.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_EMPRESA_DESCRIPCION);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(txtCodigoEmpresa.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_EMPRESA_CODIGO);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(idBigInteger);
		empresa.setNombre(nombreEmpresa);
		empresa.setDescripcion(descripcionEmpresa);
		empresa.setCodigo(txtCodigoEmpresa);
		 
		if("AGREGA".equals(crud)){
			empresa.setFechaCreacion(new Date());
			empresa.setUsuarioCreacion(ConstantesMant.USUARIO_DEFAULT);
			empresa.setUsuarioModificacion(null);
			empresa.setFechaModificacion(null);
			empresa.setEstado(ConstantesLib.ACTIVO);  
			empresa = empresaService.registrar(empresa);
		}else if("ACTUALIZA".equals(crud)){
			empresa.setFechaCreacion(null);
			empresa.setUsuarioCreacion(null);
			empresa.setFechaModificacion(new Date());
			empresa.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			empresa.setEstado(null);  
			empresa = empresaService.actualizar(empresa);
		}else if("ELIMINA".equals(crud)){ 
			empresa.setFechaCreacion(null);
			empresa.setUsuarioCreacion(null);
			empresa.setFechaModificacion(new Date());
			empresa.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			empresa.setEstado(estado);  
			empresa = empresaService.eliminar(empresa);
		} 
		resultado.setSeGuardo(empresa.isSeGuardo());
		resultado.setCodigoError(empresa.getCodigoError());
		escribeResultado(response, resultado);
	}
			
	@ResourceMapping(value=Mappings.LISTAR_AREAS_RESOURCE)
	public void obtenerListaAreasResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtId") BigInteger id){
		logger.debug("JSON:obtenerListaAreasResource");
		logger.debug("page=>"+page+"rows=>"+limit+"id=>"+id); 
		int rows = limit; 
		
		Area area = new Area();
		Empresa empresaDto = new Empresa();
		empresaDto.setIdEmpresa(id); 
		area.setEmpresa(empresaDto);
		
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		
		List<Area> lArea = areaService.listar(area, fromRegistro, toRegistro);
		Integer cantidadRegistros = (Integer)areaService.total(area);
		JqGridDataArea objJqGrid = new JqGridDataArea();
		if(lArea != null && !lArea.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lArea);
			objJqGrid.setSuccess(ConstantesMant.ESTADO_OK);
		}else{
			objJqGrid.setSuccess(ConstantesMant.ESTADO_ERR);
		} 
		Gson gson = new GsonBuilder().create();
		try {
			response.getWriter().write(gson.toJson(objJqGrid));
		} catch (IOException e) {
			logger.error("error al mandar gson:", e);
		}
	} 
	@ResourceMapping(value=Mappings.GUARDAR_AREA)
	public void guardarArea(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdArea") BigInteger id,@RequestParam("txtNombreArea") String nombreArea,
			@RequestParam("txtDescripcionArea") String descripcionArea,@RequestParam("txtCodigoArea") String codigoArea,
			@RequestParam("cboEstadoArea") String estado,@RequestParam("tipoCRUDArea") String crud,@RequestParam("txtIdEmpresaArea") BigInteger txtIdEmpresaArea 
			){ 
		logger.info("JSON:guardarArea");   
		Resultado resultado = new Resultado();
		
		if(nombreArea.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_AREA_NOMBRE);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(codigoArea.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_AREA_CODIGO);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		
		Area area = new Area();
		area.setIdArea(id);
		area.setNombre(nombreArea);
		area.setDescripcion(descripcionArea);
		area.setCodigo(codigoArea);
		
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(txtIdEmpresaArea);
		area.setEmpresa(empresa);
		 
		if("AGREGA".equals(crud)){
			area.setFechaCreacion(new Date());
			area.setUsuarioCreacion(ConstantesMant.USUARIO_DEFAULT);
			area.setFechaModificacion(null);
			area.setUsuarioModificacion(null);
			area.setEstado(ConstantesLib.ACTIVO);
			area =areaService.registrar(area);
		}else if("ACTUALIZA".equals(crud)){
			area.setFechaCreacion(null);
			area.setUsuarioCreacion(null);
			area.setFechaModificacion(new Date());
			area.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			area = areaService.actualizar(area);
		}else if("ELIMINA".equals(crud)){
			area.setEstado(estado);
			area.setFechaModificacion(new Date());
			area.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			area = areaService.eliminar(area);
		} 
		resultado.setSeGuardo(area.isSeGuardo());
		resultado.setCodigoError(area.getCodigoError());
		escribeResultado(response, resultado);
	}
	
	@ResourceMapping(value=Mappings.LISTAR_TD_RESOURCE)
	public void obtenerListaTDResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtIdEmpresa") BigInteger idEmpresa,@RequestParam("txtIdArea") BigInteger idArea){
		logger.debug("JSON:obtenerListaTDResource");
		logger.debug("page=>"+page+"rows=>"+limit+"idEmpresa=>"+idEmpresa+"idArea=>"+idArea); 
		int rows = limit; 
		TipoDocumental td = new TipoDocumental();
		Area area = new Area();
		area.setIdArea(idArea);
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(idEmpresa); 
		area.setEmpresa(empresa);
		td.setArea(area);
		td.setEstado("");
		
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		  
		List<TipoDocumental> lTipoDocumental = tdService.listar(td, fromRegistro, toRegistro);
		Integer cantidadRegistros = (Integer)tdService.total(td);
		JqGridDataTipoDocumental objJqGrid = new JqGridDataTipoDocumental();
		if(lTipoDocumental != null && !lTipoDocumental.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lTipoDocumental);
			objJqGrid.setSuccess(ConstantesMant.ESTADO_OK);
		}else{
			objJqGrid.setSuccess(ConstantesMant.ESTADO_ERR);
		} 
		Gson gson = new GsonBuilder().create();
		try {
			response.getWriter().write(gson.toJson(objJqGrid));
		} catch (IOException e) {
			logger.error("error al mandar gson:", e);
		}
	} 
	@ResourceMapping(value=Mappings.GUARDAR_TD)
	public void guardarTipoDocumental(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdTD") BigInteger idTD,@RequestParam("txtNombreTD") String nombreTD,
			@RequestParam("txtDescripcionTD") String descripcionTD,@RequestParam("txtNombreTablaTD")String txtNombreTablaTD,@RequestParam("txtCodigoTD") String codigoTD,
			@RequestParam("cboEstadoTD") String estadoTD,@RequestParam("tipoCRUDTD") String crudTD,
			@RequestParam("txtIdEmpresaTD") BigInteger idEmpresa ,@RequestParam("txtIdAreaTD") BigInteger idArea 
			){  
		logger.info("JSON:guardarTipoDocumental"); 
		Resultado resultado = new Resultado();
		
		if(nombreTD.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_TD_NOMBRE);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(codigoTD.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_TD_CODIGO);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(txtNombreTablaTD.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_TD_NOMBRETABLA);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		
		
		TipoDocumental td = new TipoDocumental();
		Area area = new Area();
		area.setIdArea(idArea);
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(idEmpresa); 
		area.setEmpresa(empresa);
		td.setIdTipoDocumental(idTD);
		td.setNombre(nombreTD);
		td.setDescripcion(descripcionTD);
		td.setCodigo(codigoTD); 
		td.setArea(area);
		td.setNombreTablaTipoDocEmpresaArea(txtNombreTablaTD);
		 
		if("AGREGA".equals(crudTD)){
			td.setFechaModificacion(null);
			td.setUsuarioModificacion(null);
			td.setFechaCreacion(new Date());
			td.setUsuarioCreacion(ConstantesMant.USUARIO_DEFAULT);
			td.setEstado(ConstantesLib.ACTIVO);
			td =tdService.registrar(td);
		}else if("ACTUALIZA".equals(crudTD)){
			td.setFechaCreacion(null);
			td.setUsuarioCreacion(null);
			td.setFechaModificacion(new Date());
			td.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			td.setEstado(null);
			td = tdService.actualizar(td);
		}else if("ELIMINA".equals(crudTD)){
			td.setFechaCreacion(null);
			td.setUsuarioCreacion(null);
			td.setFechaModificacion(new Date());
			td.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			td.setEstado(estadoTD);
			td = tdService.eliminar(td);
		} 
		
		resultado.setSeGuardo(td.isSeGuardo());
		resultado.setCodigoError(td.getCodigoError());
		escribeResultado(response, resultado);
	}
	
}
