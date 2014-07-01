package com.controllers;


import java.util.Iterator;
import java.util.List;

import com.DAO.CuadrillaDAO;
import com.DAO.EmpleadoDAO;
import com.entities.Categoria;
import com.entities.Cuadrilla;
import com.entities.Empleado;
import com.entities.Incidente;

public class CuadrillaHndlr implements ICuadrillaHndlr {

	private CuadrillaDAO cuadriDAO = new CuadrillaDAO();
	private EmpleadoDAO empDAO = new EmpleadoDAO();
		
	@Override
	public void crearCuadrilla(Cuadrilla cuadri) {
		try {
			cuadriDAO.crearCuadrilla(cuadri);
		} catch (Exception e) {
			 throw e;
		}
		
	}
	
	@Override
	public void actualizarIncidenteCuadrilla(int incidente, int cuadrilla, String descargo, String estado){
		cuadriDAO.actualizarIncidenteCuadrilla(incidente, cuadrilla, descargo, estado);
	}
	
	@Override
	public boolean HayEmpleados(){
		return empDAO.HayEmpleados();
	}
	
	@Override
	public List<Cuadrilla> getCuadrillas() {
		return cuadriDAO.getCuadrillas();
	}
	@Override
	public List<Incidente> getIncidentes(int cuadrilla) {
		return cuadriDAO.getIncidentes(cuadrilla);
	}
	
	@Override
	public List<Empleado> obtenerEmpleados() {
		return empDAO.getEmpleados();
	}
	
	@Override
	public List<Categoria> obtenerCategorias() {
		return cuadriDAO.getCategorias();
	}
	
	@Override
	public List<Empleado> obtenerEmpleadosByCuadrilla(int idCuadrilla){
		return empDAO.getEmpleadosCuadrilla(idCuadrilla);
	}

	@Override
	public void asignarEmpleadoCuadrilla(Integer cuadrilla, List<Empleado> target) {
		cuadriDAO.eliminarEmpleados(cuadrilla);
		@SuppressWarnings("rawtypes")
		Iterator itera = target.iterator();
		while (itera.hasNext()) {
			Integer empId = Integer.parseInt((itera.next().toString()));
			cuadriDAO.agregarEmpleadoCuad(cuadrilla, empId);
		}
	}

	@Override
	public void asignarCategoriasCuadrilla(Integer cuadrilla, List<Categoria> target) {
		cuadriDAO.eliminarCategoria(cuadrilla);
		@SuppressWarnings("rawtypes")
		Iterator itera = target.iterator();
		while (itera.hasNext()) {
			Integer IdCat = Integer.parseInt((itera.next().toString()));
			cuadriDAO.agregarCategoriasCuad(cuadrilla, IdCat);
		}
	}

	@Override
	public List<Cuadrilla> getCuadrillasPorZona(int idzona) {
		return cuadriDAO.getCuadrillasPorZona(idzona);
	}
	
	@Override
	public void agregarIncidenteCuad(int cuadrilla, int IdIncidente) {
		System.out.println("HNDLR incidente " + IdIncidente + "cuadrilla " + cuadrilla);
		cuadriDAO.agregarIncidenteCuad(cuadrilla,IdIncidente);		
		
	}
	


}
