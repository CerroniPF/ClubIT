package com.egg.clubit.controladores;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.RespuestaRepositorio;
import com.egg.clubit.servicios.RespuestaServicio;
import com.egg.clubit.servicios.UsuarioServicio;




@Controller
@RequestMapping("/")
public class usuarioControlador {
	@Autowired
	UsuarioServicio usuarioServicio;
	
	@Autowired
	RespuestaServicio respuestaServicio;	
	//--------------------------------------------------------------------------------------------	
	@GetMapping("/registro")
	public ModelAndView registro(ModelMap modelo)  {
		ModelAndView mav = new ModelAndView("registroUsuario");

		return mav;
	}
	
	
	@PostMapping("/registro")
	public String registroUsuario(ModelMap modelo ,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String nombreUsuario,@RequestParam String mail,@RequestParam String pass,@RequestParam String pass2)throws ErrorServicio {

		try {
			usuarioServicio.registro(nombre,apellido,nombreUsuario,mail,pass,pass2);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			
			modelo.put("nombre", nombre );
			modelo.put("apellido", apellido );
			modelo.put("nombreUsuario", nombreUsuario );
			modelo.put("mail", mail );
			return "/registroUsuario";
		}
		return  "index";
	}

	//--------------------------------------------------------------------------------------------	
	
	@GetMapping("/ingreso")
	public ModelAndView ingreso()  {
		ModelAndView mav = new ModelAndView("ingresoUsuario");
		return mav;
	}
	
	
	@GetMapping("/login")
	public String login(HttpSession httpSession, @RequestParam(required = false) String error, Model model) {
		
//		//Si el usuario ya está logueado lo redirigimos a la principal
//		Usuario user = (Usuario) httpSession.getAttribute("usersession");
//		if (user != null && user.getActivo()) {
//			return "redirect:/";
//		}
		
		if (error != null) {
			model.addAttribute("error", "Nombre de usuario o clave incorrectos.");
		}
		return "ingresoUsuario";
	}

	@GetMapping("/logout")
	public String logout() {
		return "index";
	}
	//--------------------------------------------------------------------------------------------	
	
	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@GetMapping("/perfil")
	public ModelAndView perfil() {
		ModelAndView mav = new ModelAndView("perfil");
		return mav;
	}
	//--------------------------------------------------------------------------------------------	
	
	// hacer un mensaje de que si se cambio el nombre de usuario se mostrara la proxima vez que ingrese
	@PostMapping("/editarUsuario")
	public String editarUsuario(ModelMap modelo ,@RequestParam String mail,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String nombreUsuario2) {

		try {
			usuarioServicio.modificar(mail,nombre,apellido,nombreUsuario2);
			
		} catch (ErrorServicio e) {
			
		
			return "/perfil";
		}
		return  "redirect:/";
	}
	//--------------------------------------------------------------------------------------------	
}