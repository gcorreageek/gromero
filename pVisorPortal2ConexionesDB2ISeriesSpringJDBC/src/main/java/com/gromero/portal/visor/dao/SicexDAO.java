package com.gromero.portal.visor.dao;

import java.math.BigDecimal;

import com.gromero.portal.visor.domain.TBN016;

public interface SicexDAO {

	BigDecimal actualizarObtenerNumerador(String codigo);
	TBN016 actualizarTBN016Operaciones(TBN016 tbn016);
	
	
}
