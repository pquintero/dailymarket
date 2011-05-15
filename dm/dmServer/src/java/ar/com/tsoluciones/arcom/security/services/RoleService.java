package ar.com.tsoluciones.arcom.security.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.hibernate.SessionContext;
import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.util.Cast;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p> Brinda los servicios necesarios para la administración de roles <p/>
 *
 * @author Jporporatto - jporporatto@artech-consulting.com
 * @version 1.0 - May 18, 2004
 * @see
 */

public class RoleService {
	/**
	 * <p> Constructor por defecto </p>
	 */
	private RoleService() {
		//
	}

	/**
	 * <p> Devuelve un rol en base al Id recibido </p>
	 *
	 * @param id = id del Usuario
	 * @return El rol correspondiente al ID
	 */
	public static Role getRole(Long id) throws HibernateException {
		Role role = HibernateService.getObject(Role.class, id, LockMode.NONE);

		// Inicializar colecciones
		Hibernate.initialize(role.getPermissions());

		return role;
	}

	/**
	 * <p> Devuelve la lista de roles no eliminados(deleted = false)</p>
	 *
	 * @return List - La lista de roles
	 */

	public static List<Role> getRoles() throws HibernateException {
		return HibernateService.findAllExludeDeleted(Role.class, false);
	}

	/**
	 * <p> Devuelve la lista de roles (todos)</p>
	 *
	 * @return List - La lista de roles
	 */
	public static List<Role> getRolesAll() throws HibernateException {
		return HibernateService.findAll(Role.class);
	}

	/**
	 * <p> Actualiza o crea una nueva instancia de Role en la base de datos</p>
	 *
	 * @param role El rol a ser persistido
	 */
	public static void saveRole(Role role) throws HibernateException {
		HibernateService.saveObject(role);
	}

	/**
	 * <p> Actualiza o crea una nueva instancia de Role en la base de datos</p>
	 *
	 * @param role El rol a ser persistido
	 */
	public static void updateRole(Role role) throws HibernateException {
		HibernateService.updateObject(role);
	}

	/**
	 * Busca la instancia por clave principal
	 *
	 * @param name nombre unico
	 * @return objeto si fue encontrado o null
	 * @throws HibernateException error recuperar.
	 */

	public static Role getByName(String name) throws HibernateException {

		return HibernateService.findByFilterUnique(Role.class, "name", name);
	}

	/**
	 * Busca usuarios de un rol
	 * @param role Rol de los usuarios
	 * @return Lista de usuarios
	 * @throws HibernateException Cuando algo sale mal
	 */

	public static List<User> getUserByRole(Role role) throws HibernateException {
		Criteria criteria = SessionContext.currentSession().createCriteria(User.class)
			.createAlias("roles", "r")
			.add(Restrictions.eq("r.id", role.getId()));
		return Cast.castList(User.class, criteria.list());

	}

	public static Role findByName(String name) throws HibernateException {
		return HibernateService.findByFilterUnique(Role.class, "name", name);
	}

	public static List<User> findUsersByRole(Role role) throws HibernateException {
		Query q = SessionContext.currentSession().createQuery("select u1 from User as u1 inner join u1.roles as r1 where r1.id = " + role.getId());
		return Cast.castList(User.class, q.list());
	}

}
