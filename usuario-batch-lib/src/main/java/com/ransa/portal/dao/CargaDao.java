package com.ransa.portal.dao;

import java.util.Collection;

import com.ransa.portal.model.Carga;

public interface CargaDao {

	public int registrarCarga(String idUsuarios, String idUsuarioCreador, String indPortal);

	public void eliminarCarga(int idCarga, String idUsuarioModificador);

	public Collection<String> getIdUsuariosCargados(int idCarga);

	public Collection<Carga> getCargas(String indPortal);

}