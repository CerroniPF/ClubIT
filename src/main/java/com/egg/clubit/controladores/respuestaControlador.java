package com.egg.clubit.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.servicios.PosteoServicio;
import com.egg.clubit.servicios.RespuestaServicio;

@Controller
@RequestMapping("/")
public class respuestaControlador {
	@Autowired
	RespuestaServicio respuestaServicio;

	@Autowired
	PosteoServicio posteoServicio;

	@PostMapping("/posteo/{id}/respuesta")
	public RedirectView respuesta(HttpSession httpSession, ModelMap modelo, @RequestParam String idPost, @RequestParam String respuestaRTA) throws ErrorServicio    {
		
		RedirectView rv = new RedirectView();
		Usuario usuarioRespuesta = (Usuario) httpSession.getAttribute("usersession");
		String id = idPost;
		try {
			respuestaServicio.crearRespuesta(idPost, usuarioRespuesta, respuestaRTA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rv.setUrl("/posteo/" + id );

		return rv;
	}
}