package com.egg.clubit.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.servicios.PosteoServicio;
import com.egg.clubit.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class IndexControlador {
	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	PosteoServicio posteoServicio;

	@GetMapping("/")
	public ModelAndView index(Model model) {
		ModelAndView mav = new ModelAndView("index");
		List<Posteo> posteo=posteoServicio.listarTodos();
		mav.addObject("posteos", posteo);
		return mav;
	}

	
	@GetMapping("/posteos/{id}")
	public String posteos(Model model, @PathVariable String id)  {
		Usuario usuario = usuarioServicio.buscarPorId(id);

		List<Posteo> posteos = usuario.getPost();
		model.addAttribute("posteos", posteos);

		return "prueba";
	}

	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@GetMapping("/crearPost")
	public ModelAndView crearPost(HttpSession httpSession) {
		
//		Usuario usuario = (Usuario) httpSession.getAttribute("usersession");
//		if(usuario == null) {
//			ModelAndView mav = new ModelAndView("ingresoUsuario");
//			return mav;
//		}
		
		ModelAndView mav = new ModelAndView("crearPosteo");
		return mav;
	}

	
	// activar en el validar la etiqueta y poner aca la atiqueta pasada por parrametro
	
	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@PostMapping("/crearPost")
	public RedirectView crearPostMetodoPost(Model modelo,
			HttpSession httpSession, 
			@RequestParam String titulo,
			@RequestParam String posteo) throws ErrorServicio {
		RedirectView rv = new RedirectView();
		String id2 ="";
		Usuario usuario = (Usuario) httpSession.getAttribute("usersession");
		
		if(usuario == null) {
			rv.setUrl("redirect:/");
			return rv;
		}
		try {
		///////ACA HAY QUE BUSCAR LA ETIQUETA CON ETIQUETASERVICIO
		////Etiqueta etiqueta1 = new Etiqueta(); CAMBIAR ESTA LÍNEA DE CÓDIGO
			
			posteoServicio.crearPost(titulo, posteo, null, usuario);
			id2 = usuario.getId();
		} catch (ErrorServicio e) {
			modelo.addAttribute("error", e.getMessage());
			modelo.addAttribute("titulo", titulo);
			modelo.addAttribute("posteo", posteo);
			//modelo.addAttribute("etiqueta", etiqueta);
			rv.setUrl("redirect:/");
			return rv;
		}
		rv.setUrl("posteos/"+ id2);
		return rv;
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@GetMapping("/perfil")
	public ModelAndView perfil() {
		ModelAndView mav = new ModelAndView("perfil");
		return mav;
	}

	@GetMapping("/verPosteo/{id}")
	public ModelAndView verPosteo(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("verPosteo");
		return mav;
	}
}
