package ar.com.tsoluciones.emergencies.server.hibernate;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.ConnectionProviderFactory;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.ReflectHelper;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * A connection provider that uses a C3P0 connection pool. Hibernate will use this by
 * default if the <tt>hibernate.c3p0.*</tt> properties are set.
 * @see ConnectionProvider
 * @author various people
 */
public class C3P0ConnectionProvider implements ConnectionProvider {

	private ComboPooledDataSource ds;
	private Integer isolation;
	private static Vector<ComboPooledDataSource> dataSources = new Vector<ComboPooledDataSource>();

	private static final Log log = LogFactory.getLog(C3P0ConnectionProvider.class);

	public Connection getConnection() throws SQLException {
		final Connection c = ds.getConnection();
		if (isolation != null)
			c.setTransactionIsolation(isolation.intValue());
		if (c.getAutoCommit())
			c.setAutoCommit(false);
		return c;
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	public void configure(Properties props) throws HibernateException {
		String jdbcDriverClass = props.getProperty(Environment.DRIVER);
		String jdbcUrl = props.getProperty(Environment.URL);
		Properties connectionProps = ConnectionProviderFactory.getConnectionProperties(props);

		log.info("C3P0 using driver: " + jdbcDriverClass + " at URL: " + jdbcUrl);
		log.info("Connection properties: " + connectionProps);

		if (jdbcDriverClass == null) {
			log.warn("No JDBC Driver class was specified by property " + Environment.DRIVER);
		} else {
			try {
				Class.forName(jdbcDriverClass);
			} catch (ClassNotFoundException cnfe) {
				try {
					ReflectHelper.classForName(jdbcDriverClass);
				} catch (ClassNotFoundException e) {
					String msg = "JDBC Driver class not found: " + jdbcDriverClass;
					log.fatal(msg, e);
					throw new HibernateException(msg, e);
				}
			}

		}

		try {

			int minPoolSize = PropertiesHelper.getInt(Environment.C3P0_MIN_SIZE, props, 1);
			int maxPoolSize = PropertiesHelper.getInt(Environment.C3P0_MAX_SIZE, props, 100);
			int maxIdleTime = PropertiesHelper.getInt(Environment.C3P0_TIMEOUT, props, 0);
			int maxStatements = PropertiesHelper.getInt(Environment.C3P0_MAX_STATEMENTS, props, 0);
			int acquireIncrement = PropertiesHelper.getInt(Environment.C3P0_ACQUIRE_INCREMENT, props, 1);
			int idleTestPeriod = PropertiesHelper.getInt(Environment.C3P0_IDLE_TEST_PERIOD, props, 0);

			ComboPooledDataSource pooledDS = new ComboPooledDataSource();
			pooledDS.setMinPoolSize(minPoolSize);
			pooledDS.setMaxPoolSize(maxPoolSize);
			pooledDS.setAcquireIncrement(acquireIncrement);
			pooledDS.setMaxIdleTime(maxIdleTime);
			pooledDS.setMaxStatements(maxStatements);
			pooledDS.setIdleConnectionTestPeriod(idleTestPeriod);

			pooledDS.setJdbcUrl(jdbcUrl);
			pooledDS.setProperties(connectionProps);
			ds = pooledDS;

			// Lo agrego a una lista de datasources estatica asi la puedo acceder desde fuera de la clase			
			if (!dataSources.contains(ds))
				dataSources.addElement(ds);
		} catch (Exception e) {
			log.fatal("could not instantiate C3P0 connection pool", e);
			throw new HibernateException("Could not instantiate C3P0 connection pool", e);
		}

		String i = props.getProperty(Environment.ISOLATION);
		if (i == null) {
			isolation = null;
		} else {
			isolation = new Integer(i);
			log.info("JDBC isolation level: " + Environment.isolationLevelToString(isolation.intValue()));
		}

	}

	public void close() {
		try {
			DataSources.destroy(ds);

			// Quito el datasources de la lista estatica
			dataSources.remove(ds);
		} catch (SQLException sqle) {
			log.warn("could not destroy C3P0 connection pool", sqle);
		}
	}

	public static void printReport(PrintStream out) {
		for (int i = 0; i < dataSources.size(); i++) {
			DataSource dataSource = dataSources.elementAt(i);
			try {
				if (dataSource instanceof ComboPooledDataSource) {
					ComboPooledDataSource pds = (ComboPooledDataSource) dataSource;
					out.println("------- URL: " + pds.getConnection().getMetaData().getURL() + " ----------");
					out.println("num_connections: " + pds.getNumConnections());
					out.println("num_busy_connections: " + pds.getNumBusyConnections());
					out.println("num_idle_connections: " + pds.getNumIdleConnections());
				}
			} catch (SQLException e) {
				out.print("No se pudo obtener el reporte de conexiones");
				log.error("Error obteniendo reporte de conexiones en pool ", e);
			}
		}
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}
}
