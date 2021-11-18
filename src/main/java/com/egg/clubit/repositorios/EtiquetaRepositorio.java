package com.egg.clubit.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Etiqueta;

@Repository
public interface EtiquetaRepositorio extends JpaRepository<Etiqueta, String> {
	@Query(value="SELECT e FROM Etiqueta e WHERE e.nombre LIKE :nombre ")
	public Etiqueta buscarPorNombre(@Param("nombre") String nombre);
}