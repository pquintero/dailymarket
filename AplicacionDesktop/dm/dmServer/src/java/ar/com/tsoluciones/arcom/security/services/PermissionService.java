package ar.com.tsoluciones.arcom.security.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;

import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.security.Permission;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.util.Cast;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p> Brinda los servicios necesarios para la administración de permisos <p/>
 *
 * @author Jporporatto - jporporatto@artech-consulting.com
 * @version 1.0 - May 18, 2004
 * @see
 */
public class PermissionService {
	/**
	 * <p> Constructor por defecto </p>
	 */
	private PermissionService() {
		//
	}

	/**
	 * <p> Devuelve un permiso en base al Id recibido </p>
	 *
	 * @param id id del Permission
	 * @return Permission - El permiso correspondiente al ID
	 */
	public static Permission getPermission(Long id) throws HibernateException {
		return HibernateService.getObject(Permission.class, id, LockMode.NONE);
	}

	public static Permission getByName(String name) throws HibernateException {

		return HibernateService.findByFilterUnique(Permission.class, "name", name);
	}

	/**
	 * <p> Devuelve la lista de permisos (todos)</p>
	 *
	 * @return List - La lista de permisos
	 */
	public static List<Permission> getPermissionsAll() throws HibernateException {
		return HibernateService.findAll(Permission.class);
	}

	/**
	 * <p> Actualiza o crea una nueva instancia de Permiso en la base de datos</p>
	 *
	 * @param permission permiso a ser persistido
	 */
	public static void savePermission(Permission permission) throws HibernateException {
		HibernateService.saveObject(permission);
	}

	/**
	 * <p> Actualiza o crea una nueva instancia de Permiso en la base de datos</p>
	 *
	 * @param permission El permiso a ser persistido
	 */
	public static void updatePermission(Permission permission) throws HibernateException {
		HibernateService.updateObject(permission);
	}

	public static List<User> getUserByPermission(Permission permission) throws HibernateException {

		List<User> allPermission = new ArrayList<User>();

		List<User> userPermission = Cast.castList(User.class, HibernateService.findByFilters("select u from ar.com.tsoluciones.arcom.security.User u join u.permissions p "
				+ "where p.name = '" + permission.getName() + "'"));

		List<User> rolePermission = Cast.castList(User.class, HibernateService.findByFilters("select u from	ar.com.tsoluciones.arcom.security.User u " + "		join u.roles r "
				+ "		join r.permissions p " + "where 	p.name = '" + permission.getName() + "'"));

		allPermission.addAll(userPermission);

		allPermission.addAll(rolePermission);

		return allPermission;

	}

	public static Permission findByName(String name) throws HibernateException {
		return HibernateService.findByFilterUnique(Permission.class, "name", name);
	}
}
