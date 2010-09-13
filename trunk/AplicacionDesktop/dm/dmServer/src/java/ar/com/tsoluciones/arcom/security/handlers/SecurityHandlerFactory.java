package ar.com.tsoluciones.arcom.security.handlers;

import java.util.Hashtable;

import ar.com.tsoluciones.arcom.serviceproxy.ServiceFactory;

public abstract class SecurityHandlerFactory extends ServiceFactory {
    protected static final Hashtable<String, String> handlerClasses = new Hashtable<String, String>();
    protected static final String defaultHandler = "DB";
    private static SecurityHandlerFactory instance = null;


    public synchronized static SecurityHandlerFactory getInstance() {
        if (instance == null) {
            init();
            String factoryClassName = "ar.com.tsoluciones.arcom.security.handlers.SecurityHandlerFactoryImpl";
            try {
                instance = (SecurityHandlerFactory) Class.forName(factoryClassName).newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    private static void init() {
        handlerClasses.put(defaultHandler, "ar.com.tsoluciones.arcom.security.handlers.SecurityHandlerDatabaseImpl");
    }

    public abstract SecurityHandler getSecurityHandler();
}
