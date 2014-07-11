package pe.com.silex.batch;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import pe.com.silex.service.ParameterService;
import pe.com.silex.service.ProcesoService;
/**
 * Clase donde se llama a la tarea que se va a ejecutar constantemente
 * @author USUARIO
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SilexJob extends QuartzJobBean {
	private static final Logger logger = Logger.getLogger(SilexJob.class);
	
    @Autowired
    private ProcesoService procesoService; 
    
    @Autowired
    private ParameterService parameterService;
 
    @Override
    protected void executeInternal(JobExecutionContext context) {
        //Aca se hace la logica
    	 
//			procesoService.ejecutarProceso(null);
		 
    	
//    	String msg = "holaa:" + Calendar.getInstance().getTime() + "\n";
////    	System.out.println(msg);
    	logger.debug("==========>PROBANDO:"+Calendar.getInstance().getTime());
    	
    }
}

