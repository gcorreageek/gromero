package pe.com.ransa.portal.carga.controller;

import static pe.com.ransa.portal.carga.common.ConstantesMant.MODE_VIEW;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
import pe.com.ransa.portal.carga.common.Mappings;
import pe.com.ransa.portal.carga.dto.Area;
import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.Empresa;
import pe.com.ransa.portal.carga.dto.JqGridDataCuenta;
import pe.com.ransa.portal.carga.dto.JqGridDataTipoDocumental;
import pe.com.ransa.portal.carga.dto.JqGridDataUsuario;
import pe.com.ransa.portal.carga.dto.Resultado;
import pe.com.ransa.portal.carga.dto.TipoDocumental;
import pe.com.ransa.portal.carga.dto.UsuarioDTO;
import pe.com.ransa.portal.carga.service.ClienteService;
import pe.com.ransa.portal.carga.service.CuentaService;
import pe.com.ransa.portal.carga.service.TipoDocumentalService;
import pe.com.ransa.portal.carga.service.UsuarioService;
import pe.com.ransa.portal.carga.service.VDocsService;
import pe.com.ransa.portal.carga.viemodel.FormCommand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping(MODE_VIEW)
@SessionAttributes(ConstantesMant.MANTPRIMAX_ACCESOUSUARIO_COMMAND_CLASS)
public class MantPrimaxAccesoUsuarioController { 

