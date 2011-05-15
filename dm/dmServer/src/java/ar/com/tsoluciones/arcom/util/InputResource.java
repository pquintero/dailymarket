package ar.com.tsoluciones.arcom.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Clase utilizada para obtener recursos en forma de archivos, url o como recursos del
 * classloader.
 *
 * Una vez construida la instancia se obtiene la localización resultante mediante el metodo
 * getUrl().
 *
 *
 * @author Ipoletti
 * @version 1.0, 01/09/2004
 * @see
 */
public class InputResource {

    public static final String URL = "URL";
    public static final String FILE = "File";
    public static final String RESOURCE = "Resource";

    private String location;
    private String type;
    private URL url;
    private long lastModified;

    /**
     * Constructor.
     *
     * @param type tipo de recurso ("URL", "File" o "Resource"), tambien se pueden utilizar las constantes estaticas.
     * @param location ubicacion/nombre del recurso.
     */
    public InputResource(String type, String location) {
        if (URL.equalsIgnoreCase(type)) {
            try {
                url = new URL(location);

                File file = new File(url.getFile());

                if (file.exists()) {
                    lastModified = file.lastModified();
                }
            } catch (MalformedURLException ex) {
            	ex.printStackTrace();
            }
        } else if (FILE.equalsIgnoreCase(type)) {
            try {
                File file = new File(location);
                url = file.toURL();
                lastModified = file.lastModified();
            } catch (MalformedURLException ex) {
            	ex.printStackTrace();
            }
        } else {
            url = Thread.currentThread().getContextClassLoader().getResource(location);
        }

        this.type = type;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
