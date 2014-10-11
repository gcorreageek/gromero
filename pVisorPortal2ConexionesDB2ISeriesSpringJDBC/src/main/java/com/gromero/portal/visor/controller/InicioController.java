package com.gromero.portal.visor.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InicioController {
	private final Logger log = Logger.getLogger(getClass());
 
//	@Autowired
//	ConsultaService consultaService;
	
	@RequestMapping("/inicio.html"  )
	public String inicio(HttpServletRequest request,Model m) { 
//		List<MmTempo> LmmTempo = consultaService.listaMmTempo();
//		for (MmTempo mmTempo : LmmTempo) {
//			log.debug("==>"+mmTempo.getImp112()+"|"+mmTempo.getImp113());
//		}
//		m.addAttribute("LmmTempo", LmmTempo);
		return "prueba"; 
	} 
  
	
}
