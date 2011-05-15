package ar.com.tsoluciones.arcom.enterprise.services.implementation;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import ar.com.tsoluciones.arcom.cor.InternalErrorException;
import ar.com.tsoluciones.arcom.enterprise.DocType;
import ar.com.tsoluciones.arcom.enterprise.services.proxyinterface.DocTypeServiceInterface;
import ar.com.tsoluciones.arcom.hibernate.SessionContext;
import ar.com.tsoluciones.util.Cast;

/**
 * <p>Administra el entity DocType</p>
 *
 * @author despada
 * @version 1.0, Dec 20, 2004, 9:30:06 AM
 * @see ar.com.tsoluciones.arcom.enterprise.DocType
 */
public class DocTypeService implements DocTypeServiceInterface {

    /**
     * Obtiene un DocType
     *
     * @param id id del DocType
     * @return objeto DocType
     */
    public DocType get(Long id) {
        try {
            return ar.com.tsoluciones.arcom.enterprise.services.DocTypeService.getDocType(id);
        }
        catch (HibernateException e) {
            throw new InternalErrorException("Imposible buscar tipo de documento", e, id);
        }
    }

    /**
     * Graba un objeto DocType
     *
     * @param entity Objeto entity
     */
    public void save(DocType entity) {
        try {
            ar.com.tsoluciones.arcom.enterprise.services.DocTypeService.saveDocType(entity);
        }
        catch (HibernateException e) {
            throw new InternalErrorException("Imposible salvar tipo de documento", e, entity);
        }
    }

    /**
     * Actualiza un objeto DocType
     *
     * @param entity Objeto entity a actualizar
     */
    public void update(DocType entity) {
        try {
            ar.com.tsoluciones.arcom.enterprise.services.DocTypeService.updateDocType(entity);
        }
        catch (HibernateException e) {
            throw new InternalErrorException("Imposible salvar tipo de documento", e, entity);
        }
    }
    
    public DocType getByName(String name) {
    	try {
            return ar.com.tsoluciones.arcom.enterprise.services.DocTypeService.getDocTypeByName(name);
        }
        catch (HibernateException e) {
            throw new InternalErrorException("Imposible buscar tipo de documento", e, name);
        }
    }

    /**
     * Obtiene todos los objetos DocType
     *
     * @return array de DocType
     */
    public DocType[] getAll() {
        try {
            Session session = SessionContext.currentSession();
            Criteria criteria = session.createCriteria(DocType.class);
            criteria.setCacheable(true);
            criteria.setCacheRegion("DocType.getAll");
            // Hacer distinct
            Set<DocType> set = new LinkedHashSet<DocType>(Cast.castList(DocType.class, criteria.list()));

            return set.toArray(new DocType[0]);
        } catch (HibernateException e) {
            throw new InternalErrorException("Error al intentar otener todos los objetos DocType", e);
        }
    }

    /**
     * Elimina un tipo de documento
     * @param docType tipo de documento
     */
    public void delete(DocType docType) {
        try {
            ar.com.tsoluciones.arcom.enterprise.services.DocTypeService.deleteDocType(docType);
        }
        catch (HibernateException e) {
            throw new InternalErrorException("Imposible eliminar tipo de documento", e, docType);
        }
    }

}
