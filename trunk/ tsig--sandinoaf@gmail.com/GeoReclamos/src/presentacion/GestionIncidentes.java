package presentacion;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import com.controllers.CuadrillaHndlr;
import com.controllers.ICuadrillaHndlr;
import com.controllers.IIncidenteHndlr;
import com.controllers.IncidenteHndlr;
import com.entities.Cuadrilla;
import com.entities.Incidente;


@ManagedBean
@ViewScoped
public class GestionIncidentes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String descripcion;
	private String categoria;
	private String longitud;
	private String latitud;
	private int idzona;
	private String nombrezona;
	private String puntos;
	private String estado;
	private List<Cuadrilla> cuadrilla;
	private int idCuadrilla;
	

	public int getIdCuadrilla() {
		return idCuadrilla;
	}

	public void setIdCuadrilla(int idCuadrilla) {
		this.idCuadrilla = idCuadrilla;
	}

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

	public GestionIncidentes(){
		IIncidenteHndlr inH = new IncidenteHndlr(); 	
		setPuntos(inH.PuntosSistema().toString());
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
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
	public int getIdzona() {
		return idzona;
	}
	public void setIdzona(int idzona) {
		this.idzona = idzona;
	}
	public String getNombrezona() {
		return nombrezona;
	}
	public void setNombrezona(String nombrezona) {
		this.nombrezona = nombrezona;
	}
	public String getPuntos() {
		return puntos;
	}
	public void setPuntos(String puntos) {
		this.puntos = puntos;
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

	
	public void cargarControles(javax.faces.event.ActionEvent a){
		ICuadrillaHndlr hndlrCadrilla = new CuadrillaHndlr();
		List<Cuadrilla> cuadrillaSist = hndlrCadrilla.getCuadrillasPorZona(idzona);
		setCuadrilla(cuadrillaSist);
	}
	
	public void cambioCuadrilla(){
	System.out.println(idCuadrilla);		
		
	}
	
	public void altaIncidenteCuarilla(javax.faces.event.ActionEvent a){
		ICuadrillaHndlr hndlrCadrilla = new CuadrillaHndlr();
		hndlrCadrilla.agregarIncidenteCuad(idCuadrilla, id);
		System.out.println("hola " + id + "cuadrilla " + idCuadrilla);
	}	
	
}
