package com.gromero.portal.visor.dao;

import java.util.List;

import com.gromero.portal.visor.domain.DetalleOperacionTodo;
import com.gromero.portal.visor.domain.MmTempo;
import com.gromero.portal.visor.domain.RSap21;
import com.gromero.portal.visor.domain.TBN016;

public interface ConsultaDAO {

//	Object procesarTarea(Object o);
//	RSap21 insertSap21(RSap21 sap21);
//	void ejecutaProcedimientoSILEX21C(Object... o);
//	List<DetalleOperacionTodo> consultaRSAP04();
//	List<DetalleOperacionTodo> consultaRSAP04Adicionales();
//	String getNRFSAPPorNROOPG(Double nroFact);
//	TBN016 actualizarTBN016Operaciones(TBN016 tbn016);
//	Double actualizarObtenerNumerador(String string);
	
	List<DetalleOperacionTodo> consulta1RSAP04();
	List<DetalleOperacionTodo> consulta2RSAP04();
	List<DetalleOperacionTodo> consulta3RSAP04();
	
	List<MmTempo> listaMmTempo();
	
}