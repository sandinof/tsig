package presentacion;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class ReporteCantidad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReporteCantidad(){}
	
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
		
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	private int cantidad;
}
