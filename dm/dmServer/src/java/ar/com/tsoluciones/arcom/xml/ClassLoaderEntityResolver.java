package ar.com.tsoluciones.arcom.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import ar.com.tsoluciones.arcom.logging.Log;

/**
 * Utilizada para obtener los DTD de los XML a partir del raiz del classloader donde se
 * este ejecutando.
 *
 * Debera especificarse en el XMLParser si se desea utilizar el mismo.
 * En el XML se deberá ingresar directamente el nombre del archivo DTD sin anteponer ningun caracter
 *
 * @author Ipoletti
 * @version 1.0, 05/10/2004
 * @see
 */
public class ClassLoaderEntityResolver implements EntityResolver {
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String file = systemId;

        if (systemId.startsWith("/"))
            file = file.substring(1);

        Log.xml.info("Resolviendo " + systemId + " como " + loader.getResource(file));
        InputStream in = loader.getResourceAsStream(file);
        return new InputSource(in);
    }
}
