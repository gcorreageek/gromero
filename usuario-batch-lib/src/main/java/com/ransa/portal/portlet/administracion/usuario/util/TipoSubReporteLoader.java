package com.ransa.portal.portlet.administracion.usuario.util;

import java.util.ArrayList;
import java.util.Collection;

import com.ransa.portal.util.Util;

public class TipoSubReporteLoader {	
	
	private Collection<TipoSubReporteBean> subReportes;
	
	public void setSubReportes(boolean isSubReporteDatosUsuario) {
		if (isSubReporteDatosUsuario) {
			subReportes = new ArrayList<TipoSubReporteBean>();
			TipoSubReporteBean subReporteDatosUsuario = new TipoSubReporteBean();
			subReporteDatosUsuario.setIdTipoReporte(Util.ID_SUBREPORTE_DATOS_USUARIO);
			subReportes.add(subReporteDatosUsuario);
		} else {
			subReportes = new ArrayList<TipoSubReporteBean>();
			TipoSubReporteBean subReporteDatosUsuario = new TipoSubReporteBean();
			subReporteDatosUsuario.setIdTipoReporte(Util.ID_SUBREPORTE_DATOS_USUARIO);
			subReportes.add(subReporteDatosUsuario);		
			TipoSubReporteBean subReporteGrupos = new TipoSubReporteBean();
			subReporteGrupos.setIdTipoReporte(Util.ID_SUBREPORTE_GRUPOS);
			subReportes.add(subReporteGrupos);
			TipoSubReporteBean subReporteTiposRecursos = new TipoSubReporteBean();
			subReporteTiposRecursos.setIdTipoReporte(Util.ID_SUBREPORTE_TIPOS_RECURSOS);
			subReportes.add(subReporteTiposRecursos);
			TipoSubReporteBean subReporteNegocios = new TipoSubReporteBean();
			subReporteNegocios.setIdTipoReporte(Util.ID_SUBREPORTE_NEGOCIOS);
			subReportes.add(subReporteNegocios);
		}
	}
	
	public Collection<TipoSubReporteBean> createBeanCollection() {		
		return subReportes;
	}
	
}