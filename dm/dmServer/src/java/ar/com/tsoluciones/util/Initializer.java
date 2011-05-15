package ar.com.tsoluciones.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ar.com.tsoluciones.arcom.hibernate.SchemaChecker;
import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.logging.TSLogger;
import ar.com.tsoluciones.arcom.system.SystemManager;
import ar.com.tsoluciones.emergencies.server.gui.core.configuration.Configuration;

/**
 * Listener que se ejecuta al iniciar y concluir el contexto de la aplicación
 */
public class Initializer implements ServletContextListener {

    private TSLogger logger = Log.getLogger(Initializer.class);

    public void contextDestroyed(ServletContextEvent arg0) {

        logger.warn("Destruyendo contexto de la aplicación F911");
    }

    public void contextInitialized(ServletContextEvent arg0) {
        logger.warn("Inicializando contexto de la aplicación F911");
        logger.info("================================ Comenzando configuración del Sistema");

        try {
            // Cargar Entidades persistidas por Hibernate.
            SystemManager.init();

            // Configurar Emergencies
            logger.info("================================ Configurando Emergencies");
            Configuration.getInstance();

            logger.info("================================ Sistema configurado correctamente");
        } catch (Exception e) {
            logger.error("================================ ERROR al configurar Sistema F911", e);
        }

        SchemaChecker checker = new SchemaChecker();
        checker.check();

      
    }

	
	
}
