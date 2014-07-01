package presentacion;


import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.controllers.IZonaHndlr;
import com.controllers.ZonaHndlr;
import com.entities.Zona;

@ManagedBean
public class AltaZona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreZona = "";
	private String descripcion;
	private int importancia;
	private String headerStr;
	

	private String btnStr;
	private String puntosZona;
	private boolean disableBtnCrear = false;
	private int zonaIdCreada;

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

	public void altaZona() {
		IZonaHndlr hndlr = new ZonaHndlr();
		Zona zon = new Zona();
		
		String msg = "";
		Severity tipoMsg = FacesMessage.SEVERITY_INFO;
		
		//Validacion de los puntos
		if (puntosZona.equals("[]")){
			tipoMsg = FacesMessage.SEVERITY_WARN;
			msg = "Debe marcar la zona a registrar";
		}
		else if (hndlr.intersectaZonaConExistente(puntosZona)){
			tipoMsg = FacesMessage.SEVERITY_WARN;
			msg = "La zona no puede intersectar una zona existente";
		}
		else{
			zon.setDescripcion(nombreZona);
			zon.setImportancia(importancia);
			zonaIdCreada = hndlr.crearZona(zon, puntosZona);
			tipoMsg = FacesMessage.SEVERITY_INFO;
			msg = "Se agrego correctamente la zona";
			disableBtnCrear = true;
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(tipoMsg, msg, ""));
		
	}
	
	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public String getHeaderStr() {
		return headerStr;
	}

	public void setHeaderStr(String headerStr) {
		this.headerStr = headerStr;
	}

	public String getBtnStr() {
		return btnStr;
	}

	public void setBtnStr(String btnStr) {
		this.btnStr = btnStr;
	}

	public String getPuntosZona() {
		return puntosZona;
	}
	
	public void setPuntosZona(String puntosZona) {
		this.puntosZona = puntosZona;
	}
	
	public int getImportancia() {
		return importancia;
	}

	public void setImportancia(int importancia) {
		this.importancia = importancia;
	}
	
	public boolean isDisableBtnCrear() {
		return disableBtnCrear;
	}

	public void setDisableBtnCrear(boolean disableBtnCrear) {
		this.disableBtnCrear = disableBtnCrear;
	}
	
	public void setZonaIdCreada(int zonaIdCreada) {
		this.zonaIdCreada = zonaIdCreada;
	}
	
	public int getZonaIdCreada() {
		return zonaIdCreada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}