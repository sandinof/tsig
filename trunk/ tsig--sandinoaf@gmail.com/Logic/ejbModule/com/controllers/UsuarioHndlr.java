package com.controllers;

import java.util.List;

import com.DAO.UsuarioDAO;
import com.entities.Usuario;
import com.utilities.StringEncrypter;

public class UsuarioHndlr implements IUsuarioHndlr {

	private UsuarioDAO usrDAO = new UsuarioDAO();
	
	@Override
	public void crearUsuario(Usuario usr) throws Exception {
		try {
			String pass = StringEncrypter.md5(usr.getPassword());
			usr.setPassword(pass);
			usrDAO.crearUsuario(usr);
		} catch (Exception e) {
			 throw e;
		}
		
	}

	@Override
	public void modificarUsuario(Usuario usr) {
		usrDAO.modificarUsuario(usr);
	}

	@Override
	public void eliminarUsuario(String mail) {
		usrDAO.eliminarUsuario(mail);
	}

	@Override
	public Usuario getUsuarioById(int id) {
		return usrDAO.getUsuarioById(id);
	}

	@Override
	public Usuario getUsuarioByMail(String mail) {
		return usrDAO.getUsuarioByMail(mail);
	}

	@Override
	public List<Usuario> getUsuarios() {
		return usrDAO.getUsuarios();
	}

	@Override
	public boolean loginUsr(String mail, String password) {
		Usuario usr = getUsuarioByMail(mail);
		
		boolean loginCorrecto = false;
		String passMD5 = "";
		
		if (usr != null){
			try {
				passMD5 = StringEncrypter.md5(password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (passMD5.equals(usr.getPassword())){
				loginCorrecto = true;
			}
			else
				loginCorrecto = false;
		}
		else
			loginCorrecto = false;

		return loginCorrecto;
	}

}
