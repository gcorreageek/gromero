package pe.com.silex.batch;

import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailAwareTrigger;
/**
 * La Clase no se usa porque no esta configurado el Cron(Ejemplo: 22 10 11 22 ?..)
 * @author USUARIO
 */
public class PersistableCronTriggerFactoryBean extends CronTriggerFactoryBean {
	
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
 
        //Remove the JobDetail element
        getJobDataMap().remove(JobDetailAwareTrigger.JOB_DETAIL_KEY);
    }
}

