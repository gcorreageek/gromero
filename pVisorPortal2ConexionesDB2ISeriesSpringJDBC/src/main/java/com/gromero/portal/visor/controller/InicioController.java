package com.gromero.portal.visor.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gromero.portal.visor.domain.Usuario;
import com.gromero.portal.visor.service.ProcesoService;

@Controller
public class InicioController {
	private final Logger log = Logger.getLogger(getClass());
 
//	@Autowired
//	ProcesoService procesoService;
	
	@RequestMapping("/inicio.html"  )
	public String inicio(HttpServletRequest request,Model m,Usuario u) { 
//		procesoService.executarProceso(null);
		m.addAttribute("usu", u);
		return "/i/otros/pages-login"; 
	} 
  
	
}
