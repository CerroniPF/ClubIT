package com.egg.clubit.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.clubit.entidades.Respuesta;

@Repository
public interface RespuestaRepositorio extends JpaRepository <Respuesta, String> {

}
