package ar.com.tsoluciones.util;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.logging.TSLogger;
import ar.com.tsoluciones.arcom.system.SystemManager;

/**
 * Clase base para aplicaciones utilitarias que necesiten ejecutarse por línea de comandos.
 * @see ar.com.tsoluciones.arcom.hibernate.SchemaChecker
 * @see ar.com.tsoluciones.arcom.hibernate.SchemaExporter
 */
public abstract class CommandLine {
    protected Properties ps = new Properties();
    public TSLogger logger = Log.getLogger(getClass());

    protected void configureLogger(String args[]) {
        ps.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        ps.setProperty("log4j.appender.stdout.Target", "System.out");
        ps.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
        ps.setProperty("log4j.rootLogger", "INFO, stdout");
        PropertyConfigurator.configure(ps);
    }

    /**
     * Método que debe invocarse para ejecutar el comando.
     */
    public void run(String args[]) {
        configureLogger(args);
        SystemManager.init();
        execute(args);
        System.exit(0);
    }

    /**
     * Método a partir del que se implementa la lógica del comando.
     */
    protected abstract void execute(String args[]);

}
