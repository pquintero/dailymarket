package ar.com.tsoluciones.arcom.security.services;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 */

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.type.Type;

import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.util.Cast;

/*
 * <p> Brinda los servicios necesarios para la administración de usuarios <p/>
 *
 * @author Jporporatto, despada (modificaciones de performance)
 * @version 1.0 - May 18, 2004
 */

public class UserService {

	/**
	 * <p> Constructor por defecto </p>
	 */
	private UserService() {
		//
	}

	/**
	 * <p> Devuelve un usuario en base al Id recibido </p>
	 *
	 * @param id = id del Usuario
	 * @return User - El usuario correspondiente al ID
	 */
	public static User getUser(Long id) throws HibernateException {
		User user = HibernateService.getObject(User.class, id, LockMode.READ);

		if (user == null)
			return null;

		// Inicializar colecciones lazy
		Hibernate.initialize(user.getAddresses());
		Hibernate.initialize(user.getEmails());
		Hibernate.initialize(user.getPhones());
		Hibernate.initialize(user.getPermissions());
		Hibernate.initialize(user.getRoles());

		// Obtener todos los permisos
		Role[] roleArray = user.getRoles().toArray(new Role[0]);
		for (int i = 0; i < roleArray.length; i++) {
			Hibernate.initialize(roleArray[i].getPermissions());
		}
		return user;
	}

	/**
	 * Obtiene un usuario por su nombre
	 *
	 * @param username Nombre del usuario
	 * @return Usuario
	 */
	public static User getUserByUserName(String username) throws HibernateException {
		List<User> l = Cast.castList(User.class, HibernateService.findByFilter(User.class, new String[] { "username", "deleted" }, new Object[] { username, Boolean.FALSE }, new Type[] {
				Hibernate.STRING, Hibernate.BOOLEAN }));

		if (l == null || l.size() == 0)
			return null;

		User listedUser = l.get(0);

		return getUser(listedUser.getId());
	}

	/**
	 * Obtiene un usuario por su nombre
	 *
	 * @param legajo El legajo buscado
	 * @return Usuario
	 */
	public static User getUserByLegajo(String legajo) throws HibernateException {
		User user = null;

		List<User> list = HibernateService.findByFilter(User.class, "legajo", legajo);

		// Obtener usuarios con distintos legajos (no deberia haber mas de uno, pero por compatibilidad hacia atras lo acepto)
		for (int i = 0; i < list.size(); i++) {
			user = list.get(i);
		}

		// Obtener primero el usuario por compatibilidad hacia atras. Si no hay solo usuario, devolver usuario-empleado.
		if (user != null)
			return getUser(user.getId());

		return null;
	}

	public static List<User> getAdministrators() throws HibernateException {
		return HibernateService.findByFilter(User.class, "sysAdmin", Boolean.valueOf(true), Hibernate.BOOLEAN);
	}

	public static List<User> getByLastName(String apellido) throws HibernateException {
		return HibernateService.findByFilterLike(User.class, "lastName", apellido);
	}

	/**
	 * <p> Devuelve la lista de usuarios no eliminados(deleted = false)</p>
	 *
	 * @return List - La lista de usuarios
	 */
	public static List<User> getUsers() throws HibernateException {
		return HibernateService.findAllExludeDeleted(User.class, false);
	}

	/**
	 * <p> Llama al metodo que Actualiza o crea una nueva instancia de
	 * Usuario en la base de datos</p>
	 *
	 * @param user - El usuario a ser persistido
	 */
	public static void saveUser(User user) throws HibernateException {
		HibernateService.saveObject(user);
	}

	/**
	 * <p> Llama al metodo que Actualiza usuarios </p>
	 *
	 * @param user - El usuario a ser persistido
	 */
	public static void updateUser(User user) throws HibernateException {
		HibernateService.updateObject(user);
	}

}
