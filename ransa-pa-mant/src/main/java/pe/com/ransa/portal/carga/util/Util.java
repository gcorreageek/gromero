package pe.com.ransa.portal.carga.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Util {

	private static final Log logger = LogFactory.getLog(Util.class);
	
	static Properties propFile;
	
//	static{
//		try {			
//			propFile = new Properties();
//			propFile.load(new FileInputStream(Mappings.PATH_FILE_PROPERTIES));	
//		} catch (IOException e) {
//			logger.error(e.getMessage(),e);
//		}
//	}
	public static String parseDoubleOintegerTOstring(String doubleOinteger){
		String entero = "0";
		try {
			entero = new Integer(new Double(Double.parseDouble(doubleOinteger)).intValue()).toString();
		} catch (Exception e) {
			logger.error("[parseDoubleOintegertoString]",e);
		} 
		return entero;
	}
	public static String parseIntegerTOString(String integer){
		String bigDecimal = "0";
		try {
			bigDecimal = new BigDecimal(integer).toBigInteger().toString();
		} catch (Exception e) {
			logger.error("[parseIntegerTOString]",e);
		} 
		return bigDecimal;
	}
//	usuarios.split(",");
	public static String[] parseStringSplit(String cadena){
		String[] usuario = null;
		try {
			usuario = cadena.split(",");
		} catch (Exception e) {
			logger.error("[parseStringSplit]",e);
			usuario = new String[]{""};
		} 
		return usuario;
	}
//	
	public static String getParamConfiguracion(String key){
		String prop = "";
		if(propFile!=null){
			prop = propFile.getProperty(key).toString();
		}
		return prop;
	}
	
	public static String getLastValue(String[] array){
		String result="";
		if(array!=null && array.length>0){
			result=array[array.length-1];
		}
		return result;
	}
	
	public static long convertNumber(String value){
		long number = 0;
		try{
			number = Long.parseLong(value);
		}catch(NumberFormatException e){
			logger.warn(e.getMessage()+" - value: "+value);
		}		
		return number;
	}
	
	public static int convertInt(String value){
		int number = 0;
		try{
			number = Integer.parseInt(value);
		}catch(NumberFormatException e){
			logger.warn(e.getMessage()+" - value: "+value);
		}		
		return number;
	}
	
	public static String convertirFecha(Date date, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	public static String reformatDate(Object fecha, String formatInicio, String formatFin) {
		String myDateString = "";
		SimpleDateFormat formatter1 = new SimpleDateFormat(formatInicio);
		Date myDate = null;

		if (fecha != null){
			String fechaStr = fecha.toString();
			if (!fechaStr.equals("") && !fechaStr.equals("0")){
				try {
					myDate = formatter1.parse(fechaStr);

				} catch (ParseException e) {
					logger.error(e);
				}
		
				SimpleDateFormat formatter2 = new SimpleDateFormat(formatFin);
				myDateString = formatter2.format(myDate);
			}
		}

		return myDateString;
	}
	public static String formateadoBigDecimal(BigDecimal num){
		Locale locale  = new Locale("es", "US");
		String pattern = "###,##0.00";  
		DecimalFormat decimalFormat = (DecimalFormat)  NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		String formateado = decimalFormat.format(num);
//		System.out.println("=>"+formateado);
//		String formateado = "242.442.222.220,80";
		String parte1 = formateado.substring(0, formateado.length()-3).replace('.', ',');
		String parte2 = formateado.substring(formateado.length()-3, formateado.length()).replace(',', '.');
		return new StringBuilder().append(parte1).append(parte2).toString();
	}
	public static void main(String[] args) {
		
		DecimalFormat df = new DecimalFormat("#####.##");
	
		System.out.println("number2:"+formateadoBigDecimal(new BigDecimal(2321229822123227.44044)));
	}
	public static String formatNumberToString(double number)
	{
		String formatNumber = "";
		DecimalFormat df;
		DecimalFormatSymbols misimbolo;
		misimbolo=new DecimalFormatSymbols();
		misimbolo.setDecimalSeparator('.');
//		misimbolo.set
		df = new DecimalFormat("#####0.0", misimbolo);
		formatNumber = df.format(number);

		return formatNumber;
	}
	
	public static String formatoArchivo(String nomArchivo){
		return nomArchivo.substring(nomArchivo.lastIndexOf("."));
		
	}
	
	public static boolean suprimirArchivo(String source_name) {
		File source_file = new File(source_name);

		try {
			if (!source_file.exists() || !source_file.isFile())
				logger.info("No se puede encontrar el archivo : " + source_name);

			if (source_file.delete() != true)
				logger.info("El archivo no puede ser eliminado.");

		} catch (Exception e) {
			logger.info("Error: "+e.getMessage(), e);
		}

		if(source_file.exists() && source_file.isFile())
			return false;

		return true;
	}
	public static String getContentType(String name) {
		String extension = name.substring(name.lastIndexOf(".")+1).toLowerCase().trim();
		if(extension.equals("pdf")) return "application/pdf";
		else if(extension.equals("xls")) return "application/vnd.ms-excel";
		else if(extension.equals("jpg")) return "image/jpeg";
		else if(extension.equals("gif")) return "image/gif";
		else if(extension.equals("bmp")) return "image/x-ms-bmp";
		else if(extension.equals("png")) return "image/x-png";
		else return "application/octet-stream";
	}

	public static String getExtension(String nombre) {
		String extension = "isValid";
		if(nombre != null && !nombre.equals("")) {
			if(nombre.toLowerCase().substring(nombre.lastIndexOf(".")).equals(".pdf") || nombre.toLowerCase().substring(nombre.lastIndexOf(".")).equals(".xls"))
				extension = "isNotValid";
		}

		return extension;
	}
	
	public static String getConvertirFecha(String fecha){
		try {
			String ano=fecha.substring(0,4);
			String mes=fecha.substring(4,6);
			String dia=fecha.substring(6,8);
			String nf=dia+"/"+mes+"/"+ano;
			return nf;
		} catch (Exception e) {
			logger.info("Error getConvertirFecha: " + fecha, e);
			return null;
		}
	}
	
	public static Integer getRevertirFecha(String fecha){
		try {
			String dia=fecha.substring(0,2);
			String mes=fecha.substring(3,5);
			String ano=fecha.substring(6,10);
			String nf=ano+""+mes+""+dia;
			return Integer.parseInt(nf);
		} catch (Exception e) {
			logger.info("Error getRevertirFecha: " + fecha, e);
			return null;
		}
	}
	
	public static String getConvertirHora(String hora){
		try {
			String h=hora.substring(0,1);
			String m=hora.substring(1,3);
			String s=hora.substring(3,5);
			String nf=h+":"+m+":"+s;
			return nf;
		} catch (Exception e) {
			logger.info("Error getConvertirHora: " + hora, e);
			return null;
		}
	}
	
	public static int validarNumero(String cadena){
		try {
			Integer.parseInt(cadena);
		} catch (NumberFormatException e) {
			try {
				new BigDecimal(cadena);
				return 0;
			} catch (NumberFormatException e2) {
				return -1;
			} catch (Exception e3) {
				return -1;
			}
		}
		
		return 1;
	}
	
	public static BigDecimal getRedondeo(BigDecimal valor){
		try {
			valor = valor.setScale(2);
		} catch (ArithmeticException e) {
			valor = valor.setScale(2, RoundingMode.CEILING);
		} catch (Exception e) {
			logger.info("Error-getRedondeo: "+e.getMessage(), e);
			valor = BigDecimal.ZERO;
		}
		return valor;
	}
	
	public static Object coalesce(Object o){
		if(o == null || "null".equalsIgnoreCase(String.valueOf(o))){
			return "";
		}
		return o;
	}
	public static  boolean esInteger(String s){
		if (s == null)
			return false;
		if (s.length() == 0)
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i)))
				return false;
		}
		return true;
	}

	public static  boolean esDate(String s){
		if (s.matches("\\d{2}/\\d{2}/\\d{4}")) {
			return true;
		}
		return false;
	} 
	 
	public static BigInteger parseStringToBigInteger(String string){
		BigInteger bigInteger = null;
		try {
			bigInteger = new BigInteger(string);
		} catch (Exception e) {
			bigInteger = null;
		}
		return bigInteger;
	}

	
}
