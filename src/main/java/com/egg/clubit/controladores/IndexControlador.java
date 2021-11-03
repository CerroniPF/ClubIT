package com.egg.clubit.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.servicios.UsuarioServicio;

@Controller 
@RequestMapping ("/")
public class IndexControlador {
	@Autowired
	UsuarioServicio usuarioServicio;

	@GetMapping("/")
	public ModelAndView index()  {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
	@GetMapping("/posteos/{mail}")
	public String posteos(Model model, @PathVariable String mail)  {
		Usuario usuario = usuarioServicio.buscarPorMail(mail);
		List<Posteo> posteos = usuario.getPost();
		model.addAttribute("posteos", posteos);
		
		return "prueba";
	}
}
