package pe.com.ransa.portal.carga.util;
 
import static com.ransa.portal.swtms.common.ConstantesComunes.UNIQUE_NAME_PAGINA_PORTLET_FILTRO;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.handler.SimpleMappingExceptionResolver;

import pe.com.ransa.portal.carga.service.PageService;

import com.ransa.exception.UnexpectedException;

public class SWPAMappingExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Log logger = LogFactory .getLog(SWPAMappingExceptionResolver.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PageService pageService;

	@Override
	protected ModelAndView doResolveException(PortletRequest request, MimeResponse response, Object handler, Exception ex) {
		logger.info("<==== Inicio Method: doResolveException ====>");
		logger.info("ex: " + ex);
		String codigoMensajeError = null;
		String mensajeException = null;
		String mensajeError = null;
		Object[] parametros = null;
		if (ex instanceof UnexpectedException) {
			UnexpectedException unexpectedException = (UnexpectedException) ex;
			mensajeException = unexpectedException.getMessage();
			codigoMensajeError = unexpectedException.getKeyMessage();
			parametros = unexpectedException.getParams();
			if (logger.isDebugEnabled()) {
				logger.debug("Exception es del tipo UnexpectedException");
				logger.debug("errorKeyMessage=" + codigoMensajeError);
				logger.debug("mensajeException: " + mensajeException);
			}
		}
		mensajeError = "Error: " + ex.getMessage();
		/*if (StringUtils.isBlank(codigoMensajeError)) {
			if (StringUtils.isBlank(mensajeException)) {
				codigoMensajeError = "error.general";
				parametros = null;
				mensajeError = messageSource.getMessage(codigoMensajeError, parametros, "Error al cargar el Mensaje", response.getLocale());
			} else {
				mensajeError = mensajeException;
			}
		}
		String regresar = messageSource.getMessage("label.regresar", null, "Error al cargar el Mensaje", response.getLocale());*/
		String regresar = "Regresar";
		String uniqueName = (String) request.getPortletSession().getAttribute( UNIQUE_NAME_PAGINA_PORTLET_FILTRO, PortletSession.APPLICATION_SCOPE);
		String url = pageService.generarUrlPagina(request, response, response.getNamespace(), uniqueName);
		if (logger.isDebugEnabled()) {
			logger.debug("codigoMensajeError=" + codigoMensajeError);
			logger.debug("mensajeError=" + mensajeError);
			logger.debug("uniqueName=" + uniqueName);
			logger.debug("url=" + url);
			logger.debug("regresar=" + regresar);
		}
//		request.setAttribute("mensajeError", mensajeError);
		request.setAttribute("mensajeError", "Ocurrio un problema vuelva a realizar la operacion");
		request.setAttribute("url", url);
		request.setAttribute("regresar", regresar);
		return super.doResolveException(request, response, handler, ex);
	}

}