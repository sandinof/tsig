package presentacion;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;

import com.controllers.IUsuarioHndlr;
import com.controllers.UsuarioHndlr;

@ManagedBean
public class LogIn implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mail;
	private String pass;
	public LogIn() {}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public void ingresar(javax.faces.event.ActionEvent a){
		boolean estaLogueado = false;
		FacesMessage msg = null;
		IUsuarioHndlr hndlrUsr = new UsuarioHndlr();
		
		if (hndlrUsr.loginUsr(mail, pass) ){
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mailusu", mail);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreusu", hndlrUsr.getUsuarioByMail(mail).getNombre());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rolusu", hndlrUsr.getUsuarioByMail(mail).getRol());
			estaLogueado = true;
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido al sistema GeoReclamos", mail);
		}
		else{
			estaLogueado= false;
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Credenciales inválidas");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, msg);
		
		FacesContext contextFaces = FacesContext.getCurrentInstance();
		
		if (estaLogueado == true ){
			if (hndlrUsr.getUsuarioByMail(mail).getRol() == 0)
				try {
					contextFaces.getExternalContext().redirect("HomeUsu.jsf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					contextFaces.getExternalContext().redirect("HomeAdm.jsf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			
		
	}

}
