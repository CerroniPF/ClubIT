package com.egg.clubit.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, String> {

}
