package com.gromero.portal.visor.utiles;

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
	
	public static void dd(int i){
		for (int j = 0; j < 100; j++) {
			System.out.println("Edith!"+i+new Date());
		} 
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 300; i++) {
			System.out.println("Hola Gustavo!!!!!"+i+new Date()); 
			if(i==30){
				Thread.sleep(3000);
				dd(i);
			}
//			System.out.println("Juntos!!!!!"+i+new Date()); 
		}
		
		
	}
	
 
}
