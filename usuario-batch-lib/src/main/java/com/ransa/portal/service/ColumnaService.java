package com.ransa.portal.service;

import java.util.List;

import com.ransa.portal.domain.Columna;

public interface ColumnaService {

	List<Columna> listaColumnas(int idAplicacion, String idUsuario, int codPantalla);
	
	void registrarColumnas(int idAplicacion, int codPantalla, String codsColumna, String idUsuario);
}
