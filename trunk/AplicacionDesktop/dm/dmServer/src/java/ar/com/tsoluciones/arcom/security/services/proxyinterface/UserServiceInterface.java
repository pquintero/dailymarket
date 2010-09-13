package ar.com.tsoluciones.arcom.security.services.proxyinterface;


import java.util.List;

import ar.com.tsoluciones.arcom.cor.ServiceException;
import ar.com.tsoluciones.arcom.security.User;

/**
 * <p>Establece la funcionalidad a implementar para la administracion del entity User</p>
 *
 * @author despada
 * @version 1.0, Dec 27, 2004, 10:30:58 AM
 * @see ar.com.tsoluciones.arcom.security.User
 */
public interface UserServiceInterface
{

  /**
   * Obtiene un User
   *
   * @param id id del User
   * @return objeto User
   */
  public User get(Long id);

  /**
   * Graba un objeto User
   *
   * @param entity Objeto entity
   * @throws ar.com.tsoluciones.arcom.cor.ServiceException
   *          Cuando la llamada levanta un error
   */
  public void save(User entity) throws ServiceException;

  /**
   * Actualiza un objeto User
   *
   * @param entity Objeto entity a actualizar
   * @throws ar.com.tsoluciones.arcom.cor.ServiceException
   *          cuando el intento levanta un error
   */
  public void update(User entity) throws ServiceException;

  /**
   * Elimina un usuario físicamente
   *
   * @param entity Usuario a eliminar
   */
  public void delete(User entity);

  /**
   * Obtiene todos los objetos User
   *
   * @return array de User
   */
  public User[] getAll();

  /**
   * Obtiene todos los objetos User (incluso los borrados logicamente)
   *
   * @return array de User
   */
  public User[] getAllWithDeleted();

  /**
   * Obtiene un usuario por su nombre
   *
   * @param username Nombre del usuario
   * @return Usuario
   * @throws ServiceException Cuando algo sale mal
   */
  public User getUserByUserName(String username);

  /**
   * Obtiene un usuario por su legajo asignado
   *
   * @param legajo Legajo asignado
   * @return El usuario obtenido
   * @throws ServiceException cuando algo sale mal
   */
  public User getUserByLegajo(String legajo);

  /**
   * Resetea el password de un usuario
   *
   * @param userId El usuario
   * @return El password
   * @throws ServiceException Error al resetear el password
   */
  public String resetPassword(Long userId) throws ServiceException;

  /**
   * Agrega un rol
   * @param roleId Id de rol
   * @param userId Id de usuario
   * @throws ServiceException Cuando hay un error
   */
  public void addRole(Long userId, Long roleId) throws ServiceException;

  /**
   * Elimina el rol
   * @param userId Id de usuario
   * @param roleId Id de rol
   * @throws ServiceException Cuando hay un error
   */
  public void deleteRole(Long userId, Long roleId) throws ServiceException;
  
  public List<User> getUser(Long roleId, Long agencyId);
  
}