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
import com.entities.Incidente;
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
			
			String query = "Delete From cuadrillaempleados where idCuadrilla = "+ cuadrilla;
			st.execute(query);

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

			st.execute(query);

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

	public List<Incidente> getIncidentes(int cuadrilla){
		List<Incidente> resultList = new ArrayList<Incidente>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//solo obtengo las cuadrillas de una zona en particular
			String query = "select * from incidente where " +
							"id in (select distinct idincidente from incidenteCuadrilla where idCuadrilla = " + 
							cuadrilla + ") AND ESTADO  <> 'RESUELTO' "; 

			ResultSet result = st.executeQuery(query);

			while (result.next()){
				Incidente in = new Incidente();
				 in.setId(result.getInt("id"));
				 in.setDescripcion(result.getString("descripcion"));
				 in.setCategorias(result.getString("categorias"));
				 in.setPrioridad(result.getInt("prioridad"));
				 in.setValido(result.getBoolean("valido"));
				 in.setEstado(result.getString("estado"));
				 in.setFecha(result.getDate("fecha"));			
				resultList.add(in);
			}
		}catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}		
		return resultList;
	}	

	
	public List<Cuadrilla> getCuadrillasZona(int zona){
		List<Cuadrilla> resultList = new ArrayList<Cuadrilla>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//solo obtengo las cuadrillas de una zona en particular
			String query = "select * from cuadrilla Where idzona = " + zona;

			ResultSet result = st.executeQuery(query);

			while (result.next()){
				Cuadrilla c = new Cuadrilla();
				c.setIdcuadrilla(result.getInt("idcuadrilla"));
				c.setIdzona(result.getInt("idzona"));			
				resultList.add(c);
			}
		}catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}		
		return resultList;
	}	
	
	public List<Cuadrilla> getCuadrillas(){
		List<Cuadrilla> resultList = new ArrayList<Cuadrilla>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//obtengo las cuadrillas del sistema
			String query = "select * from cuadrilla";

			ResultSet result = st.executeQuery(query);

			while (result.next()){
				Cuadrilla c = new Cuadrilla();
				c.setIdcuadrilla(result.getInt("idcuadrilla"));
				c.setIdzona(result.getInt("idzona"));			
				resultList.add(c);
			}
		}catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}		
		return resultList;
	}	

	public List<Cuadrilla> getCuadrillasPorZona(int idzona){
		List<Cuadrilla> resultList = new ArrayList<Cuadrilla>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//obtengo las cuadrillas del sistema
			String query = "select * from cuadrilla where idzona =" + idzona;

			ResultSet result = st.executeQuery(query);

			while (result.next()){
				Cuadrilla c = new Cuadrilla();
				c.setIdcuadrilla(result.getInt("idcuadrilla"));
				c.setIdzona(result.getInt("idzona"));			
				resultList.add(c);
			}
		}catch (Exception e) {
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
			
			String query = "Delete From cuadrillespecialidad where idCuadrilla = "+ cuadrilla;
			st.execute(query);

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

			st.execute(query);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}		
				
	}
	
	public void agregarIncidenteCuad(int cuadrilla, int IdIncidente){
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			System.out.println("DAO incidente " + IdIncidente + "cuadrilla " + cuadrilla);
			String query = "INSERT INTO incidentecuadrilla (idincidente, idcuadrilla, descargo)VALUES (" + IdIncidente+ ", " + cuadrilla  + ", " +"' '"+ ")";

			st.execute(query);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}		
				
	}
	

	public void actualizarIncidenteCuadrilla(int incidente, int cuadrilla, String  descargo, String estado){
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			String query = "UPDATE incidentecuadrilla SET descargo = '" + descargo.trim() 
						+ "'WHERE idincidente = " + incidente;
			st.execute(query);

	/*		if(estado.equals("RESUELTO")){
				
			query = "SELECT mail FROM incidenteusu where incidenteid =" + incidente;
			
			ResultSet result = st.executeQuery(query);
			mandarMail m = new mandarMail();
			
			while (result.next()){						
				m.mandarMailto(result.getString("mail"));
			}
			}*/
				
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}
		Connection con2 = ConexionSQL.getConnection();
		Statement st2 = null;
		try {
			st2 = con2.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			
			

			String query2 = "UPDATE incidente SET estado = '" + estado.trim() + "' WHERE id = " + incidente;
			st2.execute(query2);

			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}
	}
	
}
