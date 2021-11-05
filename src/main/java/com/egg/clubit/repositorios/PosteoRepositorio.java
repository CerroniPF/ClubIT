package com.egg.clubit.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Posteo;

@Repository
public interface PosteoRepositorio extends JpaRepository<Posteo, String> {
	
//	@Query(value="SELECT u FROM Usuario u WHERE u.mail LIKE :mail")
//	public Usuario buscarUsuarioPorMail(@Param("mail") String mail);
//	
//	@Query(value="SELECT p FROM Posteo p INNER JOIN Usuario u ON u.id = u WHERE p.id LIKE :id2")
//	public List<Posteo> listarPostUsuario(@Param("id2") String id2);


	@Query(value= "SELECT p FROM Posteo p ORDER BY  p.fechaPosteo DESC  ")
	public List<Posteo> ordenarPosteosFecha();
	

	@Query(value="SELECT p FROM Posteo p WHERE p.titulo LIKE %:palabraClave%")
	public List<Posteo> buscarPorPalabraClave(@Param("palabraClave") String palabraClave);
	
	@Query(value="SELECT p FROM Posteo p WHERE p.titulo LIKE %:etiqueta%")
	public List<Posteo> buscarPorLenguaje(@Param("etiqueta") String etiqueta);
}

