package com.egg.clubit.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.enumeraciones.EtiquetaEnum;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;

@Service 
public class EtiquetaServicio {
	@Autowired
	private EtiquetaRepositorio etiquetaRepositorio;
	
	@Transactional
	public void cargaAutomatica() throws ErrorServicio {
		try {
			for (EtiquetaEnum e : EtiquetaEnum.values()) {
				Etiqueta etiqueta= new Etiqueta();
	            System.out.println(e.getValor().toString());
				etiqueta.setNombre(e.getValor().toString());
				etiqueta.setContador(0);			
				etiquetaRepositorio.save(etiqueta);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional 
	public void contador(String nombre) {
		try {
			Etiqueta etiqueta = etiquetaRepositorio.buscarPorNombre(nombre);
			Integer contador = etiqueta.getContador() + 1;
			etiqueta.setContador(contador);
		} catch (Exception e) {
			System.out.println("No se pudo contabilizar la etiqueta");
		}	
	}
}
