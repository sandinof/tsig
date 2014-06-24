package presentacion;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
		
	}

	public void altaEmpleado() {
		
		IEmpleadoHndlr hndlrEmp = new EmpleadoHndlr();

		Empleado emp = new Empleado();

		emp.setNombre(nombre);
		emp.setTelefono(tel);
		emp.setCedula(cedula);
		
		String msg = "";
		
		try {
			hndlrEmp.crearEmpleado(emp);
			msg = "Empleado " + nombre + " creado correctamente.";
			
			//FacesContext contextFaces = FacesContext.getCurrentInstance();
			//contextFaces.getExternalContext().redirect("HomeAdm.jsf");
			nombre = "";
			tel = 0;
			cedula = 0;
			
			FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(msg));			
			
		} catch (Exception e) {
			msg = "Ha ocurrido un error al crear el empleado";
		}
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));

	}
	
	public void LogOut(){
		try {
			ExternalContext ctx =FacesContext.getCurrentInstance().getExternalContext();
			String ctxPath =((ServletContext) ctx.getContext()).getContextPath();
			((HttpSession) ctx.getSession(false)).invalidate();
		   
		    ctx.redirect(ctxPath + "/login.xhtml");
						
		}
	 	catch (IOException e) {
	 		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Error, Vuelva a intentar cerrar sesion"));
	 		
		}
	}

	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
