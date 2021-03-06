package com.controllers;

import java.util.List;

import com.DAO.EmpleadoDAO;
import com.entities.Empleado;


public class EmpleadoHndlr implements IEmpleadoHndlr {

	public EmpleadoHndlr(){};
	private EmpleadoDAO empDao = new EmpleadoDAO();
	
	@Override
	public void crearEmpleado(Empleado e) {
		
			try {
				empDao.crearEmpleado(e);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			} 			
			
	}

	@Override
	public List<Empleado> listaEmpleados() {
		return empDao.getEmpleados();
	}
	

}
