package com.ransa.portal.dao;

import java.util.List;

import com.ransa.portal.domain.Columna;

public interface ColumnaDAO {

	List<Columna> listaColumnas(int idAplicacion, String idUsuario, int codPantalla);
	void registrarColumnas(int idAplicacion, int codPantalla, String codsColumna, String idUsuario);

}
