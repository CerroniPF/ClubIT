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
import com.egg.clubit.repositorios.UsuarioRepositorio;

@Service
public class PosteoServicio {
	@Autowired
	private PosteoRepositorio posteoRepositorio;

	@Autowired
	private EtiquetaServicio etiquetaServicio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public List<Posteo> listarTodos() {
		return posteoRepositorio.ordenarPosteosFecha();
	}

	public List<Posteo> listarActivos() {
		return posteoRepositorio.ordenarPosteosFechaActivos();
	}


	@Transactional(readOnly = true)
	public List<Posteo> busquedaAvanzada(String palabraClave, String idEtiqueta) {
		List<Posteo> listaResultado = posteoRepositorio.busquedaAvanzada(palabraClave,idEtiqueta);
		
		if(!palabraClave.equals("")){
			listaResultado = posteoRepositorio.busquedaAvanzada(palabraClave,idEtiqueta);
			
		}
		
		if(idEtiqueta.equals("Todos")) {
			listaResultado = posteoRepositorio.buscarPorPalabraClave (palabraClave);
		}
		
		return listaResultado ;
	}


	@Transactional
	public void crearPost(String titulo, String posteo, Etiqueta etiqueta, Usuario usuario) throws ErrorServicio {
		validar(titulo, posteo, etiqueta);

		try {
			Posteo post = new Posteo();
			post.setTitulo(titulo);
			post.setPosteo(posteo);
			post.setEtiqueta(etiqueta);
			post.setEditado(false);
			post.setFechaPosteo(new Date());
			post.setUsuario(usuario);
			etiquetaServicio.contador(etiqueta.getNombre());
			post.setAlta(1);

			posteoRepositorio.save(post);
		} catch (Exception e) {
			throw new ErrorServicio("Todos los campos son obligatorios");
		}
	}

	@Transactional(readOnly = true)
	public Posteo buscarPorId(String id) {
		return posteoRepositorio.getById(id);
	}

	@Transactional
	public void cerrarPost(String id) throws Exception {
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		
		
			if (resp.isPresent()) {
				Posteo post = resp.get();
				post.setAlta(0);
				post.setId(id);
				
				posteoRepositorio.save(post);

			} else {
				throw new ErrorServicio("No se encontro el post");
			}
	}
	
	@Transactional
	public void darAlta(String id) throws Exception {
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		
		
			if (resp.isPresent()) {
				Posteo post = resp.get();
				post.setAlta(1);
				posteoRepositorio.save(post);

			} else {
				throw new ErrorServicio("No se encontro el post");
			}
	}
	
	@Transactional
	public void darBaja(String id) throws Exception {
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		
		
			if (resp.isPresent()) {
				Posteo post = resp.get();
				post.setAlta(2);
				posteoRepositorio.save(post);

			} else {
				throw new ErrorServicio("No se encontro el post");
			}
	}

	@Transactional
	public void modificar(String id, String titulo, String posteo, Etiqueta etiqueta) throws Exception {
		validar(titulo, posteo, etiqueta);
		
		Optional<Posteo> resp = posteoRepositorio.findById(id);
		if (resp.isPresent()) {
			Posteo post = resp.get();

			post.setTitulo(titulo);
			post.setPosteo(posteo);
			post.setEtiqueta(etiqueta);
			post.setEditado(true); /* Este atributo se agrego para identificar si el post fue editado desde el .html */
			post.setFechaPosteo(new Date());

			posteoRepositorio.save(post);
		} else {
			throw new ErrorServicio("No se pudo modificar el post");
		}
	}

	public void validar(String titulo, String posteo, Etiqueta etiqueta) throws ErrorServicio {

		

		if (titulo == null || titulo.isEmpty()) {
			

			throw new ErrorServicio("El titulo no puede quedar vacío");
		}

		if (posteo == null || posteo.isEmpty()) {
			

			throw new ErrorServicio("El posteo no puede quedar vacío");
		}
		if (etiqueta == null) {
			throw new ErrorServicio("La etiqueta no puede quedar vacía");
		}
	}
}