package com.egg.clubit.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.servicios.UsuarioServicio;

@Controller 
@RequestMapping ("/")

public class RegistroControlador {
	
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	
/*	@GetMapping("/ingreso")
	public String ingreso(ModelMap model) {
		
		return"cargarUsuario";
	}*/
	
	@GetMapping("/registro")
	public ModelAndView registro()  {
		ModelAndView mav = new ModelAndView("registroUsuario");
		return mav;
	}
	
	@GetMapping("/ingreso")
	public ModelAndView ingreso()  {
		ModelAndView mav = new ModelAndView("ingresoUsuario");
		return mav;
	}

	
	@PostMapping("/registro")
	public String registroUsuario(@RequestParam String nombre,@RequestParam String apellido,@RequestParam String nombreUsuario,@RequestParam String email,@RequestParam String pass,@RequestParam String pass2)throws ErrorServicio {
		
		
		try {
			usuarioServicio.registro(nombre,apellido,nombreUsuario,email,pass,pass2);
		} catch (ErrorServicio e) {
			
			e.printStackTrace();
		}
		return  "index";
	}
}
