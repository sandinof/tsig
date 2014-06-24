package com.DAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.entities.Categoria;
import com.entities.Cuadrilla;
import com.utilities.ConexionSQL;

public class CuadrillaDAO {
	

	public CuadrillaDAO() {	}
	
	private EntityManager em = Persistence.createEntityManagerFactory("Persistence").createEntityManager();
	
	public void crearCuadrilla(Cuadrilla cuadri) {
		
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			String query = "SELECT coalesce(MAX(idCuadrilla), 0) from cuadrilla";

			ResultSet result = st.executeQuery(query);
			int maxId = 0;
			while (result.next()) {
				maxId = result.getInt(1);
			}

			maxId = maxId + 1;
			cuadri.setIdcuadrilla(maxId);
			
			em.getTransaction().begin();
			em.persist(cuadri);
			em.merge(cuadri);
			em.flush();
			em.getTransaction().commit();			
			System.out.println("Cuadrilla creada correctamente");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}		
		
	}
	
	public void eliminarEmpleados(int cuadrilla){ 
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			String query = "Delete From cuadrillaempleados where idCuadrilla = "+ cuadrilla + "";
			ResultSet result = st.executeQuery(query);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}		
		
		
	}

	public void agregarEmpleadoCuad(int cuadrilla, int empId){
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			String query = "INSERT INTO cuadrillaempleados (idcuadrilla, cedulaEmpleado) VALUES (" + cuadrilla + ", " + empId + ")";

			ResultSet result = st.executeQuery(query);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}		
				
	}
	
	public List<Categoria> getCategorias(){


		List<Categoria> resultList = new ArrayList<Categoria>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//solo obtengo los empleados
			String query = "select * from categorias";

			ResultSet result = st.executeQuery(query);

			while (result.next())
			{
				Categoria c = new Categoria();
				c.setDescripcion(result.getString("descripcion"));
				c.setId(result.getInt("id"));			
				resultList.add(c);
			}
		} catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}		
		
		return resultList;

	}	
	
	public void eliminarCategoria(int cuadrilla){ 
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			String query = "Delete From cuadrillespecialidad where idCuadrilla = "+ cuadrilla + "";
			ResultSet result = st.executeQuery(query);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}		
		
		
	}

	public void agregarCategoriasCuad(int cuadrilla, int IdEspecialidad){
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			String query = "INSERT INTO public.cuadrillaespecialidad (idcuadrilla, idespecialidad)VALUES (" + cuadrilla + ", " + IdEspecialidad + ")";

			ResultSet result = st.executeQuery(query);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}		
				
	}
	
	
	
}
