package com.egg.clubit.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Posteo {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String titulo;
	@Column(length = 10000)
	private String posteo;

	private Integer alta;
	private Boolean editado;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPosteo;
	@ManyToOne	
	private Etiqueta etiqueta;
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "posteo")
	private List<Respuesta> respuesta;

	public Posteo() {
	}

	public String getTitulo() {
		return titulo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPosteo() {
		return posteo;
	}

	public void setPosteo(String posteo) {
		this.posteo = posteo;
	}

	public Integer getAlta() {
		return alta;
	}

	public void setAlta(Integer alta) {
		this.alta = alta;
	}

	public Date getFechaPosteo() {
		return fechaPosteo;
	}

	public void setFechaPosteo(Date fechaPosteo) {
		this.fechaPosteo = fechaPosteo;
	}

	public Etiqueta getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	public List<Respuesta> getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(List<Respuesta> respuesta) {
		this.respuesta = respuesta;
	}

	public Boolean getEditado() {
		return editado;
	}

	public void setEditado(Boolean editado) {
		this.editado = editado;
	}

//	@Override
//	public String toString() {
//		return "Posteo [id=" + id + ", titulo=" + titulo + ", posteo=" + posteo + ", alta=" + alta + ", editado="
//				+ editado + ", fechaPosteo=" + fechaPosteo + ", etiqueta=" + etiqueta + ", usuario=" + usuario
//				+ ", respuesta=" + respuesta + "]";
//	}
}
