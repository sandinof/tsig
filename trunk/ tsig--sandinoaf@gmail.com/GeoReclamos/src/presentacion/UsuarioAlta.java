package presentacion;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.controllers.IUsuarioHndlr;
import com.controllers.UsuarioHndlr;
import com.entities.Usuario;

@ManagedBean
public class UsuarioAlta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String mail;
	private String password;
	private int rol;
	
	final int ADMINISTRADOR = 1;
	final int USUARIO = 0;
	
	@PostConstruct
	public void init() {		
		nombre = "";
		mail = "";
		password = "";

	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public UsuarioAlta() {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void altaUsuario() {
							
		IUsuarioHndlr hndlrUsr = new UsuarioHndlr();

		Usuario usr = new Usuario();
		String msg = "";
		Severity tipoMsg = FacesMessage.SEVERITY_INFO;
		
		
		usr.setNombre(nombre);
		usr.setMail(mail);
		usr.setPassword(password);
		
		String mailSession= "";
		
		mailSession = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mailusu");
		if ( mailSession == null ) // Si no hay administrador loguado se crea un usuario normal
			usr.setRol(USUARIO);
		else
			usr.setRol(ADMINISTRADOR);

		try {
			
			hndlrUsr.crearUsuario(usr);
			
			FacesContext contextFaces = FacesContext.getCurrentInstance();
			msg = "Usuario creado: " + nombre;
			if (usr.getRol() == USUARIO){
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mailusu", mail);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreusu", nombre);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rolusu", rol);
			
				contextFaces.getExternalContext().redirect("HomeUsu.jsf");
			}
			else
				contextFaces.getExternalContext().redirect("HomeAdm.jsf");
			
		} catch (Exception e) {
			tipoMsg = FacesMessage.SEVERITY_ERROR;
			if (e.getMessage().contains("existe la llave") ){
				msg = "Ya existe el mail en el sistema";
			}else{
				msg = "Ha ocurrido un error al crear el usuario" + e.getMessage();
			}			

		}
		

	}
	

    public void addMessage(String summary, Severity tipoMsg ) {
        FacesMessage message = new FacesMessage(tipoMsg, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
