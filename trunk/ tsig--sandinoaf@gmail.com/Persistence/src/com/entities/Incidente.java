package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the incidente database table.
 * 
 */
@Entity
public class Incidente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String categorias;

	private String descripcion;

	private String estado;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private Integer prioridad;

	private Boolean valido;

	public Incidente() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategorias() {
		return this.categorias;
	}

	public void setCategorias(String categorias) {
		this.categorias = categorias;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public Boolean getValido() {
		return this.valido;
	}

	public void setValido(Boolean valido) {
		this.valido = valido;
	}

}