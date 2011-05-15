package ar.com.tsoluciones.arcom.enterprise.services.proxyinterface;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

import ar.com.tsoluciones.arcom.enterprise.DocType;

/**
 * <p>Establece la funcionalidad a implementar para la administracion del entity DocType</p>
 *
 * @author despada
 * @version 1.0, Dec 20, 2004, 9:23:17 AM
 * @see ar.com.tsoluciones.arcom.enterprise.DocType
 */
public interface DocTypeServiceInterface
{

  /**
   * Obtiene un DocType
   *
   * @param id id del DocType
   * @return objeto DocType
   */
  public DocType get(Long id);
  
  /**
   * Obtiene un DocType
   *
   * @param name name del DocType
   * @return objeto DocType
   */
  public DocType getByName(String name);

  /**
   * Graba un objeto DocType
   *
   * @param entity Objeto entity
   */
  public void save(DocType entity);

  /**
   * Actualiza un objeto DocType
   *
   * @param entity Objeto entity a actualizar
   */
  public void update(DocType entity);

  /**
   * Obtiene todos los objetos DocType
   *
   * @return array de DocType
   */
  public DocType[] getAll();

  /**
   * Elimina un tipo de documento
   * @param docType tipo de documento
   */
  public void delete(DocType docType);
}