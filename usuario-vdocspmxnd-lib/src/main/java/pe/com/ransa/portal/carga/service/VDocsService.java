package pe.com.ransa.portal.carga.service;

import pe.com.ransa.portal.carga.dto.EmpresaAreaUsuario;
import pe.com.ransa.portal.carga.dto.EmpresaUsuario;
import pe.com.ransa.portal.carga.dto.TipoDocEmpresaAreaUsuario;

public interface VDocsService {
	EmpresaUsuario registraEmpresaUsuario(EmpresaUsuario empresaUsuario);
	EmpresaAreaUsuario registraEmpresaAreaUsuario(EmpresaAreaUsuario empresaAreaUsuario);
	TipoDocEmpresaAreaUsuario registraTipoDocEmpresaAreaUsuario(TipoDocEmpresaAreaUsuario tipoDocEmpresaAreaUsuario);
}
