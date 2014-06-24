package com.DAO;

import java.util.Iterator;
import java.util.List;
import javax.persistence.*;

import com.entities.Usuario;

public class UsuarioDAO {
	
	private EntityManager em = Persistence.createEntityManagerFactory("Persistence").createEntityManager();
	
	public void crearUsuario(Usuario usr) {
		
		try {
			em.getTransaction().begin();
			em.persist(usr);
			em.merge(usr);
			em.flush();
			em.getTransaction().commit();
			System.out.println("Usuario creado correctamente");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
			throw ex;
		}

	}
	
	public void modificarUsuario(Usuario usr) {
		
		try {
			em.getTransaction().begin();
			em.merge(usr);
			em.flush();
			em.getTransaction().commit();
			System.out.println("Usuario modificado correctamente");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}

	}
	
	public void eliminarUsuario(String mail)
	{
		em.getTransaction().begin();
		try{
			
			Usuario usrElim = em.find(Usuario.class, mail);
			
			em.remove(usrElim);
			em.getTransaction().commit();
			
			System.out.println("Eliminado correctamente");
		}catch(Exception ex)
		{
			em.getTransaction().rollback();
			System.out.println("Ha ocurrido un error al Eliminar el Evento");
			ex.printStackTrace();
		}
		
		finally
		{
			em.close();
		}
	}
	
	public Usuario getUsuarioById(int id){
		Usuario usr = null;
		try {
			usr = em.find(Usuario.class, id);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("\n" + ex.getMessage());
		}
		return usr;
	}
	
	public Usuario getUsuarioByMail(String mail) {
		
		try {
			EntityManagerFactory em2 = Persistence
					.createEntityManagerFactory("Persistence");
			em = em2.createEntityManager();
			List<Usuario> resultList = (List<Usuario>) em.createQuery("select u from Usuario u", Usuario.class).getResultList();
			Usuario usr, usr2 = null;
			Iterator<Usuario> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				usr = iterator.next();
				if (usr.getMail().equals(mail))
					usr2 = usr;
			}
			return usr2;
		} catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}
		
	}
	
	public List<Usuario> getUsuarios(){
		List<Usuario> resultList = null;
		
		try {
			EntityManagerFactory em2 = Persistence
					.createEntityManagerFactory("Persistence");
			em = em2.createEntityManager();
			resultList = (List<Usuario>) em.createQuery("select u from Usuario u", Usuario.class).getResultList();
			
		} catch (Exception e) {
			System.out.print("\n" + e.getMessage());
			return null;
		}
		
		return resultList;
	}

}
