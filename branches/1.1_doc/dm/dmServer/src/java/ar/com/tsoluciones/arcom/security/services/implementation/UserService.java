package ar.com.tsoluciones.arcom.security.services.implementation;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */


import java.util.List;

import org.hibernate.HibernateException;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;

/**
 * <p>Administra el entity User</p>
 *
 * @author despada
 * @version 1.0, Dec 27, 2004, 10:31:04 AM
 * @see ar.com.tsoluciones.arcom.security.User
 */
public class UserService implements UserServiceInterface {

	/**
	 * Obtiene un User
	 *
	 * @param id id del User
	 * @return objeto User
	 */
	public User get(Long id) {
		try {
			return ar.com.tsoluciones.arcom.security.services.UserService.getUser(id);

		} catch (HibernateException e) {
			throw new InternalErrorException("Error buscando el usuario", e, id);
		}
	}

	/**
	 * Elimina un usuario físicamente
	 * @param entity Usuario a eliminar
	 * @throws ServiceException Cuando hay un error
	 */
	public void delete(User entity) {
		try {
			HibernateService.deleteObject(entity);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar eliminar usuario", e, entity);
		}
	}

	/**
	 * Obtiene todos los objetos User
	 *
	 * @return array de User
	 */
	public User[] getAll() {
		try {
			return ar.com.tsoluciones.arcom.security.services.UserService.getUsers().toArray(new User[0]);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar otener todos los objetos User", e);
		}
	}

	  /**
	   * Obtiene todos los objetos User (incluso los borrados logicamente)
	   *
	   * @return array de User
	   */
	  public User[] getAllWithDeleted() {
		try {
			return HibernateService.findAll(User.class).toArray(new User[0]);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar otener todos los objetos User", e);
		}
	}

	/**
	 * Obtiene un usuario por su nombre
	 * @param username Nombre del usuario
	 * @return  Usuario
	 */
	public User getUserByUserName(String username) {
		try {
			return ar.com.tsoluciones.arcom.security.services.UserService.getUserByUserName(username);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar obtener todos los objetos User", e, username);
		}
	}

	/**
	 * Obtiene un usuario por su nombre
	 * @param legajo El legajo buscado
	 * @return  Usuario
	 */
	public User getUserByLegajo(String legajo) {
		try {
			return ar.com.tsoluciones.arcom.security.services.UserService.getUserByLegajo(legajo);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar otener el objeto User", e);
		}
	}

	public void update(User entity) {
		try {
			HibernateService.updateObject(entity);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar actualizar el objeto User", e);
		}
	}

}
