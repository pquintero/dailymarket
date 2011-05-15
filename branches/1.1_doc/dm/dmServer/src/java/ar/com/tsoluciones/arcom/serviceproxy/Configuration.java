package ar.com.tsoluciones.arcom.serviceproxy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jconfig.ConfigurationManager;
import org.jconfig.event.FileListener;
import org.jconfig.event.FileListenerEvent;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.arcom.logging.TSLogger;
import ar.com.tsoluciones.arcom.serviceproxy.aspects.Aspect;
import ar.com.tsoluciones.arcom.serviceproxy.proxys.ServiceProxy;
import ar.com.tsoluciones.util.Cast;

/**
 * Copyright (c) Telefónica Soluciones
 * Todos los derechos reservados.
 */

/**
 * <p>
 * Configuracion del ServiceFactory. Es un singleton que se inicializa en el
 * arranque, y emplea el framework JConfig para tomar la configuración.
 * </p>
 * <p>
 * Para emplearlo, obtenga la instancia del singleton y llame el metodo:
 *
 * <pre>
 * ServiceProxy serviceProxy = Configuration.getInstance().getServiceProxy(serviceObject);
 * </pre>
 *
 * </p>
 *
 * @author despada
 * @version 1.0, Feb 28, 2005, 4:32:36 PM
 * @see ar.com.tsoluciones.arcom.serviceproxy.proxys.BasicServiceProxy
 * @since 1.0.0.0
 */
public class Configuration implements FileListener {
    // Parametros de configuración por defecto
    private final static String CONFIG_FILENAME = "servicefactory";

    // Implementacion de Singleton
    private static Configuration instance;

    // Objetos instanciados por la configuracion
    private ServiceProxy serviceProxy = null;

    private List<Aspect> aspectList = new ArrayList<Aspect>();

    /**
     * Obtiene la instancia del singleton
     *
     * @return Instancia del singleton
     */
    public static synchronized Configuration getInstance() {
        return getInstance(false);
    }

    /**
     * Obtiene la instancia del singleton
     *
     * @param reload
     *            Fuerza recarga de configuracion
     * @return Instancia del singleton
     */
    public static synchronized Configuration getInstance(boolean reload) {
        if (instance == null) {
            // Instanciar configuracion
            instance = new Configuration();
            // Escuchar cambios
            ConfigurationManager.getInstance().addFileListener(CONFIG_FILENAME, instance);
        }

        // Recargar si el usuario lo pidio
        if (reload)
            instance.reload();

        // Devolver objeto
        return instance;
    }

    /**
     * Constructor del archivo de configuracion
     */
    private Configuration() {
        reload();
    }

    /**
     * Constructor privado del singleton
     */
    private void reload() {
        Log.getLogger(this.getClass()).info("Recargando archivo de configuracion " + CONFIG_FILENAME + "_config.xml");

        // Levantar datos
        org.jconfig.Configuration configuration = ConfigurationManager.getConfiguration(CONFIG_FILENAME);

        // General
        String serviceProxyImplementation = configuration.getProperty("proxy",
                        "ar.com.tsoluciones.arcom.serviceproxy.proxys.BasicServiceProxy", "general");
        // Construir el proxy
        this.serviceProxy = ServiceProxyFactory.construct(serviceProxyImplementation);

        // Aspectos, se parsean a mano
        reloadAspects();
    }

    /**
     * Recarga la configuracion de aspectos
     */
    private void reloadAspects() {
        TSLogger log = Log.getLogger(Configuration.class);
        try {
            Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResource(
                            CONFIG_FILENAME + "_config.xml"));
            log.debug("Levantando configuracion: " + document.asXML());

            Node[] nodeArray = Cast.castList(Node.class, document.selectNodes("/properties/category[@name='aspects']/aspect")).toArray(
                            new Node[0]);

            for (int i = 0; i < nodeArray.length; i++) {
                Element element = (Element) nodeArray[i];
                String aspectImplementation = element.attributeValue("implementation");
                String layerNumber = element.attributeValue("layer");

                log.info("Construyendo aspecto: " + aspectImplementation);

                // Instanciar mediante el factory
                Aspect aspect = AspectFactory.construct(layerNumber, aspectImplementation);
                // Agregar a la lista
                aspectList.add(aspect);

                // Tomar patterns
                Element[] elementArray = Cast.castList(Element.class, element.elements("pattern")).toArray(new Element[0]);

                for (int j = 0; j < elementArray.length; j++) {
                    String pattern = elementArray[j].getText();

                    log.info("Configurando aspecto " + aspectImplementation + " con pattern " + pattern);
                    aspect.addPattern(pattern);
                }

                //Levanta anotaciones asociadas.
                Element[] anotaciones = Cast.castList(Element.class, element.elements("annotation")).toArray(new Element[0]);
                for (Element anotacion : anotaciones) {
                    String annotLiteralClass = anotacion.getText();
                    log.info("Configurando aspecto " + aspectImplementation + " con anotación " + annotLiteralClass);
                    aspect.addAnnotation(annotLiteralClass);
                }

                // Tomar parametros
                elementArray = Cast.castList(Element.class, element.elements("parameter")).toArray(new Element[0]);

                for (int j = 0; j < elementArray.length; j++) {
                    String name = elementArray[j].attributeValue("name");
                    String value = elementArray[j].getText();

                    aspect.addConfiguration(name, value);
                }
            }
        } catch (DocumentException e) {
            throw new RuntimeException("Error al intentar parsear subdocumento de aspectos", e);
        }
    }

    /**
     * Retorna la implementacion de proxy pedida ya configurada
     *
     * @return Implementacion de proxy pedida
     */
    public ServiceProxy getServiceProxy(Object serviceObject) {
        Aspect[] aspectArray = aspectList.toArray(new Aspect[0]);

        ServiceProxy serviceProxy = this.serviceProxy.newInstance();
        serviceProxy.setServiceObject(serviceObject);
        serviceProxy.setAspects(aspectArray);

        return serviceProxy;
    }

    public void fileChanged(FileListenerEvent event) {
        Log.getLogger(this.getClass()).info("Alteración de configuración detectada");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.getLogger(this.getClass()).warn(
                            "Cuidado, espera interrumpida mientras se aguardaba para cargar el archivo");
        }

        // Recargar
        Configuration.getInstance(true);
    }

    public List<Aspect> getAspectList() {
        return aspectList;
    }

}