package presentacion;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
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
		IUsuarioHndlr hndlrUsr = new UsuarioHndlr();
		Severity tipoMsg = FacesMessage.SEVERITY_WARN;
		String msg = "";
		if (hndlrUsr.loginUsr(mail, pass) ){
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mailusu", mail);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreusu", hndlrUsr.getUsuarioByMail(mail).getNombre());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rolusu", hndlrUsr.getUsuarioByMail(mail).getRol());
			estaLogueado = true;			
			
		}
		else{
			estaLogueado= false;
			msg ="Usuario y contraseña no coinciden";			
			addMessage(msg, tipoMsg);
		}
				
		if (estaLogueado == true ){
			if (hndlrUsr.getUsuarioByMail(mail).getRol() == 0)
				try {
					msg = "Bienvenido " + hndlrUsr.getUsuarioByMail(mail).getNombre().trim() + " al sistema GeoReclamos";
					FacesContext.getCurrentInstance().getExternalContext().redirect("HomeUsu.jsf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					msg = "Bienvenido " + hndlrUsr.getUsuarioByMail(mail).getNombre().trim() + " al sistema GeoReclamos";
					addMessage(msg, tipoMsg);
					FacesContext.getCurrentInstance().getExternalContext().redirect("HomeAdm.jsf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			
		
	}

    public void addMessage(String summary, Severity tipoMsg ) {
        FacesMessage message = new FacesMessage(tipoMsg, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
