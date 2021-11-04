package com.egg.clubit.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.PosteoRepositorio;

@Service
public class PosteoServicio {
	
	@Autowired
	public PosteoRepositorio posteoRepositorio;

	// cambie void por listaposteo
	public List<Posteo> listarTodos() {
		return posteoRepositorio.ordenarPosteosFecha();

	}
	
	@Transactional(readOnly = true)
	public void listarPostUsuario() {
		System.out.println("***************************");
		System.out.println("***************************");
	}
	
	@Transactional(readOnly = true)
	public void listarPorUsuarioYLenguaje(String email) {
		
	}
	
	@Transactional
	public void crearPost(String titulo, String posteo, Etiqueta etiqueta, Usuario usuario) throws ErrorServicio {
	validar(titulo,posteo, etiqueta);


		try {
			Posteo post = new Posteo();
			post.setTitulo(titulo);
			post.setPosteo(posteo);
			post.setEtiqueta(etiqueta);
			post.setEditado(false);
			post.setFechaPosteo(new Date());
			post.setUsuario(usuario);
			posteoRepositorio.save(post);
		} catch (Exception e) {
			throw new ErrorServicio("Todos los campos son obligatorios");
		}
	}

	@Transactional
	public void darBaja(String id) throws Exception {
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		if (resp.isPresent()) {
			Posteo post = resp.get();
			post.setAlta(false);
		} else {
			throw new ErrorServicio("No se encontro el post");
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