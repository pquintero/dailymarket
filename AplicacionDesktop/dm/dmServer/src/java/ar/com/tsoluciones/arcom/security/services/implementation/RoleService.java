package ar.com.tsoluciones.arcom.security.services.implementation;

import java.util.List;

import org.hibernate.HibernateException;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.RoleServiceInterface;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p> Brinda los servicios necesarios para la administración de roles <p/>
 *
 * @author Jporporatto - jporporatto@artech-consulting.com
 * @version 1.0 - May 18, 2004
 * @see
 */

public class RoleService implements RoleServiceInterface{
	/**
	 * <p> Devuelve un rol en base al Id recibido </p>
	 *
	 * @param id = id del Usuario
	 * @return El rol correspondiente al ID
	 */
	public Role get(Long id){
        try {
            return ar.com.tsoluciones.arcom.security.services.RoleService.getRole(id);
        } catch (HibernateException e) {
            throw new InternalErrorException("Error al obtener el rol", e,id);
        }
    }

	/**
	 * <p> Devuelve la lista de roles no eliminados(deleted = false)</p>
	 *
	 * @return List - La lista de roles
	 */

	public Role getByName(String name){
        try {
            return ar.com.tsoluciones.arcom.security.services.RoleService.getByName(name);
        } catch (HibernateException e) {
            throw new InternalErrorException("Error al obtener el rol", e,name);
        }
    }


	 public List<Role> getRoles(){
	        try {
	            return ar.com.tsoluciones.arcom.security.services.RoleService.getRoles();
	        } catch (HibernateException e) {
	            throw new InternalErrorException("Error al obtener roles", e);
	        }
	    }

	 public List<Role> getRolesAll(){
	        try {
	            return ar.com.tsoluciones.arcom.security.services.RoleService.getRolesAll();
	        } catch (HibernateException e) {
	            throw new InternalErrorException("Error al obtener roles", e);
	        }
	    }

	/**
	 * <p> Actualiza o crea una nueva instancia de Role en la base de datos</p>
	 *
	 * @param role El rol a ser persistido
	 */

	 public void save(Role role){
	        try {
	            ar.com.tsoluciones.arcom.security.services.RoleService.saveRole(role);
	        } catch (HibernateException e) {
	            throw new InternalErrorException("Error al grabar rol, rol duplicado", e,role);
	        }
	    }

	/**
	 * <p> Actualiza o crea una nueva instancia de Role en la base de datos</p>
	 *
	 * @param role El rol a ser persistido
	 */

	 public void update(Role role){
	        try {
	            ar.com.tsoluciones.arcom.security.services.RoleService.updateRole(role);
	        } catch (HibernateException e) {
	            throw new InternalErrorException("Error al actualizar rol", e,role);
	        }
	    }

	/**
	 * Busca la instancia por clave principal
	 *
	 * @param name nombre unico
	 * @return objeto si fue encontrado o null
	 */

	public Role findByName(String role){
        try {
            return ar.com.tsoluciones.arcom.security.services.RoleService.findByName(role);
        } catch (HibernateException e) {
            throw new InternalErrorException("Error al buscar role", e, role);
        }
    }

	/**
	 * Busca usuarios de un rol
	 * @param role Rol de los usuarios
	 * @return Lista de usuarios
	 */

	public List<User> getUserByRole(Role role){
        try {
            return ar.com.tsoluciones.arcom.security.services.RoleService.getUserByRole(role);
        } catch (HibernateException e) {
            throw new InternalErrorException("Error al buscar usuario por rol", e, role);
        }
	}
	public void delete(Role entity){
		try {
			HibernateService.deleteObject(entity);
    	} catch (HibernateException e) {
    		throw new InternalErrorException("Error al intentar eliminar rol", e, entity);
    	  }
	}
}
