package com.egg.clubit.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Posteo;

@Repository
public interface PosteoRepositorio extends JpaRepository<Posteo, String> {
	
}
