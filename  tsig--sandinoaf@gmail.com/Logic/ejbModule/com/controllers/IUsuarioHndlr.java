package com.controllers;

import java.util.List;

import com.entities.Usuario;

public interface IUsuarioHndlr {
	
	public void crearUsuario(Usuario usr) throws Exception;
	
	public void modificarUsuario(Usuario usr);
	
	public void eliminarUsuario(String mail);
	
	public Usuario getUsuarioById(int id);
	
	public Usuario getUsuarioByMail(String mail);
	
	public List<Usuario> getUsuarios();
	
	public boolean loginUsr(String mail, String password);	
}
