package com.egg.clubit.entidades;

import java.util.Date;

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
	private String respuesta;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Posteo posteo;
	
	
	
	
	
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

//	@Override
//	public String toString() {
//		return "Respuesta [id=" + id + ", fechaResp=" + fechaResp + ", respuesta=" + respuesta + "]";
//	}
}
