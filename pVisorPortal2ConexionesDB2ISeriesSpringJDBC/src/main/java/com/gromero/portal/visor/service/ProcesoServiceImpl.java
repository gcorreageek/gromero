package com.gromero.portal.visor.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gromero.portal.visor.dao.ConsultaDAO;
import com.gromero.portal.visor.dao.RnsLibDAO;
import com.gromero.portal.visor.dao.SicexDAO;
import com.gromero.portal.visor.domain.DetalleOperacionTodo;
import com.gromero.portal.visor.domain.RSap21;
import com.gromero.portal.visor.domain.TBN016;
import com.gromero.portal.visor.utiles.UtilesDate;

@Service
public class ProcesoServiceImpl implements ProcesoService {
	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	RnsLibDAO rnsLibDAO;
	@Autowired
	SicexDAO sicexDAO;
	@Autowired
	ConsultaDAO consultaDAO;
	
	@Transactional
	@Override
	public Object executarProceso(Object o) {
		log.info("Inicio Proceso"); 
		java.math.BigDecimal NROFACT = new BigDecimal(0);
		String varStr = "";
		Integer correlativo = 0;  
		String fecha = UtilesDate.fechaActualJunta();
		String hora = UtilesDate.horaActualJunta();
		List<DetalleOperacionTodo> lDetalleOperacion1 = consultaDAO.consulta1RSAP04();
		log.info("Lista COTIZADOS");
		int i =0;
		for (DetalleOperacionTodo item : lDetalleOperacion1) {
			
			if(i==2)
				break;
			log.info("------------------------------------------");
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
		log.info("Finaliza Proceso");
		return o;
	}
	
	private void procesos(DetalleOperacionTodo item,List<DetalleOperacionTodo> lDetalleOperacion,BigDecimal NROFACT,String varStr,Integer correlativo,String fecha,String hora){
		RSap21 rSap21 = new  RSap21();
		rSap21.setP1("E");
		rSap21.setP2("EZ");
		rSap21.setP3("G");
		rSap21.setP4(new BigDecimal(item.getNUMOPE()));
		rSap21.setP5(new BigDecimal(item.getNROOI())); 
		
	    log.info("Actualiza y Obtiene Nrofactura, De TAM044 obtiene UTLNUM x la CLAVE:"+"NROFACTPRV");
	    NROFACT = sicexDAO.actualizarObtenerNumerador("NROFACTPRV");
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
		rnsLibDAO.rsap21Insert(rSap21);
		
		
		log.info("Ejecuta Procedimiento SILEX21C:"+"EZ"+"|"+
				"G"+"|"+
				StringUtils.leftPad(item.getNUMOPE(), 10, " ")+"|"+
				StringUtils.leftPad(item.getNROOI(), 10, " ")+"|"+
				StringUtils.rightPad(item.getNRODOCID(), 15, " ")+"|"+// item.CODREF
				NROFACT+"".toString().trim()+"|"+
				StringUtils.leftPad(varStr, 2, " ")+"|"+
				StringUtils.leftPad(varStr, 10, " "));
		
		log.debug("1:"+StringUtils.leftPad(item.getNUMOPE(), 10, " ").length());
		log.debug("2:"+StringUtils.leftPad(item.getNROOI(), 10, " ").length());
		log.debug("3:"+StringUtils.rightPad(item.getNRODOCID(), 15, " ").length());
		log.debug("4:"+(NROFACT+"".toString().trim()).length());
		log.debug("5:"+StringUtils.leftPad(varStr, 2, " ").length());
		log.debug("6:"+StringUtils.leftPad(varStr, 10, " ").length());
		
		rnsLibDAO.ejecutaProcedimientoSILEX21C(
				new Object[]{"EZ",
							"G",
							StringUtils.leftPad(item.getNUMOPE(), 10, " "),
							StringUtils.leftPad(item.getNROOI(), 10, " "),
							StringUtils.rightPad(item.getNRODOCID(), 15, " "),// item.CODREF
							NROFACT+"".toString().trim(), 
							StringUtils.leftPad(varStr, 2, " "),
							StringUtils.leftPad(varStr, 10, " ")});	
		
		log.info("Obtengo NRFSAP de RSAP21 x el NROFACT:"+NROFACT);
		String codigoViene = rnsLibDAO.getNRFSAPPorNROOPG(NROFACT);
		log.info("NRFSAP:"+codigoViene);
		
		Integer codigo = Integer.parseInt(codigoViene==null?"0":codigoViene);
		TBN016 tbn016 = new TBN016();
		tbn016.setNROPEDI(codigo.toString());
		tbn016.setIDOPEPED(item.getIDOPEPED());
		log.info("Actualiza en TBN016, el NROPEDI="+tbn016.getNROPEDI()+", x el IDOPEPED="+tbn016.getIDOPEPED());
		sicexDAO.actualizarTBN016Operaciones(tbn016);
		
	}
	
	public static void main(String[] args) {
		String fecha = UtilesDate.fechaActualJunta();
		String hora = UtilesDate.horaActualJunta();
		System.out.println("=>"+fecha+"|"+hora);
		
	}

}
