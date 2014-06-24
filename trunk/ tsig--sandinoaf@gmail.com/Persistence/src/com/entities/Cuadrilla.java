package com.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cuadrilla database table.
 * 
 */
@Entity
public class Cuadrilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer idcuadrilla;

	private Integer idzona;

	public Cuadrilla() {
	}

	public Integer getIdcuadrilla() {
		return this.idcuadrilla;
	}

	public void setIdcuadrilla(Integer idcuadrilla) {
		this.idcuadrilla = idcuadrilla;
	}

	public Integer getIdzona() {
		return this.idzona;
	}

	public void setIdzona(Integer idzona) {
		this.idzona = idzona;
	}

}