package com.gromero.portal.visor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gromero.portal.visor.constantes.ConstantesComunes;
import com.gromero.portal.visor.domain.Usuario;
import com.gromero.portal.visor.domain.UsuarioEmpresaArea;
import com.gromero.portal.visor.service.UsuarioEmpresaAreaService;
import com.gromero.portal.visor.service.UsuarioService;

@Controller
public class InicioController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Autowired
	UsuarioService usuarioService; 
	
	@Autowired
	UsuarioEmpresaAreaService usuarioEmpresaAreaService; 
	
	@RequestMapping("/inicio.html"  )
	public String inicio(HttpServletRequest request,Model m,Usuario u) {  
		log.info("inicio"+u.getUserName());
		log.info("inicio"+u.getUserPass());
		HttpSession session = request.getSession();
		String ruta = (String) session.getAttribute("session_ruta");
		Usuario usuSession = (Usuario) session.getAttribute("session_usuario");
		if(ruta==null || "".equals(ruta)){
			session.setAttribute("session_ruta", ConstantesComunes.RUTA_WEB_LIB);
		} 
		log.debug("usuarios:"+usuSession);
		
		if(usuSession!=null){
			log.debug("usuarios:"+usuSession.getUserName());
			return "/index_plantilla";
		} 
		if(u==null)
			u = new Usuario();
		m.addAttribute("usu", u);
		return "/i/otros/pages-login"; 
	} 
	@RequestMapping("/cambiar.html"  )
	public String cambiar(HttpServletRequest request) {  
		log.info("cambiar");  
		return "cambiar"; 
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
		return "cambiar"; 
	} 
//	login
	@RequestMapping("/login.html"  )
	public String login(HttpServletRequest request,Model m,Usuario u){
		log.debug("usuario 1:"+u.getUserName());
		log.debug("usuario 2:"+u.getUserPass());
		HttpSession session = request.getSession();
		Usuario uSale = (Usuario) session.getAttribute("session_usuario");
		if(uSale!=null ){
			return "/index_plantilla";
		}
		
		if(u.getUserName()==null){
			log.debug("mensaje usuario"+"Ingrese Usuario");
			m.addAttribute("mensaje_usuario", "Ingrese Usuario");
			return inicio(request,m,u);
		}
		if(u.getUserPass()==null){
			log.debug("mensaje usuario"+"Ingrese Password");
			m.addAttribute("mensaje_usuario", "Ingrese Password");
			return inicio(request,m,u);
		}
		
		
		List<Usuario> lUsuario = usuarioService.buscarXuserName(u);
		if(lUsuario.isEmpty()){
			log.debug("mensaje usuario"+"Usuario no existe");
			m.addAttribute("mensaje_usuario", "Usuario no existe");
			return inicio(request,m,u);
		}else{
			Usuario u2 = lUsuario.get(0);
			log.debug("usuarioViene:"+u.getUserPass());
			log.debug("usuarioSale:"+u2.getUserPass());
			if(!u.getUserPass().equals(u2.getUserPass())){
				log.debug("mensaje usuario"+"Password incorrecto");
				m.addAttribute("mensaje_usuario", "Password incorrecto");
				return inicio(request,m,u);
			}else{
//				UsuarioEmpresaArea uea = new UsuarioEmpresaArea();
//				uea.setIdUsuario(u2.getIdUsuario());
//				List<UsuarioEmpresaArea> lUEA =usuarioEmpresaAreaService.buscarXidUsuario(uea);
//				for (UsuarioEmpresaArea uea1 : lUEA) {
//					log.debug("uea:"+uea1.getUsuario().getNombres()
//							+"|"+uea1.getEmpresaArea().getEmpresa().getEmpresa()
//							+"|"+uea1.getEmpresaArea().getArea().getArea());
//				}
				session.setAttribute("session_usuario", u2); 
				log.debug("mensaje usuario"+"Usuario OK");
				m.addAttribute("mensaje_usuario", "Usuario OK");
				return "/index_plantilla";
			}
		} 
	}
	
	@RequestMapping("/des_logueo.html"  )
	public String desLogueo(HttpServletRequest request,Model m,Usuario u){
		log.debug("[desLogueo]");
		HttpSession session = request.getSession();
		Usuario uSale = (Usuario) session.getAttribute("session_usuario");
		if(uSale!=null ){  
			session.removeAttribute("session_usuario");
		} 
		return inicio(request,m,u);
	}
	
	
}
