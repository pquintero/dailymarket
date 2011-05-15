package ar.com.tsoluciones.arcom.enterprise.services;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import ar.com.tsoluciones.arcom.enterprise.DocType;
import ar.com.tsoluciones.arcom.hibernate.HibernateService;

/**
 * Copyright (c) Telefonica Soluciones
 * Todos los derechos reservados
 * <p> Brinda los servicios necesarios para la administración de Companias <p/>
 *
 * @author Gustavo Blanco - gblanco@telefonicab2b.com
 * @version 1.0 - 26/07/2004 14:07:09
 * @see ar.com.tsoluciones.arcom.enterprise.DocType DocType
 * @since 1.0
 */
public class DocTypeService {
    private DocTypeService() {
    	//
    }

    /**
     * <p> Devuelve un Tipo de documento en base al Id recibido </p>
     *
     * @param Long id = id del tipo de documento
     * @return DocType - El tipo de documento correspondiente al ID
     * @author Gustavo Blanco - gblanco@telefonicab2b.com
     * @version 1.0 - 26/07/2004 14:07:09
     */
    public static DocType getDocType(Long id) throws HibernateException {
        return HibernateService.getObject(DocType.class, id, LockMode.READ);
    }
    
    public static DocType getDocTypeByName(String name) {
    	Criterion crit1 = Restrictions.eq("name", name);
		return HibernateService.findOneByCriteria(DocType.class, crit1);
    }

    /**
     * <p> Invoca al metodo que crea o actualiza una nueva instancia de DocType en la base de
     * datos </p>
     *
     * @param DocType doctype = Objeto DocType a ser persistido
     * @author Gustavo Blanco - gblanco@telefonicab2b.com
     * @version 1.0 - 26/07/2004 14:07:09
     */
    public static void saveDocType(DocType doctype) throws HibernateException {

        HibernateService.saveObject(doctype);
    }

    /**
     * <p> Invoca al metodo que actualiza una nueva instancia de DocType en la base de
     * datos </p>
     *
     * @param DocType doctype = Objeto DocType a ser persistido
     * @author Gustavo Blanco - gblanco@telefonicab2b.com
     * @version 1.0 - 26/07/2004 14:07:09
     */
    public static void updateDocType(DocType doctype) throws HibernateException {

        HibernateService.updateObject(doctype);
    }

    /**
     * <p> Invoca al metodo que borra una instancia de DocType en la base de
     * datos </p>
     *
     * @param DocType doctype = Objeto DocType a ser borrado
     * @author Gustavo Blanco - gblanco@telefonicab2b.com
     * @version 1.0 - 26/07/2004 14:07:09
     */
    public static void deleteDocType(DocType doctype) throws HibernateException {

        HibernateService.deleteObject(doctype);
    }


    /**
     * <p> Busca una instancia de DocType por nombre</p>
     *
     * @param name nombre de tipo de doc. por el cual buscar
     * @author Ignacio Poletti
     * @version 1.0 - 27/07/2004
     */
    public static DocType findByName(String name) throws HibernateException {
        return HibernateService.findByFilterUnique(DocType.class, "name", name);
    }

}
