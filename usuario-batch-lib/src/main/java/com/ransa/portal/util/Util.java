package com.ransa.portal.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.ibm.icu.util.Calendar;
import com.ransa.portal.exception.EjecucionException;
import com.ransa.portal.model.util.MetaData;
import com.ransa.portal.service.locator.PortletServiceLocator;

import sun.misc.BASE64Encoder;

/**
 * Clase con metodos genericos que lo utilizan varias clases
 * 
 * @author Christian D. Nuñez del Prado Rodriguez *
 */
public class Util {

	private static Logger logger = Logger.getLogger(Util.class);
	
	public static final String BUSQUEDA_USUARIO_POR_ID = "0";
	public static final String BUSQUEDA_USUARIO_POR_CORREO_ELECTRONICO = "1";	
	public static final int ACTUALIZACION_NORMAL = 1;
	public static final int ACTUALIZACION_PARCIAL = 0;
	public static final int NUMERO_VACIO = -1;	
    public static final String STRING_VACIO = "";
    public static final String PDFS = "pdfs";
    public static final String SEPARADOR = "#";
    public static final String SEPARADOR2 = ",";
    public static final String SEPARADOR3 = ", ";
    public static final String SEPARADOR4 = "<br>";
    public static final String INDICADOR_RETORNAR_RECURSOS = "0";
    public static final String IND_PORTAL_INTRANET = "I";
    public static final String IND_PORTAL_EXTRANET = "E";
    public static final String FLAG_EXCLUSION = "1";
    public static final String ESTADO_USUARIO_ACTIVO = "A";
    public static final String ESTADO_USUARIO_INACTIVO = "I";
    public static final int CREACION_NUEVO_USUARIO_EXTRANET = 1;
    public static final int ACTIVACION_USUARIO = 2;
    public static final int NO_EXISTE_USUARIO = 3;
    public static final int EXISTE_MAS_DE_UN_USUARIO = 4;
    public static final String NO_VALIDAR_ESTADO_USUARIO = "1";
    public static final String COMBO_DEFAULT_SELECTION = "-1";
    public static final String CREAR_USUARIO = "0";
    public static final int FORZADO_CAMBIO_CONTRASENIA = 0;
    public static final String ID_USUARIO = "idUsuario";
    public static final String CORREO_ELECTRONICO_USUARIO = "correoElectronicoUsuario";    
    public static final String IS_LOGIN = "isLogin";
    public static final String CONTRASENIA_ENCRIPTADA = "contraseniaEncriptada";
    public static final String NUEVA_CONTRASENIA_ENCRIPTADA = "nuevaContraseniaEncriptada";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";     
    public static final String PARAMETRO_ERROR = "parametroError";
    public static final String ID_SUBREPORTE_GRUPOS = "1";
    public static final String ID_SUBREPORTE_TIPOS_RECURSOS = "2";
    public static final String ID_SUBREPORTE_DATOS_USUARIO = "3";
    public static final String ID_SUBREPORTE_NEGOCIOS = "4";
    public static final String ID_ARCHIVO_PDF = "1";
    public static final String ID_ARCHIVO_XLS = "2";
    public static String NOMBRE_PDF_FICHA_CREACION_USUARIO = "FichaUsuarioCreacion-?.pdf";
    public static String NOMBRE_XLS_FICHA_CREACION_USUARIO = "FichaUsuarioCreacion-?.xls";
    public static int CONTADOR = 0;
    public static final String ID_AUTOGENERADO = "AUTOGENERADO";
    public static final String SEPARADOR_OR = "or";
    public static final String PWD_RESET_TRUE= "TRUE";
    public static final String PARAM_ALMA_PERU="ALMA";
	public static final String PARAM_CASA="CASA";
	public static final String PARAM_CCPM_CASA="LZ";
	public static final String PARAM_CCPM_PERU="AM";	
	public static final String ALMv3_PATH_FILE_PROPERTIES = "/Apps/PortalApplications/ransa-config-pa-inventariosWeb/ransa-pa-swpl.properties"; //28/01/2014 jlg
	public static final String ALMv3_VAR_PROP_APPCODE = "app.code"; //28/01/2014 jlg
	public static final int ALMv3_TIPO_COLUMNA_CONS_SALDOS = 1; //28/01/2014 jlg
	public static final int ALMv3_TIPO_COLUMNA_CONS_MOVIMIENTOS = 2; //28/01/2014 jlg
	    
    public static final String USER_TIVOLI_ROOT= "";
    //public static final String USER_TIVOLI_ROOT= "cn=root";
    public static final String PWD_TIVOLI_ROOT= "";
    //public static final String PWD_TIVOLI_ROOT= "password";
    static Properties propFile; //28/01/2014 jlg
    
    public static String convertObjectToString(Object object) {
    	String string = null;
		if (object != null)
			string = (String) object;
		return string;
    }
    
    public static String getNombreArchivoFichaCreacionUsuarioPDF() {
    	try {
    		CONTADOR++;
    	} catch (Exception e) {
    		CONTADOR = 0;
    	}
    	return NOMBRE_PDF_FICHA_CREACION_USUARIO.replace("?", "" + CONTADOR);
    }
    
