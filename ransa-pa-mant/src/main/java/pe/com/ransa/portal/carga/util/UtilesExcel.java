package pe.com.ransa.portal.carga.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import pe.com.ransa.portal.carga.controller.MantPrimaxEmpresaAreaTDController;

public class UtilesExcel {
	private static Logger logger = Logger.getLogger(UtilesExcel.class);
	private static boolean isDebugEnabled = logger.isDebugEnabled();

	public static void main(String[] args) { 
		List<String[]> l = new UtilesExcel().readExcel(100,2,null);
		for (String[] s : l) { 
			for (String string : s) {
				System.out.print("|"+string+"|");
			}
			System.out.println("");
		}
	}
	public UtilesExcel() {
		// TODO Auto-generated constructor stub
	} 
	
	public static List<String[]> readExcel(int nColumnas,int nHoja,InputStream is){
		logger.debug("readExcel()");
		List<String[]> filas = new ArrayList<String[]>();
		FileInputStream file = null;
		try {
			HSSFWorkbook workbook = null;
			if(is==null){
				logger.debug("cargo el excel por defecto");
				file = new FileInputStream(new File("d:\\VDPMX_Plantilla_Carga_Usuarios.xls"));
				workbook =  new HSSFWorkbook(file);
			}else{
				logger.debug("cargo el excel por el InputStream");
				workbook =  new HSSFWorkbook(is);
			} 
		    HSSFSheet sheet = workbook.getSheetAt(nHoja);
		    Iterator<Row> rowIterator = sheet.iterator();
		    while(rowIterator.hasNext()) {
		    	String[] columnas   = new String[nColumnas];
		        Row row = rowIterator.next();
		        Iterator<Cell> cellIterator = row.cellIterator();
		        int nc= 0;
		        while(cellIterator.hasNext()) {
		        	String celda = ""; 
		            Cell cell = cellIterator.next(); 
		            switch(cell.getCellType()) {
		                case Cell.CELL_TYPE_BOOLEAN: 
		                    celda = cell.getBooleanCellValue()+"";
		                    break;
		                case Cell.CELL_TYPE_NUMERIC:
		                    celda = cell.getNumericCellValue()+"";
		                    break;
		                case Cell.CELL_TYPE_STRING: 
		                    celda = cell.getStringCellValue()+"";
		                    break; 
		            }
		            columnas[nc]= celda;
		            if(nc==nColumnas-1)//verifica si es mayor al numero de columnas
		            	break;
		            nc++;   
		        } 
		        filas.add(columnas); 
		        logger.debug("Columnas=>"+Arrays.toString(columnas));
		    }
		} catch (FileNotFoundException e) {
			logger.error("[FileNotFoundException]",e);
		} catch (IOException e) {
			logger.error("[IOException]",e);
		}finally{
			try {
				if(file!=null)
					file.close();
			} catch (IOException e) {
				logger.error("[IOException,finally]",e);
			}
		}
		return filas;
	}
}
