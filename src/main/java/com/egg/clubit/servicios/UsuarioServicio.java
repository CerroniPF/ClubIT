package com.egg.clubit.servicios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.UsuarioRepositorio;


@Service
public class UsuarioServicio {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Transactional
	public void registro(String nombre, String apellido, String nombreUsuario, String mail, String contrasena,
			String contrasena2) throws ErrorServicio {
		validar(nombre, apellido, nombreUsuario, mail, contrasena, contrasena2); 

		try {
			Usuario usuario = new Usuario();
			Usuario usuario2 = new Usuario();
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setNombreUsuario(nombreUsuario);

			usuario.setMail(mail);
			usuario.setContrasena(contrasena);
			usuario.setAlta(true);
			usuario.setRolAdministrador(false);

			
		//	System.out.println(usuario2=buscarPorMail(mail));
			
			
			usuario2=buscarPorMail(mail);
			if (usuario2==null) {
				usuarioRepositorio.save(usuario);
				} else {
				throw new ErrorServicio("El usuario ya se encuentra registrado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ingreso(String mail, String contrasena) throws ErrorServicio {

		Usuario user = usuarioRepositorio.buscarUsuarioPorMail(mail);
		if (user.getMail().equals(mail)) {

			if (user.getContrasena().equals(contrasena)) {
				/* Ver qué hacer. IngresoControlador; */

			} else {
				throw new ErrorServicio("La contraseña es incorrecta");
			}
		} else {
			throw new ErrorServicio("El usuario no existe");
		}

	}

	public void modificar(String nombreUsuario, String nombreUsuarioNuevo) throws ErrorServicio {

		Usuario usuario = usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario);

		if (usuarioRepositorio.buscarPorNombreUsuario(nombreUsuarioNuevo) == null) {
			usuario.setNombreUsuario(nombreUsuarioNuevo);
			usuarioRepositorio.save(usuario);

		} else {
			throw new ErrorServicio("El nombre de usuario ya existe");
		}

	}

	public void baja(String nombreUsuario) {
		/* Verificar que exista el usuario */
		try {

			Usuario usuario = usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario);
			usuario.setAlta(false);
			usuarioRepositorio.save(usuario);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void validar(String nombre, String apellido, String nombreUsuario, String mail, String contrasena,
			String contrasena2) throws ErrorServicio {
		
	
		System.out.println(contrasena);
		Integer largo=contrasena.length();
		System.out.println(contrasena2);
		System.out.println(contrasena.length()+"largo");
		

		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre de usuario no puede quedar vacío");

		}
		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("El apellido de usuario no puede quedar vacío");

		}
		if (nombreUsuario == null || nombreUsuario.isEmpty()) {
			throw new ErrorServicio("El nombre de usuario no puede quedar vacío");

		}
		if (usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario) != null) {
			throw new ErrorServicio("El nombre de usuario ya existe");

		}
		// el mail == null creo que no hace nada
		if (mail == null || mail.isEmpty()) {
			throw new ErrorServicio("El mail de usuario no puede quedar vacío");

		}
	
		if (   largo < 4 || largo > 16 ) { 
			throw new ErrorServicio("La contraseña de usuario no culple las condiciones (4-16)");
		}
		// si las contraseñas son iguales guarda el ususario
		if (!contrasena.equals(contrasena2)) {
			throw new ErrorServicio("Las contraseñas no coinciden");
		}

	}
	
	public Usuario buscarPorMail(String mail) {
		
		Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);

		return usuario;
	}

}