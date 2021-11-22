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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Posteo;

import com.egg.clubit.entidades.Usuario;


import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;
import com.egg.clubit.repositorios.UsuarioRepositorio;
import com.egg.clubit.servicios.PosteoServicio;
import com.egg.clubit.servicios.RespuestaServicio;
import com.egg.clubit.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class IndexControlador {
	@Autowired
	UsuarioServicio usuarioServicio;

	@Autowired
	PosteoServicio posteoServicio;

	@Autowired
	RespuestaServicio respuestaServicio;

	@Autowired
	EtiquetaRepositorio etiquetaRepositorio;

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@GetMapping("/")
	public ModelAndView index(ModelMap model) {
		ModelAndView mav = new ModelAndView("index");

		List<Usuario> usuarios = usuarioRepositorio.findAll();

		List<Posteo> posteo = posteoServicio.listarActivos();
		List<Etiqueta> etiquetas = etiquetaRepositorio.findAll();
		mav.addObject("posteos", posteo);
		mav.addObject("etiquetas", etiquetas);
		mav.addObject("usuarios", usuarios);
		return mav;
	}
//	-------------------------------------------------------------------------------------------------------------------------

	@GetMapping("/mostrarTodos")
	public ModelAndView adminMostrarTodos(ModelMap model) {
		ModelAndView mav = new ModelAndView("adminMostrarTodos");

		List<Usuario> usuarios = usuarioRepositorio.findAll();

		List<Posteo> posteo = posteoServicio.listarTodos();
		List<Etiqueta> etiquetas = etiquetaRepositorio.findAll();
		mav.addObject("posteos", posteo);
		mav.addObject("etiquetas", etiquetas);
		mav.addObject("usuarios", usuarios);
		return mav;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	
	@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	@GetMapping("/admin")
	public ModelAndView admin(ModelMap model,HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("admin");
		List<Etiqueta> etiquetas = etiquetaRepositorio.findAll();
		List<Usuario> usuarios = usuarioRepositorio.findAll();
		mav.addObject("etiquetas", etiquetas);
		mav.addObject("usuarios", usuarios);
		Usuario usuario = (Usuario) httpSession.getAttribute("usersession");
		if(!usuario.getRolAdministrador()) {
			ModelAndView mavi = new ModelAndView("redirect:/");
			return mavi;
		}
		return mav;
	}
	
	
	@PostMapping("/borrarUsuarioAdmin")
	public String bajaUsuario( @RequestParam String mail) {
		System.out.println("asdsadas");
		usuarioServicio.baja(mail);
		
		return "redirect:/";
	}
//	-------------------------------------------------------------------------------------------------------------------------
	
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
//	-------------------------------------------------------------------------------------------------------------------------

	@PostMapping("/darAdmin")
	public String darAdmin( @RequestParam String mail) {
		usuarioServicio.asignarRol(mail);
		
		return "redirect:/admin";
	}
//	-------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/cerrarPosteo")
	public String cerrarPosteo( @RequestParam String id ) throws Exception {
		System.out.println(id+ "id");

		posteoServicio.cerrarPost(id);
		
		return "redirect:/";
	}
//	-------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/bajaPost")
	public String bajaPost( @RequestParam String id ) throws Exception {
		posteoServicio.darBaja(id);
		return "redirect:/";
	}
	//-------------------------------------------------------------------------------------------------------------------------
	@PostMapping("/altaPost")
	public String altaPost( @RequestParam String id ) throws Exception {
		posteoServicio.darAlta(id);
		return "redirect:/";
	}
//-------------------------------------------------------------------------------------------------------------------------
@PostMapping("/bajaRespuesta")
public String bajaRespuesta( @RequestParam String id) throws Exception {
	respuestaServicio.darBaja(id);
	return "redirect:/";
	//ver redirect

 }



//-------------------------------------------------------------------------------------------------------------------------
@PostMapping("/altaRespuesta")
public String altaRespuesta( @RequestParam String id ) throws Exception {
	respuestaServicio.darAlta(id);
	
	return "redirect:/";
  }
}
