package com.ransa.portal.service.impl;

import java.util.List;

import com.ransa.portal.dao.ColumnaDAO;
import com.ransa.portal.dao.factory.DaoFactory;
import com.ransa.portal.domain.Columna;
import com.ransa.portal.service.ColumnaService;


public class ColumnaServiceImpl implements ColumnaService {
	
	
	private ColumnaDAO columnaDAO;
	
	public ColumnaServiceImpl() {
		columnaDAO = DaoFactory.getInstance().getColumnaDao();
	}
	
	public List<Columna> listaColumnas(int idAplicacion, String idUsuario, int codPantalla) { 
		return columnaDAO.listaColumnas(idAplicacion, idUsuario, codPantalla);
	}
	
	public void registrarColumnas(int idAplicacion, int codPantalla, String codsColumna, String idUsuario){
		columnaDAO.registrarColumnas(idAplicacion, codPantalla, codsColumna, idUsuario);
	}
}
