package pe.com.ransa.portal.carga.dao;

import pe.com.ransa.portal.carga.dto.EmpresaAreaUsuario;
import pe.com.ransa.portal.carga.dto.EmpresaUsuario;
import pe.com.ransa.portal.carga.dto.TipoDocEmpresaAreaUsuario;

public interface VDocsDao {

	EmpresaUsuario ingresaEmpresaUsuario(EmpresaUsuario empresaUsuario) throws Exception;
	EmpresaAreaUsuario ingresaEmpresaAreaUsuario(EmpresaAreaUsuario empresaAreaUsuario) throws Exception;
	TipoDocEmpresaAreaUsuario ingresaTipoDocEmpresaAreaUsuario(TipoDocEmpresaAreaUsuario tipoDocempresaAreaUsuario) throws Exception;
}
