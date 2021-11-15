package com.egg.clubit.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.entidades.Respuesta;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.PosteoRepositorio;
import com.egg.clubit.repositorios.UsuarioRepositorio;

@Service
public class PosteoServicio {
	
	@Autowired
	public PosteoRepositorio posteoRepositorio;
	@Autowired
	public UsuarioRepositorio usuarioRepositorio;

	// cambie void por listaposteo
	public List<Posteo> listarTodos() {
		return posteoRepositorio.ordenarPosteosFecha();


	}
	
	
	@Transactional(readOnly = true)
	public void listarPostUsuario() {
		//esto lo resolvió lorenzo, lo borramos o no? VOT SI/NO.
	}

	
	
	@Transactional(readOnly = true)
	public List<Posteo> listarPorPalabraClave(String palabraClave) throws ErrorServicio {
	
		try {
			List<Posteo> listarPosteoPalabraClave=posteoRepositorio.buscarPorPalabraClave(palabraClave);
			return listarPosteoPalabraClave;
		} catch (Exception e) {
		throw new ErrorServicio("No se encontró ningún post:(");
		}
	 
	}
	
	@Transactional(readOnly = true)
	public List<Posteo> listarPorLenguaje(String etiqueta) {
		List<Posteo> listarPorLenguaje= posteoRepositorio.buscarPorLenguaje(etiqueta);
		return listarPorLenguaje;
	}
	
	@Transactional
	public void crearPost(String titulo, String posteo, Etiqueta etiqueta, Usuario usuario) throws ErrorServicio {
	validar(titulo,posteo, etiqueta);
	

		try {
			Posteo post = new Posteo();
			post.setTitulo(titulo);
			post.setPosteo(posteo);
			post.setEtiqueta(etiqueta);
		
			post.setEditado(true);
			post.setFechaPosteo(new Date());
			post.setUsuario(usuario);
			posteoRepositorio.save(post);
		} catch (Exception e) {
			throw new ErrorServicio("Todos los campos son obligatorios");
		}
	}

	public Posteo buscarPorId(String id) {
		return posteoRepositorio.getById(id);
	}
	
	
	@Transactional
	public void darBaja(String id, String idLogueado) throws Exception {
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		Optional<Usuario> user = usuarioRepositorio.findById(idLogueado);
		Usuario usuario = user.get();
		if (usuario.getRolAdministrador().equals(true)) {
			if (resp.isPresent()) {
				Posteo post = resp.get();

				post.setAlta(2);
			} else {
				throw new ErrorServicio("No se encontro el post");
			}
		
		}else {
			if (resp.isPresent()) {
				Posteo post = resp.get();

				post.setAlta(0);
			} else {
				throw new ErrorServicio("No se encontro el post");
			}
			
		}
		
	}
	
	@Transactional
	public void modificar(String id, String titulo, String posteo, Etiqueta etiqueta) throws Exception {		
		
		validar(titulo,posteo, etiqueta);
		
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		
		if (resp.isPresent()) {
			Posteo post = resp.get();
			
			post.setTitulo(titulo);
			post.setPosteo(posteo);
			post.setEtiqueta(etiqueta);
			post.setEditado(true);				/* Este atributo se agrego para identificar si el post fue editado desde el .html */
			post.setFechaPosteo(new Date());
			
			
			posteoRepositorio.save(post);
		} else {
			throw new ErrorServicio("No se pudo modificar el post");
		}
	}	
	
	public void validar( String titulo, String posteo, Etiqueta etiqueta) throws ErrorServicio {
		System.out.println("gatito");

		if (titulo == null || titulo.isEmpty()) {
			System.out.println("gato");
			throw new ErrorServicio("El titulo no puede quedar vacío");
			

		}
		if (posteo == null || posteo.isEmpty()) {
			System.out.println("posteo");
			throw new ErrorServicio("El posteo no puede quedar vacío");

		}
//		if (etiqueta == null) {
//			System.out.println("etiquera");
//			throw new ErrorServicio("La etiqueta no puede quedar vacío");
//
//		}
		
		}

	
	
	
	
}