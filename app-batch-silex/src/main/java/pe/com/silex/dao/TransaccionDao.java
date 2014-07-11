package pe.com.silex.dao;

import java.math.BigDecimal;

import pe.com.silex.bean.RSap21;
import pe.com.silex.bean.TBN016;

public interface TransaccionDao {
	//SicexDAO
	BigDecimal actualizarObtenerNumerador(String codigo);
	TBN016 actualizarTBN016Operaciones(TBN016 tbn016); 
	
	// RnsLibDAO
	String getNRFSAPPorNROOPG(BigDecimal nroFact);
	RSap21 rsap21Insert(RSap21 rsap21);
	void ejecutaProcedimientoSILEX21C(final Object... o);
	
	
}
