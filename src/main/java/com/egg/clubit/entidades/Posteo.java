package com.egg.clubit.entidades;

import java.util.Date;
import java.util.List;

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
	private String posteo;
	private Boolean cerrar;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPosteo;
	@ManyToOne
	private Etiqueta etiqueta;
	@OneToMany
	private List<Respuesta> respuesta;
	
	public Posteo() {	}
	
		public String getTitulo() {
		return titulo;
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

	public Boolean getCerrar() {
		return cerrar;
	}

	public void setCerrar(Boolean cerrar) {
		this.cerrar = cerrar;
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

	@Override
	public String toString() {
		return "Posteo [id=" + id + ", posteo=" + posteo + ", cerrar=" + cerrar + ", fechaPosteo=" + fechaPosteo
				+ ", etiqueta=" + etiqueta + ", respuesta=" + respuesta + "]";
	}
}
