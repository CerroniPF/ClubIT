package com.egg.clubit.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Etiqueta;
import com.egg.clubit.entidades.Posteo;

@Repository
public interface PosteoRepositorio extends JpaRepository<Posteo, String> {
	@Query(value= "SELECT p FROM Posteo p ORDER BY  p.fechaPosteo DESC")
	public List<Posteo> ordenarPosteosFecha();
	
	@Query(value= "SELECT p FROM Posteo p WHERE p.alta <> 2 ORDER BY  p.fechaPosteo DESC")
	public List<Posteo> ordenarPosteosFechaActivos();
	
	@Query(value="SELECT p FROM Posteo p WHERE p.titulo LIKE %:palabraClave% ORDER BY p.fechaPosteo DESC")
	public List<Posteo> buscarPorPalabraClave(@Param("palabraClave") String palabraClave);
	
	@Query(value="SELECT * FROM Posteo p WHERE p.titulo LIKE %:palabraClave% AND p.etiqueta_id LIKE :etiquetaId ORDER BY p.fecha_posteo DESC" ,  nativeQuery = true )
	public List<Posteo> busquedaAvanzada(@Param("palabraClave") String palabraClave, @Param("etiquetaId") String etiquetaId);
	

}

