package ar.com.tsoluciones.arcom.security.services;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;

import ar.com.tsoluciones.arcom.hibernate.HibernateService;
import ar.com.tsoluciones.arcom.security.Group;

/**
 * Copyright (c) Telefonica Soluciones Todos los derechos reservados <p/>Class
 * description....
 *
 * @author Jporporatto - jporporatto@artech-consulting.com
 * @version 1.0 - May 24, 2004
 * @see @since 0.2,
 */
public class GroupService {

	/**
     * <p>
     * Devuelve un grupo en base al Id recibido
     * </p>
     *
     */
    public static Group getGroup(Long id) throws HibernateException {
        return HibernateService.getObject(Group.class, id,
                LockMode.NONE);
    }

    /**
     * <p>
     * Actualiza o crea una nueva instancia de Grupo en la base de datos
     * </p>
     *
     */
    public static void saveGroup(Group group) throws HibernateException {
        HibernateService.saveObject(group);
    }

    /**
     *
     * @param group
     * @throws HibernateException
     */
    public static void deleteGroup(Group group) throws HibernateException {
        HibernateService.deleteObject(group);
    }

    /**
     * <p>
     * Actualiza o crea una nueva instancia de grupo en la base de datos
     * </p>
     */
    public static void updateGroup(Group group) throws HibernateException {
        HibernateService.updateObject(group);
    }

    /**
     * <p>
     * Busca una instancia de Group por nombre
     * </p>
     *
     * @param name
     *            nombre del group por el cual buscar
     * @return group o null
     */
    public static Group findByName(String name) throws HibernateException {
        return HibernateService.findByFilterUnique(Group.class, "name",
                name);
    }
}
