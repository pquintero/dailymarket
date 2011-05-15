package ar.com.tsoluciones.arcom.hibernate;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.Mapping;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.ColumnMetadata;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.TableMetadata;

import ar.com.tsoluciones.arcom.system.SystemManager;
import ar.com.tsoluciones.util.CommandLine;

/**
 * <p>
 * Valida los mapeos de hibernate contra la estructura de la base de datos.
 * </p>
 * <p>
 * Verifica lo siguiente:
 * <li>Conectividad</li>
 * <li>Existencia de tablas mapeadas</li>
 * <li>Existencia de columnas mapeadas</li>
 * <li>Coincidencia de tipo de datos entre la columna mapeada</li>
 * <li>Coincidencia de la longitud de los campos varchar mapeados</li>
 * </p>
 */
public class SchemaChecker extends CommandLine {
    public void check() {
        logger.info("Haciendo la validación de los esquemas mapeados");
        Par pars[] = { new Par(SystemManager.getConfiguration(), SystemManager.getSessionFactory())};
        for (Par par: pars) {
            MiSchemaValidator validator = new MiSchemaValidator(par.configuration, par.sessionFactory);
            validator.validate();
        }
        logger.info("Terminó la validación de los esquemas");
    }

    public class MiSchemaValidator {
        private Configuration config;
        private SessionFactory sessionFactory;

        private final static String PROPERTY_URL = "hibernate.connection.url";

        private Dialect dialect;

        private Properties properties;

        public MiSchemaValidator(Configuration config, SessionFactory sessionFactory) {
            this.config = config;
            this.sessionFactory = sessionFactory;
            dialect = Dialect.getDialect(config.getProperties());
            properties = new Properties();
            properties.putAll(dialect.getDefaultProperties());
            properties.putAll(config.getProperties());
        }

        @SuppressWarnings("unchecked")
        public void validate() {
            Connection conn = null;
            Session session = null;
            try {
                session = sessionFactory.openSession();
                session.beginTransaction();
                conn = session.connection();
                DatabaseMetadata meta = new DatabaseMetadata(conn, dialect);
                Mapping mapping = config.buildMapping();

                String defaultCatalog = properties.getProperty(Environment.DEFAULT_CATALOG);
                String defaultSchema = properties.getProperty(Environment.DEFAULT_SCHEMA);

                Iterator<Table> iter = config.getTableMappings();
                while (iter.hasNext()) {
                    Table table = iter.next();
                    logger.info("Validando " + table.getName());
                    if (table.isPhysicalTable()) {
                        TableMetadata tableInfo = meta.getTableMetadata(table.getName(),
                                        (table.getSchema() == null) ? defaultSchema : table.getSchema(), (table
                                                        .getCatalog() == null) ? defaultCatalog : table.getCatalog(),
                                        table.isQuoted());
                        if (tableInfo == null) {
                            logger.error(String.format("La tabla %s no existe", table.getName()));
                        } else {
                            validateColumns(table, tableInfo, dialect, mapping);
                        }
                    }
                }
                session.getTransaction().commit();
            } catch (Throwable t) {
                logger.error("Error al hacer el chequeo del schema " + config.getProperty(PROPERTY_URL), t);
                try {
                    if (session != null && session.getTransaction().isActive())
                        session.getTransaction().rollback();
                } catch (HibernateException e) {
                    logger.error("No se pudo hacer rollback de la transacción", e);
                }
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void validateColumns(Table table, TableMetadata tableInfo, Dialect dialect, Mapping mapping) {
        Iterator<Column> iter = table.getColumnIterator();
        while (iter.hasNext()) {
            try {
                Column col = iter.next();
                ColumnMetadata columnInfo = tableInfo.getColumnMetadata(col.getName());

                if (columnInfo == null) {
                    throw new HibernateException("Columna inexistente: " + col.getName() + " en la tabla "
                                    + Table.qualify(tableInfo.getCatalog(), tableInfo.getSchema(), tableInfo.getName()));
                }
                final boolean typesMatch = col.getSqlType(dialect, mapping).startsWith(
                                columnInfo.getTypeName().toLowerCase())
                                || columnInfo.getTypeCode() == col.getSqlTypeCode(mapping);
                if (!typesMatch) {
                    throw new HibernateException(String.format(
                                    "El tipo de la columna %s es inválido. Se esperaba %s y se encontró %s", col
                                                    .getName(), columnInfo.getTypeName(), col.getSqlType(dialect,
                                                    mapping)));
                }
                if (columnInfo.getTypeName().equals("varchar")) {
                    if (col.getLength() != columnInfo.getColumnSize())
                        throw new HibernateException(String.format(
                                        "Longitud de campo distinta para la columna %s. Mapeo: %s, Base: %s", col
                                                        .getName(), col.getLength(), columnInfo.getColumnSize()));
                }

            } catch (Throwable t) {
                logger.warn(String.format("Error al validar columna en la tabla %s. Msg: %s", tableInfo.getName(), t
                                .getMessage()));
            }

        }
    }

    public static class Par {
        Configuration configuration;
        SessionFactory sessionFactory;

        public Par(Configuration configuration, SessionFactory sessionFactory) {
            this.configuration = configuration;
            this.sessionFactory = sessionFactory;
        }
    }

    @Override
    protected void configureLogger(String args[]) {
        ps.put("log4j.category.ar.com.tsoluciones.arcom.hibernate.SchemaChecker", "DEBUG, stdout");
        super.configureLogger(args);
    }

    @Override
    protected void execute(String args[]) {
        check();
    }

    public static void main(String[] args) {
        new SchemaChecker().run(args);
    }
}
