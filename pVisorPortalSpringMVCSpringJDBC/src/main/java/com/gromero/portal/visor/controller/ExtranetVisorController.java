package com.gromero.portal.visor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gromero.portal.visor.domain.Empresa;
import com.gromero.portal.visor.domain.Usuario;
import com.gromero.portal.visor.domain.UsuarioEmpresaArea;
import com.gromero.portal.visor.service.UsuarioEmpresaAreaService;

@Controller
@RequestMapping("/e/visor")
public class ExtranetVisorController {
	private final Log log = LogFactory.getLog(getClass()); 
	
	@Autowired
	UsuarioEmpresaAreaService usuarioEmpresaAreaService; 
	
	@SuppressWarnings("unused") 
	@RequestMapping("/inicio.html")
	private String inicio(HttpServletRequest request,Model m){
		
		HttpSession session = request.getSession();
		log.info(" Entro a Intranet Inicio"+estaEnSession(session));
		if(!estaEnSession(session)){
			log.debug("NO esta en session");
			m.addAttribute("usu", new Usuario());
			return "/i/otros/pages-login";
		}else{
			log.debug("esta en session");
		}
		Usuario u2 = (Usuario) session.getAttribute("session_usuario");
		
		UsuarioEmpresaArea uea = new UsuarioEmpresaArea();
		uea.setIdUsuario(u2.getIdUsuario());
		List<UsuarioEmpresaArea> lUEA =usuarioEmpresaAreaService.buscarXidUsuario(uea);
		 
		
		
//		lEmpresa.add(e);
		
//		m.addAttribute("", mapEmpresas);
		
		return "/e/visor/visor_e";
	}
	
	@RequestMapping("/ver_pdf.html")
	private String verPdf(){
		log.info(" Entro a Ver PDF");
		return "/e/visor/ventana";
	}
	

	private boolean estaEnSession(HttpSession session){ 
		Usuario u = (Usuario) session.getAttribute("session_usuario");
		if(u==null){//no esta logueado
			return false;
		}else{
			return true;
		} 
	}
}
