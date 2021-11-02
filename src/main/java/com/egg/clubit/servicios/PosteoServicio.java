package com.egg.clubit.servicios;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Posteo;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.PosteoRepositorio;

@Service
public class PosteoServicio {
	@Autowired
	public PosteoRepositorio posteoRepositorio;

	public void listarTodos() {
		
	}
	
	
	public void listarPostUsuario() {
		System.out.println("***************************");
		//System.out.println(posteoRepositorio.listarPostUsuario());
		System.out.println("***************************");
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	public void listarPorUsuarioYLenguaje(String email) {
		
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	public void crearPost(String titulo, String posteo, Etiqueta etiqueta) throws Exception {
		//validar();
		
		try {
			Posteo post = new Posteo();
			post.setTitulo(titulo);
			post.setPosteo(posteo);
			post.setEtiqueta(etiqueta);
			post.setEditado(false);
			post.setFechaPosteo(new Date());
			
			posteoRepositorio.save(post);
		} catch (Exception e) {
			throw new ErrorServicio("Todos los campos son obligatorios");
		}
	}

	//@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	public void darBaja(String id) throws Exception {
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		if (resp.isPresent()) {
			Posteo post = resp.get();
			post.setAlta(false);
		} else {
			throw new ErrorServicio("No se encontro el post");
		}
	}
	
	//@PreAuthorize("hasAnyRole('ROLE_ACTIVO')")
	public void modificar(String id, String titulo, String posteo, Etiqueta etiqueta) throws Exception {
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
}