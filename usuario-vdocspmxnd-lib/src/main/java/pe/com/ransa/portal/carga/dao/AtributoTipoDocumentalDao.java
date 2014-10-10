package pe.com.ransa.portal.carga.dao;

import java.util.List;

import pe.com.ransa.portal.carga.dto.AtributoTipoDocumental;

public interface AtributoTipoDocumentalDao {
	List<AtributoTipoDocumental> listaAtributoTipoDocumental(AtributoTipoDocumental atributo,Integer inicio, Integer fin) throws Exception;
}
