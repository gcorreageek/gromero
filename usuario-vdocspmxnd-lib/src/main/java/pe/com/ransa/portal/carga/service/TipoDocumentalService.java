package pe.com.ransa.portal.carga.service;

import java.util.List;

import pe.com.ransa.portal.carga.dto.TipoDocumental;

/**
 * Servicio que implementa la funcionalidad asociada a los Clientes de Primax
 * @author dmirandat
 *
 */
public interface TipoDocumentalService {

	TipoDocumental registrar(TipoDocumental td);
	TipoDocumental actualizar(TipoDocumental td);
//	TipoDocumental actualizarTipoDocumentalEmpresaArea(TipoDocumental td);
	TipoDocumental eliminar(TipoDocumental td);
//	TipoDocumental eliminarTipoDocumentalEmpresaArea(TipoDocumental td);
	
	public List<TipoDocumental> listar(TipoDocumental td,Integer inicio,Integer fin);
	public Integer total(TipoDocumental td);
	
	
	
	public List<TipoDocumental> listarTipoDocumentalActivos(String idUsuario,String txtEmpresaFiltro,String txtAreaFiltro,String txtTdFiltro);
//	public Integer total(TipoDocumental td);
	 
	
	
	
	
}
