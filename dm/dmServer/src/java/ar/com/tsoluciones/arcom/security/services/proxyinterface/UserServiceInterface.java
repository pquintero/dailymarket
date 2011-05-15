package ar.com.tsoluciones.arcom.security.services.proxyinterface;


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
   * Actualiza un usuario físicamente
   *
   * @param entity Usuario a eliminar
   */
  public void update(User entity);

  
}