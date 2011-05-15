/*
 * TSLogger.java
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

package ar.com.tsoluciones.arcom.logging;

import java.util.Hashtable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * <p> Provee estaticamente categorias standard de logeo, en 5 niveles. Se configura
 * via un archivo de configuracion, dependiente de la implementacion. Actualmente
 * la implementacion es LOG4J
 * </p>
 * <p/>
 * <p> Para logear:
 * <code>
 * Log.categoria.debug("msg....");
 * o
 * Log.categoria.debug("msg....", exception);
 * </code>
 * </p>
 *
 * @author Andres Herrera - aherrera@artech-consulting.com
 * @version 1.0, 16/03/2004
 * @see ar.com.tsoluciones.arcom.logging.TSLogger TSLogger>
 * @since 0.1
 */
public class TSLogger {
    private static Hashtable<String, TSLogger> instances = new Hashtable<String, TSLogger>();
    private Logger implementation = null;

    /**
     *
     * @param name
     * @return
     */
    public static TSLogger getLogger(String name) {
        TSLogger logger = instances.get(name);
        if (logger == null) {
            logger = new TSLogger(name);
            instances.put(name, logger);
        }

        return logger;
    }

    public static TSLogger getLogger(Class<?> name) {
        return getLogger(name.toString());
    }


    private TSLogger(String name) {
        Logger logImpl = Logger.getLogger(name);
        this.implementation = logImpl;
    }

    public void debug(Object msg) {
        debug(msg, null);
    }

    public void info(Object msg) {
        info(msg, null);
    }

    public void warn(Object msg) {
        warn(msg, null);
    }

    public void error(Object msg) {
        error(msg, null);
    }

    public void fatal(Object msg) {
        fatal(msg, null);
    }

    public void debug(Object msg, Throwable t) {
        logg(Level.DEBUG, msg, t);
    }

    public void info(Object msg, Throwable t) {
        logg(Level.INFO, msg, t);
    }

    public void warn(Object msg, Throwable t) {
        logg(Level.WARN, msg, t);
    }

    public void error(Object msg, Throwable t) {
        logg(Level.ERROR, msg, t);
    }

    public void fatal(Object msg, Throwable t) {
        logg(Level.FATAL, msg, t);
    }

    private void logg(Priority priority, Object msg, Throwable t) {
        if (t == null)
            implementation.log(priority, msg);
        else
            implementation.log(priority, msg, t);
    }
}