    public static String getNombreArchivoFichaCreacionUsuarioXLS() {
    	try {
    		CONTADOR++;
    	} catch (Exception e) {
    		CONTADOR = 0;
    	}
    	return NOMBRE_XLS_FICHA_CREACION_USUARIO.replace("?", "" + CONTADOR);
    }
    
    /**
     * Obtiene el valor del atributo recibido segun el tipo recibido
     * 
     * @param atributo 	objeto recibido que puede ser un List o String
     * @return valor del atributo en String
     */
    @SuppressWarnings("unchecked")
    public static String getAtributo(Object atributo) {
    	logger.debug("getAtributo");	
    	String tempAtributo = STRING_VACIO;
		if (atributo != null) {
		    if (atributo instanceof List) {
		    	logger.debug("atributo es Array");
				List tempNombreCompania = (List) atributo;
				// Verificando que existe por lo menos un nombre de compañia
				if (tempNombreCompania.size() > 0) {
				    tempAtributo = (String) tempNombreCompania.get(0);
				}
		    } else {
		    	logger.debug("atributo es String");
		    	tempAtributo = (String) atributo;
		    }
		} else {
			logger.debug("atributo es null");
		}
		return tempAtributo;
    }

    /**
      * Convierte un String a int y retorna -1 si cadena no representa un
      * numero entero.
      * 
      * @param cadena
      *                String que representa un numero entero
      * @return numero entero obtenido de la cadena
      */
    public static int parseInt(String cadena) {
		int num = NUMERO_VACIO;
		if (cadena != null && !Util.STRING_VACIO.equals(cadena)) {
		    try {
		    	num = Integer.parseInt(cadena);
		    } catch (NumberFormatException e) {
		    	Util.showStackTrace(e);
		    }
		}
		return num;
    }

    public static void showStackTrace(Throwable throwable) {
    	if(logger.isDebugEnabled()) {
    		getStackTrace(throwable);
    	}
    }
   
    public static String getStackTrace(Throwable throwable) {
    	logger.debug("getStackTraceAsString");
        StringBuffer stringbuffer = new StringBuffer();
        int i = 1;
        for(Throwable throwable1 = throwable; throwable1 != null;
        		throwable1 = throwable1.getCause()) {
        	String mensaje = throwable1.getMessage();
        	logger.debug("ERROR "+i+" : "+mensaje);
            stringbuffer.append(mensaje);
            i++;
        }
        String tempResultado = stringbuffer.toString();
        logger.debug("tempResultado=" + tempResultado);
        return tempResultado;
    }
        
