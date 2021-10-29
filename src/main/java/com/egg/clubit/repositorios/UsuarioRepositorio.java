package com.egg.clubit.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, String> {
	
	@Query("SELECT u FROM Usuario u WHERE u.mail LIKE: mail")
	public Usuario buscarPorUsuario(@Param("mail") String mail);
	
	@Query("SELECT n FROM Usuario n WHERE n.nombreUsuario LIKE: nombreUsuario")
	public Usuario buscarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);
	
}
