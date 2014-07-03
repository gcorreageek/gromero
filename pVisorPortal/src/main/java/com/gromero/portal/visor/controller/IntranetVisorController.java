package com.gromero.portal.visor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/i/visor")
public class IntranetVisorController {
	private final Log log = LogFactory.getLog(getClass()); 
	
	@SuppressWarnings("unused") 
	@RequestMapping("/inicio.html")
	private String inicio(){
		log.info(" Entro a Intranet Inicio");
		return "/i/visor/visor_i";
	}
}
