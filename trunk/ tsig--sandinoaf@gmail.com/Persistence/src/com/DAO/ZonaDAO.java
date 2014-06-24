package com.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.entities.Zona;
import com.utilities.ConexionSQL;

public class ZonaDAO {

	private EntityManager em = Persistence.createEntityManagerFactory(
			"Persistence").createEntityManager();

	public int crearZona(Zona zon, String puntosZona) {

		try {
			Connection con = ConexionSQL.getConnection();
			Statement statement = con.createStatement();

			String query = "SELECT MAX(idzona) FROM zona;";

			ResultSet result = statement.executeQuery(query);
			int maxId = 0;
			while (result.next()) {
				maxId = result.getInt(1);
			}

			
			maxId = maxId + 1;
			zon.setIdzona(maxId);

			em.getTransaction().begin();
			em.persist(zon);
			em.merge(zon);
			em.flush();
			em.getTransaction().commit();

			statement = con.createStatement();
			statement.execute("insert into zonageo(zonaid, geom) values ("
					+ zon.getIdzona()
					+ ", (SELECT ST_MPolyFromText('MULTIPOLYGON(" + puntosZona
					+ ")',4326)))");
			System.out.println("Zona creada correctamente con id: "
					+ zon.getIdzona());

			return zon.getIdzona();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
			return -1;
		}

	}

	public int modificarZona(Zona zon) {

		try {
			em.getTransaction().begin();
			em.merge(zon);
			em.flush();
			em.getTransaction().commit();

			System.out.println("Evento modificado correctamente");
			return zon.getIdzona();

		} catch (Exception ex) {
			em.getTransaction().rollback();
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
			return -1;
		}

	}

	public void eliminarZona(int id) {
		em.getTransaction().begin();
		try {

			Zona usrElim = em.find(Zona.class, id);

			em.remove(usrElim);
			em.getTransaction().commit();

			System.out.println("Zona eliminado correctamente");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			System.out.println("Ha ocurrido un error al Eliminar el Zona");
			ex.printStackTrace();
		}

		finally {
			em.close();
		}
	}

	public Zona getZonaById(int id) {
		Zona z = null;
		try {
			z = em.find(Zona.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}
		return z;
	}

	public List<Zona> getZonas() {
		List<Zona> resultList = new ArrayList<Zona>();

		try {
			Connection con = ConexionSQL.getConnection();
			Statement st = null;
			st = con.createStatement();		
			//obtengo las zonas
			String query = "select * from Zona ";

			ResultSet result = st.executeQuery(query);

			while (result.next())
			{
				Zona z = new Zona();
				z.setDescripcion(result.getString("descripcion"));
				z.setIdzona(result.getInt("idzona"));			
				resultList.add(z);
			}
		} catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}		

		return resultList;
	}

	public boolean intersectaZonaConExistente(String puntosZona) {
		
		try {
		Connection con = ConexionSQL.getConnection();
		Statement statement;
			statement = con.createStatement();

		String query = "select * from zonageo z where ST_Intersects(z.geom,(SELECT ST_MPolyFromText('MULTIPOLYGON("+puntosZona+")',4326)))";

		return statement.executeQuery(query).next();
		
		} catch (SQLException e) {
			System.out.println("Error al consultar si una zona intersecta otra ya existente");
			e.printStackTrace();
			return false;
		}
	}

	public int obtenerZonaPorIncidente(String longitud, String latitud) {
		try {
			
			Connection con = ConexionSQL.getConnection();
			Statement statement = con.createStatement();

			String query = "SELECT zonaid FROM zonageo WHERE ST_Intersects(ST_PointFromText('POINT(" + longitud + " " + latitud + ")', 4326),geom);";

			ResultSet result = statement.executeQuery(query);
			int zonaId = -1;
			while (result.next()) {
				zonaId = result.getInt(1);
			}

			return zonaId;
			
			} catch (SQLException e) {
				System.out.println("Error en obtenerZonaPorIncidente");
				e.printStackTrace();
				return -1;
			}
	}
}