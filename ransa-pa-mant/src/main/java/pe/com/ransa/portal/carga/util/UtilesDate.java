package pe.com.ransa.portal.carga.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilesDate {

	private static final Log logger = LogFactory.getLog(UtilesDate.class);
	
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
	
	public static String formatoFecha(Calendar fecha) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd"); 
		return df.format(new Date(fecha.getTime().getTime()));
	}
	public static String fechaActualFormato(String formato) {
		Date fecha = new Date();
		DateFormat df = null;
		if(formato==null || formato.equals("")){
			df = new SimpleDateFormat("dd/MM/yyyy"); 
		}else{
			df = new SimpleDateFormat(formato); 
		}
		return df.format(new Date(fecha.getTime()));
	}

	public static Date stringToDate(String fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		Date date = null;
		try {
			date = formatter.parse(fecha);
		} catch (ParseException e) { 
			logger.error("[stringToDate]",e); 
			return null;
		}
		return date;
	}
	public static Date stringToDate(String fecha,String formato) {
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		SimpleDateFormat formatter = new SimpleDateFormat(formato); 
		Date date = null;
		try {
			date = formatter.parse(fecha);
		} catch (ParseException e) { 
			logger.error("[stringToDate]",e); 
			return null;
		}
		return date;
	}
	
	public static  boolean fecha1esMenorAfecha2(Date f1,Date f2){
//		logger.info("Fecha1:"+f1.getTime());
//		logger.info("Fecha2:"+f2.getTime());
		if(f1.equals(f2)){
//			logger.info("Son iguales");
			return false;
		} 
		if(f1.before(f2)){
			return false;
		}  
		return true; 
	} 
	public static void main(String[] args) {
		Calendar f1 = Calendar.getInstance();
//		f1.setTime(new Date());
		f1.setTime(stringToDate("19/50/2014"));
		System.out.println("Date:"+f1);
		System.out.println("Date:"+f1.getTime());
		System.out.println("Date:"+validateDate("07/99/2014"));
//		Calendar f2 = Calendar.getInstance();
//		Date d =stringToDate("19/07/2014");
//		f2.setTime(d);
		//19/07/2014
		//12/12/2012
		
//		System.out.println("Yeah:"+fecha1esMenorAfecha2(UtilesDate.stringToDate("20/20/2014"),UtilesDate.stringToDate("22")));
	} 
	
	/**
	 * Codigo obtenido de http://www.mkyong.com/regular-expressions/how-to-validate-date-with-regular-expression/
	 */
	public static Pattern pattern;
	public static  Matcher matcher;
	private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((17|18|19|20)\\d\\d)";
//	private static final String DATE_PATTERN = "((17|18|19|20)\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])";
	
	public static boolean validateDate(final String date) {
		pattern = Pattern.compile(DATE_PATTERN);
		matcher = pattern.matcher(date);
		if (matcher.matches()) {
			matcher.reset();
			if (matcher.find()) {
				String day = matcher.group(1);
				String month = matcher.group(2);
//				String day = matcher.group(3);
//				String month = matcher.group(2);
				int year = Integer.parseInt(matcher.group(3));
				if (day.equals("31") && (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")
								|| month.equals("04") || month.equals("06") || month .equals("09"))) {
					return false; // only 1,3,5,7,8,10,12 has 31 days
				} else if (month.equals("2") || month.equals("02")) {
					// leap year
					if (year % 4 == 0) {
						if (day.equals("30") || day.equals("31")) {
							return false;
						} else {
							return true;
						}
					} else {
						if (day.equals("29") || day.equals("30") || day.equals("31")) {
							return false;
						} else {
							return true;
						}
					}
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
