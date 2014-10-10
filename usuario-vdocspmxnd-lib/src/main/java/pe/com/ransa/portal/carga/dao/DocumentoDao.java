package pe.com.ransa.portal.carga.dao;

import java.util.List;

import pe.com.ransa.portal.carga.dto.Documento;

public interface DocumentoDao {
	List<Documento> listaDocumento(Documento documento,Integer inicio, Integer fin) throws Exception;
	
}
