package pe.com.ransa.portal.carga.controller;

import static pe.com.ransa.portal.carga.common.ConstantesMant.MODE_VIEW;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
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
import pe.com.ransa.portal.carga.dto.Cliente;
import pe.com.ransa.portal.carga.dto.Cuenta;
import pe.com.ransa.portal.carga.dto.JqGridDataCliente;
import pe.com.ransa.portal.carga.dto.JqGridDataCuenta;
import pe.com.ransa.portal.carga.dto.Resultado;
import pe.com.ransa.portal.carga.service.ClienteService;
import pe.com.ransa.portal.carga.service.CuentaService;
import pe.com.ransa.portal.carga.viemodel.FormCommand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping(MODE_VIEW)
@SessionAttributes(ConstantesMant.MANTPRIMAX_CLIENTECUENTA_COMMAND_CLASS)
public class MantPrimaxClienteCuentaController { 

	private static Logger logger = Logger.getLogger(MantPrimaxClienteCuentaController.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();
	
	@Autowired
	ClienteService clienteService;
	@Autowired
	CuentaService cuentaService;
	
	@RequestMapping
	public String index(RenderRequest renderRequest, RenderResponse renderResponse, Model model){
		logger.info("index()");   
		FormCommand form = new FormCommand();  
		model.addAttribute(ConstantesMant.MANTPRIMAX_CLIENTECUENTA_COMMAND_CLASS, form);
		return ConstantesMant.VIEW_MANTPRIMAX_CLIENTECUENTA;
	} 
 //txtRuc txtRazonSocial txtCodAuxiliar txtEstadoFiltro
	@ResourceMapping(value=Mappings.LISTAR_CLIENTES_RESOURCE)
	public void obtenerListaClientesResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("txtRuc") String ruc, @RequestParam("txtRazonSocial") String razonSocial,
			@RequestParam("txtCodAuxiliar") String codigo, @RequestParam("txtEstadoFiltro") String estado){
		logger.debug("JSON:obtenerListaAreasResource");
		logger.debug("page=>"+page+"rows=>"+limit+"ruc=>"+ruc+"razonSocial=>"+razonSocial+"codigo=>"+codigo+"estado=>"+estado); 
		int rows = limit; 
		if(page<=0){
			page =1;
		}
		Cliente cliente = new Cliente();
		cliente.setRUC(ruc==null?"":ruc);
		cliente.setRazonSocial(razonSocial==null?"":razonSocial);
		cliente.setCodigo(codigo==null?"":codigo);
		cliente.setEstado(estado==null?"":estado);
		
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		
		List<Cliente> lCliente = clienteService.listarCliente(cliente	, fromRegistro, toRegistro);
		Integer cantidadRegistros = (Integer)clienteService.totalCliente(cliente);
  
		JqGridDataCliente objJqGrid = new JqGridDataCliente();
		if(lCliente != null && !lCliente.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lCliente);
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
 
	@ResourceMapping(value=Mappings.GUARDAR_CLIENTE)
	public void guardarCliente(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdCliente") BigInteger id,@RequestParam("txtRucCliente") String ruc,@RequestParam("txtRazonSocialCliente") String rz,
			@RequestParam("txtCodAuxiliarCliente") String codAuxiliar,@RequestParam("cboEstadoCliente") String estado,@RequestParam("tipoCRUDCliente") String crud
			){ 
		logger.info("JSON:guardarCliente");  
		Resultado resultado = new Resultado();
		if(ruc.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_CLIENTE_RUC);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(rz.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_CLIENTE_RAZONSOCIAL);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(codAuxiliar.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_CLIENTE_CODAUXILIAR);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		} 
		
		
		Cliente cliente = new Cliente();
		cliente.setId(id);
		cliente.setRUC(ruc);
		cliente.setRazonSocial(rz);
		cliente.setCodigo(codAuxiliar);
		if("AGREGA".equals(crud)){
			cliente.setUsuarioCreacion(ConstantesMant.USUARIO_DEFAULT);
			cliente.setFechaCreacion(new Date()); 
			cliente.setUsuarioModificacion(null);
			cliente.setFechaModificacion(null);
			cliente.setEstado(EstadoEliminacion.ESTADO_A.estado);
			cliente = clienteService.registrar(cliente);
		}else if("ACTUALIZA".equals(crud)){ 
			cliente.setUsuarioCreacion(null);
			cliente.setFechaCreacion(null); 
			cliente.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			cliente.setFechaModificacion(new Date());
			cliente.setEstado(null);
			cliente = clienteService.actualizar(cliente);
		}else if("ELIMINA".equals(crud)){ 
			cliente.setUsuarioCreacion(null);
			cliente.setFechaCreacion(null); 
			cliente.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			cliente.setFechaModificacion(new Date());
			cliente.setEstado(estado);
			cliente = clienteService.eliminar(cliente);
		} 
		
		resultado.setSeGuardo(cliente.isSeGuardo());
		resultado.setCodigoError(cliente.getCodigoError());
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
 
	@ResourceMapping(value=Mappings.LISTAR_CUENTAS_RESOURCE)
	public void obtenerListaCuentasResource(ResourceRequest request, ResourceResponse response,   
			@RequestParam("page") int page, @RequestParam("rows") int limit ,
			@RequestParam("id") BigInteger idCliente){
		logger.debug("JSON:obtenerListaAreasResource");
		if(page<=0){
			page =1;
		}
		logger.debug("page=>"+page+"rows=>"+limit+"id=>"+idCliente);
		Cuenta cuenta = new Cuenta();
		cuenta.setIdCliente(idCliente==null?new BigInteger("0"):idCliente);
		cuenta.setIdCuenta(new BigInteger("0")); 
		
		int rows = limit; 
		int fromRegistro = ((page-1) * rows) + 1;
		int toRegistro = fromRegistro + rows - 1;
		
		List<Cuenta> lCuenta = null;
		Integer cantidadRegistros = null;		
		if(idCliente==null){
			lCuenta = new ArrayList<Cuenta>() ;
			cantidadRegistros = 0;
		}else{
			lCuenta = cuentaService.listarCliente(cuenta, fromRegistro, toRegistro);
			cantidadRegistros = (Integer)cuentaService.totalCliente(cuenta);
		}
		
  
		JqGridDataCuenta objJqGrid = new JqGridDataCuenta();
		if(lCuenta != null && !lCuenta.isEmpty()){
			objJqGrid.setPage(page);
	        objJqGrid.setTotal(((cantidadRegistros + rows - 1) / rows));
	        objJqGrid.setRecords(cantidadRegistros);
	        
			objJqGrid.setRows(lCuenta);
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
	
	@ResourceMapping(value=Mappings.GUARDAR_CUENTA)
	public void guardarCuenta(ResourceRequest request, ResourceResponse response,  
			@RequestParam("txtIdCuenta") BigInteger idCuenta,@RequestParam("txtIdCliente") BigInteger idCliente,@RequestParam("txtNumeroCuenta")String nroCuenta,
			@RequestParam("txtPlacaVehicular") String placaVehicular,@RequestParam("txtNombreUsuario") String nombreUsuario,
			@RequestParam("txtCodEs") String codEs,@RequestParam("cboEstadoCuenta") String estado,@RequestParam("tipoCRUDCuenta") String crud
			){
		logger.info("JSON:guardarCuenta");  
		logger.debug("JSON:guardarCuenta:"+idCuenta+"|"+idCliente);  
		Resultado resultado = new Resultado();
		if(nroCuenta.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_CUENTA_NROCUENTA);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(nombreUsuario.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_CUENTA_NOMBREUSUARIO);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		}
		if(placaVehicular.equals("")){
			resultado.setCodigoError(ConstantesLib.CODERROR_CUENTA_PLACAVEHICULAR);
			resultado.setSeGuardo(false);
			escribeResultado(response, resultado);
			return;
		} 
		
		
		Cuenta cuenta = new Cuenta();
		cuenta.setIdCuenta(idCuenta);
		cuenta.setIdCliente(idCliente);
		cuenta.setNumeroCuenta(nroCuenta);
		cuenta.setPlacaVehicular(placaVehicular);
		cuenta.setNombreUsuario(nombreUsuario);
		cuenta.setCodigoEs(codEs); 
		
		logger.debug("==>"+cuenta.getIdCliente()+"|"+cuenta.getIdCuenta());
		
		if("AGREGA".equals(crud)){
			cuenta.setUsuarioCreacion(ConstantesMant.USUARIO_DEFAULT);
			cuenta.setFechaCreacion(new Date()); 
			cuenta.setUsuarioModificacion(null);
			cuenta.setFechaModificacion(null);
			cuenta.setEstado(EstadoEliminacion.ESTADO_A.estado); 
			cuenta = cuentaService.registrar2(cuenta);
		}else if("ACTUALIZA".equals(crud)){
			cuenta.setUsuarioCreacion(null);
			cuenta.setFechaCreacion(null); 
			cuenta.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			cuenta.setFechaModificacion(new Date());
			cuenta.setEstado(null); 
			cuenta = cuentaService.actualizar(cuenta);
		}else if("ELIMINA".equals(crud)){
			cuenta.setUsuarioCreacion(null);
			cuenta.setFechaCreacion(null); 
			cuenta.setUsuarioModificacion(ConstantesMant.USUARIO_DEFAULT);
			cuenta.setFechaModificacion(new Date());
			cuenta.setEstado(estado);  
			cuenta = cuentaService.eliminar(cuenta);
		}
		resultado.setSeGuardo(cuenta.isSeGuardo());
		resultado.setCodigoError(cuenta.getCodigoError());
		escribeResultado(response, resultado);
		
	}
}
