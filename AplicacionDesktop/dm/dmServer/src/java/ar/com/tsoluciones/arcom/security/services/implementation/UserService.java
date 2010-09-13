package ar.com.tsoluciones.arcom.security.services.implementation;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.hibernate.SessionContext;
import ar.com.tsoluciones.arcom.security.Loginable;
import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.RoleServiceInterface;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.UserServiceInterface;
import ar.com.tsoluciones.util.Cast;

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
	 * Graba un objeto User
	 *
	 * @param entity Objeto entity
	 * @throws ar.com.tsoluciones.arcom.cor.ServiceException Cuando la llamada levanta un error
	 */
	public void save(User entity) throws ServiceException {
		try {
			Criteria criteria = SessionContext.currentSession().createCriteria(User.class);
			criteria.add(Restrictions.or(Restrictions.eq("legajo", entity.getLegajo()),Restrictions.eq("username", entity.getUsername())));
			User user = (User) criteria.uniqueResult();
			if (user != null)
				throw new ServiceException("El nombre de usuario o legajo ya fue asignado al usuario " + user.getUsername());

			ar.com.tsoluciones.arcom.security.services.UserService.saveUser(entity);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar guardar objeto User", e, entity);
		}
	}

	/**
	 * Actualiza un objeto User
	 *
	 * @param entity Objeto entity a actualizar
	 * @throws ar.com.tsoluciones.arcom.cor.ServiceException cuando el intento levanta un error
	 */
	public void update(User entity) throws ServiceException {
		try {
			Criteria criteria = SessionContext.currentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("legajo", entity.getLegajo()));
			criteria.add(Restrictions.not(Restrictions.eq("id", entity.getId())));
			User user = (User) criteria.uniqueResult();
			if (user != null)
				throw new ServiceException("El legajo asignado a este usuario ya fue asignado al usuario " + user.getUsername());

			ar.com.tsoluciones.arcom.security.services.UserService.updateUser(entity);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar actualizar objeto User", e, entity);
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
			throw new InternalErrorException("Error al intentar otener todos los objetos User", e, username);
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

	/**
	 * Resetea el password de un usuario
	 * @param userId El usuario
	 * @return El password
	 * @throws ServiceException Error al resetear el password
	 */
	public String resetPassword(Long userId) throws ServiceException {
		Loginable loginable = get(userId);
		return loginable.resetPassword();
	}

	/**
	 * Agrega un rol
	 * @param roleId Id de rol
	 * @param userId Id de usuario
	 * @throws ServiceException Cuando hay un error
	 */
	public void addRole(Long userId, Long roleId) throws ServiceException {
		// Tomar rol
		RoleServiceInterface roleServiceInterface = new RoleService();
		Role role = roleServiceInterface.get(roleId);

		// Agregar rol
		UserServiceInterface userServiceInterface = new UserService();
		User user = userServiceInterface.get(userId);
		user.addRole(role);
		userServiceInterface.update(user);
	}

	/**
	 * Elimina el rol
	 * @param userId Id de usuario
	 * @param roleId Id de rol
	 * @throws ServiceException Cuando hay un error
	 */
	public void deleteRole(Long userId, Long roleId) throws ServiceException {
		// Tomar rol
		RoleServiceInterface roleServiceInterface = new RoleService();
		Role role = roleServiceInterface.get(roleId);

		// Quitarlo
		UserServiceInterface userServiceInterface = new UserService();
		User user = userServiceInterface.get(userId);
		user.removeRole(role);
		userServiceInterface.update(user);
	}

	public List<User> getUser(Long roleId, Long agencyId) {
		Session session = SessionContext.currentSession();
		Criteria criteria = session.createCriteria(User.class)
			.createAlias("roles", "role")
			.add(Restrictions.eq("agency.id", agencyId))
			.add(Restrictions.eq("role.id", roleId));
		return Cast.castList(User.class, criteria.list());		
	}
}
