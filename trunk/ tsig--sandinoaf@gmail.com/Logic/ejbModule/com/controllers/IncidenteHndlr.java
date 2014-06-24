package com.controllers;

import java.util.List;

import org.json.JSONArray;

import com.DAO.IncidenteDAO;
import com.entities.Incidente;

public class IncidenteHndlr implements IIncidenteHndlr {

	public IncidenteHndlr(){};
	private IncidenteDAO inD = new IncidenteDAO();
	
	@Override
	public int crearIncidente(Incidente in, String mail, String lat, String lon) {	
		try {
				return inD.crearIncidente(in, mail, lat, lon);				
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			} 			
			
	}

	@Override
	public List<Incidente> InicidentesUus(String mail) {
		return inD.getIncidentesUsu(mail);
	}


	@Override
	public Incidente getIncidenteById(int id) {
		// TODO Auto-generated method stub
		return inD.getInciedenteById(id);
	}

	@Override
	public JSONArray PuntosIn() {
		return inD.PuntosIn();
	}

	
	

}
