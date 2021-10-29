package com.egg.clubit.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller 
@RequestMapping ("/registro")

public class RegistroControlador {
/*	@GetMapping("/ingreso")
	public String ingreso(ModelMap model) {
		
		return"cargarUsuario";
	}*/
	
	@GetMapping("/ingreso")
	public ModelAndView ingreso()  {
		ModelAndView mav = new ModelAndView("cargarUsuario");
		
		
		return mav;
	}

}
