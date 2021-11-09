package com.egg.clubit.servicios;

import java.util.ArrayList;
import java.util.List;

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
import com.egg.clubit.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PosteoServicio posteoServicio;

	@Autowired
	private EtiquetaServicio etiquetaServicio;
	
	@Transactional(readOnly = true)
	public Usuario buscarPorId (String id) {
		Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(null);
		return usuario;
	}
	
	@Transactional
	public void registro(String nombre, String apellido, String nombreUsuario, String mail, String contrasena,
			String contrasena2) throws ErrorServicio {
		validar(nombre, apellido, nombreUsuario, mail, contrasena, contrasena2);
		//acá está la carga de las etiquetas
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

			// System.out.println(usuario2=buscarPorMail(mail));
			
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

//	public void ingreso(String mail, String contrasena) throws ErrorServicio {
//
//		Usuario user = usuarioRepositorio.buscarUsuarioPorMail(mail);
//		if (user.getMail().equals(mail)) {
//
//			if (user.getContrasena().equals(contrasena)) {
//				/* Ver qué hacer. IngresoControlador; */
//
//			} else {
//				throw new ErrorServicio("La contraseña es incorrecta");
//			}
//		} else {
//			throw new ErrorServicio("El usuario no existe");
//		}
//
//	}

	@Transactional
	public void modificar(String mail ,String nombre,String apellido, String nombreUsuario) throws ErrorServicio {
		
		//no se puede buscar por un valor que se va a modificar posteriormente se rompe 

		System.out.println(mail);
		Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);

		System.out.println(usuario);
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre de usuario no puede quedar vacío");

		}else {
			
			usuario.setNombre(nombre);
		}
		
		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("El apellido de usuario no puede quedar vacío");

		}else {
			
			usuario.setApellido(apellido);
			
		}
		if (nombreUsuario == null || nombreUsuario.isEmpty()) {
			throw new ErrorServicio("El apellido de usuario no puede quedar vacío");

		}else {

			usuario.setNombreUsuario(nombreUsuario);
		}

		usuarioRepositorio.save(usuario);
		
		System.out.println(usuario.getNombreUsuario());


		

	}

	@Transactional
	public void baja(String nombreUsuario) {
		/* Verificar que exista el usuario */
		try {
			Usuario usuario = usuarioRepositorio.buscarPorNombreUsuario(nombreUsuario);
			usuario.setAlta(false);
			usuario.setNombre(null);
			usuario.setApellido(null);
			usuario.setNombreUsuario(usuario.getId());
			usuario.setMail(null);
			usuario.setContrasena(null);
			
			usuarioRepositorio.save(usuario);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void validar(String nombre, String apellido, String nombreUsuario, String mail, String contrasena,
			String contrasena2) throws ErrorServicio {

		System.out.println(contrasena);
		Integer largo = contrasena.length();
		System.out.println(contrasena2);
		System.out.println(contrasena.length() + "largo");

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
			//session.setAttribute("usersession", usuario);
			session.setAttribute("usersession", usuario);

			User user = new User(usuario.getMail(), usuario.getContrasena(), permisos);
			return user;
		} else {
			return null;
		}
	}
}