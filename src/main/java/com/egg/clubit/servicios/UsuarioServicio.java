package com.egg.clubit.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.egg.clubit.entidades.Usuario;
import com.egg.clubit.errorservicio.ErrorServicio;
import com.egg.clubit.repositorios.EtiquetaRepositorio;
import com.egg.clubit.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	@Autowired
	private EtiquetaServicio etiquetaServicio;



	@Transactional(readOnly = true)
	public Usuario buscarPorId(String id) {
		Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(null);
		return usuario;
	}

	@Transactional
	public void registro(String nombre, String apellido, String nombreUsuario, String mail, String contrasena,
			String contrasena2) throws ErrorServicio {
		validar(nombre, apellido, nombreUsuario, mail, contrasena, contrasena2);

		// Acá está la carga de las etiquetas
		//etiquetaServicio.cargaAutomatica();


		try {
			Usuario usuario = new Usuario();
			Usuario usuario2 = new Usuario();
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setNombreUsuario(nombreUsuario);

			usuario.setMail(mail);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			usuario.setContrasena(encoder.encode(contrasena));
			usuario.setAlta(true);
			usuario.setRolAdministrador(false);

			usuario2 = usuarioRepositorio.buscarUsuarioPorMail(mail);
			if (usuario2 == null) {
				usuarioRepositorio.save(usuario);
			} else {
				throw new ErrorServicio("El usuario ya se encuentra registrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void modificar(String mail, String nombreModificado, String apellidoModificado,
			String nombreUsuarioModificado) throws ErrorServicio {
		Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);
		String id = usuario.getId();

		List<Usuario> listaUsuario = usuarioRepositorio.findAll();
		Boolean bandera = true;
		if (nombreModificado == null || nombreModificado.isEmpty()) {
			throw new ErrorServicio("El nombre de usuario no puede quedar vacío");
		}

		if (apellidoModificado == null || apellidoModificado.isEmpty()) {
			throw new ErrorServicio("El apellido de usuario no puede quedar vacío");
		}
		if (nombreUsuarioModificado == null || nombreUsuarioModificado.isEmpty()) {
			throw new ErrorServicio("El apellido de usuario no puede quedar vacío");
		}

		for (Usuario aux : listaUsuario) {
			if (aux.getNombreUsuario().equals(nombreUsuarioModificado) && !aux.getId().equals(id)) {
				bandera = false;

				break;
			} else {
				bandera = true;
			}
		}

		if (bandera == true) {
			usuario.setNombre(nombreModificado);
			usuario.setApellido(apellidoModificado);
			usuario.setNombreUsuario(nombreUsuarioModificado);
			usuario.setMail(mail);

			usuarioRepositorio.save(usuario);

			throw new ErrorServicio("Cambios realizados exitósamente");
		} else {

			throw new ErrorServicio("El nombre de usuario ya existe");
		}
	}

	@Transactional
	public void baja(String mail) {
		/* Verificar que exista el usuario */
		try {
			System.out.println("entro");
			Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);
			usuario.setAlta(false);
			usuario.setNombre(null);
			usuario.setApellido(null);
			usuario.setNombreUsuario("dado de Baja");
			usuario.setMail(null);
			usuario.setContrasena(null);

			usuarioRepositorio.save(usuario);
		} catch (Exception e) {
			System.out.println("No se pudo dar de baja al usuario");
		}
	}

	public void validar(String nombre, String apellido, String nombreUsuario, String mail, String contrasena,
			String contrasena2) throws ErrorServicio {

		System.out.println(contrasena);
		Integer largo = contrasena.length();
		System.out.println(contrasena2);
		System.out.println(contrasena.length() + "largo");
		
		if (usuarioRepositorio.buscarUsuarioPorMail(mail) != null) {
			throw new ErrorServicio("El mail ya fue utilizado.");

		}
		
		if (mail == null || mail.isEmpty()) {
			throw new ErrorServicio("El mail no puede quedar vacío");

		}
		
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
		if (largo < 4 || largo > 16) {
			throw new ErrorServicio("La contraseña de usuario no culple las condiciones (4-16)");
		}
		// si las contraseñas son iguales guarda el ususario
		if (!contrasena.equals(contrasena2)) {
			throw new ErrorServicio("Las contraseñas no coinciden");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);
		if (usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			GrantedAuthority activo = new SimpleGrantedAuthority("ROLE_ACTIVO");
			permisos.add(activo);

			// Guardamos sus atributos
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			// session.setAttribute("usersession", usuario);
			session.setAttribute("usersession", usuario);

			User user = new User(usuario.getMail(), usuario.getContrasena(), permisos);
			return user;
		} else {

			throw new UsernameNotFoundException("El usuario no fue encontrado");
		}
	}

	@Transactional
	public void asignarRol(String idLogueado, String idReceptor) throws ErrorServicio {
		Optional<Usuario> user2 = usuarioRepositorio.findById(idReceptor);
		Optional<Usuario> user = usuarioRepositorio.findById(idLogueado);

		Usuario usuarioAdmin = user.get();
		if (usuarioAdmin.getRolAdministrador().equals(true)) {
			if (user2.isPresent()) {
				Usuario usuarioReceptor = user2.get();

				usuarioReceptor.setRolAdministrador(true);
			} else {
				throw new ErrorServicio("El usuario no existe.");
			}
		} else {

			throw new ErrorServicio("El usuario no es administrador, tocá de acá.");
		}
	}
	
	
	@Transactional
	public void asignarRol(String mail) {
		
		Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);

		if (usuario.getRolAdministrador().equals(true)) {

			usuario.setRolAdministrador(false);
			usuarioRepositorio.save(usuario);
			} else {
				usuario.setRolAdministrador(true);
				usuarioRepositorio.save(usuario);
			}
		} 
	
	
	
}