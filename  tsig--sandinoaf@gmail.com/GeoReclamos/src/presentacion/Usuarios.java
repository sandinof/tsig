package presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import com.controllers.UsuarioHndlr;
import com.controllers.IUsuarioHndlr;
import com.entities.Usuario;

@ManagedBean
public class Usuarios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> usrs;
	private Usuario usuarioSeleccionado;

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
	
		IUsuarioHndlr hndlrEnv = new UsuarioHndlr();
		usrs = hndlrEnv.getUsuarios();
	}
	
	public Usuarios() {
		IUsuarioHndlr hndlrEnv = new UsuarioHndlr();
		usrs = hndlrEnv.getUsuarios();
	}

	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario UsuarioSeleccionado) {
		this.usuarioSeleccionado = UsuarioSeleccionado;
	}

	public void eliminar() {

		IUsuarioHndlr hndlrUsuario = new UsuarioHndlr();
		hndlrUsuario.eliminarUsuario(usuarioSeleccionado.getMail());
		usrs.remove(usuarioSeleccionado);
		addMessage(new FacesMessage("se ha eliminado el usuario seleccionado"));
	}

	public void editar(int idzona) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("UsuarioAlta.jsf?id=" + idzona);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Usuario> getUsrs() {
		return usrs;
	}

	public void setUsrs(List<Usuario> usrs) {
		this.usrs = usrs;
	}

}
