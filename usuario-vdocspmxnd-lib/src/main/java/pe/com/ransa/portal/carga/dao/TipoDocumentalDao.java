package pe.com.ransa.portal.carga.dao;

import java.util.List;

import pe.com.ransa.portal.carga.dto.TipoDocumental;


public interface TipoDocumentalDao { 
	public TipoDocumental ingresarTipoDoc(TipoDocumental td)  throws Exception;
	public TipoDocumental modificarTipoDoc(TipoDocumental td) throws Exception;
	public TipoDocumental modificarTipoDocEmpresaArea(TipoDocumental td)throws Exception;
	public TipoDocumental eliminarTipoDoc(TipoDocumental td) throws Exception;
	public TipoDocumental eliminarTipoDocEmpresaArea(TipoDocumental td)throws Exception;
	public List<TipoDocumental> listarTipoDoc(TipoDocumental td,Integer inicio,Integer fin) throws Exception;
	public Integer totalTipoDoc(TipoDocumental td) throws Exception;
	public List<TipoDocumental> listarEmpresaAreaTipoDoc(TipoDocumental td,Integer inicio,Integer fin) throws Exception;
	public TipoDocumental ingresarTipoDocEmpresaArea(TipoDocumental td) throws Exception;
	
}
