package com.egg.clubit.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private String apellido;
	private String nombreUsuario;
	private String mail;
	private String contrasena;
	@OneToMany(mappedBy = "usuario")
	private List<Posteo> post;
	@OneToMany(mappedBy = "usuario")
	private List<Respuesta> respuesta;
	private Boolean rolAdministrador;
	private Boolean alta;
	
	public Usuario() {	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public List<Posteo> getPost() {
		return post;
	}

	public void setPost(List<Posteo> post) {
		this.post = post;
	}

	public List<Respuesta> getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(List<Respuesta> respuesta) {
		this.respuesta = respuesta;
	}

	public Boolean getRolAdministrador() {
		return rolAdministrador;
	}

	public void setRolAdministrador(Boolean rolAdministrador) {
		this.rolAdministrador = rolAdministrador;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

//	@Override
//	public String toString() {
//		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", nombreUsuario="
//				+ nombreUsuario + ", mail=" + mail + ", contrasena=" + contrasena + ", post=" + post + ", respuesta="
//				+ respuesta + ", rolAdministrador=" + rolAdministrador + ", alta=" + alta + "]";
//	};
}
