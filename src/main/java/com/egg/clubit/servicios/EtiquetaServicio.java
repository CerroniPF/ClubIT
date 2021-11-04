package com.egg.clubit.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;
import com.egg.clubit.repositorios.UsuarioRepositorio;

@Service 
public class EtiquetaServicio {
	@Autowired
	private EtiquetaRepositorio etiquetaRepositorio;
	
	@Transactional
	public void registro(String nombre) throws ErrorServicio {
		try {
			Etiqueta etiqueta= new Etiqueta();
			etiqueta.setNombre(nombre);
			etiqueta.setContador(0);
			
			etiquetaRepositorio.save(etiqueta);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Transactional	
	public void contador() throws ErrorServicio{
		
		for (iterable_type iterable_element : iterable) {
			
		}
		
	}
	
	

}
