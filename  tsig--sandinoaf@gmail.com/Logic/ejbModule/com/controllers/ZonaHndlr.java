package com.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.DAO.ZonaDAO;
import com.entities.Zona;

public class ZonaHndlr implements IZonaHndlr {

	private ZonaDAO zonaDAO = new ZonaDAO();
	
	
	@Override
	public void modificarZona(Zona veh) {
		zonaDAO.modificarZona(veh);
	}

	@Override
	public void eliminarZona(int id) {
		zonaDAO.eliminarZona(id);
	}

	@Override
	public Zona getZonaById(int id) {
		return zonaDAO.getZonaById(id);
	}

	@Override
	public List<Zona> getZonas() {
		return zonaDAO.getZonas();
	}

	@Override
	public HashMap<String, Integer> obtenerListZonas() {
		List<Zona> listaVe = zonaDAO.getZonas();
		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		Iterator<Zona> itera = listaVe.iterator();
		Zona aux = null;
		while (itera.hasNext()) {
			aux = itera.next();
			resultado.put(aux.getDescripcion(),aux.getIdzona());
		}
		return resultado;
	}

	@Override
	public int crearZona(Zona zon, String puntosZona) {
		return zonaDAO.crearZona(zon, formatearPuntosZona(puntosZona));
	}

	@Override
	public boolean intersectaZonaConExistente(String puntosZona) {
		return zonaDAO.intersectaZonaConExistente(formatearPuntosZona(puntosZona));
	}

	private String formatearPuntosZona(String puntosZona) {
		String puntos = puntosZona;
		puntos = puntos.replaceAll(Pattern.quote("[[\""), "((");
		puntos = puntos.replaceAll("\"]]", "))");
		puntos = puntos.replaceAll("\",\"", ",");
		puntos = puntos.replaceAll(Pattern.quote("\"],[\""), ")),((");
		return puntos;
	}

	@Override
	public int obtenerZonaPorIncidente(String longitud, String latitud) {
		return zonaDAO.obtenerZonaPorIncidente(longitud,latitud);
	}

}