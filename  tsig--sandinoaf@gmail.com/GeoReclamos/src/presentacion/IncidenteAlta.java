package presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.controllers.IIncidenteHndlr;
import com.controllers.IncidenteHndlr;
import com.entities.Incidente;

@ManagedBean
public class IncidenteAlta implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	public IncidenteAlta(){		
		IIncidenteHndlr inH = new IncidenteHndlr(); 
		String mail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mailusu");
		setLstpuntos(inH.PuntosIn(mail).toString());	
	}
	
	private Integer id = 0;
	private String descripcion;
	private String estado;
	private Date fecha = new Date();
	private String categoria;
	private Integer prioridad;
	private Boolean valido = true;
	private String longitud;
	private String latitud;
	private String lstpuntos;
	private String masunos;
	
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
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Integer getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}
	public Boolean getValido() {
		return valido;
	}
	public void setValido(Boolean valido) {
		this.valido = valido;
	}

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public void altaIncidente(javax.faces.event.ActionEvent a){
		
		FacesMessage msg = null;
		IIncidenteHndlr inH = new IncidenteHndlr(); 
		Incidente in = new Incidente();
		in.setDescripcion(this.descripcion);
		in.setEstado("valido"); // de cara al usuario se crea un incidente valido
		in.setFecha(fecha);
		in.setCategorias(categoria);
		in.setPrioridad(1); // le seteamos 1 por defecto
		in.setValido(valido);
		String mail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mailusu");
		if(!mail.equals(""))
		{
			String cadena = "";				
			int result = inH.crearIncidente(in, mail, latitud, longitud);
			cadena = String.valueOf(result);			
			
			FacesContext contextFaces = FacesContext.getCurrentInstance();
			try {
				contextFaces.getExternalContext().redirect("HomeUsu.jsf");
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se creo el incidente", cadena);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "No se pudo crear el incidente", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}

	public void masunosreport(javax.faces.event.ActionEvent a){
		IIncidenteHndlr inH = new IncidenteHndlr(); 

		String mail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mailusu");
	
			inH.reportarCreados(mail, this.getMasunos());
			FacesContext contextFaces = FacesContext.getCurrentInstance();
				try {
					contextFaces.getExternalContext().redirect("HomeUsu.jsf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	}
	
	public String getLstpuntos() {
		return lstpuntos;
	}
	public void setLstpuntos(String lstpuntos) {
		this.lstpuntos = lstpuntos;
	}
	public String getMasunos() {
		return masunos;
	}
	public void setMasunos(String masunos) {
		this.masunos = masunos;
	}
	
}
