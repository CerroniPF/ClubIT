package com.egg.clubit.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;
import com.egg.clubit.servicios.PosteoServicio;
import com.egg.clubit.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class IndexControlador {
	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	PosteoServicio posteoServicio;

	@Autowired
	EtiquetaRepositorio etiquetaRepositorio;

	@GetMapping("/")
	public ModelAndView index(ModelMap model) {
		ModelAndView mav = new ModelAndView("index");

		List<Posteo> posteo = posteoServicio.listarTodos();
		List<Etiqueta> etiquetas = etiquetaRepositorio.findAll();
		mav.addObject("posteos", posteo);
		mav.addObject("etiquetas", etiquetas);
		
		return mav;
	}

	@GetMapping("/buscar")
	public ModelAndView mostrarPalabraClave(Model model, @RequestParam String titulo, @RequestParam String etiqueta)
			throws ErrorServicio {

		ModelAndView mav = new ModelAndView("index");
		List<Posteo> posteo = posteoServicio.busquedaAvanzada(titulo, etiqueta);
		// IMPORTANTE NO BORRAR(luciano)
		List<Etiqueta> etiquetas = etiquetaRepositorio.findAll();
		mav.addObject("etiquetas", etiquetas);
		mav.addObject("posteos", posteo);

		return mav;
	}
	
}
