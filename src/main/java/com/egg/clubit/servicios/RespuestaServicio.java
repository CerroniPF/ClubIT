package com.egg.clubit.servicios;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.entidades.Respuesta;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.PosteoRepositorio;
import com.egg.clubit.repositorios.RespuestaRepositorio;

@Service
public class RespuestaServicio {
	@Autowired
	public PosteoRepositorio posteoRepositorio;

	@Autowired
	public RespuestaRepositorio respuestaRepositorio;

	// dalta recibir datos
	@Transactional
	public void crearRespuesta(String idPost, Usuario usuarioRespuesta, String respuestaRTA) throws Exception {
		validar(respuestaRTA);
		// Preguntar cómo validar que el usuario fue logueado.
		// Para nosotros(luciano y yo) el botón de comentar no debería estar activado
		// para los no logueados
		// System.out.println("entro3");

		Posteo post = posteoRepositorio.findById(idPost).get();
		
		//System.out.println(post);
		// O desde el front se pone el botón en gris diciendo que el post está cerrado?
		if (post.getAlta() == false) {
			System.out.println("entro2");
			throw new ErrorServicio("El posteo fue cerrado y no admite más respuestas.");
		} else {
			try {
				Respuesta respuesta = new Respuesta();
				respuesta.setUsuario(usuarioRespuesta);
				respuesta.setPosteo(post);
				respuesta.setRespuesta(respuestaRTA);
				respuesta.setFechaResp(new Date());
				System.out.println("entro");
				
				respuestaRepositorio.save(respuesta);
			} catch (Exception e) {
				throw new ErrorServicio("Todos los campos son obligatorios");
			}
		}
	}
	/*
	 * @Transactional public void darBaja(String id) throws Exception {
	 * Optional<Respuesta> resp = respuestaRepositorio.findById(id); if
	 * (resp.isPresent()) { Respuesta respuesta = resp.get();
	 * respuesta.setAlta(false); } else { throw new
	 * ErrorServicio("No se encontro el post"); } }lo volamos?? VOT Si/NO
	 */

	@Transactional
	public void modificar(String idRespuesta, String rtaModificado) throws Exception {

		validar(rtaModificado);
		System.out.println("sadas");
		Optional<Respuesta> resp = respuestaRepositorio.findById(idRespuesta);
		// esto es por si respetamos el CU y no se puede auto responder hablar con loren
		// :)

		if (resp.isPresent()) {
			Respuesta respuesta = resp.get();

			respuesta.setRespuesta(rtaModificado);
			respuesta.setFechaResp(new Date());

			respuestaRepositorio.save(respuesta);

		} else {
			throw new ErrorServicio("No se pudo modificar la respuesta");
		}
	}

	public void validar(String respuesta) throws ErrorServicio {
		if (respuesta == null || respuesta.isEmpty()) {
			if (respuesta.length() > 254)
				throw new ErrorServicio("La respuesta no puede estar vacia");
		}
		if (respuesta.length() > 254) {
			throw new ErrorServicio("La respuesta tiene que ser menor a 255 caracteres");
		}
	}
}
