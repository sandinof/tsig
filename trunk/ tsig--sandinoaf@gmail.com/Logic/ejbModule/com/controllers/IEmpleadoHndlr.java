package com.controllers;

import java.util.List;

import com.entities.Empleado;
import com.entities.Incidente;

public interface IEmpleadoHndlr {
	public void crearEmpleado(Empleado e);
	public List<Empleado> listaEmpleados();
}
