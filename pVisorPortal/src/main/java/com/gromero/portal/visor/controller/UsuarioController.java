package com.gromero.portal.visor.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gromero.portal.visor.domain.Usuario;
import com.gromero.portal.visor.service.UsuarioService;

@Controller
public class UsuarioController {
	private final Logger log = Logger.getLogger(getClass());
	@Autowired
	UsuarioService usuarioService; 
	
	@RequestMapping(value="inicio_usuario.html")
	public String inicioUsuario(Model m){
		m.addAttribute("usuario", new Usuario());
		return "/i/otros/usuario_insert";
	}
	
	@RequestMapping(value="insertar_usuario.html")
	public String insertarUsuario(Model m,Usuario a){
		usuarioService.insertar(a);
		log.debug("usuario:"+a.getIdUsuario()+"|"+a.getUserName());
//		a.setUserName("gusta");
//		usuarioService.actualizar(a);
		Usuario a2 = new Usuario();
		a2.setUserName("%g%");
		List<Usuario> lstUsuario = usuarioService.buscarXuserName(a2);
		for (Usuario u : lstUsuario) {
			log.debug("u:"+u.getIdUsuario()+"|"+u.getUserName());
		}
		return "/e/visor/visor_e";
	}
	
}
