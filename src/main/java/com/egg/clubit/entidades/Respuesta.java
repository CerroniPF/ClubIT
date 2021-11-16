package com.egg.clubit.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Respuesta {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaResp;
	@Column(length = 10000)
	private String respuesta;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Posteo posteo;
	private Integer alta;
	
	public Respuesta() {	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFechaResp() {
		return fechaResp;
	}

	public void setFechaResp(Date fechaResp) {
		this.fechaResp = fechaResp;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Posteo getPosteo() {
		return posteo;
	}

	public void setPosteo(Posteo posteo) {
		this.posteo = posteo;
	}

	public Integer getAlta() {
		return alta;
	}

	public void setAlta(Integer alta) {
		this.alta = alta;
	}


//	@Override
//	public String toString() {
//		return "Respuesta [id=" + id + ", fechaResp=" + fechaResp + ", respuesta=" + respuesta + "]";
//	}
}
