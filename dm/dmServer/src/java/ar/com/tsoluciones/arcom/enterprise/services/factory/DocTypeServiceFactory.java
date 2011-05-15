package ar.com.tsoluciones.arcom.enterprise.services.factory;

import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;
import ar.com.tsoluciones.arcom.enterprise.services.implementation.DocTypeService;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>Factory que construye un objeto que suple la funcionalidad de admin de DocType</p>
 *
 * @author despada
 * @version 1.0, Dec 20, 2004, 9:24:01 AM
 * @see 1.1
 * @since DocType, DocTypeServiceInterface
 */
public class DocTypeServiceFactory extends ServiceFactory
{
  /**
   * Construye un objeto DocTypeService
   * @return DocTypeService
   */
  @Override
public Object newInstance()
  {
    return this.newServiceInstance(new DocTypeService());
  }
}
