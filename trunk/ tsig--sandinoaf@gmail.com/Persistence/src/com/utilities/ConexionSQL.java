package com.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionSQL {
	
	private static ConexionSQL conexion;
	private static String url;
	private static String username;
	private static String pass;
	private static Connection psqlCon;
	
	private ConexionSQL(){
		try{
			Class.forName("org.postgresql.Driver");
			url = "jdbc:postgresql://localhost:5432/ReparTSIG";
			username = "postgres";
			pass = "root";
			psqlCon = DriverManager.getConnection(url,username,pass);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	public static void setUrl(String myUrl) {
		url = myUrl;
	}

	public static void setUsername(String myUser) {
		username = myUser;
	}

	public static void setPassword(String myPass) {
		pass = myPass;
	}

	public static ConexionSQL getConexion() {
		if (conexion == null)
			conexion = new ConexionSQL();
		return conexion;
	}

	public static Connection getConnection() {
		if (conexion == null)
			conexion = new ConexionSQL();
		return psqlCon;
	}
	
	public void cerrarConexion(){
		try {
			if (!psqlCon.isClosed()){
				psqlCon.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}