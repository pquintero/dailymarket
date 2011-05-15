package ar.com.tsoluciones.arcom.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import ar.com.tsoluciones.arcom.system.SystemManager;
import ar.com.tsoluciones.util.CommandLine;

/**
 * Exporta los esquemas de la base 911 y de la de auditoría.
 */
public class SchemaExporter extends CommandLine {

    public static void main(String args[]) {
        new SchemaExporter().run(args);
    }

    @Override
    protected void configureLogger(String args[]) {
        ps.put("log4j.category.ar.com.tsoluciones.arcom.hibernate.SchemaExporter", "DEBUG, stdout");
        super.configureLogger(args);
    }

    @Override
    protected void execute(String args[]) {
        ps.setProperty("log4j.org.hibernate.tool.hbm2ddl.SchemaExport", "INFO, stdout");
        export(SystemManager.getConfiguration(), "schema-911.sql");
    }

    private void export(Configuration configuration, String filename) {
        SchemaExport export = new SchemaExport(configuration);
        export.setOutputFile(filename);
        export.setDelimiter(";");
        export.execute(true, false, false, true);
    }
}
