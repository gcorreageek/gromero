package pe.com.silex.batch;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailAwareTrigger;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import pe.com.silex.service.ParameterService;
/**
 * Clase que se usa para setear los datos de cada cuanto tiempo quiero que se repita la tarea,
 * se esta usando en ves del cron, por defecto le pasamos parametros, pero por aca les pasamos otros
 * que quedan
 * @author USUARIO 
 */
public class PersistableSimpleTriggerFactoryBean extends SimpleTriggerFactoryBean{
	
	private ParameterService parameterService;
	private static final Logger logger = Logger.getLogger(PersistableSimpleTriggerFactoryBean.class);
	
	@Autowired
	public void setParameterService(ParameterService parameterService){
		this.parameterService = parameterService;
	}
	
	@Override
	public void setRepeatInterval(long repeatInterval) {
		try {
			long newRepeatInterval = parameterService.getPeriodicity();
			logger.info("//////////////////////////////Se modifico la periodicidad: " + newRepeatInterval);
			repeatInterval = newRepeatInterval;
		} catch (Exception e) {
			logger.error("Error al setear la periodicidad: " + e.getMessage(), e);
			logger.info("Se utilizara el valor por defecto de: " + repeatInterval);
		}
		super.setRepeatInterval(repeatInterval);
	}
	@Override
	public void afterPropertiesSet() throws ParseException {
		super.afterPropertiesSet();
		
		getJobDataMap().remove(JobDetailAwareTrigger.JOB_DETAIL_KEY);
	}

}
