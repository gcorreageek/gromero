package com.gromero.portal.visor.dao;

import java.math.BigDecimal;

import com.gromero.portal.visor.domain.RSap21;

public interface RnsLibDAO {

	String getNRFSAPPorNROOPG(BigDecimal nroFact);
	RSap21 rsap21Insert(RSap21 rsap21);
	
	void ejecutaProcedimientoSILEX21C(final Object... o);
	
}
