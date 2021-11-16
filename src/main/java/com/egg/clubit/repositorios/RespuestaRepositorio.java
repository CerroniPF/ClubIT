package com.egg.clubit.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.egg.clubit.entidades.Respuesta;

@Repository
public interface RespuestaRepositorio extends JpaRepository <Respuesta, String> {

	
	@Query(value= "SELECT * FROM respuesta r WHERE r.posteo_id LIKE :idPost ORDER BY  r.fecha_resp ASC",  nativeQuery = true )
	public List<Respuesta> ordenarRespuesta(@Param("idPost") String idPost);
	

}
