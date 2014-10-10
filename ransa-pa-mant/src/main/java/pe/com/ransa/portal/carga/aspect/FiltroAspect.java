package pe.com.ransa.portal.carga.aspect;

import java.lang.reflect.Field;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import pe.com.ransa.portal.carga.dto.Cliente;

@Aspect
public class FiltroAspect {
	private Logger logger = Logger.getLogger(FiltroAspect.class);
	private boolean isDebugEnabled = logger.isDebugEnabled();
	
	@SuppressWarnings("rawtypes")
	@Before("execution(* pe.com.ransa.portal.carga.service.impl.ClienteServiceImpl.registrar(..))")
	public void logBeforeSave(JoinPoint joinPoint) {
//		public Cliente registrar(Cliente cliente) {
		logger.info("Hizo Registrar USUARIO==>" + joinPoint.getSignature().getName());
//		logger.info("User: " + usuario.getIdUsuario());
		
//		Object[] del = joinPoint.getArgs();
		
		try {
			/**
			 * Registrar en una tabla bitacora
			 */ 
//			Class clas = obj.getClass();
//			Field field1 = clas.getDeclaredField("idUsuarioCrea");
//			if(!field1.isAccessible()){
//				field1.setAccessible(true);
//				field1.set(obj, new String("idUsuarioSession"));
//				field1.setAccessible(false);
//			}
//			Field field2 = clas.getDeclaredField("fechaCreacion");
//			if(!field2.isAccessible()){
//				field2.setAccessible(true);
//				field2.set(obj, Calendar.getInstance().getTime());
//				field2.setAccessible(false);
//			}
		} catch (SecurityException e) {
			logger.error("ERROR====>>>>",e);
		} catch (IllegalArgumentException e) {
			logger.error("ERROR====>>>>",e);
		}  catch (Exception e) {
			logger.error("ERROR====>>>>",e);
		}
	}
	
	
}
