package com.egg.clubit.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Etiqueta;

@Repository
public interface EtiquetaRepositorio extends JpaRepository<Etiqueta, String> {
	
}
