package com.egg.clubit.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String respuesta(HttpSession httpSession, ModelMap modelo, @RequestParam String idPost, @RequestParam String respuestaRTA) throws ErrorServicio    {
		Usuario usuarioRespuesta = (Usuario) httpSession.getAttribute("usersession");
		try {
			respuestaServicio.crearRespuesta(idPost, usuarioRespuesta, respuestaRTA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}
}