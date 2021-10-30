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
	public void registro(String nombre, String apellido, String nombreUsuario, String mail, String pass,
			String pass2) throws ErrorServicio {
		validar(nombre, apellido, nombreUsuario, mail, pass, pass2);

		try {
			Usuario usuario = new Usuario();
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setNombreUsuario(nombreUsuario);

			usuario.setMail(mail);
			usuario.setContrasena(pass);
			usuario.setAlta(true);
			usuario.setRolAdministrador(false);

			
			System.out.println("entro");
			if (!usuarioRepositorio.buscarPorUsuario(mail).equals(mail)) {
				usuarioRepositorio.save(usuario);
			} else {
				throw new ErrorServicio("El usuario ya se encuentra registrado");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void ingreso(String mail, String contrasena) throws ErrorServicio {

		Usuario user = usuarioRepositorio.buscarPorUsuario(mail);
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
		if (mail == null || mail.isEmpty()) {
			throw new ErrorServicio("El mail de usuario no puede quedar vacío");

		}
		if (contrasena == null || contrasena.isEmpty() || contrasena.length() > 4 || contrasena.length() < 16) {
			throw new ErrorServicio("La contraseña de usuario no puede quedar vacío");
		}
		if (contrasena.equals(contrasena2)) {
			throw new ErrorServicio("Las contraseñas no coinciden");
		}

	}

}