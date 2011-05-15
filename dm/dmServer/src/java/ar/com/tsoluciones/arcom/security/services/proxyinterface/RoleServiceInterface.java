package ar.com.tsoluciones.arcom.security.services.proxyinterface;

import java.util.List;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.Role;
import ar.com.tsoluciones.arcom.security.User;


/**
 * <p>
 * Establece la funcionalidad para un objeto rol
 * </p>
 *
 * @author despada
 * @version Aug 24, 2005, 8:59:49 AM
 * @see 
 */
public interface RoleServiceInterface
{
	/**
	 * <p> Devuelve un rol en base al Id recibido </p>
	 * @param id del Permission
	 * @return Role - El rol correspondiente al ID
	 */
	public Role get(Long id);

	/**
	 * Obtiene el rol por nombre
	 * @param name Nombre
	 * @return Rol
	 */
	public Role getByName(String name);

	/**
	 * <p> Devuelve la lista de roles no eliminados(deleted = false)</p>
	 * @return List - La lista de roles
	 */				
	public List<Role> getRoles();


	/**
	 * <p> Devuelve la lista de TODOS los roles </p>
	 * @return List - La lista de permisos
	 */
	public List<Role> getRolesAll();


	/**
	 * <p> Actualiza o crea una nueva instancia de Rol en la base de datos</p>
	 * @param role rol a ser persistido
	 */
	public void save(Role role);

	/**
	 * <p> Actualiza o crea una nueva instancia de Rol en la base de datos</p>
	 * @param role El rol a ser persistido
	 */
	public void update(Role role);

	/**
	 * Obtiene usuarios con el rol
	 * @param role Rol
	 * @return Lista de usuarios
	 * @throws ServiceException Cuando hay un error
	 */
	public List<User> getUserByRole(Role role);

	/**
	 * Encuentra los roles por nombre
	 * @param name Nombre
	 * @return Roles
	 */
	public Role findByName(String name);

    /**
	 * Elimina un Rol físicamente
	 *
	 * @param Rol a eliminar
	 */
	public void delete(Role role);

}