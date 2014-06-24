package com.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the empleados database table.
 * 
 */
@Entity
@Table(name="empleados")
public class Empleado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer cedula;

	private String nombre;

	private Integer telefono;

	public Empleado() {
	}

	public Integer getCedula() {
		return this.cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTelefono() {
		return this.telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

}