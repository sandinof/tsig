package presentacion;

import java.util.List;

import javax.faces.context.FacesContext;

import com.controllers.IIncidenteHndlr;
import com.controllers.IncidenteHndlr;
import com.entities.Incidente;
import java.io.Serializable;

public class ListaIncidentes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Incidente> incidentes;
	
	public List<Incidente> getIncidentes() {
		return incidentes;
	}

	public void setIncidentes(List<Incidente> incidentes) {
		this.incidentes = incidentes;
	}

	public ListaIncidentes (){
		String mail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mailusu");
		if(!mail.equals("")){
			IIncidenteHndlr inH = new IncidenteHndlr(); 
			if (inH.InicidentesUus(mail)!= null){
				incidentes = inH.InicidentesUus(mail);
			}
		}
	}
	

	
}
