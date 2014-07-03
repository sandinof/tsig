package presentacion;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.controllers.EmpleadoHndlr;
import com.controllers.IEmpleadoHndlr;
import com.entities.Empleado;

@ManagedBean
public class AltaEmpleado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private int tel;
	private int cedula;
	
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public AltaEmpleado() {
		nombre = "";
		tel = 0;
		cedula = 0;		
	}

	public void altaEmpleado() {
		String msg = "";
		Severity tipoMsg = FacesMessage.SEVERITY_INFO;
		
		IEmpleadoHndlr hndlrEmp = new EmpleadoHndlr();

		Empleado emp = new Empleado();

		emp.setNombre(nombre);
		emp.setTelefono(tel);
		emp.setCedula(cedula);
		
		try {
			hndlrEmp.crearEmpleado(emp);
			msg = "Empleado " + nombre + " creado correctamente.";
			
			nombre = "";
			tel = 0;
			cedula = 0;
			
		} catch (Exception e) {
				
			if (e.getMessage().contains("existe la llave") ){
				msg = "Ya esta creado el empleado en el sistema";
			}else{
				msg = "Ha ocurrido un error al crear el empleado \n" + e.getMessage();
			}
			tipoMsg = FacesMessage.SEVERITY_ERROR;
		}
		
		addMessage(msg, tipoMsg);		

	}
	
    public void addMessage(String summary, Severity tipoMsg ) {
        FacesMessage message = new FacesMessage(tipoMsg, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
