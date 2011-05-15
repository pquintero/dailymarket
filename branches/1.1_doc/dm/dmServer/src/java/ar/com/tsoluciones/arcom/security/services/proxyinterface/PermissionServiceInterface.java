package ar.com.tsoluciones.arcom.security.services.proxyinterface;

import java.util.List;

import ar.com.tsoluciones.arcom.security.Permission;
import ar.com.tsoluciones.arcom.security.User;

/**
 * <p>
 * Funcionalidad de permisos a implementar
 * </p>
 *
 * @author despada
 * @version Aug 30, 2005, 2:55:21 PM
 * @see 
 */
public interface PermissionServiceInterface
{
	/**
	 * <p> Devuelve un permiso en base al Id recibido </p>
	 * @param id del Permission
	 * @return Permission - El permiso correspondiente al ID
	 */
	public Permission get(Long id);

	/**
	 * Obtiene el permiso por nombre
	 * @param name Nombre
	 * @return Permiso
	 */
	public Permission getByName(String name);

	/**
	 * <p> Devuelve la lista de TODOS los permisos </p>
	 * @return List - La lista de permisos
	 */
	public List<Permission> getPermissionsAll();

	/**
	 * <p> Actualiza o crea una nueva instancia de Permiso en la base de datos</p>
	 * @param permission permiso a ser persistido
	 */
	public void save(Permission permission);

	/**
	 * <p> Actualiza o crea una nueva instancia de Permiso en la base de datos</p>
	 * @param permission El permiso a ser persistido
	 */
	public void update(Permission permission);

	/**
	 * Obtiene usuarios con el permiso
	 * @param permission Permiso
	 * @return Lista de usuarios
	 */
	public List<User> getUserByPermission(Permission permission);

	/**
	 * Encuentra los permisos por nombre
	 * @param name Nombre
	 * @return Permisos
	 */
	public Permission findByName(String name);

    /**
	 * Elimina un Permiso físicamente
	 *
	 * @param permission  a eliminar
	 */
	public void delete(Permission permission);


}
