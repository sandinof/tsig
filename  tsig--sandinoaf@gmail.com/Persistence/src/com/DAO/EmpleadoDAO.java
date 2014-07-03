package com.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.entities.Empleado;
import com.utilities.ConexionSQL;


public class EmpleadoDAO {
	
	private EntityManager em = Persistence.createEntityManagerFactory("Persistence").createEntityManager();
	
	public void crearEmpleado(Empleado emp) {		
		try {
			em.getTransaction().begin();
			em.persist(emp);
			em.merge(emp);
			em.flush();
			em.getTransaction().commit();
			System.out.println("Empleado creado correctamente");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
			throw ex;
		}
	}
	
	public boolean HayEmpleados() {
		try {
			
			Connection con = ConexionSQL.getConnection();
			Statement statement = con.createStatement();
			//Ej.
			//SELECT zonaid FROM zonageo WHERE ST_Intersects(ST_PointFromText('POINT(-34.784240092785 -56.305867847788)', 4326), geom)
			String query = "SELECT * FROM empleados";

			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				return true;
			}

			return false;
			
			} catch (SQLException e) {
				System.out.println("Error al obtener cantidad de empleados");
				e.printStackTrace();
				return false;
			}
	}

	
	public List<Empleado> getEmpleados(){


		List<Empleado> resultList = new ArrayList<Empleado>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//solo obtengo los empleados
			String query = "select * from Empleados";

			ResultSet result = st.executeQuery(query);

			while (result.next())
			{
				Empleado e = new Empleado();
				e.setNombre(result.getString("nombre"));
				e.setCedula(result.getInt("cedula"));			
				resultList.add(e);
			}
		} catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}		
		
		return resultList;

	}


	public List<Empleado> getEmpleadosCuadrilla(int idCuadrilla) {

		List<Empleado> resultList = new ArrayList<Empleado>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//solo obtengo los empleados
			String query = "select emp from Empleados emp where cedula IN "
					+ "(select distinct cedulaEmpleado from cuadrillaempleados Where idcuadrilla = " + idCuadrilla + ")";

			ResultSet result = st.executeQuery(query);

			while (result.next())
			{
				Empleado e = new Empleado();
				e.setNombre(result.getString("nombre"));
				e.setCedula(result.getInt("cedula"));			
				resultList.add(e);
			}
		} catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}		

		return resultList;
	}
}