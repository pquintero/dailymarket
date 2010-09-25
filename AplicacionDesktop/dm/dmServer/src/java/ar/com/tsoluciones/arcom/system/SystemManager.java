package ar.com.tsoluciones.arcom.system;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ar.com.tsoluciones.arcom.hibernate.CustomNamingStrategy;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.emergencies.server.gui.core.configuration.DatasourceConfig;

/**
 * Copyright (c) Tito Company.
 * Todos los derechos reservados
 */

/**
 * <p>
 * Clase utilitaria con referencia a las configuraciones y a las session
 * factories de Hibernate que usa la aplicación.
 * </p>
 *
 * @author aherrera@artech-consulting.com, aclocchiatti@artech-consulting.com
 * @version 1.0 - 10/05/2004 10:29:46
 * @see
 * @since 0.2,
 */
public class SystemManager {

    private static SessionFactory factory;
    private static Configuration config;

    public static void init(){
        try {
            config = new Configuration();
            config.setNamingStrategy(CustomNamingStrategy.INSTANCE);
            config.configure();
            factory = config.buildSessionFactory();
            
        } catch (HibernateException e) {
            Log.general.fatal("Fatal error initializing TSoluciones framework", e);
            throw new RuntimeException("Fatal error initializing TSoluciones framework", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Configuration getConfiguration() {
        return config;
    }

    public static void addConfigure(URL url) throws HibernateException {
        config = config.configure(url);
        factory = config.buildSessionFactory();
    }
}
