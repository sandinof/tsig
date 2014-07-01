package com.controllers;

import com.entities.Categoria;
import com.entities.Cuadrilla;
import com.entities.Empleado;
import com.entities.Incidente;

import java.util.List;


public interface ICuadrillaHndlr {
	public void crearCuadrilla(Cuadrilla cuadri);
	public boolean HayEmpleados();
	public List<Empleado> obtenerEmpleados();
	public List<Categoria> obtenerCategorias();
	public List<Empleado> obtenerEmpleadosByCuadrilla(int idCuadrilla);
	public void asignarEmpleadoCuadrilla(Integer i, List<Empleado> list);
	public void asignarCategoriasCuadrilla(Integer i, List<Categoria> list);
	public List<Cuadrilla> getCuadrillas();
	public List<Cuadrilla> getCuadrillasPorZona(int idzona);
	public List<Incidente> getIncidentes(int cuadrilla);
	void actualizarIncidenteCuadrilla(int incidente, int cuadrilla, String descargo, String estado);
	public void agregarIncidenteCuad(int cuadrilla, int IdIncidente);
}
