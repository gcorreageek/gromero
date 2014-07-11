package com.gromero.portal.visor.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gromero.portal.visor.dao.ProcesoDAO;
import com.gromero.portal.visor.domain.DetalleOperacionTodo;
import com.gromero.portal.visor.domain.TBN016;

@Service
public class Proceso3ServiceImpl implements ProcesoService {
	@Autowired
	ProcesoDAO procesoDao;
	
	@Override
	public Object procesarTarea(Object o) {
		double NROFACT = 0;
		String varStr = "";
		Integer cont = 0;  
		List<DetalleOperacionTodo> lDetalleOperacion1 = procesoDao.consulta1RSAP04();
		for (DetalleOperacionTodo item : lDetalleOperacion1) {
			procesos(item, lDetalleOperacion1, NROFACT, varStr, cont);
		}   
		List<DetalleOperacionTodo> lDetalleOperacion2 = procesoDao.consulta2RSAP04();
		for (DetalleOperacionTodo item : lDetalleOperacion2) {
			procesos(item, lDetalleOperacion2, NROFACT, varStr, cont);
		} 
		List<DetalleOperacionTodo> lDetalleOperacion3 = procesoDao.consulta3RSAP04();
		for (DetalleOperacionTodo item : lDetalleOperacion3) {
			procesos(item, lDetalleOperacion3, NROFACT, varStr, cont);
		} 
		return o;
	}  
	
	private void procesos(DetalleOperacionTodo item,List<DetalleOperacionTodo> lDetalleOperacion,double NROFACT,String varStr,Integer cont ){
		if(cont == 0){
			for (DetalleOperacionTodo item1 : lDetalleOperacion) { 
				if(item.getNUMOPE().equals(item1.getNUMOPE()) && item.getNROOI().equals( item1.getNROOI() )
					&& item.getNRODOCID().equals(item1.getNRODOCID()) && item.getCODMON().equals(item1.getCODMON())){
					cont = cont+1;
				}
			}
			 NROFACT = procesoDao.actualizarObtenerNumerador("NROFACTPRV");
		}
		cont = cont - 1; 
		
		procesoDao.ejecutaProcedimientoSILEX21C(
				new Object[]{"EZ",
							"G",
							StringUtils.leftPad(item.getNUMOPE(), 10, " "),
							StringUtils.leftPad(item.getNROOI(), 10, " "),
							StringUtils.rightPad(item.getNRODOCID(), 15, " "),// item.CODREF
							NROFACT+"".toString().trim(), 
							StringUtils.leftPad(varStr, 2, " "),
							StringUtils.leftPad(varStr, 10, " ")});	
		Integer codigo = Integer.parseInt(procesoDao.getNRFSAPPorNROOPG(NROFACT));
		TBN016 tbn016 = new TBN016();
		tbn016.setNROPEDI(codigo.toString());
		tbn016.setIDOPEPED(item.getIDOPEPED());
		procesoDao.actualizarTBN016Operaciones(tbn016);
	}
 
	@Override
	public Object grabarPedidosVentas(Object o) {
		//TODO - De donde sale y como obtengo la data de esto lstIDOPEPED
		return null;
	}

	@Override
	public Object grabarPedidosVentasAdicionales(Object o) {
		//TODO - De donde sale y como obtengo la data de esto lstIDOPEPED
		return null;
	}
 

}
