package ar.com.tsoluciones.arcom.security.handlers;

import ar.com.tsoluciones.arcom.logging.Log;

public class SecurityHandlerFactoryImpl extends SecurityHandlerFactory {
    private SecurityHandler handler = null;

    @Override
    public Object newInstance() {
        if (handler == null) {
            String className = handlerClasses.get(defaultHandler);
            Log.security.info("Creating security handler: " + className);
            try {
                    handler = (SecurityHandler)Class.forName(className).newInstance();
                } catch (Exception ex){
                    Log.security.fatal("Instantiation error creating security handler. ",  ex);
                    throw new RuntimeException (ex);
                }
        }
        return this.newServiceInstance(handler);
    }

    @Override
	public SecurityHandler getSecurityHandler() {
        return (SecurityHandler)newInstance();
    }
}
