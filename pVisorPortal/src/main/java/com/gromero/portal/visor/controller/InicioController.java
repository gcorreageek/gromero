package com.gromero.portal.visor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gromero.portal.visor.constantes.ConstantesComunes;

@Controller
public class InicioController {
	private final Logger log = Logger.getLogger(getClass());
	
	@RequestMapping("/inicio.html"  )
	public String inicio(HttpServletRequest request) {  
		log.info("prueba"); 
		HttpSession session = request.getSession();
		session.setAttribute("session_ruta", ConstantesComunes.RUTA_WEB_LIB);
		return "index_plantilla"; 
	} 
	@RequestMapping("/cambiar.html"  )
	public String cambiar(HttpServletRequest request) {  
		log.info("cambiar");  
		return "ingresar_ruta_lib"; 
	} 
	
	@RequestMapping("/ingresar_ruta_lib.html"  )
	public String ingresarRutaLib(HttpServletRequest request) {  
		log.info("ingresar ruta lib");
		String ruta = request.getParameter("ruta");
		log.info("ruta"+ruta);
		HttpSession session = request.getSession();
		String rutaSession = (String) session.getAttribute("session_ruta");
		
		if(rutaSession==null){
			session.setAttribute("session_ruta", ruta);
		}else{
			session.removeAttribute("session_ruta");
			session.setAttribute("session_ruta", ruta);
		}  
		return "ingresar_ruta_lib"; 
	} 

 
	
	
}