    public static String generarContrasenia() {
		String[] caracteres = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
				"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
				"w", "x", "y", "z"};
		String[] numeros = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		int pos;
		String indicadorNumero = "n", indicadorCaracter = "l", contrasenia = "", caracter;
		// n = numero, l = caracter en minuscula, L = caracter en mayuscula		
		String[] ordenGeneracion = {"n", "n", "l", "l", "n", "n", "L", "L" };
		for (int i = 0; i < ordenGeneracion.length; i++) {
			if (indicadorNumero.equals(ordenGeneracion[i])) {
				pos = (int)(Math.random() * 10);
				if (pos > 9) { pos = 9; }
				caracter = numeros[pos];
			} else if (indicadorCaracter.equals(ordenGeneracion[i])) {
				pos = (int)(Math.random() * 100 / 4);
				if (pos > 25) { pos = 25; }
				caracter = caracteres[pos];
			} else {
				pos = (int)(Math.random() * 100 / 4);
				if (pos > 25) { pos = 25; }
				caracter = caracteres[pos].toUpperCase();
			}	
			contrasenia = contrasenia + caracter;
		}
		logger.debug("password generado: '" + contrasenia + "'");
		return contrasenia;
	}
    
	public static String formatFecha(Date date){
		if(date!=null) {
			SimpleDateFormat fmt = new SimpleDateFormat(FORMATO_FECHA);
			return fmt.format(date);
		} else
			return STRING_VACIO;
	}
	
	public static Date convertStringToDate(String date){
		if(date!=null) {
			SimpleDateFormat fmt = new SimpleDateFormat(FORMATO_FECHA);
			Date tempDate = null;
			try {
				tempDate = fmt.parse(date);
			} catch (ParseException e) {
				showStackTrace(e);
			}
			return tempDate;
		} else
			return null;
	}
	
	public static String encriptar(String texto) {
		logger.debug("encriptar");
		logger.debug("texto=" + texto);
		MessageDigest md = null;
		try {
			// Instancia de generador SHA
			md = MessageDigest.getInstance("SHA");
			md.update(texto.getBytes("UTF-8")); 
		} catch (Exception e) {
			throw new EjecucionException("Error al encriptar texto=" + texto, e);
		}
		// Obtención del resumen de mensaje
		byte raw[] = md.digest();
		// Traducción a BASE64
		String hash = (new BASE64Encoder()).encode(raw);
		logger.debug("textEncriptado=" + hash);
		return hash;
	}
    
	public static void enviarCorreoElectronico(String subject, String body, String email1, String email2) {
		logger.debug("enviarCorreoElectronico");
		logger.debug("subject=" + subject);
		logger.debug("body=" + body);
		logger.debug("email1=" + email1);
		logger.debug("email2=" + email2);
		logger.debug("obteniendo Session");
		Email email = new Email(PortletServiceLocator.getInstance().getMailSession());
		email.setBody(body);
		email.setSubject(subject);
		try {
			InternetAddress tempEmail1 = new InternetAddress(email1);
			email.setFrom(tempEmail1);
			email.addTO(new InternetAddress(email2));
			email.addBCC(tempEmail1);
			logger.debug("enviando correo electronico...");
			email.send();
		} catch (Exception e) {
			throw new EjecucionException("No se pudo enviar correo electronico", e);
		}
	}
	
	public static String getCodigoErrorLDAP(String mensajeError) {
		logger.debug("getCodigoErrorLDAP");
		String codigoErrorLDAP = STRING_VACIO;
		try {
			int posicionData = mensajeError.indexOf("data ");
			codigoErrorLDAP = mensajeError.substring(posicionData + 5, posicionData + 8);
			logger.debug("verificando si codigoErrorLDAP es null");
			if (codigoErrorLDAP == null) {
				logger.debug("codigoErrorLDAP es null");
				codigoErrorLDAP = STRING_VACIO;
			}
		} catch (Exception e) {
			logger.debug("No se pudo obtener codigo de error ldap");
			Util.showStackTrace(e);
		}		
		logger.debug("codigoErrorLDAP=" + codigoErrorLDAP);
		return codigoErrorLDAP;
	}
	
	public static Date getCorrectDate(Date date) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		return calendar.getTime();
	}
	
	public static String getFechaActual(){
		return formatFecha(new Date());
	}
	
	public static String[] concatenar(Object[] objetos){
		String[] tempObjetos = new String[2];
		boolean bandera = true;
		StringBuffer ids = new StringBuffer();		
		StringBuffer nombres = new StringBuffer();		
		if (objetos != null && objetos.length > 0) {
			if (objetos[0] instanceof MetaData) {
				MetaData metaData = null;
				for (Object object : objetos) {
					logger.debug("objecto=" + object.toString());			
					metaData = (MetaData) object;
					if (bandera) {				
						ids.append(metaData.getValue());
						nombres.append(metaData.getLabel());
						bandera = false;
					} else {
						ids.append(SEPARADOR2);
						ids.append(metaData.getValue());
						nombres.append(SEPARADOR2);				
						nombres.append(metaData.getLabel());
					}
				}
			} else {
				for (Object object : objetos) {
					logger.debug("objecto=" + object.toString());			
					String[] tempObject = object.toString().split(SEPARADOR);
					if (bandera) {				
						ids.append(tempObject[0]);
						nombres.append(tempObject[1]);
						bandera = false;
					} else {
						ids.append(SEPARADOR2);
						ids.append(tempObject[0]);
						nombres.append(SEPARADOR2);				
						nombres.append(tempObject[1]);
					}
				}
			}
		} else {
			ids.append(STRING_VACIO + NUMERO_VACIO);
			nombres.append(STRING_VACIO + NUMERO_VACIO);
		}		
		tempObjetos[0] = ids.toString();		
		logger.debug("idsConcatenados=" + tempObjetos[0]);
		tempObjetos[1] = nombres.toString();		
		logger.debug("nombresConcatenados=" + tempObjetos[1]);
		return tempObjetos;
	}
	
	public static String[] getParametrosMensajeError(Collection<String> idUsuariosEnvioCorreoElectronicoFallido) {
		boolean bandera = true;
		StringBuilder idsConcatenados = new StringBuilder();
		for (String idUsuario : idUsuariosEnvioCorreoElectronicoFallido) {
			if (bandera) {
				idsConcatenados.append(idUsuario);
				bandera = false;
			} else {
				idsConcatenados.append(idUsuario);
				idsConcatenados.append(Util.SEPARADOR3);
			}
		}
		String[] parametrosMensajeError = new String[1];
		parametrosMensajeError[0] = idsConcatenados.toString();
		return parametrosMensajeError;
	}
	
	public static boolean validarIdUsuario(String idUsuario) {
		String caracteresValidos = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_$";
		String[] letras = idUsuario.split(STRING_VACIO);
		boolean resultado = true;
		for (String letra : letras) {
			if (!STRING_VACIO.equals(letra)) {
				letra = letra.toUpperCase();
				if (caracteresValidos.indexOf(letra) == -1) {
					resultado = false;
					break;
				}
			}			
		}
		return resultado;
	}
	
	//28/01/2014 jlg
	
	static{
		try {			
			propFile = new Properties();
			propFile.load(new FileInputStream(ALMv3_PATH_FILE_PROPERTIES));	
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	public static String getParamConfiguracion(String key){
		String prop = "";
		if(propFile!=null){
			prop = propFile.getProperty(key).toString();
		}
		return prop;
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
		
}