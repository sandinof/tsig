package com.DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.entities.Incidente;
import com.utilities.ConexionSQL;
import com.utilities.jsonConverter;

import org.json.*;

public class IncidenteDAO {

	public IncidenteDAO(){}
	private EntityManager em = Persistence.createEntityManagerFactory("Persistence").createEntityManager();
	
	public int  crearIncidente(Incidente in, String mail, String lat, String lon) {
		
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
	
		try {
			
			String query = "SELECT coalesce(MAX(id), 0) from incidente";

			ResultSet result = st.executeQuery(query);
			int maxId = 0;
			while (result.next()) {
			maxId = result.getInt(1);
			}

			maxId = maxId + 1;
			in.setId(maxId);
			
			em.getTransaction().begin();
			em.persist(in);
			em.merge(in);
			em.flush();
			em.getTransaction().commit();			
			System.out.println("Incidente creado correctamente");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
			return -1;
		}		
		
		try {
			
		    String query = "INSERT INTO incidenteusu(mail, incidenteid) VALUES ('"+ mail +"', "+ in.getId() +")";
			st.execute(query);
			System.out.println("Incidente creado");
			System.out.println("En " + lat + "" + lon + "");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("\n" + e.getMessage());
			return -1;
		}
		
		try {
			
			   st.execute("insert into incidentegeo(incidenteid, geom) values ("
					   + in.getId() + ", st_setsrid(st_makepoint("
					   + lon + "," + lat + "), 4326) )");
					   System.out.println("Envio creado correctamente");
					   System.out.println("Latitud: " + lon);
					   System.out.println("Longitud: " + lat);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("\n" + e.getMessage());
			return -1;
		}
		
		return in.getId();
		
	}
	
	public void modificarIncidente(Incidente in) {
		
		try {
			em.getTransaction().begin();
			em.merge(in);
			em.flush();
			em.getTransaction().commit();
			System.out.println("Incidente modificado correctamente");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}

	}
	
	public Incidente getInciedenteById(int id){
		Incidente in = null;
		try {
			in = em.find(Incidente.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}
		return in;
	}
	
	public List<Incidente> getIncidentesUsu(String mail){
		List<Incidente> resultList = new ArrayList<Incidente>();
		
		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//solo obtengo los incidentes validos reportados por el usuario
			String query = "select distinct on (id) a.* from (select * from incidente) a INNER JOIN" 
					+ " (select * from incidenteusu) b ON a.id = b.incidenteid " 
					+ " WHERE a.valido = 'true'  AND b.mail = '"+ mail +"'";
			
			ResultSet result = st.executeQuery(query);
			
			while (result.next())
			{
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
				
		} catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}
		
		return resultList;
	}
	
	public JSONArray PuntosIn(String mail){
		
		Connection con = ConexionSQL.getConnection();
		Statement st = null;			
	
		String query = "SELECT i.incidenteid, ST_Y(geom), ST_X(geom), descripcion, categorias, CASE WHEN mail = '"+ mail +"' THEN 1 ELSE 0";
			   query = query + " END AS reportado FROM public.incidentegeo join public.incidente on id = incidenteid join public.incidenteusu";
			   query = query + " i on i.incidenteid = id where valido = true";
		
		ResultSet result = null;
		JSONArray rr = null;

		
		try {
			st = con.createStatement();
			result = st.executeQuery(query);
			try {
				jsonConverter jConv = new jsonConverter();
				rr = jConv.convertToJSON(result);
				
				System.out.println(rr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return rr;
	}
	
public JSONArray PuntosSistema(){
		
		Connection con = ConexionSQL.getConnection();
		Statement st = null;			
	
		String query = "SELECT  distinct on (t.incidenteid) t.zonaid, z.descripcion "+"descripcionzona"+", t.incidenteid, t.x, t.y, t.descripcion, t.categorias, t.estado FROM";
		   query = query + " (SELECT  zonageo.zonaid, incidentegeo.incidenteid, ST_X(incidentegeo.geom) as x, ST_Y(incidentegeo.geom) as y, incidente.descripcion, categorias, estado FROM";
		   query = query + " zonageo INNER JOIN incidentegeo on st_contains(zonageo.geom,incidentegeo.geom)";
		   query = query + " INNER JOIN incidente on  id = incidenteid) t";
		   query = query + " INNER JOIN zona z on z.idzona = t.zonaid";
			
		ResultSet result = null;
		JSONArray rr = null;

		
		try {
			st = con.createStatement();
			result = st.executeQuery(query);
			try {
				jsonConverter jConv = new jsonConverter();
				rr = jConv.convertToJSON(result);
				
				System.out.println(rr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return rr;
	}
	
	public int obtenerZonaIncidente(String longitud, String latitud) {
		try {
			
			Connection con = ConexionSQL.getConnection();
			Statement statement = con.createStatement();
			//Ej.
			//SELECT zonaid FROM zonageo WHERE ST_Intersects(ST_PointFromText('POINT(-34.784240092785 -56.305867847788)', 4326), geom)
			String query = "SELECT zonaid FROM zonageo WHERE ST_Intersects(ST_PointFromText('POINT(" + longitud.trim() + " " + latitud.trim() + ")', 4326),geom);";

			ResultSet result = statement.executeQuery(query);
			int idZona = 0;
			while (result.next()) {
				idZona = result.getInt(1);
			}

			return idZona;
			
			} catch (SQLException e) {
				System.out.println("Error al obtener zona del incidente");
				e.printStackTrace();
				return -1;
			}
	}

	public void  reportarCreados(String mail, String listaReportados) {
		
		Connection con = ConexionSQL.getConnection();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		try {
			String masUnos[]; 
			if(listaReportados.contains(",")){
			masUnos = listaReportados.split(",");
			}else{
				masUnos = new String[1];
				masUnos [0]= listaReportados;
			}
			int i = 0;
			if (masUnos.length > 0 && !masUnos [0].equals("")){
				for(i = 0; i < masUnos.length; i ++){				
			    String query = "INSERT INTO incidenteusu(mail, incidenteid) VALUES ('"+ mail +"', "+ masUnos[i] +")";
				st.execute(query);
				System.out.println("Incidente creado '" + masUnos[i]+ "' ");
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("\n" + e.getMessage());

		}
		
	}
    
}
