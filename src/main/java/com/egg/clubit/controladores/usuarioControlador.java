package com.egg.clubit.controladores;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;
import com.egg.clubit.servicios.RespuestaServicio;
import com.egg.clubit.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class usuarioControlador {
	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	RespuestaServicio respuestaServicio;

	@Autowired
	EtiquetaRepositorio etiquetaRepositorio;

	// --------------------------------------------------------------------------------------------
	@ModelAttribute
	public void addAttributes(Model modelo) {
		List<Etiqueta> listaEtiquetas = etiquetaRepositorio.findAll();
		modelo.addAttribute("etiquetas", listaEtiquetas);
	}

	@GetMapping("/registro")
	public ModelAndView registro(Model modelo) {
		ModelAndView mav = new ModelAndView("registroUsuario");

		return mav;
	}

	@PostMapping("/registro")
	public String registroUsuario(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido,
			@RequestParam String nombreUsuario, @RequestParam String mail, @RequestParam String pass,
			@RequestParam String pass2) throws ErrorServicio {

		try {
			usuarioServicio.registro(nombre, apellido, nombreUsuario, mail, pass, pass2);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());

			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("nombreUsuario", nombreUsuario);
			modelo.put("mail", mail);
			modelo.put("registroMensaje", e.getMessage());

			return "/registroUsuario";
		}

		return "redirect:/login?mensajeRegistro=mensajeRegistro";
	}

	// --------------------------------------------------------------------------------------------

	@GetMapping("/ingreso")
	public ModelAndView ingreso() {
		ModelAndView mav = new ModelAndView("ingresoUsuario");
		
		return mav;
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpSession httpSession,
			@RequestParam(required = false) String error, @RequestParam(required = false) String mensajeRegistro,
			Model model) {

		if (error != null) {
			model.addAttribute("error", "Nombre de usuario o clave incorrectos");
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		}
		if (mensajeRegistro != null) {
			model.addAttribute("mensajeRegistro", "Registro exitoso");
		}
		
		return "ingresoUsuario";
	}

	@GetMapping("/logout")
	public String logout() {
		return "index";
	}
	// --------------------------------------------------------------------------------------------

	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@GetMapping("/perfil")
	public ModelAndView perfil() {
		ModelAndView mav = new ModelAndView("perfil");
		return mav;
	}
	// --------------------------------------------------------------------------------------------

	@PostMapping("/editarUsuario")
	public String editarUsuario(HttpServletRequest request, ModelMap modelo, @RequestParam String mail,
			@RequestParam String nombre, @RequestParam String apellido, @RequestParam String nombreUsuario2) {

		try {
			usuarioServicio.modificar(mail, nombre, apellido, nombreUsuario2);


		} catch (ErrorServicio e) {
			if (e.getMessage().equals("El nombre de usuario ya existe")) {
				modelo.put("error", e.getMessage());

				return "/perfil";
			} else {

				modelo.put("exito", e.getMessage());
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				usuarioServicio.loadUserByUsername(mail);

				return "/perfil";
			}
		}
		
		return "redirect:/";
	}

	// --------------------------------------------------------------------------------------------

	@PostMapping("/perfil")
	public String bajaUsuario(HttpServletRequest request, @RequestParam String mail) {

		usuarioServicio.baja(mail);
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}