	private static Logger logger = Logger.getLogger(MantPrimaxAccesoUsuarioController.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();
	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	TipoDocumentalService tdService;
	@Autowired
	VDocsService vdocsService;
	@Autowired
	CuentaService cuentaService;
	@Autowired
	ClienteService clienteService;
	
	
	@RequestMapping
	public String index(RenderRequest renderRequest, RenderResponse renderResponse, Model model){
		logger.info("index()");   
		FormCommand form = new FormCommand();  
		model.addAttribute(ConstantesMant.MANTPRIMAX_ACCESOUSUARIO_COMMAND_CLASS, form);
		return ConstantesMant.VIEW_MANTPRIMAX_ACCESOUSUARIO;
	} 
	@ResourceMapping(value=Mappings.LISTAR_USUARIOS_RESOURCE)
	public void obtenerListaUsuarioResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtIdUsuario") String idUsuario,
			@RequestParam("txtTipoUsuario") Integer tipoUsuario, 
			@RequestParam("txtEstado") String estado){ 
		logger.debug("JSON:obtenerListaUsuarioResource");
		logger.debug("page=>"+page+"rows=>"+limit+"idUsuario=>"+idUsuario+"tipoUsuario=>"+tipoUsuario+"estado=>"+estado); 
		int rows = limit; 
		
//		logger.debug("CANTIDAD DE TODOS:"+cantidadRegistros); logger.debug("CANTIDAD DE PAG:"+lTdNuevaPaginada.size());
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(idUsuario.toUpperCase());
		usuario.setIdTipoUsuario(tipoUsuario);
		usuario.setStsUsuario(estado);
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1; 
		logger.debug("DE:"+fromRegistro+"!A:"+toRegistro); 
		
		List<UsuarioDTO> lUsuario = usuarioService.listarUsuario(usuario, fromRegistro, toRegistro);
		logger.debug("CANTIDAD DE PAG:"+lUsuario.size());
		Integer cantidadRegistros = (Integer)usuarioService.totalUsuario(usuario);
		logger.debug("CANTIDAD DE TODOS:"+cantidadRegistros);
		JqGridDataUsuario objJqGrid = new JqGridDataUsuario();
		if(lUsuario != null && !lUsuario.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setRecords(cantidadRegistros);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        
			objJqGrid.setRows(lUsuario);
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
 
	@ResourceMapping(value=Mappings.LISTAR_ACCESODOCUMENTALES_RESOURCE)
	public void obtenerListaAccesoDocumentalResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtIdUsuario") String idUsuario){ 
		logger.debug("JSON:obtenerListaAccesoDocumentalResource");
		logger.debug("page=>"+page+"rows=>"+limit+"idUsuario=>"+idUsuario); 
		int rows = limit; 
		if(page<=0){
			page =1;
		}
		logger.debug("page=>"+page+"rows=>"+limit+"idUsuario=>"+idUsuario); 
		
		TipoDocumental td = new TipoDocumental();
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(idUsuario.toUpperCase()); 
		td.setUsuario(usuario);
		Area area = new Area();
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger("0"));
		area.setEmpresa(empresa);
		area.setIdArea(new BigInteger("0"));
		td.setArea(area);
		td.setIdTipoDocumental(new BigInteger("0"));
		
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		
		List<TipoDocumental> lTd = usuarioService.listarAccesoDocumental(td, fromRegistro, toRegistro);
		
		Integer cantidadRegistros = (Integer)usuarioService.totalAccesoDocumental(td);
		JqGridDataTipoDocumental objJqGrid = new JqGridDataTipoDocumental();
		if(lTd != null && !lTd.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lTd);
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
	
	@ResourceMapping(value=Mappings.LISTAR_ACCESODOCUMENTALESACTIVOS_RESOURCE)
	public void obtenerListaTipoDocumentalActivosDocumentalResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtIdUsuario") String idUsuario,
			@RequestParam("txtEmpresaFiltro") String txtEmpresaFiltro,@RequestParam("txtAreaFiltro") String txtAreaFiltro,
			@RequestParam("txtTdFiltro") String txtTdFiltro){ 
		logger.debug("JSON:obtenerListaTipoDocumentalActivosDocumentalResource");
		logger.debug("page=>"+page+"rows=>"+limit+"idUsuario=>"+idUsuario+"txtEmpresaFiltro=>"+txtEmpresaFiltro+"txtAreaFiltro=>"+txtAreaFiltro+"txtTdFiltro=>"+txtTdFiltro); 
		txtEmpresaFiltro = txtEmpresaFiltro==null?"":txtEmpresaFiltro;
		txtAreaFiltro = txtAreaFiltro==null?"":txtAreaFiltro;
		txtTdFiltro = txtTdFiltro==null?"":txtTdFiltro;
		
		List<TipoDocumental> lTdNuevaPaginada = new ArrayList<TipoDocumental>();
		Integer cantidadRegistros  = 0;
		
		int rows = limit; 
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		
		if(!idUsuario.equals("")){ 
			List<TipoDocumental> lTd  = tdService.listarTipoDocumentalActivos(idUsuario.toUpperCase(),txtEmpresaFiltro,txtAreaFiltro,txtTdFiltro);
			cantidadRegistros  = lTd.size();
			int c=1;
			for (TipoDocumental tipoDocumental : lTd) {
//				logger.debug(fromRegistro+"<="+c+"&&"+toRegistro+">="+c+"\t\t"+(fromRegistro<=c  && toRegistro>=c));
				if(fromRegistro<=c  && toRegistro>=c ){
					logger.debug("registra!");
					lTdNuevaPaginada.add(tipoDocumental);
				}
				c++;
			}
		}
		JqGridDataTipoDocumental objJqGrid = new JqGridDataTipoDocumental();
		if(lTdNuevaPaginada != null && !lTdNuevaPaginada.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lTdNuevaPaginada);
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
	
	
	@ResourceMapping(value=Mappings.GUARDAR_ACCESOUSUARIODOCUMENTAL)
	public void guardarTipoDocumentalUsuario(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdUsuario") String idUsuario,@RequestParam("idEmpresaidAreaidTipoDocumental") String[] idEmpresaidAreaidTipoDocumental ){
		logger.info("JSON:guardarTipoDocumentalUsuario"); 
		Resultado resultado = new Resultado();
		logger.debug("URLS:"+idUsuario+"!"+Arrays.toString(idEmpresaidAreaidTipoDocumental));
		List<TipoDocumental> ltd=  usuarioService.ingresarUsuarioTdEmpresaArea(idUsuario.toUpperCase(), idEmpresaidAreaidTipoDocumental);
		int contadorDeErrores=0;
		for (TipoDocumental tipoDocumental : ltd) {
			if(!tipoDocumental.isSeGuardo()) {
				contadorDeErrores++;
			}
		} 
		resultado.setSeGuardo(contadorDeErrores<1);
		resultado.setMensajeError(contadorDeErrores+"");
		resultado.setCodigoError(ConstantesLib.CODERROR_ACCESO_TD_NOSEINGRESARONVARIOS);
		escribeResultado(response, resultado);
	}
	@ResourceMapping(value=Mappings.ELIMINAR_ACCESOUSUARIODOCUMENTAL)
	public void eliminarTipoDocumentalUsuario(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdUsuario") String idUsuario,@RequestParam("cboEstadoTipoDocumentalUsuario") String cboEstadoTipoDocumentalUsuario,
			@RequestParam("idEmpresaidAreaidTipoDocumental") String idEmpresaidAreaidTipoDocumental ){
		logger.info("JSON:eliminarTipoDocumentalUsuario"); 
		Resultado resultado = new Resultado();
		logger.debug("URLS:"+idUsuario+"!"+idEmpresaidAreaidTipoDocumental);
		String[] ids = idEmpresaidAreaidTipoDocumental.split("-");
		String idEmpresa = ids[0];
		String idArea = ids[1];
		String idTipoDocumental = ids[2];
		
		UsuarioDTO  usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(idUsuario.toUpperCase());
		
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(new BigInteger(idEmpresa));
		empresa.setUsuarioDto(usuarioDto);
		empresa.setFechaModificacion(new Date());
		empresa.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
		empresa.setEstado(cboEstadoTipoDocumentalUsuario);
		
		Area area = new Area();
		area.setEmpresa(empresa);
		area.setIdArea(new BigInteger(idArea));
		area.setUsuarioDto(usuarioDto);
		area.setFechaModificacion(new Date());
		area.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
		area.setEstado(cboEstadoTipoDocumentalUsuario);
		
		TipoDocumental td = new TipoDocumental();
		td.setArea(area);
		td.setIdTipoDocumental(new BigInteger(idTipoDocumental));
		td.setUsuarioDto(usuarioDto);
		td.setFechaModificacion(new Date());
		td.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
		td.setEstado(cboEstadoTipoDocumentalUsuario);
		
		TipoDocumental  tdRes = usuarioService.eliminarTipoDocumentalAreaEmpresaUsuario(usuarioDto, empresa, area, td);
 
		resultado.setSeGuardo(tdRes.isSeGuardo());
		resultado.setCodigoError(tdRes.getCodigoError());
		escribeResultado(response, resultado);
	}
	/**
	 * Cuentas
	 */
	@ResourceMapping(value=Mappings.LISTAR_ACCESOCUENTAS_RESOURCE)
	public void obtenerListaAccesoCuentasResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtIdUsuario") String idUsuario,@RequestParam("estado") String estado){ 
		logger.debug("JSON:obtenerListaAccesoCuentasResource");
		logger.debug("page=>"+page+"rows=>"+limit+"idUsuario=>"+idUsuario+"|Estado:"+estado); 
		int rows = limit; 
		Cuenta cuenta = new Cuenta();
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(idUsuario); 
		cuenta.setUsuarioDto(usuario);
		cuenta.setEstado(estado);
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		
		List<Cuenta> lTd = usuarioService.listarAccesoCuenta(cuenta, fromRegistro, toRegistro);
		
		Integer cantidadRegistros = (Integer)usuarioService.totalAccesoCuenta(cuenta);
		JqGridDataCuenta objJqGrid = new JqGridDataCuenta();
		if(lTd != null && !lTd.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lTd);
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
	@ResourceMapping(value=Mappings.LISTAR_CLIENTECUENTAS_RESOURCE)
	public void obtenerListaClienteCuentasResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtRucMantCuentasFiltro") String ruc,@RequestParam("txtNroCuentaMantCuentasFiltro") String nroCuenta,
			@RequestParam("txtRazonSocialMantCuentasFiltro") String razonSocial, @RequestParam("txtIdUsuario") String idUsuario){
		logger.debug("JSON:obtenerListaClienteCuentasResource");
		logger.debug("page=>"+page+"rows=>"+limit+"|txtIdUsuario:"+idUsuario+"|ruc:"+ruc+"|nroCuenta:"+nroCuenta+"|razonSocial:"+razonSocial); 
		int rows = limit; 
		Cuenta cuenta = new Cuenta();  
		Cliente cliente = new Cliente();
		cliente.setRUC(ruc==null?"":ruc);
		cliente.setRazonSocial(razonSocial==null?"":razonSocial);
		cuenta.setCliente(cliente);
		cuenta.setNumeroCuenta(nroCuenta==null?"":nroCuenta);
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(idUsuario);
		cuenta.setUsuarioDto(usuarioDto);
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		List<Cuenta> lCuentaNuevo = new ArrayList<Cuenta>();
		List<Cuenta> lTd = null;
		Integer cantidadRegistros = null;
		if(idUsuario.equals("")){
			cantidadRegistros = 0;
			lTd = new ArrayList<Cuenta>();
		}else{
			lTd =usuarioService.listarClienteCuentaActivas(cuenta);
			cantidadRegistros = lTd.size();
			logger.debug("cantidad:"+cantidadRegistros);
		}
		int c = 1;
		for (Cuenta cuenta2 : lTd) { 
			if(fromRegistro<=c &&  toRegistro>=c){
				lCuentaNuevo.add(cuenta2);
			}
			c++;
		}
		JqGridDataCuenta objJqGrid = new JqGridDataCuenta();
		if(lTd != null && !lTd.isEmpty()){
			objJqGrid.setPage(page);
			objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
			objJqGrid.setRecords(cantidadRegistros);
			
			objJqGrid.setRows(lCuentaNuevo);
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
	 
	@ResourceMapping(value=Mappings.GUARDAR_CUENTACLIENTE_USUARIO)
	public void guardarCuentaClienteUsuario(ResourceRequest request, ResourceResponse response,  
			@RequestParam("idClienteidCuenta") String[] idClienteidCuenta,
			@RequestParam("txtIdUsuario") String idUsuario){
		logger.info("JSON:guardarCuentaClienteUsuario"); 
		logger.debug("idClienteidCuenta:"+Arrays.toString(idClienteidCuenta)+"|idUsuario:"+idUsuario);
		Resultado resultado = new Resultado();
		List<Cuenta> lCuenta=  usuarioService.ingresarUsuarioClienteCuenta(idUsuario.toUpperCase(), idClienteidCuenta);
		int contadorDeErrores=0;
		for (Cuenta cuenta : lCuenta) {
			if(!cuenta.isSeGuardo()) {
				contadorDeErrores++;
			}
		} 
		resultado.setSeGuardo(contadorDeErrores<1);
		resultado.setMensajeError(contadorDeErrores+"");
		resultado.setCodigoError(ConstantesLib.CODERROR_ACCESO_TD_NOSEINGRESARONVARIOS);
		escribeResultado(response, resultado);
	}
	@ResourceMapping(value=Mappings.ELIMINAR_ACCESOUSUARIOCUENTA)
	public void eliminarCuentaUsuario(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdUsuario") String idUsuario,@RequestParam("cboEstadoCuentaUsuario") String cboEstadoCuentaUsuario,
			@RequestParam("idClienteidCuenta") String idClienteidCuenta ){
		logger.info("JSON:eliminarTipoDocumentalUsuario"); 
		Resultado resultado = new Resultado();
		logger.debug("URLS:"+idUsuario+"!"+idClienteidCuenta);
		String[] ids = idClienteidCuenta.split("-");
		String idCliente = ids[0];
		String idCuenta = ids[1]; 
		
		UsuarioDTO  usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(idUsuario.toUpperCase());
		
		Cliente cliente = new Cliente();
		cliente.setId(new BigInteger(idCliente));
		cliente.setUsuarioDto(usuarioDto);
		cliente.setFechaModificacion(new Date());
		cliente.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
		cliente.setEstado(cboEstadoCuentaUsuario);
		
		Cuenta cuenta = new Cuenta();
		cuenta.setIdCuenta(new BigInteger(idCuenta));
		cuenta.setCliente(cliente);
		cuenta.setUsuarioDto(usuarioDto);
		cuenta.setFechaModificacion(new Date());
		cuenta.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
		cuenta.setEstado(cboEstadoCuentaUsuario);
 
		
		Cuenta  tdRes = usuarioService.eliminarClienteCuentaUsuario(usuarioDto, cliente, cuenta);
 
		resultado.setSeGuardo(tdRes.isSeGuardo());
		resultado.setCodigoError(tdRes.getCodigoError());
		escribeResultado(response, resultado);
	}
 
	
	private void escribeResultado(ResourceResponse response,  Resultado resultado) {
		Gson gson = new GsonBuilder().create();
		try {
			response.getWriter().write(gson.toJson(resultado));
		} catch (IOException e) {
			logger.error("[escribeResultado]",e);
		}
	}
}
