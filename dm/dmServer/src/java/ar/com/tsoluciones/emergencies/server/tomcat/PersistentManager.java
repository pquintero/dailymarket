package ar.com.tsoluciones.emergencies.server.tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.catalina.Container;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.session.StandardSession;
import org.apache.catalina.util.CustomObjectInputStream;

import ar.com.tsoluciones.util.Cast;


/**
 * Clase que extiende el manager estandar de tomcat.
 * Al iniciarse el tomcat levantara las sesiones persistidas (directorio %tomcat%\sessions) y las hara validas sin
 * importar la fecha real de ultimo acceso ya que solo se persisten al iniciar la sesión y no se actualizan con las
 * fechas de acceso.
 *
 * @author POLETTII
 */

public class PersistentManager
        extends StandardManager {

    private static File persistedSessionsDir;

    public PersistentManager() {
        log.info("Instanciando " + this.getClass().getName());

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("ar/com/tsoluciones/emergencies/server/tomcat/persistent.properties");
        log.info("is="+is);
        if (is != null) {
            Properties prop = new Properties();
            try {
                prop.load(is);
                String path = prop.getProperty("persistent.path");
                if (path != null && path.length() > 0) {
                    persistedSessionsDir = new File(path);
                }
                is.close();
            } catch (IOException e) {
                log.error("Error levantando archivo de propiedades de sesiones en /ar/com/tsoluciones/emergencies/server/tomcat/persistent.properties", e);
            }
        }
    }

    @Override
	protected StandardSession getNewSession() {
        return new PersistentSession(this);
    }

    @Override
	protected void doLoad() throws ClassNotFoundException, IOException {

        try {
            File path = getPersistedSessionsDir();
            log.info("Cargando sesiones persistidas desde " + path);

            //super.doLoad();
            sessions.clear();

            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                log.info("Cargando " + file.getName());

                try {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream objectIn = new CustomObjectInputStream(fis, this.container.getLoader().getClassLoader());

                    PersistentSession session = (PersistentSession) getNewSession();
                    session.readObjectData(objectIn);
                    session.setManager(this);
                    Cast.castMap(String.class, PersistentSession.class, sessions).put(session.getId(), session);
                    session.activate();
                    session.endAccess();

                    // La sesión pudo haber expirado, la recreo.
                    session.doSessionValid();
                } catch (Exception e) {
                    log.error("Error cargando session " + file.getName() + ", se continuara la carga", e);
                }
            }
        } catch (Exception e) {
            log.error("Se produjo un error cargando sesiones anteriores", e);
        }
    }

    public static File getPersistedSessionsDir() {
        return persistedSessionsDir;
    }

    @Override
	public void setContainer(Container container) {
        try {
            super.setContainer(container);

            if (persistedSessionsDir == null) {
                URL url = Thread.currentThread().getContextClassLoader().getResource("./");
                String path = url.toString().substring(5);

                persistedSessionsDir = new File(path, "../../sessions");
            }

            persistedSessionsDir.mkdirs();

            log.info("Directorio de sesiones persistidas: " + persistedSessionsDir.toString());
        } catch (Exception e) {
            log.error("Se produjo un error obteniendo directorio de sesiones persistentes", e);
        }
    }

}
