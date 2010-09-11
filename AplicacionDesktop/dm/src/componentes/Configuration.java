package componentes;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Clase que contiene los valores de configuracion del sistema.
 * Utiliza el archivo client_config.properties como fuente.
 *
 * @author POLETTII, aclocchiatti
 * @version 1.0, 07/04/2006
 * @see
 */
public class Configuration {
	private static Configuration instance = null;

	private String telefrontDispatcher;
	private String gisBaseURL;
	private String gisInstanceCode;
	private String JTapiPeer;
	private int xmlReadTimeout;
	private int xmlConnectionTimeout;
	private int fileReadTimeout;
	private int ctiReconnect; //solo Siemens
	
    private Configuration() {
		Properties prop = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("client_config.properties");
		if (inputStream == null)
			logInitializatonError(new Exception("No se encuentra el archivo de configuracion client_config.properties"));

		try {
			prop.load(inputStream);

			this.telefrontDispatcher = prop.getProperty("telefront.dispatcher");
			this.gisBaseURL = prop.getProperty("gis.base.url");
			this.gisInstanceCode = prop.getProperty("gis.instance.code");
			this.JTapiPeer = prop.getProperty("cti.jtapiPeer");
			this.xmlConnectionTimeout = Integer.parseInt(prop.getProperty("telefront.xml.connection.timeout"));
			this.xmlReadTimeout = Integer.parseInt(prop.getProperty("telefront.xml.read.timeout"));
			this.fileReadTimeout = Integer.parseInt(prop.getProperty("telefront.file.read.timeout"));
			this.ctiReconnect = Integer.parseInt(prop.getProperty("siemens.reconnect"));

        } catch (Exception e) {
			logInitializatonError(e);
			e.printStackTrace();
		}
	}

	public static synchronized Configuration getInstance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}

	private static void logInitializatonError(Exception e) {
		JOptionPane.showMessageDialog(null, "Error obteniendo datos de configuracion: " + e.getMessage());
		System.out.println(e);
	}

	public String getGisBaseURL() {
		return gisBaseURL;
	}

	public String getTelefrontDispatcher() {
		return telefrontDispatcher;
	}

	public String getGisInstanceCode() {
		return gisInstanceCode;
	}

	public String getJTapiPeer() {
		return JTapiPeer;
	}
	
	public int getCtiReconnect() {
		return ctiReconnect;
	}

	public File getTempDir() {
		String tempdir;
		if (System.getProperty("user.home") != null)
			tempdir = System.getProperty("user.home") + File.separator + "F911" + File.separator + "temp";
		else
			tempdir = "c:" + File.separator + "F911" + File.separator + "temp";

		File file = new File(tempdir);
		file.mkdirs();

		return file;
	}

    public int getFileReadTimeout() {
		return fileReadTimeout;
	}

	public int getXmlConnectionTimeout() {
		return xmlConnectionTimeout;
	}

	public int getXmlReadTimeout() {
		return xmlReadTimeout;
	}
	
}