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
    
    private static Map<String, Configuration> dynamicMapConfig = new HashMap<String, Configuration>();
    private static Map<String, SessionFactory> dynamicMapFactory = new HashMap<String, SessionFactory>();

    public static void init(){
        try {
            config = new Configuration();
            config.setNamingStrategy(CustomNamingStrategy.INSTANCE);
            config.configure();
            factory = config.buildSessionFactory();
            
            Map<String, DatasourceConfig> map = 
            	ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration.getInstance().getDynamicDatasources();
            
            Set<String> dskeys = map.keySet();
            for (String name : dskeys) {
            	DatasourceConfig dconfig = map.get(name);
            	
            	if (dconfig.isDefaultDatasource()) {
            		dynamicMapConfig.put(dconfig.getName(), SystemManager.config);
                    dynamicMapFactory.put(dconfig.getName(), SystemManager.factory);
                    continue;
            	}
            	
            	Configuration dynConfiguration = new Configuration();
            	dynConfiguration.setNamingStrategy(CustomNamingStrategy.INSTANCE);
            	dynConfiguration.configure();
            
            	dynConfiguration.setProperty("hibernate.connection.url", dconfig.getUrl());
            	dynConfiguration.setProperty("hibernate.connection.username", dconfig.getUsername());
            	dynConfiguration.setProperty("hibernate.connection.password", dconfig.getPassword());
            	
            	dynConfiguration.setProperty("hibernate.c3p0.max_size", dconfig.getPoolMaxSize().toString());
            	dynConfiguration.setProperty("hibernate.c3p0.min_size", dconfig.getPoolMinSize().toString());
            	dynConfiguration.setProperty("hibernate.c3p0.idleTestPeriod", dconfig.getIdleTestPeriod().toString());           	
            	
            	dynConfiguration.setNamingStrategy(CustomNamingStrategy.INSTANCE);
            	
            	dynamicMapConfig.put(name, dynConfiguration);
            	dynamicMapFactory.put(name, dynConfiguration.buildSessionFactory());
            	
            }
            
            
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

    public static Configuration getConfigurationDynamic(String dynamicDSName) {
    	return dynamicMapConfig.get(dynamicDSName);
    }
    
    public static SessionFactory getSessionFactoryDynamic(String dynamicDSName) {
    	return dynamicMapFactory.get(dynamicDSName);
    }
    
    public static Map<String,Configuration> getConfigurationDynamic() {
    	return dynamicMapConfig;
    }
    
    public static Map<String,SessionFactory> getSessionFactoryDynamic() {
    	return dynamicMapFactory;
    }

    public static void addConfigure(URL url) throws HibernateException {
        config = config.configure(url);
        factory = config.buildSessionFactory();
    }
}
