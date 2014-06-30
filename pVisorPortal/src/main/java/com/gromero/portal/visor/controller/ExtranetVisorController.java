package com.gromero.portal.visor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/e/visor")
public class ExtranetVisorController {
	private final Log log = LogFactory.getLog(getClass()); 
	
	@SuppressWarnings("unused") 
	@RequestMapping("/inicio.html")
	private String inicio(){
		log.info(" Entro a Intranet Inicio");
		return "/e/visor/visor_e";
	}
	

}
