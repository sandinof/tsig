package presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.entities.Incidente;
import com.controllers.CuadrillaHndlr;
import com.controllers.ICuadrillaHndlr;
import com.entities.Cuadrilla;


@ManagedBean
public class ActualizaciondeIncidente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idCuadrilla;
	private int idIncidente;
	public String descargo;
	public String estado;
	private List<Cuadrilla> cuadrilla;
	private List<Incidente> incidentesCuadrilla;
	
	@PostConstruct
	public void init() {
		
		String mailUsr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mailusu");
		
		boolean estaLog = true;
		try {
			if (mailUsr == null)
				estaLog = false;
			
			if (estaLog && mailUsr.equals(""))
				estaLog = false;
			
			if (!estaLog){
					FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.jsf");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ICuadrillaHndlr hndlrCadrilla = new CuadrillaHndlr();
		List<Cuadrilla> cuadrillaSist = hndlrCadrilla.getCuadrillas();
		setCuadrilla(cuadrillaSist);

		List<Incidente> incidentesCuadrilla= null;
		setIncidentesCuadrilla(incidentesCuadrilla);
			
	}

	public ActualizaciondeIncidente() {}

	public void cambioCuadrilla(){
		//al cambiar de cuadrilla cambio la lista de incidentes 
		ICuadrillaHndlr hndlrCadrilla = new CuadrillaHndlr();
		
		List<Incidente> incidentesCuadrilla = hndlrCadrilla.getIncidentes(idCuadrilla);
		setIncidentesCuadrilla(incidentesCuadrilla);		
		
	}
	
	public void GuardarCambios(){
		//actualizo el incidente
		ICuadrillaHndlr hndlrCadrilla = new CuadrillaHndlr();
		System.out.print("mi estado es = " + estado);
		hndlrCadrilla.actualizarIncidenteCuadrilla(idIncidente, idCuadrilla, descargo, estado);

	}
	
	public int getIdCuadrilla() {
		return idCuadrilla;
	}

	public void setIdCuadrilla(int idCuadrilla) {
		this.idCuadrilla = idCuadrilla;
	}
	
	public String getDescargo() {
		return descargo;
	}

	public void setDescargo(String descargo) {
		this.descargo = descargo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Cuadrilla> getCuadrilla() {
		return cuadrilla;
	}

	public void setCuadrilla(List<Cuadrilla> cuadrilla) {
		this.cuadrilla = cuadrilla;
	}

	public List<Incidente> getIncidentesCuadrilla() {
		return incidentesCuadrilla;
	}

	public void setIncidentesCuadrilla(List<Incidente> incidentesCuadrilla) {
		this.incidentesCuadrilla = incidentesCuadrilla;
	}

	public int getIdIncidente() {
		return idIncidente;
	}

	public void setIdIncidente(int idIncidente) {
		this.idIncidente = idIncidente;
	}	
	
}
