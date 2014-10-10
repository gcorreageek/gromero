package com.ransa.portal.util;

import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;


public class ExcelUtil {
	private static Logger logger = Logger.getLogger(ExcelUtil.class);
	
	public static Object getManagedBean(String mgdBeanName) {
		String expression = "#{" + mgdBeanName + "}";		
		Object value = null;		
		logger.trace("Expresion " + expression);
		
		if ((expression.indexOf("#{") != -1) && (expression.indexOf("#{") < expression.indexOf('}'))) {
			logger.trace("antes de crear el bean");
			value = getFacesContext().getApplication().createValueBinding(expression).getValue(getFacesContext());
			//value = getFacesContext().getApplication().getVariableResolver().resolveVariable(getFacesContext(), expression);
			logger.trace("despues de crear el bean");
		} else {
			value = expression;
		}
		return value;
	}
	
	//protected Object resolveExpression(String expression) {		
	//}
	
	
	private static FacesContext getFacesContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if(facesContext==null)
			logger.trace("El FacesContext es NULL");
		else
			logger.trace("El FacesContext no es NULL");
		return facesContext;
	}
}
