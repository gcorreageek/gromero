package pe.com.silex.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilesDate {

	public static String fechaActualJunta(){//yyyyMMdd
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); 
		StringBuilder fecha = new StringBuilder();
		fecha.append(calendar.get(Calendar.YEAR));
		fecha.append((calendar.get(Calendar.MONTH)+"").length()==1?"0"+(calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH)+1));
		fecha.append((calendar.get(Calendar.DAY_OF_MONTH)+"").length()==1?"0"+calendar.get(Calendar.DAY_OF_MONTH):calendar.get(Calendar.DAY_OF_MONTH)); 
		return fecha.toString();
	}
	public static String horaActualJunta(){//HHmmss
		DateFormat df = new SimpleDateFormat("HHmmss"); 
		return df.format(new Date());
	}
	
 
}
