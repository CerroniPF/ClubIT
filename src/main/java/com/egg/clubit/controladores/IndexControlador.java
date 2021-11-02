package com.egg.clubit.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller 
@RequestMapping ("/")
public class IndexControlador {
	@GetMapping("/")
	public ModelAndView index()  {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}

	
	@GetMapping("/crearPost")
	public ModelAndView crearPost()  {
		ModelAndView mav = new ModelAndView("crearPosteo");
		return mav;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@GetMapping("/perfil")
	public ModelAndView perfil()  {
		ModelAndView mav = new ModelAndView("perfil");
		return mav;
	}
	

}
