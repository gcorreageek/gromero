package com.gromero.portal.visor.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Inicio {
	private final Logger log = Logger.getLogger(getClass());
//	private final Log log = LogFactory.getLog(getClass());
	 
	
	@RequestMapping("/inicio.html"  )
	public String inicio() {  
		log.info("prueba");
		log.error("prueba");
		return "index_portal"; 
	} 

 
	
	
}
