package com.egg.clubit.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IngresoControlador {
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

}
