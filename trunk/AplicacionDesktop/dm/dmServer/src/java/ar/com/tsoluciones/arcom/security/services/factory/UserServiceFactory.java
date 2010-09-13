package ar.com.tsoluciones.arcom.security.services.factory;

import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
import ar.com.tsoluciones.arcom.security.services.implementation.UserService;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Construye una clase que cumple con la interfaz de funcionalidad de usuarios</p>
 *
 * @author despada
 * @version 1.0, Dec 27, 2004, 10:29:49 AM
 * @see ar.com.tsoluciones.arcom.security.User
 * @since 2.0
 */
public class UserServiceFactory extends ServiceFactory
{

  /**
   * Construye el objeto
   * @return Un objeto UserServiceInterface
   */
  @Override
public Object newInstance()
  {
    return this.newServiceInstance(new UserService());
  }
}
