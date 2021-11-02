package com.egg.clubit.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Posteo;

@Repository
public interface PosteoRepositorio extends JpaRepository<Posteo, String> {
	
//	@Query(value="SELECT p FROM Posteo p INNER JOIN p.usuario_post ON p.id = up.post_id WHERE up.usuario_id LIKE 'b55682ec-bd2a-4316-b1ce-c6092b425a01'")
//	public List<Posteo> listarPostUsuario();
	//public Lista<Posteo> listarPostUsuario(@Param("mail") String mail);
}
