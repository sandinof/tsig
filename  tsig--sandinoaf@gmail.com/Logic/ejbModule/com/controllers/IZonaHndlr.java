package com.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


import com.entities.Zona;
import com.utilities.reporZona;

public interface IZonaHndlr {
	
	public void modificarZona(Zona zon);
	
	public void eliminarZona(int id);
	
	public Zona getZonaById(int id);
	
	public List<Zona> getZonas();

	public HashMap<String, Integer> obtenerListZonas();

	public int crearZona(Zona zon, String puntosZona);

	public boolean intersectaZonaConExistente(String puntosZona);
	
	public int obtenerZonaPorIncidente(String obtenerLongEnvio, String obtenerLatEnvio);
	
	public List<reporZona> reportPorzona(Date start, Date end);
	
}