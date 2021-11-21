package com.egg.clubit.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.entidades.Respuesta;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;
import com.egg.clubit.repositorios.RespuestaRepositorio;
import com.egg.clubit.servicios.PosteoServicio;
import com.egg.clubit.servicios.RespuestaServicio;
import com.egg.clubit.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class posteoControlador {
	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	PosteoServicio posteoServicio;

	@Autowired
	RespuestaServicio respuestaServicio;

	@Autowired
	EtiquetaRepositorio etiquetaRepositorio;

	@Autowired
	RespuestaRepositorio respuestaRepositorio;

//--------------------------------------------------------------------------------------------	
	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@GetMapping("/crearPost")
	public ModelAndView crearPost(Model modelo, HttpSession httpSession) {

		ModelAndView mav = new ModelAndView("crearPosteo");
		return mav;
	}

	/*
	 * Activar en el validar en la etiqueta y poner aca la atiqueta pasada por
	 * parametro
	 */
	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@PostMapping("/crearPost")
	public RedirectView crearPostMetodoPost(Model modelo, HttpSession httpSession, @RequestParam String titulo,
			@RequestParam Etiqueta etiqueta, @RequestParam String posteo) throws ErrorServicio {
		RedirectView rv = new RedirectView();
		Usuario usuario = (Usuario) httpSession.getAttribute("usersession");

		if (usuario == null) {
			rv.setUrl("redirect:/");
			return rv;
		}
		try {
			posteoServicio.crearPost(titulo, posteo, etiqueta, usuario);
		} catch (ErrorServicio e) {
			modelo.addAttribute("error", e.getMessage());
			modelo.addAttribute("titulo", titulo);
			modelo.addAttribute("posteo", posteo);
			rv.setUrl("redirect:/");
			return rv;
		}
		rv.setUrl("/");
		return rv;
	}

	@ModelAttribute
	public void addAttributes(Model modelo) {
		List<Etiqueta> listaEtiquetas = etiquetaRepositorio.findAll();
		modelo.addAttribute("etiquetas", listaEtiquetas);
	}
//--------------------------------------------------------------------------------------------	

	/* ESTE MÉTODO MUESTRA 1 SOLO POSTEO */
	@GetMapping("/posteo/{id}")
	public String posteo(Model modelo, @PathVariable String id) {

		Posteo posteo = posteoServicio.buscarPorId(id);
		modelo.addAttribute("posteo", posteo);
		List<Respuesta> listaRespuestaOrdenada=respuestaRepositorio.ordenarRespuesta(id);
		modelo.addAttribute("respuestas", listaRespuestaOrdenada);
		System.out.println();
		
			return "mostrarPosteo";
		}
	
//--------------------------------------------------------------------------------------------
}