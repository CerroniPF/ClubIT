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
import com.egg.clubit.repositorios.UsuarioRepositorio;

@Service
public class RespuestaServicio {
	@Autowired
	private PosteoRepositorio posteoRepositorio;

	@Autowired
	private RespuestaRepositorio respuestaRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public void crearRespuesta(String idPost, Usuario usuarioRespuesta, String respuestaRTA) throws Exception {
		validar(respuestaRTA);

		Posteo post = posteoRepositorio.findById(idPost).get();


		if (post.getAlta() == 0) {

			throw new ErrorServicio("El posteo fue cerrado y no admite más respuestas");
		} else {
			try {
				Respuesta respuesta = new Respuesta();

				respuesta.setUsuario(usuarioRespuesta);
				respuesta.setPosteo(post);
				respuesta.setRespuesta(respuestaRTA);
				respuesta.setFechaResp(new Date());
				respuesta.setAlta(1);

				respuestaRepositorio.save(respuesta);
			} catch (Exception e) {
				throw new ErrorServicio("Todos los campos son obligatorios");
			}
		}
	}

	@Transactional
	public void darBaja(String id, String idLogueado) throws Exception {
		Optional<Respuesta> resp = respuestaRepositorio.findById(id);
		Optional<Usuario> user = usuarioRepositorio.findById(idLogueado);

		Usuario usuario = user.get();
		if (usuario.getRolAdministrador().equals(true)) {
			if (resp.isPresent()) {
				Respuesta respuesta = resp.get();
				respuesta.setAlta(2);
			} else {
				throw new ErrorServicio("No se encontro la respuesta");
			}
		} else {
			if (resp.isPresent()) {
				Respuesta respuesta = resp.get();

				respuesta.setAlta(0);
			} else {
				throw new ErrorServicio("No se encontro la respuesta");
			}
		}
	}


	@Transactional
	public void modificar(String idRespuesta, String rtaModificado) throws Exception {
		validar(rtaModificado);

		Optional<Respuesta> resp = respuestaRepositorio.findById(idRespuesta);

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
			if (respuesta.length() < 1)
				throw new ErrorServicio("La respuesta no puede estar vacia");
		}
		if (respuesta.length() > 10000) {
			throw new ErrorServicio("La respuesta tiene que ser menor a 10000 caracteres!!!");
		}
	}
}
