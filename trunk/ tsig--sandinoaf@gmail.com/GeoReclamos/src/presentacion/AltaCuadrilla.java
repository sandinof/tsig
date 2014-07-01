package presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DualListModel;

import com.entities.Categoria;
import com.entities.Empleado;
import com.entities.Zona;
import com.controllers.CuadrillaHndlr;
import com.controllers.ICuadrillaHndlr;
import com.controllers.IZonaHndlr;
import com.controllers.ZonaHndlr;
import com.entities.Cuadrilla;


@ManagedBean
public class AltaCuadrilla implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idCuadrilla;
	private int idZona;
	private DualListModel<Empleado> empleados;
	private DualListModel<Categoria> categorias;
	private List<Zona> zonas;
	private boolean noSelecZona = true;
	
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
		
		ICuadrillaHndlr hndlr = new CuadrillaHndlr();
		if (hndlr.HayEmpleados()){
			List<Empleado> empleadosSource = hndlr.obtenerEmpleados();
			setEmpleados(new DualListModel<Empleado>(empleadosSource, new ArrayList<Empleado>()));
	
			if (empleadosSource.isEmpty() ) {
				String msg = "";
				msg = "Ha ocurrido un error al crear la cuadrilla";
				//FacesContext contextFaces = FacesContext.getCurrentInstance();
				FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(msg));
			}
			 
			
			List<Categoria> categoriasSource = hndlr.obtenerCategorias();
			setCategorias(new DualListModel<Categoria>(categoriasSource, new ArrayList<Categoria>()));
	
			IZonaHndlr hndlrZona = new ZonaHndlr();
			List<Zona> zonasSist= hndlrZona.getZonas();
			setZonas(zonasSist);
	
			setNoSelecZona(false);
		}else{
			String msg = "";
			msg = "No hay empleados ingresados en el sistema. Favor darlos de alta";
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(msg));

		}
			
	}

	public AltaCuadrilla() {}

	public void CrearCuadrilla() {
		String msg = "";
		ICuadrillaHndlr hndlrCuad = new CuadrillaHndlr();

		Cuadrilla cuad = new Cuadrilla();		
		cuad.setIdzona(idZona);
		
		try {
			hndlrCuad.crearCuadrilla(cuad);
			msg = "Cuadrilla " + cuad.getIdcuadrilla() + " creada correctamente.";
			setIdCuadrilla(cuad.getIdcuadrilla());
			asignar();
			//FacesContext contextFaces = FacesContext.getCurrentInstance();
			//contextFaces.getExternalContext().redirect("HomeAdm.jsf");
			
		} catch (Exception e) {
			msg = "Ha ocurrido un error al crear la cuadrilla";
		}
		
		FacesContext contextFaces = FacesContext.getCurrentInstance();
		try {
			contextFaces.getExternalContext().redirect("AltaCuadrilla.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(msg));

	}

	public int getIdCuadrilla() {
		return idCuadrilla;
	}

	public void setIdCuadrilla(int idCuadrilla) {
		this.idCuadrilla = idCuadrilla;
	}

	public int getIdZona() {
		return idZona;
	}

	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}

	public DualListModel<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(DualListModel<Empleado> empleados) {
		this.empleados = empleados;
	}

	public DualListModel<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(DualListModel<Categoria> categorias) {
		this.categorias = categorias;
	}

	private void cargaInicial() {
		ICuadrillaHndlr hndlr = new CuadrillaHndlr();
		List<Empleado> empleadosSource = hndlr.obtenerEmpleados();
		List<Empleado> empleadosTarget = hndlr.obtenerEmpleadosByCuadrilla(idCuadrilla);
		ArrayList<Empleado> arrSacar = new ArrayList<Empleado>();
		Iterator<Empleado> itera1 = empleadosSource.iterator();
		while (itera1.hasNext()) {
			Empleado rep1 = itera1.next();
			Iterator<Empleado> itera2 = empleadosTarget.iterator();
			boolean estaAsignado = false;
			while (itera2.hasNext() && !estaAsignado) {
				Empleado rep2 = itera2.next();
				if (rep1.getCedula() == rep2.getCedula()){
					arrSacar.add(rep1);
					estaAsignado = true;
				}
			}
		}
		
		for (Empleado r : arrSacar) {
			empleadosSource.remove(r);
		}
		
		setEmpleados(new DualListModel<Empleado>(empleadosSource, empleadosTarget));
		
	}


	public void asignar() {
		String msg = "";
		Severity tipoMsg = FacesMessage.SEVERITY_INFO;
		try {
			ICuadrillaHndlr hndlr = new CuadrillaHndlr();
			hndlr.asignarEmpleadoCuadrilla(idCuadrilla, empleados.getTarget());
			msg = "Se han asignado los empleados a la Cuadrilla ";
			tipoMsg = FacesMessage.SEVERITY_INFO;
		} catch (Exception e) {
			msg = "Ha ocurrido un error al asignar los empleados la Cuadrilla";
			tipoMsg = FacesMessage.SEVERITY_WARN;
		}

		try {
			ICuadrillaHndlr hndlr = new CuadrillaHndlr();
			hndlr.asignarCategoriasCuadrilla(idCuadrilla, categorias.getTarget());
			msg = "Se han asignado los empleados a la Cuadrilla ";
			tipoMsg = FacesMessage.SEVERITY_INFO;
		} catch (Exception e) {
			msg = "Ha ocurrido un error al asignar los empleados la Cuadrilla";
			tipoMsg = FacesMessage.SEVERITY_WARN;
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(tipoMsg, msg, ""));


	}


	public List<Zona> getZonas() {
		return zonas;
	}

	public void setZonas(List<Zona> zonas) {
		this.zonas = zonas;
	}

	public boolean isNoSelecZona() {
		return noSelecZona;
	}

	public void setNoSelecZona(boolean noSelecZona) {
		this.noSelecZona = noSelecZona;
	}
	
	public void cambioZona(){
		noSelecZona = (idZona == 0);
		if (!noSelecZona){
			cargaInicial();
		}
	}
	
	
}
