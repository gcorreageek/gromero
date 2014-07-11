package pe.com.silex.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.silex.bean.DetalleOperacionTodo;
import pe.com.silex.bean.RSap21;
import pe.com.silex.bean.TBN016;
import pe.com.silex.dao.ConsultaDao;
import pe.com.silex.dao.TransaccionDao;
import pe.com.silex.service.ParameterService;
import pe.com.silex.service.ProcesoService;
import pe.com.silex.util.UtilesDate;
@Service
public class ProcesoServiceImpl implements ProcesoService {

	private static final Logger log = Logger.getLogger(ProcesoServiceImpl.class);
	@Autowired
	ConsultaDao consultaDAO;
	@Autowired
	TransaccionDao transaccionDAO;
	
//	@Autowired
//	ParameterService parametrizadoService;
	
	@Override
	public Object ejecutarProceso(Object o) { 
		log.info("================================================>Inicio Proceso"); 
		java.math.BigDecimal NROFACT = new BigDecimal(0);
		String varStr = "";
		Integer correlativo = 0;  
		String fecha = UtilesDate.fechaActualJunta();
		String hora = UtilesDate.horaActualJunta();
		List<DetalleOperacionTodo> lDetalleOperacion1 = consultaDAO.consulta1RSAP04();
		log.info("LISTA COTIZADOS="+lDetalleOperacion1.size());
		int i =1;
		for (DetalleOperacionTodo item : lDetalleOperacion1) {
			log.info(i+"------------------------------------------");
			procesos(item, lDetalleOperacion1, NROFACT, varStr, correlativo,fecha,hora);
			i++;
		}   
//		log.info("Lista ADICIONALES");
//		List<DetalleOperacionTodo> lDetalleOperacion2 = consultaDAO.consulta2RSAP04();
//		for (DetalleOperacionTodo item : lDetalleOperacion2) {
//			procesos(item, lDetalleOperacion1, NROFACT, varStr, correlativo,fecha,hora);
//		} 
//		log.info("Lista CAMBIO DE PROVEEDOR");
//		List<DetalleOperacionTodo> lDetalleOperacion3 = consultaDAO.consulta3RSAP04();
//		for (DetalleOperacionTodo item : lDetalleOperacion3) {
//			procesos(item, lDetalleOperacion1, NROFACT, varStr, correlativo,fecha,hora);
//		} 
		log.info("================================================>Finaliza Proceso");
		return o;
	}
	
	
	private void procesos(DetalleOperacionTodo item,List<DetalleOperacionTodo> lDetalleOperacion,BigDecimal NROFACT,String varStr,Integer correlativo,String fecha,String hora)  {
		RSap21 rSap21 = new  RSap21();
		rSap21.setP1("E");
		rSap21.setP2("EZ");
		rSap21.setP3("G");
		rSap21.setP4(new BigDecimal(item.getNUMOPE()));
		rSap21.setP5(new BigDecimal(item.getNROOI())); 
		
	    log.info("Actualiza y Obtiene Nrofactura, De TAM044 obtiene UTLNUM x la CLAVE:"+"NROFACTPRV");
	    NROFACT = transaccionDAO.actualizarObtenerNumerador("NROFACTPRV");
	    log.info("Actualizo el UTLNUM:"+NROFACT+", De TAM044 x la CLAVE:"+"NROFACTPRV");
	    
		rSap21.setP6(new BigDecimal(correlativo));
		rSap21.setP7(item.getSIGLA().trim() + item.getSERIE().trim());
		rSap21.setP8(new BigDecimal(item.getCODSRVCO()));
		if ("".equals(item.getNRODOCID().trim())) {
			rSap21.setP9(" ");
		} else {
			rSap21.setP9(item.getNRODOCID().trim());
		} 
		
		
		rSap21.setP10(NROFACT);
		rSap21.setP11(new BigDecimal(1));
		rSap21.setP12(new BigDecimal(item.getFECOPE().trim()));
		rSap21.setP13(new BigDecimal(item.getCODMON().trim()));
		rSap21.setP14(new BigDecimal(item.getIMPSCPR().trim()));
		rSap21.setP15(new BigDecimal(1));
		rSap21.setP16(new BigDecimal(0));
		rSap21.setP17(new BigDecimal(item.getCDRFCODMAT().trim()));
		rSap21.setP18(new BigDecimal(item.getNCCSAP().trim()));
		rSap21.setP19(new BigDecimal(0)); 
		rSap21.setP20(new BigDecimal(0)); 
		rSap21.setP21(" ");
		rSap21.setP22(" ");
		rSap21.setP23("P");
		rSap21.setP24(new BigDecimal(fecha));
		rSap21.setP25(new BigDecimal(hora));
		rSap21.setP26("SILEX");
		rSap21.setP27("SILEX");
		rSap21.setP28("SILEX");
		rSap21.setP29(new BigDecimal(fecha));
		rSap21.setP30(new BigDecimal(hora));
		rSap21.setP31("SILE");
		rSap21.setP32(new BigDecimal(1));
		transaccionDAO.rsap21Insert(rSap21);
		
		
		log.info("Ejecuta Procedimiento SILEX21C:"+"EZ"+"|"+
				"G"+"|"+
				StringUtils.leftPad(item.getNUMOPE(), 10, " ")+"|"+
				StringUtils.leftPad(item.getNROOI(), 10, " ")+"|"+
				StringUtils.rightPad(item.getNRODOCID(), 15, " ")+"|"+// item.CODREF
				NROFACT+"".toString().trim()+"|"+
				StringUtils.leftPad(varStr, 2, " ")+"|"+
				StringUtils.leftPad(varStr, 10, " "));
		
//		log.debug("1:"+StringUtils.leftPad(item.getNUMOPE(), 10, " ").length());
//		log.debug("2:"+StringUtils.leftPad(item.getNROOI(), 10, " ").length());
//		log.debug("3:"+StringUtils.rightPad(item.getNRODOCID(), 15, " ").length());
//		log.debug("4:"+(NROFACT+"".toString().trim()).length());
//		log.debug("5:"+StringUtils.leftPad(varStr, 2, " ").length());
//		log.debug("6:"+StringUtils.leftPad(varStr, 10, " ").length());
		 
		transaccionDAO.ejecutaProcedimientoSILEX21C(
				new Object[]{"EZ",
							"G",
							StringUtils.leftPad(item.getNUMOPE(), 10, " "),
							StringUtils.leftPad(item.getNROOI(), 10, " "),
							StringUtils.rightPad(item.getNRODOCID(), 15, " "),// item.CODREF
							NROFACT+"".toString().trim(), 
							StringUtils.leftPad(varStr, 2, " "),
							StringUtils.leftPad(varStr, 10, " ")});	
		
		log.info("Obtengo NRFSAP de RSAP21 x el NROFACT:"+NROFACT);
		String codigoViene = transaccionDAO.getNRFSAPPorNROOPG(NROFACT);
		log.info("NRFSAP:"+codigoViene);
		
		Integer codigo = Integer.parseInt(codigoViene==null?"0":codigoViene);
		TBN016 tbn016 = new TBN016();
		tbn016.setNROPEDI(codigo.toString());
		tbn016.setIDOPEPED(item.getIDOPEPED());
		log.info("Actualiza en TBN016, el NROPEDI="+tbn016.getNROPEDI()+", x el IDOPEPED="+tbn016.getIDOPEPED());
		transaccionDAO.actualizarTBN016Operaciones(tbn016);
		
	}

}
