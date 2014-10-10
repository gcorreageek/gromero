package pe.com.ransa.portal.carga.dao;

import java.util.List;

import pe.com.ransa.portal.carga.dto.Area;


public interface AreaDao { 
	public Area ingresarArea(Area area) throws Exception;
	public Area modificarArea(Area area)throws Exception;
	public Area eliminarArea(Area area)throws Exception;
	public Area eliminarEmpresaArea(Area area)throws Exception;
	public List<Area> listarArea(Area area,Integer inicio,Integer fin)throws Exception;
	public List<Area> listarAreaXcodigo(Area area,Integer inicio,Integer fin)throws Exception;
	public List<Area> listarEmpresaAreaUsuario(Area area,Integer inicio,Integer fin)throws Exception;
	public Integer totalArea(Area area)throws Exception;
	
	public Area ingresarEmpresaArea(Area area)throws Exception;
	
}
