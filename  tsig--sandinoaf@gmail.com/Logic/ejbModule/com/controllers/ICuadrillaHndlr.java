package com.controllers;

import com.entities.Categoria;
import com.entities.Cuadrilla;
import com.entities.Empleado;
import java.util.List;


public interface ICuadrillaHndlr {
	public void crearCuadrilla(Cuadrilla cuadri);
	public List<Empleado> obtenerEmpleados();
	public List<Categoria> obtenerCategorias();
	public List<Empleado> obtenerEmpleadosByCuadrilla(int idCuadrilla);
	public void asignarEmpleadoCuadrilla(Integer i, List<Empleado> list);
	public void asignarCategoriasCuadrilla(Integer i, List<Categoria> list);
}
