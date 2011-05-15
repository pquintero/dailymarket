package ar.com.tsoluciones.arcom.security.services.implementation;

import java.util.List;

import org.hibernate.HibernateException;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.security.Permission;
import ar.com.tsoluciones.arcom.security.User;
import ar.com.tsoluciones.arcom.security.services.proxyinterface.PermissionServiceInterface;

/*
 * <p>
 * Implementa la funcionalidad de permisos
 * </p>
 *
 * @author despada
 * @version Aug 30, 2005, 2:55:32 PM
 * @see
 */

public class PermissionService implements PermissionServiceInterface {
	public Permission get(Long id) {
		try {
			return ar.com.tsoluciones.arcom.security.services.PermissionService.getPermission(id);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al obtener el permiso", e, id);
		}
	}

	public Permission getByName(String name) {
		try {
			return ar.com.tsoluciones.arcom.security.services.PermissionService.getByName(name);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al obtener el permiso", e, name);
		}
	}

	public List<Permission> getPermissionsAll() {
		try {
			return ar.com.tsoluciones.arcom.security.services.PermissionService.getPermissionsAll();
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al obtener permisos", e);
		}
	}

	public void save(Permission permission) {
		try {
			ar.com.tsoluciones.arcom.security.services.PermissionService.savePermission(permission);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al grabar permiso, permiso duplicado", e, permission);
		}
	}

	public void update(Permission permission) {
		try {
			ar.com.tsoluciones.arcom.security.services.PermissionService.updatePermission(permission);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al actualizar permiso", e, permission);
		}
	}

	public List<User> getUserByPermission(Permission permission) {
		try {
			return ar.com.tsoluciones.arcom.security.services.PermissionService.getUserByPermission(permission);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al buscar usuario por permiso", e, permission);
		}
	}

	public Permission findByName(String name) {
		try {
			return ar.com.tsoluciones.arcom.security.services.PermissionService.findByName(name);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al buscar permiso", e, name);
		}
	}

	/**
	 * Elimina un usuario físicamente
	 *
	 * @param entity Usuario a eliminar
	 */
	public void delete(Permission entity) {
		try {

			HibernateService.deleteObject(entity);
		} catch (HibernateException e) {
			throw new InternalErrorException("Error al intentar eliminar permuiso", e, entity);
		}
	}

}
