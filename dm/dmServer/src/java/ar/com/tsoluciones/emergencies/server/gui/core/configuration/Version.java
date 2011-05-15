package ar.com.tsoluciones.emergencies.server.gui.core.configuration;

import org.jconfig.event.FileListener;
import org.jconfig.event.FileListenerEvent;
import org.jconfig.ConfigurationManager;
import ar.com.tsoluciones.arcom.logging.Log;

/**
 * Obtiene caracteristicas de la version del producto
 */
public class Version implements FileListener
{
  private static Version instance;
  private final static String CONFIG_FILENAME = "version";

  private String version;
  
  /**
   * Obtiene instancia del singleton
   * @return instancia del singleton
   */
  public static synchronized Version getInstance()
  {
    return getInstance(false);
  }

  /**
   * Obtiene instancia del singleton
   * @param reload Indica si debe ser recargado
   * @return instancia del singleton
   */
  public static synchronized Version getInstance(boolean reload)
  {
    if (instance == null)
    {
      // Instanciar configuracion
      instance = new Version();
      // Escuchar cambios
      ConfigurationManager.getInstance().addFileListener(CONFIG_FILENAME, instance);
    }

    // Recargar si el usuario lo pidio
    if (reload) instance.reload();

    // Devolver objeto
    return instance;
  }

  /**
   * Constructor privado del singleton
   */
  private Version()
  {
    reload();
  }

  /**
   * Obtiene el manager de configuracion
   * @return Manager de configuracion
   */
  private org.jconfig.Configuration getConfigurationManager()
  {
    return ConfigurationManager.getConfiguration(CONFIG_FILENAME);
  }

  /**
   * Recarga el singleton
   */
  private void reload()
  {
    Log.getLogger(this.getClass()).warn("Recargando archivo de configuracion " + CONFIG_FILENAME + "_config.xml");

    // Levantar datos
    org.jconfig.Configuration configuration = getConfigurationManager();

    this.version = configuration.getProperty("version", "no especificada");

    Log.getLogger(this.getClass()).warn("Finalizo carga de archivo de configuracion");
  }

  /**
   * Notificacion de cambio de archivo
   * @param fileListenerEvent Archivo detectado
   */
  public void fileChanged(FileListenerEvent fileListenerEvent)
  {
    reload();
  }

  /**
   * Obtiene la version del producto
   * @return Version del producto
   */
  public String getVersion()
  {
    return version;
  }
}