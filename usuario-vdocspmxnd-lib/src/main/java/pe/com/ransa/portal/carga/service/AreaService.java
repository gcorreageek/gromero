package pe.com.ransa.portal.carga.service;

import java.util.List;

import pe.com.ransa.portal.carga.dto.Area;

/**
 * Servicio que implementa la funcionalidad asociada a los Clientes de Primax
 * @author dmirandat
 *
 */
public interface AreaService {

	Area registrar(Area area);
	Area actualizar(Area area);
	Area eliminar(Area area);
	
	public List<Area> listar(Area area,Integer inicio,Integer fin);
	public Integer total(Area area);
	 
	 
	
	
}
