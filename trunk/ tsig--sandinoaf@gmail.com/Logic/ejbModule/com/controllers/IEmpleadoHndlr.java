package com.controllers;

import java.util.List;

import com.entities.Empleado;

public interface IEmpleadoHndlr {
	public void crearEmpleado(Empleado e);
	public List<Empleado> listaEmpleados();
}
