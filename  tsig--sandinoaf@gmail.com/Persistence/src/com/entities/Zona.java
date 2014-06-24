package com.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the zona database table.
 * 
 */
@Entity
public class Zona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer idzona;

	private String descripcion;

	private Integer importancia;

	public Zona() {
	}

	public Integer getIdzona() {
		return this.idzona;
	}

	public void setIdzona(Integer idzona) {
		this.idzona = idzona;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getImportancia() {
		return this.importancia;
	}

	public void setImportancia(Integer importancia) {
		this.importancia = importancia;
	}

}