package com.egg.clubit.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.enumeraciones.EtiquetaEnum;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;
import com.egg.clubit.repositorios.UsuarioRepositorio;

@Service 
public class EtiquetaServicio {
	@Autowired
	private EtiquetaRepositorio etiquetaRepositorio;
	private EtiquetaEnum etiquetaEnum;
	/*queda en global por si queremos hacer la modificación por el front*/
	
	@Transactional
	public void cargaAutomatica() throws ErrorServicio {
		try {
			
			for (EtiquetaEnum e : EtiquetaEnum.values()) {

				Etiqueta etiqueta= new Etiqueta();
				//e.getValor().toString();
	            System.out.println(e.getValor().toString());
				etiqueta.setNombre(e.getValor().toString());
				etiqueta.setContador(0);			
				etiquetaRepositorio.save(etiqueta);	
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*Si queda tiempo, hacemos la carga por el front, sino a mano*/

	@Transactional 
	public void contador(String nombre) {
		try {
			Etiqueta etiqueta= etiquetaRepositorio.buscarPorNombre(nombre);
			Integer contador=etiqueta.getContador()+1;
			etiqueta.setContador(contador);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	
	
	

}
