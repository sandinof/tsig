package com.controllers;

import java.util.List;

import org.json.JSONArray;

import com.entities.Incidente;

public interface IIncidenteHndlr {
	public int crearIncidente(Incidente in ,String mail, String lat, String lon);
	public List<Incidente> InicidentesUus(String mail);
	public JSONArray PuntosIn(String mail);
	public Incidente getIncidenteById(int id);
	public void  reportarCreados(String mail, String listaReportados);
	public JSONArray PuntosSistema();
}
