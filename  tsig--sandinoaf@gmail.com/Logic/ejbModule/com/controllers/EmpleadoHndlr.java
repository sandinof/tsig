package com.controllers;

import java.util.List;

import com.DAO.EmpleadoDAO;
import com.DAO.IncidenteDAO;
import com.entities.Empleado;
import com.entities.Incidente;

public class EmpleadoHndlr implements IEmpleadoHndlr {

	public EmpleadoHndlr(){};
	private EmpleadoDAO empDao = new EmpleadoDAO();
	
	@Override
	public void crearEmpleado(Empleado e) {
		
			try {
				empDao.crearEmpleado(e);
			} catch (Exception ex) {
				ex.printStackTrace();
			} 			
			
	}

	@Override
	public List<Empleado> listaEmpleados() {
		return empDao.getEmpleados();
	}
	

}
