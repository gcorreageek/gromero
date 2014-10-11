package pe.com.ransa.portal.intranet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;
import pe.com.ransa.portal.intranet.dto.ReporteIngresoBodega;
 


public class UtilExcel {
//	private static Logger logger = Logger.getLogger(UtilExcel.class);
	public UtilExcel() { }
	public static void main(String[] args) {
		List<ReporteIngresoBodega> lReporte = new ArrayList<ReporteIngresoBodega>();
		ReporteIngresoBodega e = new ReporteIngresoBodega();
		BigInteger bb = new BigInteger("100");
		e.setDescripcionComercial(bb);
		lReporte.add(e);
//		UtilExcel.leerEscribir(lReporte);
	}
	public static  Object leerEscribir(List<ReporteIngresoBodega> lReporte,String entrada,String salida){
//		logger.info("Entra a UtilExcel");
		if(lReporte==null || lReporte.isEmpty()){
			return false;
		}    
		try { 
			Map beans = new HashMap();
			beans.put("lReporte", lReporte);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(entrada, beans, salida);
		} catch (Exception e) { 
			e.printStackTrace();
//			logger.error("[ParsePropertyException]",e);
		}   
		
		return true;
	}
	
	public static Object procesoExcel(List<ReporteIngresoBodega> lReporte,OutputStream out, String entrada,String salida) {
		UtilExcel.leerEscribir(lReporte, entrada, salida);
		File f = new File(salida);  
//		OutputStream out= null;
		FileInputStream in =null;
		try {
//			out = response.getOutputStream();
			in = new FileInputStream(f); 
			byte[] outputByte=new byte[4096];
			int length;
			while(( length = in.read(outputByte, 0, 4096 )) > 0 ) {
			   out.write( outputByte, 0, length );
			} 
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}finally{ 
			if(in!=null){ try { in.close(); } catch (IOException e) { e.printStackTrace(); } }
			if(out!=null){ try { out.close(); } catch (IOException e) { e.printStackTrace(); } }
		}
		if (!f.exists()) throw new IllegalArgumentException( "Delete: directorio no encontrado : " + salida);
		if (!f.canWrite()) throw new IllegalArgumentException("Delete: no tiene permisos de escritura: " + salida);
		if (!f.delete()) throw new IllegalArgumentException("Delete: fallo la eliminacion");
		return null;
	}
}
