package com.gromero.portal.visor.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gromero.portal.visor.dao.ProcesoDAO;
import com.gromero.portal.visor.domain.BEDatosRSAP;
import com.gromero.portal.visor.domain.DetalleOperacionTodo;
import com.gromero.portal.visor.domain.RSap21;
import com.gromero.portal.visor.domain.TBN016;

@Service
public class Proceso2ServiceImpl implements ProcesoService {
	
	@Autowired
	ProcesoDAO procesoDao;
	 
	
	@Override
	public Object procesarTarea(Object o) {
		String codigo = (String) o;
		procesoDao.getNRFSAPPorNROOPG(null);
//		grabarPedidosVentas(o);
//		grabarPedidosVentasAdicionales(o);
		return o;
	}  
 
	@Override
	public Object grabarPedidosVentas(Object o) {
		//TODO - De donde sale y como obtengo la data de esto lstIDOPEPED
		List<String> lstIDOPEPED = null;
		 
		String fecha = null;
		String hora = null;
		
		Double NROFACT = null;
		String varStr ="";
		Integer correlativo =0;   
		
		Integer cont = 0;
		
		List<DetalleOperacionTodo> lstDetalleOperacionTodo = procesoDao.consultaRSAP04();
		for (DetalleOperacionTodo doTodo : lstDetalleOperacionTodo) {
			BEDatosRSAP regis = new BEDatosRSAP();
			regis.setDESCMERC(doTodo.getIDOPEPED());
			regis.setNUMOPE(Integer.parseInt(doTodo.getNUMOPE()));
			regis.setNROCONT(Integer.parseInt(doTodo.getCODMON()));
			regis.setCCNBNS(doTodo.getNRODOCID());
			regis.setNROOI(Integer.parseInt(doTodo.getNROOI()));
			if(cont==0){
				for (DetalleOperacionTodo item1 : lstDetalleOperacionTodo) {
					if(item1.getNUMOPE()== doTodo.getNUMOPE() && item1.getNROOI() == doTodo.getNROOI() 
							&& item1.getNRODOCID()==doTodo.getNRODOCID() && item1.getCODMON()==doTodo.getCODMON()){
						cont = cont +1;
					}
				}
				NROFACT = procesoDao.actualizarObtenerNumerador("NROFACTPRV");
			}
			cont = cont -1; 
			 
				procesoDao.ejecutaProcedimientoSILEX21C( new Object[]{
					"EZ",
					"G",
					StringUtils.leftPad(doTodo.getNUMOPE(), 10, " "),
					StringUtils.leftPad(doTodo.getNROOI(), 10, " "),
					StringUtils.rightPad(doTodo.getNRODOCID(), 15, " "),
					NROFACT.toString().trim(), 
					StringUtils.leftPad(varStr, 2, " "),
					StringUtils.leftPad(varStr, 10, " ")
				}); 
				
				
				String codigo = procesoDao.getNRFSAPPorNROOPG(NROFACT);
				  
				
				TBN016 tbn016 = new TBN016();
				tbn016.setFLGPEDI("T");
				tbn016.setNROPEDI(codigo);
				tbn016.setIDOPEPED(doTodo.getIDOPEPED()); 
				procesoDao.actualizarTBN016Operaciones(tbn016);
		} 
		return null;
	}

	@Override
	public Object grabarPedidosVentasAdicionales(Object o) {
		//TODO - De donde sale y como obtengo la data de esto lstIDOPEPED
		List<String> lstIDOPEPED = null;
		Double  NROFACTA = 0.0;
		String varStrA = "";
		Integer correlativo = 0;
		String fecha = null;
		String hora = null;
		
		List<DetalleOperacionTodo> lstDetalleOperacionTodo = procesoDao.consultaRSAP04Adicionales();
		for (DetalleOperacionTodo doTodo : lstDetalleOperacionTodo) {
			RSap21 rSap21 = new  RSap21();
			rSap21.setP1("E");
			rSap21.setP2("EZ");
			rSap21.setP3("G");
			rSap21.setP4(doTodo.getNUMOPE());
			rSap21.setP5(doTodo.getNROOI());  
			
			NROFACTA = procesoDao.actualizarObtenerNumerador("NROFACTPRV");
			correlativo = 1;
			lstIDOPEPED.clear();
			lstIDOPEPED.add(doTodo.getIDOPEPED()); 
			
			rSap21.setP6(correlativo.toString());
			rSap21.setP7(doTodo.getSIGLA().trim()+doTodo.getSERIE().trim());
			rSap21.setP8(doTodo.getCODSRVCO());
			if("".equals(doTodo.getNRODOCID())){
				rSap21.setP9(" ");
			}else{
				rSap21.setP9(doTodo.getNRODOCID());
			} 
			
			rSap21.setP10(NROFACTA.toString());
			rSap21.setP11(1+"");
			rSap21.setP12(doTodo.getFECOPE());
			rSap21.setP13(doTodo.getCODMON());
			rSap21.setP14(doTodo.getIMPSCPR());
			rSap21.setP15(1+"");
			rSap21.setP16(0+"");
			rSap21.setP17(doTodo.getCDRFCODMAT());
			rSap21.setP18(doTodo.getNCCSAP());
			rSap21.setP19(0+""); 
			rSap21.setP20(0+""); 
			
			rSap21.setP21(" ");
			rSap21.setP22(" ");
			rSap21.setP23("P");
			rSap21.setP24(fecha);
			rSap21.setP25(hora);
			rSap21.setP26("SILEX");
			rSap21.setP27("SILEX");
			rSap21.setP28("SILEX");
			rSap21.setP29(fecha);
			rSap21.setP30(hora);
			rSap21.setP31("SILE");
			rSap21.setP32(1+""); 
			procesoDao.insertSap21(rSap21);	
			procesoDao.ejecutaProcedimientoSILEX21C( new Object[]{
				"EZ",
				"G",
				StringUtils.leftPad(doTodo.getNUMOPE(), 10, " "),
				StringUtils.leftPad(doTodo.getNROOI(), 10, " "),
				StringUtils.rightPad(doTodo.getNRODOCID(), 15, " "),
				NROFACTA.toString().trim(), 
				StringUtils.leftPad(varStrA, 2, " "),
				StringUtils.leftPad(varStrA, 10, " ")
			}); 
			 
			String codigo = procesoDao.getNRFSAPPorNROOPG(NROFACTA);
			StringBuilder str = new StringBuilder();
			for (String nro : lstIDOPEPED) {
				str.append("'"+nro+"',"); 
			}
			String IDOPEP = str.substring(0, str.lastIndexOf(",")).toString();
			
			TBN016 tbn016 = new TBN016();
			tbn016.setFLGPEDI("T");
			tbn016.setNROPEDI(codigo);
			tbn016.setIDOPEPED(IDOPEP); 
			procesoDao.actualizarTBN016Operaciones(tbn016);
		} 
	
		return null;
	}
 

}
